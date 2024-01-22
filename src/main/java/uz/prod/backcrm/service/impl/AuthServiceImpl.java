package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.enums.RoleName;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.RoleMapper;
import uz.prod.backcrm.payload.SignInDTO;
import uz.prod.backcrm.payload.SignUpDTO;
import uz.prod.backcrm.payload.TokenDTO;
import uz.prod.backcrm.repository.RoleRepository;
import uz.prod.backcrm.repository.UserRepository;
import uz.prod.backcrm.secret.JwtProvider;
import uz.prod.backcrm.service.abs.AuthService;
import uz.prod.backcrm.utills.CommonUtils;
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
    private final MessageSource messageSource;


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
        String message = CommonUtils.createMessage(Message.USER_NOT_FOUND_OR_DISABLED, messageSource, new Object[]{username, password});
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return (User) authentication.getPrincipal();
        } catch (DisabledException | LockedException | CredentialsExpiredException | BadCredentialsException |
                 UsernameNotFoundException disabledException) {
            throw RestException.restThrow(message, HttpStatus.UNAUTHORIZED);
        }
    }

    private void usernameNotFoundThrow(String username){
        String message = CommonUtils.createMessage(Message.USERNAME_NOT_FOUND, messageSource, new Object[]{username});
        if (!userRepository.existsByUsername(username)) {
            throw RestException.restThrow(message, HttpStatus.BAD_REQUEST);
        }
    }

    public void checkPasswordEqualityIfErrorThrow(String password, String prePassword) {
        String message = CommonUtils.createMessage(Message.MISMATCH_PASSWORDS, messageSource, null);
        if (Objects.nonNull(password) && !Objects.equals(password,prePassword)){
            throw RestException.restThrow(message, HttpStatus.BAD_REQUEST);
        }
    }

    public void phoneNumberAndUsernameIfExistsThrow(String phoneNumber, String username) {
        String message = CommonUtils.createMessage(Message.USER_ALREADY_REGISTERED, messageSource, new Object[]{phoneNumber, username});
        if (userRepository.existsByPhoneNumberOrUsername(phoneNumber, username)) {
            throw RestException.restThrow(message, HttpStatus.BAD_REQUEST);
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
                .role(roleRepository.findByName(RoleName.valueOf(signUpDTO.getRole().getName())))
                .accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).enabled(true)
                .build()));
    }
}
