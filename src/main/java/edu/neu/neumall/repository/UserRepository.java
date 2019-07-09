package edu.neu.neumall.repository;

import edu.neu.neumall.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
