package tinoborrelli.eventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tinoborrelli.eventi.entities.User;
import tinoborrelli.eventi.exceptions.NotFoundException;
import tinoborrelli.eventi.exceptions.UnauthorizedException;
import tinoborrelli.eventi.payloads.LoginDTO;
import tinoborrelli.eventi.security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authentic(LoginDTO body) {
        User user = userService.getUserByUsernameOrEmail(body.email());
        try {
            user = this.userService.getUserByUsernameOrEmail(body.email());
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Invalid email or password");
        }
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            return jwtTools.token(user);
        } else {
            throw new UnauthorizedException("Invalid email or password");
        }
    }
}
