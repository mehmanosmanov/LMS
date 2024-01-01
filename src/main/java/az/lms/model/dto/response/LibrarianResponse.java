/*
 *Created by Jaweed.Hajiyev
 *Date:22.08.23
 *TIME:16:32
 *Project name:LMS
 */

package az.lms.model.dto.response;

import az.lms.model.enums.RoleType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LibrarianResponse {
   String email;
   String name;
   String surname;
   RoleType roleType;
}
