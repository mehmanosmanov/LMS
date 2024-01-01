/*
 *Created by Jaweed.Hajiyev
 *Date:21.08.23
 *TIME:12:41
 *Project name:LMS
 */

package az.lms.model.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResponse {
   String accessToken;
   String refreshToken;
}
