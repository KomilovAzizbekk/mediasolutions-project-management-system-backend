package uz.prod.backcrm.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpDTO {

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    private String firstName;

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    private String lastName;

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    @Pattern(regexp = Rest.PHONE_NUMBER_REGEX, message = "Incorrect phone number standard")
    private String phoneNumber;

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    private String username;

    private RoleDTO role;

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    @Pattern(regexp = Rest.PASSWORD_REGEX, message = "Incorrect password standard")
    private String password;

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    @Pattern(regexp = Rest.PASSWORD_REGEX, message = "Incorrect password standard")
    private String prePassword;

}
