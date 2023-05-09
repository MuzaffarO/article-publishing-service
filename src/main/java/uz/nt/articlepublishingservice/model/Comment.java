package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(generator = "comment_id_seq")
    @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
    private Integer id;
    private String description;
    private Date createdDate;
    @ManyToOne
    private Users userId;
    @ManyToOne
    private List<> articleId;

}
