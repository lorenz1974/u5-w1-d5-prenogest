package lorenz.prenogest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lorenz.prenogest.entities.User;
import lorenz.prenogest.exceptions.EntityException;
import lorenz.prenogest.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) throws EntityException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityException(new String[] { "User", "Not found", id.toString() }));
    }

    public void createUser(User user) throws EntityException {

        // Check if the user already exists
        List<User> users = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (users.size() > 0) {
            String[] args = {
                    "User",
                    "Username or Email Already exists",
                    user.getUsername(),
                    user.getEmail()
            };
            throw new EntityException(args);
        }
        userRepository.save(user);
    }

    public User findByUsername(String username) throws EntityException {
        List<User> users = userRepository.findByUsernameOrEmail(username, username);
        if (users.size() > 0) {
            return users.get(0);
        }
        throw new EntityException(new String[] { "User", "Not found", username });
    }

    public User findByUsernameAndPassword(String username, String password) throws EntityException {
        List<User> users = userRepository.findByUsernameAndPassword(username, password);
        if (users.size() > 0) {
            return users.get(0);
        }
        throw new EntityException(new String[] { "User", "Wrong username or password", username });
    }

    public void save(User user) {
        userRepository.save(user);
    }

}
