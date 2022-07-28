/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gupshup.scheduler.dao;

import com.gupshup.scheduler.mapper.UserMapper;
import com.gupshup.scheduler.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Emmanuel Paul
 */
@Service
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserByEmail (String email) {
        String sql = "SELECT * FROM `users` WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{ email }, new UserMapper());
    }

}
