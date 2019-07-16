package edu.neu.neumall.repository;

import edu.neu.neumall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickName(String nickName);

    User findByPhone(String phone);

    User findByEmail(String email);
}
