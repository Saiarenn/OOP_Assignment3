package com.example.demo.services.interfaces;

import com.example.demo.Entity.Room;

import java.util.List;

public interface IRoomService {
    Room getById(int id);
    Room create(Room room);

    void deleteById(int id);

    List<Room> getAll();
    List<Room> getByName(String name);
}
