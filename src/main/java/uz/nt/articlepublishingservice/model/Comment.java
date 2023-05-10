package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;


import java.util.Date;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(generator = "comment_id_seq")
    @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
    private Integer id;
    private String description;
    @CreatedDate
    @CreationTimestamp
    private Date createdDate;
    @ManyToOne
    private Users users;
    @ManyToOne
    private Articles articles;

}