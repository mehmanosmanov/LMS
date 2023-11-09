package az.lms.repository;

import az.lms.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @author ashraf
 * @project LMS
 */

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
    boolean existsByIsbn(String isbn);
    Optional<Book> findByIsbn(String isbn);
    Optional<List<Book>> findByNameContaining(String bookName);

}