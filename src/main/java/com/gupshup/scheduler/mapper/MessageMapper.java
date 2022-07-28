/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gupshup.scheduler.mapper;

import com.gupshup.scheduler.model.Message;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
/**
 *
 * @author ACE
 */
public class MessageMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int numRows) throws SQLException {
        return new Message(
                rs.getInt("message_id"),
                rs.getLong("recipient"),
                rs.getLong("sender"),
                rs.getString("message"),
                rs.getString("schedule_time")
        );
    }
    
}
