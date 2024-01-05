package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.AuthController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.SignInDTO;
import uz.prod.backcrm.payload.SignUpDTO;
import uz.prod.backcrm.payload.TokenDTO;
import uz.prod.backcrm.service.abs.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ApiResult<TokenDTO> signIn(SignInDTO dto) {
        return authService.signIn(dto);
    }

    @Override
    public ApiResult<TokenDTO> signUp(SignUpDTO dto) {
        return authService.signUp(dto);
    }
}
