package edu.neu.neumall.repository;

import edu.neu.neumall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickName(String nickName);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmail(String email);
}
