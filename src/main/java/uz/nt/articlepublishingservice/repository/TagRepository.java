package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nt.articlepublishingservice.model.Tag;

import java.util.Optional;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
    @Query(value = "SELECT t.id, t.name, COUNT(*) AS usage_count\n" +
            "FROM tag AS t\n" +
            "JOIN articles_tags AS at ON t.id = at.tags_id\n" +
            "GROUP BY t.id, t.name\n" +
            "ORDER BY usage_count DESC\n" +
            "LIMIT 10;", nativeQuery = true)
    List<Tag> getPopularTags();
}
