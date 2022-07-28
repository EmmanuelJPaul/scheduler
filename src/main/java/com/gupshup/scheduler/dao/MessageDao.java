package com.gupshup.scheduler.dao;

import com.gupshup.scheduler.dto.MessageRequest;
import com.gupshup.scheduler.model.Message;
import com.gupshup.scheduler.mapper.MessageMapper;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;


/**
 *
 * @author Emmanuel Paul
 */
@Service
public class MessageDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public boolean addMessage(MessageRequest message, int userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO `messages` (message) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, message.getMessage());
                return ps;
            }, keyHolder);

        if (rows == 1) rows = jdbcTemplate.update("INSERT INTO `schedule` (message_id, user_id, schedule_time, recipient) VALUES (?,?,?,?)", keyHolder.getKey().longValue(), userId, message.getSendAt(), message.getRecipient());

        return rows != 0;
    } 
    
    public List<Message> getUnsentMessages() {
       
        String sql = "SELECT sch.message_id, msg.message, user.phone as sender, sch.recipient, sch.schedule_time FROM `schedule` as sch "
            + "INNER JOIN `messages` as msg ON sch.message_id = msg.id "
            + "INNER JOIN `users` as user ON sch.user_id = user.id "
            + "WHERE sch.schedule_time <= NOW() AND sch.status = 'UNSENT'";
        
        return jdbcTemplate.query(sql, new MessageMapper());
    }
    
    public boolean markAsPending(int messageId, String apiMessageId) {
        int row = jdbcTemplate.update("UPDATE `schedule` SET status = 'PENDING', api_message_id = ? WHERE message_id = ?", apiMessageId, messageId); 
        return row != 0;
    }

    public boolean addApiReport (String report, String status, String apiMessageId) {
        String sql = "UPDATE `schedule` SET api_status_report = ?, status = ? WHERE api_message_id = ?";
        return jdbcTemplate.update(sql, report, status, apiMessageId) != 0;
    }

}
