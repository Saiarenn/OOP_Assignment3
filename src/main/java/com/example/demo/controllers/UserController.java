package com.example.demo.controllers;

import com.example.demo.Entity.BookedRoom;
import com.example.demo.Entity.Room;
import com.example.demo.Entity.User;
import com.example.demo.services.interfaces.IBookedRoomService;
import com.example.demo.services.interfaces.IRoomService;
import com.example.demo.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
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

        User user = userService.getByEmail(email);
        String currentPassword = user.getPassword();

        if (currentPassword.equals(password)) {
            return "user";
        }
        return null;
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {

        User user = new User(name, email, password);
        userService.createUser(user);
        return redirectToLogin();
    }

    @PostMapping("/booking")
    public String getBooked(@RequestParam("id") int id,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            @RequestParam("start") LocalDate start,
                            @RequestParam("end") LocalDate end,
                            @RequestParam("isbooked") boolean isBooked) {

        Room room = roomService.getById(id);
        if (room.isBooked()) return "isBooked";

        User user = userService.getByEmail(email);
        String currentPassword = user.getPassword();

        if (!currentPassword.equals(password)) return "Invalid";

        if(start.compareTo(end) > 0) return "Invalid-dates";

        room.setBooked(isBooked);
        roomService.create(room);

        BookedRoom bookedRoom = new BookedRoom(room.getName(), room.getId(), email, start, end);
        bookedRoomService.create(bookedRoom);

        return "success";
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User u = userService.getById(id);
        if (u == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping("/cancel")
    public String redirectToCancel() {
        return "cancel";
    }

    @PostMapping ("/cancel")
    public String cancelBooking(@RequestParam("id") int roomNumber,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password) {

        Room room = roomService.getById(roomNumber);
        if (!room.isBooked()) return "isNotBooked";

        User user = userService.getByEmail(email);

        String currentPassword = user.getPassword();
        if (!currentPassword.equals(password)) return "Invalid";

        BookedRoom bookedRoom = bookedRoomService.getByRoomNumber(roomNumber);
        if (!bookedRoom.getEmail().equals(email)) return "notYour";

        room.setBooked(false);
        roomService.create(room);
        bookedRoomService.deleteById(bookedRoom.getId());

        return "success";
    }

    @GetMapping("/prolongation")
    public String redirectToProlongation() {
        return "prolongation";
    }

    @PostMapping ("/prolongation")
    public String cancelBooking(@RequestParam("id") int roomNumber,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("end") LocalDate end) {

        Room room = roomService.getById(roomNumber);
        if (!room.isBooked()) return "isNotBooked";

        User user = userService.getByEmail(email);

        String currentPassword = user.getPassword();
        if (!currentPassword.equals(password)) return "Invalid";

        BookedRoom bookedRoom = bookedRoomService.getByRoomNumber(roomNumber);
        if (!bookedRoom.getEmail().equals(email)) return "notYour";

        if(bookedRoom.getDateEnd().compareTo(end) > 0 || bookedRoom.getDateStart().compareTo(end) > 0) return "Invalid-dates";

        bookedRoom.setDateEnd(end);

        bookedRoomService.create(bookedRoom);

        return "success";
    }
}