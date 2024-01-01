/*
 *Created by Jaweed.Hajiyev
 *Date:22.08.23
 *TIME:16:09
 *Project name:LMS
 */

package az.lms.model.entity;

import az.lms.model.enums.RoleType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "librarian")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Librarian {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;
   @Column(name = "e_mail", nullable = false)
   String email;
   @Column(name = "password", nullable = false)
   String password;
   @Column(name = "first_name", nullable = false)
   String name;
   @Column(name = "last_name", nullable = false)
   String surname;
   @Enumerated(EnumType.STRING)
   @Column(name = "role_type", nullable = false)
   RoleType roleType;
}
