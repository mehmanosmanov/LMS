package az.lms.repository;

import az.lms.model.enums.OrderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mehman Osmanov on 18.08.23
 * @project LMS
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@EnableConfigurationProperties
@EnableJpaRepositories
class OrderRepositoryTest {


   @Autowired
   private OrderRepository orderRepository;


   @Test
   @Sql(scripts = "classpath:sql/borrows.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
   public void givenGetTypeOfLastOrderWhenFoundThenReturnOrderTypeOrdered() {
      //arrange
      Long studentId = 1L;
      Long bookId = 1L;
      //act
      String lastOrderType = orderRepository.getTypeOfLastOrder(studentId, bookId);
      //assert
      assertNotNull(lastOrderType);
      assertEquals(OrderType.BORROWED.name(), lastOrderType);

   }

   @Test
   @Sql(scripts = "classpath:sql/returns.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
   public void givenGetTypeOfLastOrderWhenFoundThenReturnOrderTypeReturned() {
      //arrange
      Long studentId = 2L;
      Long bookId = 1L;
      //act
      String lastOrderType = orderRepository.getTypeOfLastOrder(studentId, bookId);
      //assert
      assertNotNull(lastOrderType);
      assertEquals(OrderType.RETURNED.name(), lastOrderType);

   }

   @Test
   public void givenGetTypeOfLastOrderWhenNotFoundThenDo() {
      //arrange
      Long studentId = 100L;
      Long bookId = 100L;
      //act & assert
      String lastOrderType = orderRepository.getTypeOfLastOrder(studentId, bookId);
      assertNull(lastOrderType);

   }
}