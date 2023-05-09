package uz.nt.articlepublishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.articlepublishingservice.model.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
}
