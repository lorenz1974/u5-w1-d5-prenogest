package lorenz.prenogest.repositories;

import lorenz.prenogest.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsernameAndPassword(String userName, String password);

    List<User> findByUsernameOrEmail(String userName, String email);
}
