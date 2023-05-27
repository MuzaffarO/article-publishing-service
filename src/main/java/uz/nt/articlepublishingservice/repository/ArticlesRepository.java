package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import uz.nt.articlepublishingservice.model.Articles;
import uz.nt.articlepublishingservice.model.Tag;
import uz.nt.articlepublishingservice.model.Users;

import java.util.List;
import java.util.Optional;

public interface ArticlesRepository extends JpaRepository<Articles, Integer>, PagingAndSortingRepository<Articles, Integer> {

    List<Articles> findAllByAuthor(Users users);
    List<Articles> findAllByTags(Tag tag);

}
