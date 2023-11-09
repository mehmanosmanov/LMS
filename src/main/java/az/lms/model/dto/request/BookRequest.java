package az.lms.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * @author ashraf
 * @project LMS
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {

   @NotBlank(message = "name can't be empty")
   String name;
   @NotBlank(message = "isbn can't be empty")
   String isbn;
   @Positive(message = "count can't be negative value")
   int count;
   String image;
   @NotBlank(message = "details shouldn't be empty")
   String details;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @NotNull
   @Past(message = "publishedTime should not be in the future")
   LocalDate publishedTime;
   @NotNull
   Long categories_id;
   @NotBlank(message = "description can't be empty")
   @Size(max = 25, min = 5)
   String description;
   @NotNull
   Long author_id;

}
