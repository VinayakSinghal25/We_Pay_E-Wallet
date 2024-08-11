package wePay.com.User_wePay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import wePay.com.User_wePay.models.User;
import wePay.com.User_wePay.repository.UserRepository;

@SpringBootApplication
public class UserWePayApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${service.authority}")
	private String serviceAuthority;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserWePayApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		User user = User.builder().contact("txn-service").
				password(passwordEncoder.encode("txn-service")).email("txnservice@gmail.com").
				authorities(serviceAuthority).
				build();
		userRepository.save(user);
	}
}
