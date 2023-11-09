package az.lms.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ashraf
 * @project LMS
 */
@Entity
@Table(name = "books")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String isbn;
    @Column(name = "book_image")
    String image;
    @Column(name = "book_count")
     int count;
    @Column(name = "book_name", nullable = false)
    String name;
    @Column(name = "published_time")
    LocalDate publishedTime;
    @Column(name = "details")
    String details;
    String description;

    @ManyToMany(mappedBy = "books")
    @JsonBackReference
    Set<Author> authors;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonBackReference
    Category categories;
    public Set<Author> getAuthors(){
        if(authors==null){
            authors=new HashSet<>();
        }
        return authors;
    }

}
