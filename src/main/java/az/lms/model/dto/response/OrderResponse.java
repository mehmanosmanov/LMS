package az.lms.model.dto.response;

import az.lms.model.enums.OrderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * @author Mehman Osmanov on 11.08.23
 * @project LMS
 */
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
   @ApiModelProperty(notes = "Student ID", example = "1", required = true)
   Long studentId;
   @ApiModelProperty(notes = "Book ID", example = "1", required = true)
   Long bookId;
   @ApiModelProperty(notes = "Order time", example = "19:23:43 06.09.2023", required = true)
   LocalDateTime orderTime;
   @ApiModelProperty(notes = "Order type", example = "BORROWED/RETURNED", required = true)
   OrderType orderType;

}
