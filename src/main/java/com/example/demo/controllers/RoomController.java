package com.example.demo.controllers;

import com.example.demo.Entity.Room;
import com.example.demo.services.interfaces.IRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@RestController
@RequestMapping("hotel")
public class RoomController {
    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable int id) {
        Room r = roomService.getById(id);
        if (r == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room saved = roomService.create(room);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable int id) {
        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        var list = roomService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("by_name/{name}")
    public ResponseEntity<List<Room>> getRoomByName(@PathVariable String name) {
        var r = roomService.getByName(name);

        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("by_name")
    public ResponseEntity<List<Room>> searchRoomByName(@RequestParam("name") String name) {
        return getRoomByName(name);
    }
}
