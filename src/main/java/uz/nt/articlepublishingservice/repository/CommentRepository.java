package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.articlepublishingservice.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}