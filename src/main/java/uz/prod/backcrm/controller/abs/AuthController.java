package uz.prod.backcrm.controller.abs;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.SignInDTO;
import uz.prod.backcrm.payload.SignUpDTO;
import uz.prod.backcrm.payload.TokenDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.Valid;

@RequestMapping(AuthController.AUTH)
public interface AuthController {

    String AUTH = Rest.BASE_PATH_V1 + "auth/";

    String SIGN_UP = "sign-up";

    String SIGN_IN = "sign-in";

    @PostMapping(SIGN_IN)
    ApiResult<TokenDTO> signIn(@Valid @RequestBody SignInDTO dto);

    @PostMapping(SIGN_UP)
    ApiResult<TokenDTO> signUp(@Valid @RequestBody SignUpDTO dto);

}
