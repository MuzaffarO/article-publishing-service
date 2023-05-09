package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.articlepublishingservice.model.Likes;

public interface LikesRepository extends JpaRepository<Likes,Integer> {
}
