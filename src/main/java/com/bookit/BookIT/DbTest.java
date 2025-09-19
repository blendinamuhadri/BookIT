package com.bookit.BookIT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbTest implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RoomTypes", Integer.class);
            System.out.println("RoomTypes count: " + count);
        } catch (Exception e) {
            System.err.println("Database connection failed:");
            e.printStackTrace();
        }
    }
}