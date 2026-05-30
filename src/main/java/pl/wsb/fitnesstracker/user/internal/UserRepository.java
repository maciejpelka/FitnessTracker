package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

//Wyszukiwanie po dacie urodzenia (starszych niż...)
    List<User> findByBirthdateBefore(LocalDate time);
//Wyszukiwanie po fragmencie e-maila (bez rozróżniania wielkości liter)
    List<User> findByEmailContainingIgnoreCase(String email);
}