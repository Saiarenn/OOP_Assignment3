package com.example.demo.services;

import com.example.demo.Entity.BookedRoom;
import com.example.demo.Repository.BookedRoomRepository;
import com.example.demo.services.interfaces.IBookedRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookedRoomService implements IBookedRoomService {
    private final BookedRoomRepository bookedRoomRepository;

    public BookedRoomService(BookedRoomRepository bookedRoomRepository) {
        this.bookedRoomRepository = bookedRoomRepository;
    }

    @Override
    public BookedRoom getById(int id) {
        var r = bookedRoomRepository.findById(id);
        if (r.isPresent()) return r.get();
        return null;
    }

    @Override
    public BookedRoom getByRoomNumber(int roomNumber) {
        return bookedRoomRepository.findByRoomNumber(roomNumber);
    }

    @Override
    public void create(BookedRoom bookedRoom) {
        bookedRoomRepository.save(bookedRoom);
    }

    @Override
    public void deleteByRoomNumber(int id) {
        bookedRoomRepository.deleteByRoomNumber(id);
    }

    @Override
    public List<BookedRoom> getAll() {
        return bookedRoomRepository.findAll();
    }


    @Override
    public List<BookedRoom> getByRoomName(String roomName) {
        return bookedRoomRepository.findByRoomName(roomName);
    }
}
