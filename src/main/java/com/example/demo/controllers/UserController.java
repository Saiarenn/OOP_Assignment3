package com.example.demo.controllers;

import com.example.demo.Entity.Room;
import com.example.demo.Entity.User;
import com.example.demo.services.interfaces.IRoomService;
import com.example.demo.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("users")
public class UserController {

    private  final IUserService userService;
    private  final IRoomService roomService;

    public UserController(IUserService personService, IRoomService roomService) {
        this.userService = personService;
        this.roomService = roomService;
    }

    @GetMapping("/login")
    public ModelAndView redirectToLogin(ModelMap model) {
        return new ModelAndView("login", model);
    }

    @GetMapping("/signup")
    public ModelAndView redirectToSignup(ModelMap model) {
        return new ModelAndView("signup", model);
    }

    @PostMapping("/login")
    public ModelAndView logIn(@RequestParam("email") String email,
                              @RequestParam("password") String password, ModelMap model) {

        User user = userService.getByEmail(email);
        String currentPassword = user.getPassword();

        if (currentPassword.equals(password)) {
            return new ModelAndView("user", model);
        }
        return null;
    }

    @PostMapping("/signup")
    public ModelAndView signUp(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password, ModelMap model) {

        User user = new User(name, email, password);
        userService.createUser(user);
        return redirectToLogin(model);
    }

    @PostMapping("/booking")
    public String getBooked(@RequestParam("id") int id, @RequestParam("isbooked") boolean isBooked) {
        Room room = roomService.getById(id);
        if (room.isBooked()) return "Room is already booked!";
        room.setBooked(isBooked);
        roomService.update(room);
        return "Success";
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User u = userService.getById(id);
        if (u == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
}