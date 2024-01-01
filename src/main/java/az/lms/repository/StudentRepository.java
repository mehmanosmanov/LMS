package az.lms.repository;

import az.lms.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ashraf
 * @project LMS
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByFIN(String finCode);
    boolean existsByFIN(String finCode);
    Optional<Student> findByEmail(String email);
}
