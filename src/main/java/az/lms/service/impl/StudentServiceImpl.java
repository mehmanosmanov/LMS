package az.lms.service.impl;

import az.lms.model.dto.request.StudentRequest;
import az.lms.model.dto.response.OrderResponse;
import az.lms.model.dto.response.StudentResponse;
import az.lms.model.enums.RoleType;
import az.lms.exception.AlreadyExistsException;
import az.lms.exception.NotFoundException;
import az.lms.mapper.OrderMapper;
import az.lms.mapper.StudentMapper;
import az.lms.model.entity.Order;
import az.lms.model.entity.Student;
import az.lms.repository.OrderRepository;
import az.lms.repository.StudentRepository;
import az.lms.security.PasswordEncoder;
import az.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

/**
 * @author Mehman Osmanov on 12.08.23
 * @project LMS
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
   private final StudentRepository studentRepository;
   private final OrderRepository orderRepository;
   private final StudentMapper studentMapper;
   private final OrderMapper orderMapper;
   private final PasswordEncoder passwordCoderConfig;
   private final EmailService emailService;

   @Value("${user.greeting.mail}")
   private String message;

   @Override
   public List<StudentResponse> getAll() {
      log.info("Getting all students");
      List<Student> students = studentRepository.findAll();
      return students.stream().map(studentMapper::entityToResponse).toList();
   }

   @Override
   public void create(StudentRequest request) {
      log.info("Creating new student account");

      if (studentRepository.existsByFIN(request.getFIN())) {
         throw new AlreadyExistsException("Student already exist with such fin code");
      }
      Student student = studentMapper.requestToEntity(request);
      student.setFIN(request.getFIN().toLowerCase());
      student.setPassword(passwordCoderConfig.passwordEncode(request.getPassword()));
      student.setRoleType(RoleType.STUDENT);
      studentRepository.save(student);
      var to = request.getEmail();
      var subject = "Enrolled!";
      var text = "Hi, ".concat(request.getName()) + " " + request.getSurName()+"\n".concat(message);
      try {
         emailService.sendEmail(to, subject, text);
      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void update(StudentRequest request) {
      log.info("Updating student's fields");
      Student student = studentRepository.findByFIN(request.getFIN().toLowerCase())
              .orElseThrow(() -> new NotFoundException("Student not found with fin code"));
      Student newStudent = studentMapper.requestToEntity(request);
      newStudent.setId(student.getId());
      studentRepository.save(newStudent);
      var to = request.getEmail();
      var subject = "Data updated";
      var text = "Your personal information was updated ".concat(request.getName()) + " " + request.getSurName();
      try {
         emailService.sendEmail(to, subject, text);
      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public StudentResponse getById(String fin) {
      log.info("Getting student by fin {}", fin);
      Student student = studentRepository.findByFIN(fin.toLowerCase())
              .orElseThrow(() -> new NotFoundException("Student not found with fin code " + fin));
      return studentMapper.entityToResponse(student);
   }

   @Override
   public void deleteById(String fin) {
      log.warn("Deleting student account");
      Student student = studentRepository.findByFIN(fin.toLowerCase())
              .orElseThrow(() -> new NotFoundException("Student not found with fin code " + fin));
      studentRepository.delete(student);
   }

   @Override
   public List<OrderResponse> getStudentOrders(String fin) {
      log.warn("Getting all student's orders");
      Student student = studentRepository.findByFIN(fin.toLowerCase())
              .orElseThrow(() -> new NotFoundException("Student not found with fin=" + fin));
      List<Order> orders = orderRepository.findOrderByStudentId(student.getId());
      return orders.stream().map(orderMapper::entityToDto).toList();
   }

}