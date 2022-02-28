package it.training.spring.boottwo.cli;

import it.training.spring.boottwo.PeopleGenerator;
import it.training.spring.boottwo.PeopleRepository;
import it.training.spring.boottwo.model.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@ShellComponent
public class CliComponent {

    @Autowired
    PeopleGenerator peopleGenerator;

    @Autowired
    PeopleRepository peopleRepository;

    @ShellMethod("Initialize DB with sample random generated data")
    public void initDb() {
        System.out.println("Initializing DB...");
        peopleRepository.saveAll(peopleGenerator.buildPeopleList(20));

        for (People p : peopleRepository.findAll()) {
            System.out.println("> "+p);
        }
        System.out.println(peopleRepository.count()+" record(s) found.");
    }

    @ShellMethod("Make an HTTP API call")
    public void apiCall() {
        RestTemplate rest  = new RestTemplate();
        String baseUrl = "http://localhost:8080/api";

        List<People> pList = Arrays.asList(rest.getForObject(baseUrl+"/findAll", People[].class));

        System.out.println("REST API Response");
        for (People p : pList) {
            System.out.println("> "+p);
        }
    }

}
