package com.example.demo.controllers;

import com.example.demo.Entity.Room;
import com.example.demo.services.interfaces.IBookedRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("booked-rooms")
public class BookedRoomController {

    private final IBookedRoomService bookedRoomService;


    public BookedRoomController(IBookedRoomService bookedRoomService) {
        this.bookedRoomService = bookedRoomService;
    }

//    @GetMapping ("/delete/{id}")
//    public ResponseEntity<Room> deleteBookedRoom(@PathVariable int id) {
//        bookedRoomService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
