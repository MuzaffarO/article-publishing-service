package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

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
    private String title;
    private String about;
    private String body;
    @CreationTimestamp
    @CreatedDate
    private LocalDateTime publishDate;
    @ManyToOne
    private Users author;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;
}
