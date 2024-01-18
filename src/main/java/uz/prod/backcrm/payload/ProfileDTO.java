package uz.prod.backcrm.payload;

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
public class ProfileDTO {

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    private String username;

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    private String firstName;

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    private String lastName;

    private RoleDTO role;

    @NotBlank(message = "{SHOULD_NOT_BE_EMPTY}")
    @Pattern(regexp = Rest.PHONE_NUMBER_REGEX, message = "Incorrect phone number standard")
    private String phoneNumber;

}
