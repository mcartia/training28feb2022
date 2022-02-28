package it.training.spring.boottwo.api;

import it.training.spring.boottwo.PeopleRepository;
import it.training.spring.boottwo.model.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("people/{id}")
    public ResponseEntity<People> findById(@PathVariable("id") Long id) {
        if (peopleRepository.existsById(id)) {
            return ResponseEntity.ok(peopleRepository.findById(id).get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("testParam")
    public String testParam(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestHeader("example") String example) {
        System.out.println("firstName: "+firstName);
        System.out.println("lastName: "+lastName);
        System.out.println("example: "+example);
        return "Hello, "+firstName+" "+lastName+"!";
    }
}
