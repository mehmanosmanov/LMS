package az.lms.model.entity;

import az.lms.model.enums.RoleType;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.time.LocalDate;

/**
 * @author ashraf
 * @project LMS
 */
@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;

   @Column(length = 55, unique = true, updatable = false, nullable = false)
   String FIN;

   @Column(name = "e_mail", nullable = false)
   String email;

   @Column(nullable = false)
   String password;
   @Column(name = "first_name", length = 55, nullable = false)
   String name;
   @Column(name = "last_name", length = 55, nullable = false)
   String surName;
   LocalDate birthDate;
   @Column(name = "student_group", length = 10, nullable = false)
   String studentGroup;
   @Column(length = 100)
   String address;

   @Enumerated(value = EnumType.STRING)
   @Column(name = "role_type", nullable = false)
   RoleType roleType;

}
