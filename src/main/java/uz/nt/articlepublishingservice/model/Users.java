package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(generator = "userIdSequence")
    @SequenceGenerator(name = "userIdSequence", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
