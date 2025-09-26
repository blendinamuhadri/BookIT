package com.bookit.BookIT.service.interfac;

import com.bookit.BookIT.dto.LoginRequest;
import com.bookit.BookIT.dto.Response;
import com.bookit.BookIT.entity.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}
