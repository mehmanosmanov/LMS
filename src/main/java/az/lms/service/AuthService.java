package az.lms.service;


import az.lms.model.dto.request.LoginRequest;
import az.lms.model.dto.request.StudentRequest;
import az.lms.model.dto.response.StudentResponse;
import az.lms.model.dto.response.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    TokenResponse login(LoginRequest request);
    TokenResponse refresh(UserDetails userDetails);
    StudentResponse registration(StudentRequest user);
}
