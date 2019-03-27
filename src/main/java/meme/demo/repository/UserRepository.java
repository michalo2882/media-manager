package meme.demo.repository;

import meme.demo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
}
