package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.articlepublishingservice.model.Articles;
import uz.nt.articlepublishingservice.model.Likes;
import uz.nt.articlepublishingservice.model.Users;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Integer> {
        Optional<Likes> findByArticleAndUser(Articles article, Users users);
}
