package com.example.demo.controllers;

import com.example.demo.Entity.BookedRoom;
import com.example.demo.Entity.Room;
import com.example.demo.Entity.User;
import com.example.demo.services.interfaces.IBookedRoomService;
import com.example.demo.services.interfaces.IRoomService;
import com.example.demo.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@RestController
@RequestMapping("users")
public class UserController {

    private  final IUserService userService;
    private  final IRoomService roomService;
    private  final IBookedRoomService bookedRoomService;

    public UserController(IUserService userService, IRoomService roomService, IBookedRoomService bookedRoomService) {
        this.userService = userService;
        this.roomService = roomService;
        this.bookedRoomService = bookedRoomService;
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
    public String getBooked(@RequestParam("id") int id,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            @RequestParam("start") LocalDate start,
                            @RequestParam("end") LocalDate end,
                            @RequestParam("isbooked") boolean isBooked) {

        Room room = roomService.getById(id);
        if (room.isBooked()) return "Room is already booked!";

        User user = userService.getByEmail(email);
        String currentPassword = user.getPassword();
        if (!currentPassword.equals(password)) return "Invalid Personal Data";

        if (start.isAfter(end)) return "Invalid Dates";

        room.setBooked(isBooked);
        roomService.create(room);

        BookedRoom bookedRoom = new BookedRoom(room.getName(), room.getId(), email, start, end);
        bookedRoomService.create(bookedRoom);

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