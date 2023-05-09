package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
//@Table(
//        uniqueConstraints = @UniqueConstraint(name = "follow_unique",columnNames = {"user","follower"})
//)
@Getter
@Setter
public class Follows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Users user;
    @ManyToOne
    private Users follower;
}
