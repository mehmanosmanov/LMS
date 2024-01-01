/*
 *Created by Jaweed.Hajiyev
 *Date:12.08.23
 *TIME:01:46
 *Project name:LMS
 */

package az.lms.model.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorRequest {
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 25, min = 2, message = "Invalid Name: Must be of 2 - 25 characters")
    String name;

    @Size(max = 25, message = "Invalid Name: Must be of 3 - 30 characters")
    String surname;

    @Size(max = 200, message = "Invalid biography: Must be of max 200 characters")
    String biography;

    @NotNull(message = "Invalid birthday: Birthday is NULL")
    @Past(message = "Date should not be in the future")
    LocalDate birthDay;
}
