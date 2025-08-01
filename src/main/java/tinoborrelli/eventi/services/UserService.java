package tinoborrelli.eventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tinoborrelli.eventi.entities.User;
import tinoborrelli.eventi.exceptions.RecordNotFoundException;
import tinoborrelli.eventi.payloads.NewUserDTO;
import tinoborrelli.eventi.repositories.UserRepository;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public User create(NewUserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setEmail(userDTO.email());
        user.setName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        return userRepository.save(user);
    }

    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(User.class.getSimpleName(), id));
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new RecordNotFoundException(User.class.getSimpleName(), usernameOrEmail));
    }
}
