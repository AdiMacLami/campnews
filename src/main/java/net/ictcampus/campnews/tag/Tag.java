package net.ictcampus.campnews.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.ictcampus.campnews.post.Post;

import java.util.List;

@Entity
@Table(name = "`tag`")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Title is required")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;
}
