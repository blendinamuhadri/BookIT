package com.bookit.BookIT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bookit.BookIT.service.CustomUserDetailsService;
import com.bookit.BookIT.utils.JWTUtils;
import com.bookit.BookIT.dto.TokenRefreshRequest;
import com.bookit.BookIT.dto.TokenRefreshResponse;
import com.bookit.BookIT.dto.LoginRequest;
import com.bookit.BookIT.dto.Response;
import com.bookit.BookIT.entity.User;
import com.bookit.BookIT.service.interfac.IUserService;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request){
        String refreshToken = request.getRefreshToken();
        if(refreshToken == null || refreshToken.isBlank()){
            return ResponseEntity.badRequest().body("Refresh token is missing");
        }

        try {
            String username = jwtUtils.extractUsername(refreshToken);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Kontrollo nëse refresh tokeni është i vlefshëm
            if(jwtUtils.isValidToken(refreshToken, userDetails)){
                String newAccessToken = jwtUtils.generateToken(userDetails);
                return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }
  
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){
        Response response=userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response=userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
