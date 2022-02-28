package it.training.spring.boottwo.api;

import it.training.spring.boottwo.PeopleRepository;
import it.training.spring.boottwo.model.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    PeopleRepository peopleRepository;

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("sample")
    public People sample() {
        People p = new People();
        p.setId(1L);
        p.setFirstName("Mario");
        p.setLastName("Cartia");
        p.setEmail("mario.cartia@gmail.com");

        return p;
    }

    @GetMapping("findAll")
    public List<People> findAll() {
        return (List) peopleRepository.findAll();
    }
}
