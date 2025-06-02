package com.service.interfac;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.dto.Response;

public interface IRoomService {

    Response addNewRoom(MultipartFile photo ,String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();

    Response getAllRooms();

    Response deleteRoom(Long roomId);

    Response updateRoom(Long roomId,String roomType, BigDecimal roomPrice, MultipartFile photo );

    Response getRoomById(Long RoomId);

    Response getAvailableRoomsByDataAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    Response getAllAvailableRooms();


}
