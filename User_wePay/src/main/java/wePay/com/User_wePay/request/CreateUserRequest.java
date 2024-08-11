package wePay.com.User_wePay.request;

import com.sun.istack.NotNull;
import lombok.*;
import wePay.com.User_wePay.models.User;
import wePay.com.User_wePay.models.UserIdentifier;
import javax.validation.constraints.NotBlank;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class CreateUserRequest {

    private String name;
    @NotBlank
    private String contact;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private UserIdentifier userIdentifier;
    private String userIdentifierValue;

    public User toUser() {
        return User.builder().
                name(this.name).
                contact(this.contact).
                email(this.email).
                userIdentifier(this.userIdentifier).
                userIdentifierValue(this.userIdentifierValue).
                build();
    }
}
