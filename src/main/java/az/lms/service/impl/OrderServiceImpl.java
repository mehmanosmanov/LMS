package az.lms.service.impl;

import az.lms.dto.request.OrderRequest;
import az.lms.dto.response.OrderResponse;
import az.lms.exception.AlreadyExistsException;
import az.lms.exception.InsufficientCount;
import az.lms.exception.NotFoundException;
import az.lms.mapper.OrderMapper;
import az.lms.model.Book;
import az.lms.model.Order;
import az.lms.enums.OrderType;
import az.lms.model.Student;
import az.lms.repository.BookRepository;
import az.lms.repository.OrderRepository;
import az.lms.repository.StudentRepository;
import az.lms.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * @author Mehman Osmanov on 11.08.23
 * @project LMS
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

   private final OrderRepository orderRepository;
   private final StudentRepository studentRepository;
   private final BookRepository bookRepository;
   private final EmailService emailService;
   private final OrderMapper orderMapper;

   @Override
   public List<OrderResponse> getOrders() {
      log.info("Getting all existing orders");
      List<Order> orders = orderRepository.findAll();
      return orders.stream().map(orderMapper::entityToDto).toList();
   }

   @Transactional
   @Override
   public void borrowOrder(OrderRequest request) {
      log.info("Starting to create a new borrow order");
      Book book = bookRepository.findById(request.getBookId())
              .orElseThrow(() -> new NotFoundException("Book with ID " + request.getBookId() + " not found"));
      Student student = studentRepository.findById(request.getStudentId())
              .orElseThrow(() -> new NotFoundException("Student not found with fin code " + request.getStudentId()));
      if (book.getCount() == 0)
         throw new InsufficientCount("This book is out of stock");
      var existingOrderType = orderRepository.getTypeOfLastOrder(request.getStudentId(), request.getBookId());
      if (existingOrderType != null && existingOrderType.equalsIgnoreCase(OrderType.BORROWED.name())) {
         log.warn("You have already taken the book!");
         throw new AlreadyExistsException("You have already taken the book!");
      }
      Order order = orderMapper.dtoToEntity(request);
      book.setCount(book.getCount() - 1);
      bookRepository.save(book);
      orderRepository.save(order);
      var to = student.getEmail();
      var subject = "Borrow order";
      var message = "You made borrow oder for the ".concat(book.getName()).concat(" book successfully. ")
              .concat("Thank you for choosing us dear ")
              .concat(student.getName()) + " " + student.getSurName() + ".";
      try {
         emailService.sendEmail(to, subject, message);
      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
      log.info("Successfully made borrow order");
   }

   @Transactional
   @Override
   public void returnOrder(OrderRequest request) {
      log.info("Starting to create a new return order");
      Book book = bookRepository.findById(request.getBookId())
              .orElseThrow(() -> new NotFoundException("Book with ID " + request.getBookId() + " not found"));
      Student student = studentRepository.findById(request.getStudentId())
              .orElseThrow(() -> new NotFoundException("Student not found with fin code " + request.getStudentId()));
      var existingOrderType = orderRepository.getTypeOfLastOrder(request.getStudentId(), request.getBookId());
      if ((existingOrderType == null) || existingOrderType.equalsIgnoreCase(OrderType.RETURNED.name())) {
         log.warn("You have not taken the book!");
         throw new NotFoundException("You have not taken the book!");
      }
      Order order = orderMapper.dtoToEntity(request);
      book.setCount(book.getCount() + 1);
      bookRepository.save(book);
      orderRepository.save(order);
      var to = student.getEmail();
      var subject = "Return order";
      var message = "You made return oder for the ".concat(book.getName()).concat(" book successfully. ")
              .concat("Thank you for choosing us dear ")
              .concat(student.getName()) + " " + student.getSurName() + ".";
      try {
         emailService.sendEmail(to, subject, message);
      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
      log.info("Successfully made return order");
   }

}