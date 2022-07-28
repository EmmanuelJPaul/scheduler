
package com.gupshup.scheduler.controllers;

import com.gupshup.scheduler.dao.UserDao;
import com.gupshup.scheduler.dto.MessageRequest;
import com.gupshup.scheduler.model.Response;
import com.gupshup.scheduler.dao.MessageDao;

import com.gupshup.scheduler.model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *
 * @author Emmanuel Paul
 */
@RestController
public class ScheduleController {

    @Autowired
    UserDao userDao;
    @Autowired
    MessageDao messageDao;

    @RequestMapping(method=RequestMethod.POST, value="/schedule")
    public ResponseEntity<?> addSchedule (@RequestBody @Valid MessageRequest message) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User authUser = userDao.getUserByEmail(auth.getName());
            if(!messageDao.addMessage(message, authUser.getId())) {
                throw new Exception("Query Failed!");
            }
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new Response("Something went wrong!"));
        }

        return ResponseEntity.ok(new Response("Message scheduled successfully!"));
    }

    @RequestMapping(method=RequestMethod.POST, value="/callback")
    public void callback (@RequestBody String request) throws org.json.simple.parser.ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(request);
        JSONObject payload = (JSONObject) new JSONParser().parse(obj.get("payload").toString());

        String status = payload.get("type").toString().toUpperCase();
        String apiMessageId = status.equals("SENT") ? payload.get("gsId").toString() : payload.get("id").toString();

        messageDao.addApiReport(request, status, apiMessageId);
    }
}
