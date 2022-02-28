package it.training.spring.boottwo;

import it.training.spring.boottwo.model.People;
import org.springframework.data.repository.CrudRepository;

public interface PeopleRepository extends CrudRepository<People, Long> {
}
