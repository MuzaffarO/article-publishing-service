package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.articlepublishingservice.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
