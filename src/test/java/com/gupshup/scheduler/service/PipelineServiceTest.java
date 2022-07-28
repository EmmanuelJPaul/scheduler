package com.gupshup.scheduler.service;

import com.gupshup.scheduler.model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class PipelineServiceTest {

    @Autowired
    private PipelineService pipelineService;

    @Before
    public void teardown () {
        pipelineService.getPipeline().clear();
        System.out.println(pipelineService.getPipeline());
    }

    @Test
    public void addMessagesTest () {

        List<Message> messages = new ArrayList<>();
        messages.add(new Message (1, 56547427890l, 5682724312l, "This is a message!", "2022-07-28 15:29:00"));
        messages.add(new Message (2, 15562755890l, 1254310652l, "This is a message!", "2022-07-28 15:29:00"));
        messages.add(new Message (3, 43569567890l, 3454541422l, "This is a message!", "2022-07-28 15:29:00"));
        messages.add(new Message (4, 72789056460l, 3454414542l, "This is a message!", "2022-07-28 15:29:00"));
        messages.add(new Message (5, 13623734240l, 1212354312l, "This is a message!", "2022-07-28 15:29:00"));
        pipelineService.addMessages(messages);
        System.out.println(pipelineService.getPipeline().size());
        assertThat(pipelineService.getPipeline().size()).isEqualTo(5).withFailMessage("Unable to add Messages!");
    }

    @Test
    public void ignoreDuplicatesAddMessagesTest () {

        List<Message> messages = new ArrayList<>();
        messages.add(new Message (1, 56547427890l, 5682724312l, "This is a message!", "2022-07-28 15:29:00"));
        pipelineService.addMessages(messages);

        messages.add(new Message (2, 15562755890l, 1254310652l, "This is a message!", "2022-07-28 15:29:00"));
        messages.add(new Message (3, 43569567890l, 3454541422l, "This is a message!", "2022-07-28 15:29:00"));
        messages.add(new Message (4, 72789056460l, 3454414542l, "This is a message!", "2022-07-28 15:29:00"));
        messages.add(new Message (5, 13623734240l, 1212354312l, "This is a message!", "2022-07-28 15:29:00"));
        messages.add(new Message (1, 56547427890l, 5682724312l, "This is a message!", "2022-07-28 15:29:00"));
        pipelineService.addMessages(messages);

        assertThat(pipelineService.getPipeline().size()).isEqualTo(5).withFailMessage("Duplicates must be ignored!");
    }

    @Test
    public void addMessageTest () {

        Message message = new Message (5, 13623734240l, 1212354312l, "This is a message!", "2022-07-28 15:29:00");
        pipelineService.addMessage(message);

        assertThat(pipelineService.getPipeline().size()).isEqualTo(1).withFailMessage("Unable to add message to Pipeline!");
    }

    @Test
    public void removeMessageTest () {

        Message message = new Message (5, 13623734240l, 1212354312l, "This is a message!", "2022-07-28 15:29:00");
        pipelineService.addMessage(message);
        boolean actual = pipelineService.removeMessage(message);

        assertThat(pipelineService.getPipeline().size()).isEqualTo(0).withFailMessage("Unable to remove message from Pipeline!");
    }
}
