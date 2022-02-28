package it.training.spring.boottwo;

import com.github.javafaker.Faker;
import it.training.spring.boottwo.model.People;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PeopleGenerator {

    Faker faker;

    public PeopleGenerator() {
        this.faker = new Faker();
    }

    public People builPeople() {
        People p = new People();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = (firstName+"."+lastName+"@"+faker.internet().domainName()).toLowerCase();

        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setEmail(email);

        return p;
    }

    public List<People> buildPeopleList(int num) {
        List<People> pList = new ArrayList<People>();
        for (int i=0; i<num; i++) {
            pList.add(builPeople());
        }

        return pList;
    }

}
