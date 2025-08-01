package tinoborrelli.eventi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tinoborrelli.eventi.exceptions.BadrequestException;
import tinoborrelli.eventi.payloads.UserDTO;
import tinoborrelli.eventi.payloads.UserLoginDTO;
import tinoborrelli.eventi.payloads.UserLoginRespoDTO;
import tinoborrelli.eventi.payloads.UserRespoDTO;
import tinoborrelli.eventi.services.AuthService;
import tinoborrelli.eventi.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginRespoDTO loginEmployee(@RequestBody UserLoginDTO payload) {
        String token = authService.authenticateEmployeeAndGenerateToken(payload);
        return new UserLoginRespoDTO(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespoDTO registerUser(@RequestBody @Validated UserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String errors = validation.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                    .orElse("Validation error");
            throw new BadrequestException(errors);
        }
        return new UserRespoDTO(this.userService.save(body).getId());
    }
}
