package net.ictcampus.campnews.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.ictcampus.campnews.comment.Comment;
import net.ictcampus.campnews.tag.Tag;
import net.ictcampus.campnews.user.User;

import java.util.List;
import java.util.Set;
//Jakarta, markiert Klasse als Datenbank-Entity
@Entity
//Jakarta, gibt Tabellennamen der Datenbank
@Table(name = "`post`")
//Lombok, automatische setter und getter
@Getter
@Setter
//Lombok, automatisch parameterloser Konstruktor
@NoArgsConstructor
//Lombok, Setter geben das Objekt zurück
@Accessors(chain = true)
public class Post {
    //Jakarta/Hibernate Primärschlüssel
    @Id
    //Jakarta/Hibernate generiert PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //Jakarta nicht leer
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Text is required")
    private String text;
    //Jakarta/Hibernate m:1
    @ManyToOne(fetch = FetchType.LAZY)
    //Jakarta/Hibernate Definiert Fremdschlüssel
    @JoinColumn(name = "user_id")
    private User user;
    //Jakarta/Hibernate 1:m
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Comment> comments;
    //Jakarta/Hibernate m:m
    @ManyToMany(fetch = FetchType.LAZY)
    //Jakarta/Hibernate Zwischentabelle
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @NotBlank(message = "A post must have at least one tag")
    private Set<Tag> tags;
}
