package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "booked-rooms")
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String roomName;
    @Column
    private int roomNumber;
    @Column
    private String email;
    @Column
    private LocalDate dateStart;
    @Column
    private LocalDate dateEnd;

    public BookedRoom(String roomName, int roomNumber, String email, LocalDate dateStart, LocalDate dateEnd) {
        setRoomName(roomName);
        setRoomNumber(roomNumber);
        setEmail(email);
        setDateStart(dateStart);
        setDateEnd(dateEnd);
    }
}

