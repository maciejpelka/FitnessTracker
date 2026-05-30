package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));
    }

    public List<User> getUsersByEmailFragment(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<User> getUsersOlderThan(LocalDate date) {
        return userRepository.findByBirthdateBefore(date);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
//Aktualizacja użytkownika w systemie
    public User updateUser(Long id, User updatedUserData) {
        User existingUser = getUserById(id);
        existingUser.updateUser(
                updatedUserData.getFirstName(),
                updatedUserData.getLastName(),
                updatedUserData.getBirthdate(),
                updatedUserData.getEmail()
        );
        return userRepository.save(existingUser);
    }
// Usuwanie użytkownika po ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}