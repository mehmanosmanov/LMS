package az.lms.mapper;

import az.lms.model.dto.request.OrderRequest;
import az.lms.model.dto.response.OrderResponse;
import az.lms.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Mehman Osmanov on 11.08.23
 * @project LMS
 */
@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
   Order dtoToEntity(OrderRequest request);
   OrderResponse entityToDto(Order order);
}
