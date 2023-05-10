package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

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
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Users> follows;
    @ManyToMany(mappedBy = "follows")
    private List<Users> followers;
    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Articles> likes;
}
