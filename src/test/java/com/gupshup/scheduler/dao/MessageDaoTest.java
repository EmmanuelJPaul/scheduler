package com.gupshup.scheduler.dao;

import com.gupshup.scheduler.dto.MessageRequest;
import com.gupshup.scheduler.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageDaoTest {

    @Autowired
    private MessageDao messageDao;

    @Test
    @Transactional
    @Rollback(true)
    public void addMessageTest () {
        MessageRequest message = new MessageRequest(54534534544l, "Hello there...!", "2022-07-26 22:49:00");
        boolean actual = messageDao.addMessage(message, 1);

        assertTrue(actual, "Unable to Add a message!");
    }

    @Test
    public void getUnsentMessagesTest () {
        List<Message> messages = messageDao.getUnsentMessages();
        assertThat(messages).isInstanceOf(List.class).isNotNull().withFailMessage("Unable to fetch UNSENT messages!");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void markAsPendingTest () {
        boolean actual = messageDao.markAsPending(2, "09ffcbb2-d3f1-47d3-a4bd-f17109966685");
        assertTrue(actual, "Unable to mark message as Pending!");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addApiReportTest () {
        boolean actual = messageDao.addApiReport("{ \"test\": \"This is a test message!\" }", "FAILED","09ffcbb2-d3f1-47d3-a4bd-f17109966685");
        assertTrue(actual, "Unable to add API Report!");
    }

}
