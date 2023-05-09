package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.articlepublishingservice.model.Image;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByUserId(Integer userId);

//    @Modifying
//    @Query("update image i set i.pathSmall= ?1, i.pathMedium = ?2, i.pathLarge = ?3" +
//            " where i.userId = ?4")
//    Image setImageInfoByUserId(String pathSmall, String pathMedium, String pathLarge, Integer userId);
}
