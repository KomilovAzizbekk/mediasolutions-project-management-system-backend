package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.component.MessageService;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.enums.RoleName;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.SignInDTO;
import uz.prod.backcrm.payload.SignUpDTO;
import uz.prod.backcrm.payload.TokenDTO;
import uz.prod.backcrm.repository.RoleRepository;
import uz.prod.backcrm.repository.UserRepository;
import uz.prod.backcrm.secret.JwtProvider;
import uz.prod.backcrm.service.abs.AuthService;
import uz.prod.backcrm.utills.constants.Message;
import uz.prod.backcrm.utills.constants.Rest;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public ApiResult<TokenDTO> signIn(SignInDTO signInDTO) {
        usernameNotFoundThrow(signInDTO.getUsername());

        User currentUser = checkUsernameAndPasswordAndEtcAndSetAuthenticationOrThrow(signInDTO.getUsername(), signInDTO.getPassword());
        TokenDTO tokenDTO = generateToken(currentUser);
        return ApiResult.success(tokenDTO);
    }

    @Override
    public ApiResult<TokenDTO> signUp(SignUpDTO signUpDTO) {
        //PASSWORD VA PRE-PASSWORLARNI BIR XIL EKANLIGINI TEKSHIRADI
        checkPasswordEqualityIfErrorThrow(signUpDTO.getPassword(), signUpDTO.getPrePassword());

        //USHBU TELEFON RAQAM VA USERNAME OLDIN RO'YXATDAN O'TGAN YOKI O'TMAGAN
        phoneNumberAndUsernameIfExistsThrow(signUpDTO.getPhoneNumber(), signUpDTO.getUsername());

        User user = saveUser(signUpDTO);

        String token = jwtProvider.generateAccessToken(user, new Timestamp(System.currentTimeMillis()));
        TokenDTO tokenDTO = TokenDTO.builder()
                .accessToken(token)
                .tokenType(Rest.TYPE_TOKEN)
                .build();
        return ApiResult.success(tokenDTO);
    }

    @Override
    public TokenDTO generateToken(User user) {
        //HOZIRGI VAQT
        Timestamp issuedAt = new Timestamp(System.currentTimeMillis());

        //USER ORQALI TOKEN OLYABMIZ
        String token = jwtProvider.generateAccessToken(user, issuedAt);

        //TOKEN NI DTO QILIB BERYABMIZ
        return TokenDTO.builder()
                .tokenType(Rest.TYPE_TOKEN)
                .accessToken(token)
                .build();
    }

    @Override
    public User checkUsernameAndPasswordAndEtcAndSetAuthenticationOrThrow(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return (User) authentication.getPrincipal();
        } catch (DisabledException | LockedException | CredentialsExpiredException disabledException) {
            throw RestException.restThrow(Message.USER_NOT_FOUND_OR_DISABLED, Rest.USER_NOT_ACTIVE, HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException | UsernameNotFoundException badCredentialsException) {
            throw RestException.restThrow(Message.LOGIN_OR_PASSWORD_ERROR, Rest.INCORRECT_USERNAME_OR_PASSWORD, HttpStatus.FORBIDDEN);
        }
    }

    private void usernameNotFoundThrow(String username){
        if (userRepository.findByUsername(username).isEmpty())
            throw RestException.restThrow(MessageService.getMessage(Message.USERNAME_NOT_FOUND), HttpStatus.FORBIDDEN);
    }

    public void checkPasswordEqualityIfErrorThrow(String password, String prePassword) {
        if (Objects.nonNull(password) && !Objects.equals(password,prePassword)){
            throw RestException.restThrow(Message.PASSWORDS_AND_PRE_PASSWORD_NOT_EQUAL, HttpStatus.BAD_REQUEST);
        }
    }

    public void phoneNumberAndUsernameIfExistsThrow(String phoneNumber, String username) {
        if (userRepository.existsByPhoneNumberAndUsername(phoneNumber, username)) {
            throw RestException.restThrow(Message.USER_ALREADY_REGISTERED, Rest.PHONE_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }

    private User saveUser(SignUpDTO signUpDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(signUpDTO.getUsername());
        return optionalUser.orElseGet(() -> userRepository.save(User.builder()
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .username(signUpDTO.getUsername())
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .phoneNumber(signUpDTO.getPhoneNumber())
                .role(roleRepository.findByRoleName(RoleName.USER))
                .accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).accountNonExpired(true).enabled(true)
                .build()));
    }
}
