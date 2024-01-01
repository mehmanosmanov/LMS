/*
 *Created by Jaweed.Hajiyev
 *Date:21.08.23
 *TIME:12:38
 *Project name:LMS
 */

package az.lms.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Data
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    @ApiModelProperty(notes = "Email", example = "example@gmail.com", required = true)
    String email;
    @ApiModelProperty(notes = "Password", example = "Example123", required = true)
    String password;
}
