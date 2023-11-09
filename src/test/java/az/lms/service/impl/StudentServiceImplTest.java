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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ashraf
 * @project LMS
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
   @InjectMocks
   private StudentServiceImpl studentService;
   @Mock
   private StudentRepository studentRepository;
   @Mock
   private StudentMapper studentMapper;
   @InjectMocks
   private Student student;
   @Mock
   private OrderRepository orderRepository;
   @Mock
   private OrderMapper orderMapper;
   @Mock
   private PasswordEncoder passwordCoderConfig;


   @BeforeEach
   void setStudent() {
      student.setName("Student");
      student.setId(122L);
      student.setStudentGroup("121A");
      student.setEmail("Student12@gmail.com");
      student.setAddress("Baku,Azerbaijan");
      student.setSurName("Testov");
      student.setRoleType(RoleType.ADMIN);
      student.setFIN("1234ASF");
   }

   @Test
   public void givenGetStudentsWhenFoundThenReturnList() {
      StudentResponse studentResponse = new StudentResponse();
      studentResponse.setAddress(student.getAddress());
      studentResponse.setStudentGroup(student.getStudentGroup());
      studentResponse.setEmail(student.getEmail());
      studentResponse.setName(student.getName());
      studentResponse.setSurName(student.getSurName());
      studentResponse.setFIN(student.getFIN());

      when(studentRepository.findAll()).thenReturn(List.of(student));
      when(studentMapper.entityToResponse(student)).thenReturn(studentResponse);

      //act
      List<StudentResponse> studentResponses = studentService.getAll();
      //assert
      assertNotNull(studentResponses);
      assertEquals(studentResponses.get(0).getFIN(), studentResponse.getFIN());
      assertEquals(studentResponses.get(0).getStudentGroup(), studentResponse.getStudentGroup());
      assertEquals(studentResponses.get(0).getName(), studentResponse.getName());
      verify(studentRepository).findAll();
      verify(studentMapper).entityToResponse(student);
      verifyNoMoreInteractions(studentRepository, studentMapper);
   }

   @Test
   public void givenGetsStudentsWhenNotFoundThenReturnEmpty() {
      //arrange
      List<Student> students = new ArrayList<>();
      when(studentRepository.findAll()).thenReturn(students);
      //act
      List<StudentResponse> studentResponses = studentService.getAll();
      //assert
      assertNotNull(studentResponses);
      assertTrue(studentResponses.isEmpty());
      verify(studentRepository).findAll();
      verify(studentMapper, never()).entityToResponse(student);
      verifyNoMoreInteractions(studentRepository, studentMapper);
   }

   @Test
   public void validCreatingStudent() {
      //arrange
      student.setPassword(passwordCoderConfig.passwordEncode("student123"));
      String fin = "123L";
      Student student1 = new Student();
      student1.setFIN(fin);
      StudentRequest request = new StudentRequest();
      request.setFIN(fin);
      when(studentRepository.existsByFIN(fin)).thenReturn(false);
      when(studentMapper.requestToEntity(request)).thenReturn(student1);
      //act
      studentService.create(request);
      verify(studentRepository).existsByFIN(request.getFIN());
      verify(studentMapper).requestToEntity(request);
      verify(passwordCoderConfig).passwordEncode("student123");
      verify(studentRepository).save(any(Student.class));
      verifyNoMoreInteractions(studentRepository, studentMapper);
   }

   @Test
   public void givenCreateStudentWhenThrowAlreadyException() {
      //arrange
      String fin = "123L";
      StudentRequest studentRequest = new StudentRequest();
      studentRequest.setFIN(fin);
      when(studentRepository.existsByFIN(fin)).thenReturn(true);
      //act & assert
      assertThrows(AlreadyExistsException.class, () -> studentService.create(studentRequest));
      verify(studentRepository).existsByFIN(studentRequest.getFIN());
      verifyNoInteractions(studentMapper, passwordCoderConfig);
      verifyNoMoreInteractions(studentRepository);
   }

   @Test
   public void givenUpdateStudentThenUpdate() {
      //arrange
      Student student1 = new Student();
      student1.setFIN("1234asd");
      student1.setId(1234L);
      StudentRequest studentRequest = new StudentRequest();
      studentRequest.setFIN("1234asd");

      //act

      when(studentRepository.findByFIN(student1.getFIN())).thenReturn(Optional.of(student1));
      when(studentMapper.requestToEntity(studentRequest)).thenReturn(student1);
      studentService.update(studentRequest);
      //assert
      verify(studentRepository).findByFIN(student1.getFIN());
      verify(studentMapper).requestToEntity(studentRequest);
      verify(studentRepository).save(student1);
      verifyNoMoreInteractions(studentRepository, studentMapper);
   }

   @Test
   public void givenUpdateStudentWhenThrowEmpty() {
      StudentRequest studentRequest = new StudentRequest();
      studentRequest.setFIN("1234asd");
      when(studentRepository.findByFIN(studentRequest.getFIN())).thenReturn(Optional.empty());
      //act && assert
      assertThrows(NotFoundException.class, () -> studentService.update(studentRequest));
      verify(studentRepository).findByFIN(studentRequest.getFIN());
      verify(studentMapper, never()).requestToEntity(studentRequest);
      verify(studentRepository, never()).save(any(Student.class));
   }

   @Test
   public void givenGetByIdWhenFoundThenReturnResult() {
      //arrange
      String fin = "1234asd";
      Student student1 = new Student();
      student1.setId(12L);
      student1.setFIN(fin);
      StudentResponse studentResponse = new StudentResponse();
      studentResponse.setFIN(fin);
      when(studentRepository.findByFIN(fin)).thenReturn(Optional.of(student1));
      when(studentMapper.entityToResponse(student1)).thenReturn(studentResponse);
      //act
      StudentResponse studentResponse1 = studentService.getById(fin);
      //assert
      assertEquals(fin, studentResponse1.getFIN());
      verify(studentRepository).findByFIN(studentResponse.getFIN());
      verify(studentMapper).entityToResponse(student1);
      verifyNoMoreInteractions(studentRepository, studentMapper);
   }

   @Test
   public void givenGetByIdWhenNotFoundThenThrow404() {
      //arrange
      String fin = "1234asd";
      when(studentRepository.findByFIN(fin)).thenReturn(Optional.empty());
      //act && assert
      assertThrows(NotFoundException.class, () -> studentService.getById(fin));
      verify(studentRepository).findByFIN(fin);
      verifyNoInteractions(studentMapper);
   }

   @Test
   public void givenDeleteStudentByIdWhenFoundThenDelete() {
      //arrange
      String fin = "1234asd";
      Student student1 = new Student();
      student1.setFIN(fin);
      when(studentRepository.findByFIN(fin)).thenReturn(Optional.of(student1));
      //act
      studentService.deleteById(fin);
      //assert
      verify(studentRepository).findByFIN(fin);
      verify(studentRepository).delete(student1);
   }

   @Test
   public void givenDeleteStudentByIdWhenNotFoundThenThrow404() {
      //arrange
      String fin = "123a";
      when(studentRepository.findByFIN(fin)).thenReturn(Optional.empty());
      //act & assert
      assertThrows(NotFoundException.class, () -> studentService.deleteById(fin));
      verify(studentRepository).findByFIN(fin);
      verifyNoMoreInteractions(studentRepository);

   }

   @Test
   public void givenGetStudentOrdersWhenFoundThenReturnResult() {
      //arrange
      String fin = "12345";
      when(studentRepository.findByFIN(fin)).thenReturn(Optional.of(student));

      Order order = new Order();
      order.setStudentId(student.getId());
      order.setOrderTime(LocalDateTime.now());
      order.setBookId(1L);
      order.setId(2L);
      when(orderRepository.findOrderByStudentId(student.getId())).thenReturn(List.of(order));
      OrderResponse orderResponse = new OrderResponse();
      orderResponse.setOrderType(order.getOrderType());
      orderResponse.setStudentId(order.getStudentId());
      orderResponse.setBookId(order.getBookId());
      orderResponse.setOrderTime(order.getOrderTime());
      when(orderMapper.entityToDto(order)).thenReturn(orderResponse);
      //act
      List<OrderResponse> orderResponses = studentService.getStudentOrders(fin);
      //assert
      assertNotNull(orderResponses);
      assertEquals(orderResponses.get(0).getStudentId(), orderResponse.getStudentId());
      assertEquals(orderResponses.get(0).getOrderTime(), orderResponse.getOrderTime());
      assertEquals(orderResponses.get(0).getOrderType(), orderResponse.getOrderType());
      assertEquals(orderResponses.get(0).getBookId(), orderResponse.getBookId());

      verify(studentRepository).findByFIN(fin);
      verify(orderRepository).findOrderByStudentId(student.getId());
      verify(orderMapper).entityToDto(order);
   }

   @Test
   public void givenGetStudentOrderWhenNotFoundThenThrowException() {
      //arrange
      String fin = "12345f";
      when(studentRepository.findByFIN(fin)).thenReturn(Optional.empty());
      //act & assert
      NotFoundException exception = assertThrows(NotFoundException.class, () -> studentService.getStudentOrders(fin));
      assertEquals("Student not found with fin=" + fin, exception.getMessage());

      verify(studentRepository).findByFIN(fin);
      verifyNoInteractions(orderRepository, orderMapper);

   }

}