package it.training.spring.boottwo.api;

import com.github.javafaker.Faker;
import it.training.spring.boottwo.CustomUserRepository;
import it.training.spring.boottwo.PeopleRepository;
import it.training.spring.boottwo.model.CustomUser;
import it.training.spring.boottwo.model.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    PeopleRepository peopleRepository;

    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

    @GetMapping("test")
    public String test(HttpServletRequest req) {
        System.out.println(req.getMethod()+" "+req.getServletPath());
        return "OK";
    }

    @GetMapping("npe")
    public String npe() {
        String p = null;
        return p.toLowerCase();
    }

    @PostMapping("create")
    public People create(@RequestBody People newPeople) {
        System.out.println("Saving new "+newPeople);
        peopleRepository.save(newPeople);

        return newPeople;
    }

    @GetMapping("userInfo")
    public Authentication userInfo() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("public/hello")
    public String helloPub() {
        return "Hello, World!";
    }

    @RolesAllowed("ADMIN")
    @GetMapping("initUsers")
    public String initUsers() {
        PasswordEncoder defaultPw = new BCryptPasswordEncoder();

        Faker faker = new Faker();
        CustomUser cu1 = new CustomUser();
        cu1.setUsername(faker.name().username());
        String password = UUID.randomUUID().toString();
        cu1.setPassword("{bcrypt}"+defaultPw.encode(password));
        cu1.addRole("ROLE_USER");
        cu1.setEnabled(true);

        customUserRepository.save(cu1);

        return "Created new user: "+cu1.getUsername()+" with password: "+password;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "NullPointer exception generated")
    @ExceptionHandler
    public void onNullPointer(NullPointerException ex) {}
}
