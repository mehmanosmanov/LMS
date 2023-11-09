/*
 *Created by Jaweed.Hajiyev
 *Date:21.08.23
 *TIME:12:40
 *Project name:LMS
 */

package az.lms.service.impl;

import az.lms.model.dto.request.LoginRequest;
import az.lms.model.dto.request.StudentRequest;
import az.lms.model.dto.response.StudentResponse;
import az.lms.model.dto.response.TokenResponse;
import az.lms.model.enums.RoleType;
import az.lms.model.enums.TokenType;
import az.lms.exception.AlreadyExistsException;
import az.lms.exception.NotFoundException;
import az.lms.mapper.StudentMapper;
import az.lms.model.entity.Student;
import az.lms.repository.StudentRepository;
import az.lms.security.JwtTokenProvider;
import az.lms.security.PasswordEncoder;
import az.lms.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

   private final AuthenticationManager authenticationManager;
   private final JwtTokenProvider jwtTokenProvider;
   private final PasswordEncoder passwordEncoder;
   private final StudentRepository studentRepository;
   private final StudentMapper studentMapper;


   @Override
   public StudentResponse registration(StudentRequest user) {
      log.info("Start to find if student account is already exist");
      studentRepository.findByEmail(user.getEmail()).ifPresent(student -> {
         throw new AlreadyExistsException("Registration is already exist with email '" + user.getEmail() + "'");
      });
      Student student = studentMapper.requestToEntity(user);
      student.setPassword(passwordEncoder.passwordEncode(user.getPassword()));
      student.setRoleType(RoleType.STUDENT);
      return studentMapper.entityToResponse(studentRepository.save(student));
   }

   @Override
   public TokenResponse login(LoginRequest request) {
      log.info("Start to find if user is exist");
      try {
         Authentication authentication = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         request.getEmail(),
                         request.getPassword()));

         SecurityContextHolder.getContext().setAuthentication(authentication);
         TokenResponse tokenResponse = new TokenResponse();
         tokenResponse.setAccessToken(jwtTokenProvider.generateToken((UserDetails) authentication.getPrincipal(), TokenType.ACCESS_TOKEN));
         tokenResponse.setRefreshToken(jwtTokenProvider.generateToken((UserDetails) authentication.getPrincipal(), TokenType.REFRESH_TOKEN));
         return tokenResponse;
      } catch (InternalAuthenticationServiceException ex) {
         log.error("User not found");
         throw new NotFoundException("User not found with email '" + request.getEmail() );
      }
   }

   @Override
   public TokenResponse refresh(UserDetails userDetails) {
      TokenResponse tokenResponse = new TokenResponse();
      tokenResponse.setAccessToken(jwtTokenProvider.generateToken(userDetails, TokenType.ACCESS_TOKEN));
      tokenResponse.setRefreshToken(jwtTokenProvider.generateToken(userDetails, TokenType.REFRESH_TOKEN));
      return tokenResponse;
   }


}
