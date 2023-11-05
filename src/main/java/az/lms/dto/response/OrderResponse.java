package az.lms.dto.response;

import az.lms.enums.OrderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Mehman Osmanov on 11.08.23
 * @project LMS
 */
@Setter
@Getter
public class OrderResponse {
   @ApiModelProperty(notes = "Student ID", example = "1", required = true)
   private Long studentId;
   @ApiModelProperty(notes = "Book ID", example = "1", required = true)
   private Long bookId;
   @ApiModelProperty(notes = "Order time", example = "19:23:43 06.09.2023", required = true)
   private LocalDateTime orderTime;
   @ApiModelProperty(notes = "Order type", example = "BORROWED/RETURNED", required = true)
   private OrderType orderType;

}
