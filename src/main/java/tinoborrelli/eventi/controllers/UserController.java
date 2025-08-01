package tinoborrelli.eventi.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tinoborrelli.eventi.entities.User;
import tinoborrelli.eventi.payloads.NewUserDTO;
import tinoborrelli.eventi.services.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.userService.getUsers(page, size, sortBy);
    }

    @GetMapping("/me")
    public User getProfile(Authentication authentication) {
        return userService.getUserByUsernameOrEmail(authentication.getName());
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable UUID id) {
        return userService.getById(id);
    }
    

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody NewUserDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setName(dto.firstName());
        user.setSurname(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return userService.create(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public User update(@PathVariable long id, @Valid @RequestBody NewUserDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setName(dto.firstName());
        user.setSurname(dto.lastName());
        user.setEmail(dto.email());
        return userService.create(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }

}
