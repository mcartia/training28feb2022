package it.training.spring.boottwo;

import it.training.spring.boottwo.model.CustomUser;
import org.springframework.data.repository.CrudRepository;

public interface CustomUserRepository extends CrudRepository<CustomUser, String> {
}
