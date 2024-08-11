package wePay.com.User_wePay.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import wePay.com.User_wePay.models.User;
import wePay.com.User_wePay.request.CreateUserRequest;
import wePay.com.User_wePay.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User create(@RequestBody @Valid CreateUserRequest createUserRequest) throws JsonProcessingException {
        return userService.create(createUserRequest);
    }

    @GetMapping("/userDetails")
    public User find(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return userService.loadUserByUsername(user.getContact());
    }

    @GetMapping("/getUser")
    public User findUser(@RequestParam("contact") String contact){

        return userService.loadUserByUsername(contact);
    }

}
