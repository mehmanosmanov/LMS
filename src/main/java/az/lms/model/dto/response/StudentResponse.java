package az.lms.model.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * @author Mehman Osmanov on 12.08.23
 * @project LMS
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
   String FIN;
   String name;
   String email;
   String surName;
   String studentGroup;
   LocalDate birthDate;
   String address;
}
