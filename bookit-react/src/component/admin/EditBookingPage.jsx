import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import ApiService from "../../service/ApiService";

const EditBookingPage = () => {
  const navigate = useNavigate();
  const { bookingCode } = useParams();

  const [bookingDetails, setBookingDetails] = useState(null);
  const [error, setError] = useState(null);
  const [success, setSuccessMessage] = useState(null);
  const [loading, setLoading] = useState(true); 

  useEffect(() => {
    const fetchBookingDetails = async () => {
      try {
        const response = await ApiService.getBookingByConfirmationCode(bookingCode);
        setBookingDetails(response.booking);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false); 
      }
    };

    fetchBookingDetails();
  }, [bookingCode]);

  const achieveBooking = async (bookingId) => {
    if (!window.confirm("Are you sure you want to Achieve this booking?")) {
      return;
    }

    try {
      const response = await ApiService.cancelBooking(bookingId);
      if (response.statusCode === 200) {
        setSuccessMessage("The booking was Successfully Achieved");

        setTimeout(() => {
          setSuccessMessage("");
          navigate("/admin/manage-bookings");
        }, 3000);
      }
    } catch (error) {
      setError(error.response?.data?.message || error.message);
      setTimeout(() => setError(""), 5000);
    }
  };

  return (
    <div className="find-booking-page">
      <h2>Booking Detail</h2>

      {error && <p className="error-message">{error}</p>}
      {success && <p className="success-message">{success}</p>}

      {loading ? ( // 🔹 Nëse është loading, tregojmë mesazh
        <p>Loading booking details...</p>
      ) : (
        bookingDetails && (
          <div className="booking-details">
            <h3>Booking Details</h3>
            <p>Confirmation Code: {bookingDetails.bookingConfirmationCode}</p>
            <p>Check-in Date: {bookingDetails.checkInDate}</p>
            <p>Check-out Date: {bookingDetails.checkOutDate}</p>
            <p>Num of Adults: {bookingDetails.numOfAdults}</p>
            <p>Num of Children: {bookingDetails.numOfChildren}</p>

            <br />
            <hr />
            <br />

            <h3>User Who Made the Booking</h3>
            <div>
              <p>Name: {bookingDetails.user.name}</p>
              <p>Email: {bookingDetails.user.email}</p>
              <p>Phone Number: {bookingDetails.user.phoneNumber}</p>
            </div>

            <br />
            <hr />
            <br />

            <h3>Room Details</h3>
            <div>
              <p>Room Type: {bookingDetails.room.roomType}</p>
              <p>Room Price: ${bookingDetails.room.roomPrice}</p>
              <p>Room Description: {bookingDetails.room.roomDescription}</p>
              <p>Room Capacity: {bookingDetails.room.roomCapacity}</p>
              <img src={bookingDetails.room.roomPhotoUrl} alt="Room" className="room-photo" />
            </div>

            <br />
            <button className="achieve-booking"onClick={() => achieveBooking(bookingDetails.id)}>Delete Booking </button>
          </div>
        )
      )}
    </div>
  );
};

export default EditBookingPage;
