package az.lms.model.dto.request;

import az.lms.model.enums.OrderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * @author Mehman Osmanov on 11.08.23
 * @project LMS
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
   @ApiModelProperty(notes = "Student ID", example = "1", required = true)
   @NotNull(message = "Student ID cannot be empty")
   Long studentId;
   @ApiModelProperty(notes = "Book ID", example = "1", required = true)
   @NotNull(message = "Book ID cannot be empty")
   Long bookId;
   @ApiModelProperty(notes = "Order type", example = "BORROWED/RETURNED", required = true)
   @NotNull(message = "Order type cannot be empty")
   OrderType orderType;
}
