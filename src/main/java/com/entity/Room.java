package com.bookit.BookIT.entity


import java.lang.annotation.Inherited;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;
    private String roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;
    private list <Booking> bookings = new ArrayList<>();


    @Override
    public String toString(){
        return "Room{" +
        "id=" + id +
        ", roomType= '" + roomType + '\'' +
        ", roomPrice=" +roomPrice +
        ", roomPhotoUrl='" + roomPhotoUrl + '\'' +
        ", roomDescription='" + roomDescription + '\'' +
        ", bookings'" + bookings + 
        '}';
    } 
}