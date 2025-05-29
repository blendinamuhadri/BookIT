package com.bookit.BookIT.dto;

import com.fasterxel.jackson.annotation.JsonInclude;
import com.bookit.BookIT.entity.Booking;

import lanbok.List;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIclude(JsonIclude.Include.NON_NULL)
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String role;
    private List <Booking> bookings = new ArrayList<>();
    
}