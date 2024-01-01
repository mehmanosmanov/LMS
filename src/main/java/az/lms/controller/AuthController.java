/*
 *Created by Jaweed.Hajiyev
 *Date:21.08.23
 *TIME:12:47
 *Project name:LMS
 */

package az.lms.controller;

import az.lms.model.dto.request.LoginRequest;
import az.lms.model.dto.request.StudentRequest;
import az.lms.model.dto.response.StudentResponse;
import az.lms.model.dto.response.TokenResponse;
import az.lms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.accepted().body(authService.login(request));
    }

    // todo: refreshToken  endpoint
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.refresh(userDetails));
    }

    @PostMapping("/registration")
    public ResponseEntity<StudentResponse> studentRegistration(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(authService.registration(request));
    }
}
