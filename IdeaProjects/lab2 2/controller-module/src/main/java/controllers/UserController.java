package controllers;

import dto.UserDto;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.UserSecurityService;


@RestController
@RequestMapping("/user/")
public class UserController implements IUserController {

    private final UserSecurityService userService;

    public UserController(UserSecurityService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @Override
    public UserDto createUser(@Valid @RequestBody UserDto userDTO) {
        return userService.createUser(userDTO.name(), userDTO.password(), userDTO.role(), userDTO.ownerId());
    }

    @PostMapping("create")
    public ResponseEntity<UserDto> createUser(@RequestParam(name = "login") String login,
                                              @RequestParam(name = "password") String password,
                                              @RequestParam(name = "role") String role,
                                              @RequestParam(name = "ownerId") int ownerId) {
        try {
            return ResponseEntity.ok(userService.createUser(login, password, role, ownerId));
        } catch (ConstraintViolationException e) {
            System.out.println(e.getMessage());

            return ResponseEntity.internalServerError().build();
        }
    }
}
