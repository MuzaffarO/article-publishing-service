package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.articlepublishingservice.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
