package com.gupshup.scheduler.dao;

import com.gupshup.scheduler.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void getUserByEmailTest () {
        User user = userDao.getUserByEmail("emmanuel.paul@gupshup.io");
        assertThat(user).isInstanceOf(User.class).isNotNull().extracting("phone").withFailMessage("Unable to fetch user!");
    }

}
