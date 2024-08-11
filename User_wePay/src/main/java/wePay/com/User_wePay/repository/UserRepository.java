package wePay.com.User_wePay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wePay.com.User_wePay.models.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByContact(String contact);
}
