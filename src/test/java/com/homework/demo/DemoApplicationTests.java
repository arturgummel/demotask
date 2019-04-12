package com.homework.demo;

import com.homework.demo.entity.Person;
import com.homework.demo.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void matchAdminData() {
        Person person = personRepository.getPersonByUsername("admin");
        assertEquals(person.getPassword(), "admin");
        assertNull(person.getFirstName());
        assertNotNull(person.getId());
        assertFalse(person.getPassword().length() < 5);
        assertTrue(person.getUsername().length() > 1 && person.getUsername().length() < 50);
    }

    @Test
    public void matchUserData() {
        Person person = personRepository.getPersonByUsername("user1");
        assertEquals(person.getFirstName(), "Bot");
        assertNotNull(person.getLastName());
        assertTrue(person.getAddress().length() <= 256);
        assertNotNull(person.getBirthDate());
        person.setFirstName("Snake");
        personRepository.save(person);
        Person matchPerson = personRepository.getPersonByUsername("user1");
        assertEquals(matchPerson.getFirstName(), "Snake");
    }

    @Test
    public void matchBirthDate() {
        Person person = personRepository.getPersonByUsername("user2");
        String birthDate1 = "13-05-2000";
        String birthDate2 = "1997-07-31";
        Date birthday = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            birthday = sdf.parse(birthDate1);
            assertNotEquals(birthDate1, sdf.format(birthday));
            birthday = sdf.parse(birthDate2);
            assertEquals(birthDate2, sdf.format(birthday));
            assertNotNull(person.getBirthDate());
            assertEquals("1970-03-09", sdf.format(person.getBirthDate()));

            person.setBirthDate(birthday);
            personRepository.save(person);

            Person matchPerson = personRepository.getPersonByUsername("user2");

            assertEquals(sdf.format(birthday), sdf.format(matchPerson.getBirthDate()));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
