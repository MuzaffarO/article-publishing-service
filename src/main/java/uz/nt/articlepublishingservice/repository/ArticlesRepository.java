package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.articlepublishingservice.model.Articles;

public interface ArticlesRepository extends JpaRepository<Articles, Integer> {
}
