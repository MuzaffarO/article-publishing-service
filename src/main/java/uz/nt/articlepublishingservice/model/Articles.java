package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Articles {
    @Id
    @GeneratedValue(generator = "article_id")
    @SequenceGenerator(name = "article_id", sequenceName = "article_id_seq", allocationSize = 1)
    private Integer id;
    @NotEmpty
    @Column(name = "title", nullable = false)
    private String title;
    @NotEmpty
    @Column(name = "about", nullable = false)
    private String about;
    @NotEmpty
    @Column(name = "body", nullable = false)
    private String body;
    @CreationTimestamp
    @CreatedDate
    private LocalDateTime publishDate;
    @NotEmpty
    @NotNull
    @ManyToOne
    private Users author;
    @ManyToMany
    private Set<Tag> tags;
    @ManyToMany(mappedBy = "likedArticles")
    private List<Users> likes;
}