package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.articlepublishingservice.model.Follows;
import uz.nt.articlepublishingservice.model.Users;

import java.util.Optional;

public interface FollowsRepository extends JpaRepository<Follows,Integer> {
    Optional<Follows> findByFollowerAndUser(Users follower, Users user);
}
