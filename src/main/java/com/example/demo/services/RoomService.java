package com.example.demo.services;

import com.example.demo.Entity.Room;
import com.example.demo.Repository.RoomRepository;
import com.example.demo.services.interfaces.IRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements IRoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room getById(int id) {
        var r = roomRepository.findById(id);
        if (r.isPresent()) return r.get();
        return null;
    }

    @Override
    public Room create(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void deleteById(int id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }


    @Override
    public List<Room> getByName(String name) {
        return roomRepository.findByName(name);
    }
}
