package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Table(name = "likes",
//        uniqueConstraints = @UniqueConstraint(name = "like_unique",
//                columnNames = {"article", "user"})
//)
@Getter
@Setter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Articles article;
    @ManyToOne
    private Users user;

}
