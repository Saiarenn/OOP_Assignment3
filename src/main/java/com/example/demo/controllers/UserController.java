package com.example.demo.controllers;

import com.example.demo.Entity.Room;
import com.example.demo.Entity.User;
import com.example.demo.services.interfaces.IRoomService;
import com.example.demo.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
public class UserController {

    private  final IUserService userService;
    private  final IRoomService roomService;

    public UserController(IUserService personService, IRoomService roomService) {
        this.userService = personService;
        this.roomService = roomService;
    }

    @GetMapping("/login")
    public String redirectToLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String redirectToSignup() {
        return "signup";
    }

    @PostMapping("/login")
    public String logIn(@RequestParam("email") String email,
                        @RequestParam("password") String password) {
        //
        User user = userService.getByEmail(email);
        String currentPassword = user.getPassword();

        if (currentPassword.equals(password)) {
            return "user";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/page")
    public String page(){
        return "user";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam("name") String name,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password) {

        User user = new User(name, email, password);
        userService.createUser(user);
        return "redirect:/login";
    }


    @PostMapping("/booking")
    public String getBooked(@RequestParam("id") int id, @RequestParam("isbooked") boolean isBooked) {
        Room room = roomService.getById(id);
        if (room.isBooked()) return "Room is already booked!";
        room.setBooked(isBooked);
        roomService.update(room);
        return "Success";
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User u = userService.getById(id);
        if (u == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
}