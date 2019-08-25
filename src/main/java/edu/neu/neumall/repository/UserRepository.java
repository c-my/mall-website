package edu.neu.neumall.repository;

import edu.neu.neumall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByNickName(String nickName);

    public Optional<User> findByPhone(String phone);

    public Optional<User> findByEmail(String email);
}
