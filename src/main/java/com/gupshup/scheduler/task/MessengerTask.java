/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gupshup.scheduler.task;

import com.gupshup.scheduler.dao.MessageDao;
import com.gupshup.scheduler.model.ApiResponse;
import com.gupshup.scheduler.model.Message;
import com.gupshup.scheduler.service.ApiService;
import com.gupshup.scheduler.service.PipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 *
 * @author Emmanuel Paul
 */
@Component
public class MessengerTask extends TimerTask {

    @Autowired
    private ApiService api;

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private MessageDao messageDao;


    @Override
    public void run() {
        while (!pipelineService.getPipeline().isEmpty()) {
            Message message = pipelineService.getPipeline().get(0);
            ApiResponse response = api.sendMessage(message);
            if (response.getStatus().equals("submitted")) {
                messageDao.markAsPending(message.getId().get(), response.getMessageId());
                pipelineService.removeMessage(message);
            }
        }
    }
}
