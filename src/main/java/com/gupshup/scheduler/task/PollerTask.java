/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gupshup.scheduler.task;


import com.gupshup.scheduler.dao.MessageDao;
import com.gupshup.scheduler.model.Message;
import com.gupshup.scheduler.service.PipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emmanuel Paul
 */
@Component
public class PollerTask extends TimerTask {

    @Autowired()
    private MessageDao messageDao;
    
    @Autowired()
    private PipelineService pipelineService;
                
    @Override
    public void run() {
        try {
            List<Message> messages = messageDao.getUnsentMessages();
            pipelineService.addMessages(messages);
        } catch (Exception ex) {
            Logger.getLogger(PollerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
