package com.example.demo.services.interfaces;

import com.example.demo.Entity.BookedRoom;

import java.util.List;

public interface IBookedRoomService {
    BookedRoom getById(int id);
    BookedRoom getByRoomNumber(int roomNumber);
    void create(BookedRoom room);

    void deleteById(int id);

    List<BookedRoom> getAll();
    List<BookedRoom> getByRoomName(String roomName);
}
