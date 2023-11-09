package az.lms.model.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;


import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author ashraf
 * @project LMS
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {

   Long id;
   String name;
   String isbn;
   int count;
   String image;
   LocalDate publishedTime;
   String category;
   List<String> authorsName;
   String description;
   String details;

}
