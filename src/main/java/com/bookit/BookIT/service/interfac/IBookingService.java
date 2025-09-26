package com.bookit.BookIT.service.interfac;

import com.bookit.BookIT.dto.Response;
import com.bookit.BookIT.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}
