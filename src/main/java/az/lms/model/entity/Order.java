package az.lms.model.entity;

import az.lms.model.enums.OrderType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Mehman Osmanov on 11.08.23
 * @project LMS
 */
@Entity
@Table(name = "orders")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;

   @Column(name = "student_id", nullable = false)
   Long studentId;
   @Column(name = "book_id", nullable = false)
   Long bookId;
   @Column(name = "order_time")
   @CreationTimestamp
   LocalDateTime orderTime;
   @Column(name = "order_type", nullable = false)
   @Enumerated(EnumType.STRING)
   OrderType orderType;

}
