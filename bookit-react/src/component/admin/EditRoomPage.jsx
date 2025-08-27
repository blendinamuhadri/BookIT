import React, { useEffect, useState } from "react";
import { useParams , useNavigate} from "react-router-dom";
import ApiService from "../../service/ApiService";

const EditRoomPage = () => {
  const [roomDetails, setRoomDetails] = useState({
    roomType: "",
    roomPrice: "",
    roomDescription: "",
    roomPhotoUrl: "",
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [preview, setPreview] = useState(null);

  useEffect(() => {
    fetchRoomDetails();
  }, []);

  const fetchRoomDetails = async () => {
    try {
      const response = await ApiService.getRoomById(/* id e dhomës */);
      setRoomDetails(response.data);
    } catch (err) {
      setError("Failed to load room details");
    }
  };

  const handleChange = (e) => {
    setRoomDetails({ ...roomDetails, [e.target.name]: e.target.value });
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setPreview(URL.createObjectURL(file));
      setRoomDetails({ ...roomDetails, roomPhoto: file });
    }
  };

  const handleUpdate = async () => {
    try {
      await ApiService.updateRoom(roomDetails);
      setSuccess("Room updated successfully!");
    } catch (err) {
      setError("Failed to update room");
    }
  };

  const handleDelete = async () => {
    try {
      await ApiService.deleteRoom(/* id e dhomës */);
      setSuccess("Room deleted successfully!");
    } catch (err) {
      setError("Failed to delete room");
    }
  };

  return (
    <div className="edit-room-container">
      <h2>Edit Room</h2>
      {error && <p className="error-message">{error}</p>}
      {success && <p className="success-message">{success}</p>}

      <div className="edit-room-form">
        <div className="form-group">
          {preview ? (
            <img src={preview} alt="Room Preview"className="room-photo-preview"/>
          ) : (
            roomDetails.roomPhotoUrl && (
              <img src={roomDetails.roomPhotoUrl} alt="Room" className="room-photo"/> ))}
          <input type="file" name="roomPhoto" onChange={handleFileChange}/>
        </div>

        <div className="form-group">
          <label>Room Type</label>
          <input type="text" name="roomType" value={roomDetails.roomType} onChange={handleChange}/>
        </div>

        <div className="form-group">
          <label>Room Price</label>
          <input type="text"name="roomPrice"value={roomDetails.roomPrice}onChange={handleChange}/>
        </div>

        <div className="form-group">
          <label>Room Description</label>
          <textarea name="roomDescription" value={roomDetails.roomDescription} onChange={handleChange}></textarea>
        </div>

        <button className="update-button" onClick={handleUpdate}> Update Room</button>
        <button className="delete-button" onClick={handleDelete}> Delete Room</button>
      </div>
    </div>
  );
};

export default EditRoomPage;
