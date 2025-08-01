package tinoborrelli.eventi.services;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tinoborrelli.eventi.entities.User;
import tinoborrelli.eventi.exceptions.BadrequestException;
import tinoborrelli.eventi.exceptions.NotFoundException;
import tinoborrelli.eventi.exceptions.RecordNotFoundException;
import tinoborrelli.eventi.payloads.UserDTO;
import tinoborrelli.eventi.repositories.UserRepository;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User save(UserDTO payload) throws BadrequestException {
        this.userRepository.findByEmail(payload.email()).ifPresent(
                user -> {
                    try {
                        throw new BadRequestException("The email: " + user.getEmail() + " is already being used");
                    } catch (BadRequestException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        User newUser = new User(payload.firstName(), payload.lastName(), payload.username(), payload.email(), bcrypt.encode(payload.password()), payload.role()
        );

        return userRepository.save(newUser);
    }

    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dipendente non trovato con id " + id));
    }

    public Page<User> getUsers(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userRepository.findAll(pageable);
    }

    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(User.class.getSimpleName(), id));
    }

    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new RecordNotFoundException(User.class.getSimpleName(), usernameOrEmail));
    }

    public void delete(UUID id) {
        User found = this.getById(id);
        this.userRepository.delete(found);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

}

