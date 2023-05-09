package uz.nt.articlepublishingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {
    private Integer id;
    private String username;
    private String password;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
