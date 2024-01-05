package uz.prod.backcrm.service.abs;

import org.springframework.web.bind.annotation.RequestBody;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.SignInDTO;
import uz.prod.backcrm.payload.SignUpDTO;
import uz.prod.backcrm.payload.TokenDTO;

public interface AuthService {

    ApiResult<TokenDTO> signIn(@RequestBody SignInDTO signInDTO);

    ApiResult<TokenDTO> signUp(@RequestBody SignUpDTO signUpDTO);

    TokenDTO generateToken(User user);

    User checkUsernameAndPasswordAndEtcAndSetAuthenticationOrThrow(String username, String password);


}
