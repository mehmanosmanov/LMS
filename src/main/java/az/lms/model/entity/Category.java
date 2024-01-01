/*
 *Created by Jaweed.Hajiyev
 *Date:10.08.23
 *TIME:13:15
 *Project name:LMS
 */

package az.lms.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;
   @ApiModelProperty(name = "Category name", value = "Science fiction")
   @Column(name = "name", unique = true, nullable = false)
   String name;

   @Column(name = "description", nullable = false)
   String description;

   @Column(name = "Type", nullable = false)
   String type;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "categories")
   List<Book> books;

   @Override
   public String toString() {
      return "Category{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", description='" + description + '\'' +
              '}';
   }
}

