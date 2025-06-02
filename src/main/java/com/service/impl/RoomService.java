package com.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dto.Response;
import com.dto.RoomDTO;
//import com.dto.UserDTO;
import com.entity.Room;
//import com.entity.User;
import com.exception.OurException;
import com.repo.BookingRepository;
import com.repo.RoomRepository;
import com.service.AwsS3Service;
import com.service.interfac.IRoomService;
import com.utils.Utils;

@Service
public class RoomService implements IRoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AwsS3Service awsS3Service;

    @Override
    public Response addNewRoom(MultipartFile photo ,String roomType, BigDecimal roomPrice, String description){
        Response response = new Response();
        try{
            String imageUrl = awsS3Service.saveImageToS3(photo);
            Room room =new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("Successful.");
            response.setRoom(roomDTO);

        }catch(OurException e){
            response.setStatusCode(500);
            response.setMessage("Error Saving a Room."+e.getMessage());

        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes(){
        return roomRepository.findDistinctRoomTypes();
}

    @Override
    public Response getAllRooms(){
        Response response = new Response();
        try{
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("Successful.");
            response.setRoomList(roomDTOList);


        }catch(OurException e){
            response.setStatusCode(500);
            response.setMessage("Error Saving a Room."+e.getMessage());

        }
        return response;
        
    }

    @Override
    public Response deleteRoom(Long roomId){
        Response response = new Response();
        try{
           Room room = roomRepository.findById(roomId) .orElseThrow(() -> new OurException("Room not found."));
           RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(room);
           response.setRoom(roomDTO);


            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("Successful.");


        }catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        return response;

    }

    @Override
    public Response updateRoom(Long roomId,String description,String roomType, BigDecimal roomPrice, MultipartFile photo ){
        Response response = new Response();
        try{
            String imageUrl = null;
            if(photo != null && !photo.isEmpty()){
                imageUrl = awsS3Service.saveImageToS3(photo);
            }
            Room room = roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found."));

            if (roomType != null) room.setRoomType(roomType); 
            if(roomPrice != null) room.setRoomPrice(roomPrice);
            if(description != null)  room.setRoomDescription(description);
            if(imageUrl != null) room.setRoomPhotoUrl(imageUrl);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setStatusCode(200);
            response.setMessage("Successful.");
            response.setRoom(roomDTO);

        }catch(Exception e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long RoomId){
        Response response = new Response();
        try{
           Room room = roomRepository.findById(RoomId) .orElseThrow(() -> new OurException("Room not found."));

            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            
            response.setStatusCode(200);
            response.setMessage("Successful.");
            response.setRoom(roomDTO);

        }catch(Exception e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        return response;

    }

    @Override
    public Response getAvailableRoomsByDataAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType){
        Response response = new Response();
        try{
            List<Room> availableRooms = roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);
            List <RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);

            response.setStatusCode(200);
            response.setMessage("Successful.");
            response.setRoomList(roomDTOList);

        }
        catch(OurException e){
            response.setStatusCode(500);
            response.setMessage("Error Saving a Room."+e.getMessage());

        }
        return response;


    }

    @Override
    public Response getAllAvailableRooms(){
        Response response = new Response();
        try{
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            
            response.setStatusCode(200);
            response.setMessage("Successful.");
            response.setRoomList(roomDTOList);

        }catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        return response;

    }

}
