package com.example.demo.Repository;

import com.example.demo.Entity.BookedRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedRoomRepository extends JpaRepository<BookedRoom, Integer> {

    List<BookedRoom> findByRoomName(String roomName);
    BookedRoom findByRoomNumber(int roomNumber);
}
