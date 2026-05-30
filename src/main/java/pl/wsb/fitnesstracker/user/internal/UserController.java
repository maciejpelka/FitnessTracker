package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
//Wylistowanie podstawowych informacji o użytkownikach (tylko ID, Imię, Nazwisko)
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllSimpleUsers() {
        return userService.findAllUsers().stream()
                .map(user -> new UserSimpleDto(user.getId(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toList());
    }
//Pobranie szczegółów wybranego użytkownika
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return mapToDto(userService.getUserById(id));
    }

    @GetMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam("email") String email) {
        return userService.getUsersByEmailFragment(email).stream()
                .map(user -> new UserEmailDto(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThan(@PathVariable LocalDate time) {
        return userService.getUsersOlderThan(time).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    //Tworzenie nowego użytkownika
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto request) {
        User newUser = new User(request.firstName(), request.lastName(), request.birthdate(), request.email());
        return mapToDto(userService.createUser(newUser));
    }
//Aktualizacja użytkownika w systemie
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto request) {
        User updatedData = new User(request.firstName(), request.lastName(), request.birthdate(), request.email());
        return mapToDto(userService.updateUser(id, updatedData));
    }

    //Usuwanie użytkownika po ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthdate(), user.getEmail());
    }
}