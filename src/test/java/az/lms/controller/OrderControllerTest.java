package az.lms.controller;

import az.lms.LmsApplication;
import az.lms.model.dto.response.OrderResponse;
import az.lms.model.enums.OrderType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mehman Osmanov on 21.08.23
 * @project LMS
 */
@SpringBootTest(classes = LmsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
class OrderControllerTest {

   @LocalServerPort
   private int serverPort;

   private String url;

   @BeforeEach
   void setUp() {
      url = "http://localhost:" + serverPort + "/lms/v1/order";
   }

   @Autowired
   private ObjectMapper objectMapper;
   @Autowired
   private TestRestTemplate restTemplate;

   @Test
   @Sql(scripts = "classpath:sql/order-test-query.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
   public void givenGetOrdersWhenFoundReturnList() {
      //arrange & act
      ResponseEntity<List<OrderResponse>> response = restTemplate.exchange(
              url + "/", HttpMethod.GET, null,
              new ParameterizedTypeReference<>() {
              });

      //assert
      assertEquals(HttpStatus.OK, response.getStatusCode());
      List<OrderResponse> orders = response.getBody();
      assertNotNull(orders);
      assertFalse(orders.isEmpty());
      assertEquals(orders.size(), 6);
      assertEquals(orders.get(2).getOrderType(), OrderType.BORROWED);

   }
}