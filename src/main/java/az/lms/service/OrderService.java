package az.lms.service;

import az.lms.dto.request.OrderRequest;
import az.lms.dto.response.OrderResponse;
import az.lms.model.OrderType;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mehman Osmanov on 11.08.23
 * @project LMS
 */
@Service
public interface OrderService {

   List<OrderResponse> getOrdersList();

   OrderType createOrder(OrderRequest request);

   OrderType returnOrder(OrderRequest request);

}
