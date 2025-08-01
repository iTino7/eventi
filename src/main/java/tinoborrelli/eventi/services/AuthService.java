package tinoborrelli.eventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tinoborrelli.eventi.entities.User;
import tinoborrelli.eventi.exceptions.UnauthorizedException;
import tinoborrelli.eventi.payloads.UserLoginDTO;
import tinoborrelli.eventi.security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateEmployeeAndGenerateToken(UserLoginDTO payload) {
        User user = userService.getUserByUsernameOrEmail(payload.email());
        if (user == null || !passwordEncoder.matches(payload.password(), user.getPassword())) {
            throw new UnauthorizedException("Credentials not valid");
        }
        return jwtTools.token(user);
    }
}
