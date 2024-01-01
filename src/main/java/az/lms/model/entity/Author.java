/*
 *Created by Jaweed.Hajiyev
 *Date:10.08.23
 *TIME:12:52
 *Project name:LMS
 */

package az.lms.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authors")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 25, nullable = false)
    private String name;

    @Column(name = "last_name", length = 25)
    private String surname;

    @Column(name = "biography")
    private String biography;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "authors_book",
            joinColumns = @JoinColumn(name = "authors_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id")
    )
    private Set<Book> books;
    public void addBook(Book book){
        this.books.add(book);
        book.getAuthors().add(this);

    }
}
