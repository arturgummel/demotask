package com.homework.demo.resource;

import com.homework.demo.entity.Person;
import com.homework.demo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/")
    public String home(Model model) {
        return "index";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signIn(@RequestParam String username, @RequestParam String password, Model model) {
        Person person = personRepository.getPersonByUsername(username);
        if (person != null) {
            if (person.getPassword().equals(password)) {
                if (username.equals("admin")) {
                    return getAdminTable(model);
                } else {
                    model.addAttribute("person", person);
                    return "signedIn";
                }
            } else {
                model.addAttribute("passwordError", "Wrong password!");
                return "index";
            }
        } else {
            model.addAttribute("usernameError", "No such username!");
            return "index";
        }
    }

    @RequestMapping(value = "/updatePersonData", method = RequestMethod.POST)
    public String signIn(@RequestParam String username, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String birthDate, @RequestParam String address, Model model) {
        try {
            Person person = personRepository.getPersonByUsername(username);
            if (firstName.length() > 50) {
                model.addAttribute("firstnameError", "The length can not be more than 50 chars!");
            } else {
                if (firstName != "") {
                    person.setFirstName(firstName);
                }
            }

            if (lastName.length() > 50) {
                model.addAttribute("lastnameError", "The length can not be more than 50 chars!");
            } else {
                if (lastName != "") {
                    person.setLastName(lastName);
                }
            }

            if (address.length() > 256) {
                model.addAttribute("addressError", "The length can not be more than 256 chars!");
            } else {
                if (address != "") {
                    person.setAddress(address);
                }
            }
            if (birthDate != "") {
                Date birthday = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                birthday = sdf.parse(birthDate);
                if (!birthDate.equals(sdf.format(birthday))) {
                    birthday = null;
                    model.addAttribute("birthDateError", "Invalid birthdate format!");
                } else {
                    person.setBirthDate(birthday);
                }
            }
            personRepository.save(person);
            model.addAttribute("person", person);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "signedIn";
    }

    @RequestMapping("/adminTable")
    public String getAdminTable(Model model) {
        List<Person> allPersons = personRepository.findAll();
        model.addAttribute("persons", allPersons);
        model.addAttribute("username", allPersons.get(0).getUsername());
        return "admin";
    }
}
