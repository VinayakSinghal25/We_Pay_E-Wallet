package wePay.com.User_wePay.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wePay.com.User_wePay.models.CommonConstants;
import wePay.com.User_wePay.models.User;
import wePay.com.User_wePay.repository.UserRepository;
import wePay.com.User_wePay.request.CreateUserRequest;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.authority}")
    private String userAuthority;

    @Value("${kafka.user.created.topic}")
    private String userCreatedTopic;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public User create(CreateUserRequest createUserRequest) throws JsonProcessingException {
        User user = createUserRequest.toUser();
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setAuthorities(userAuthority);
        user= userRepository.save(user);

        // publish data of user and consumers will be consuming it
        JSONObject jsonObject =new JSONObject();
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_USER_ID, user.getId());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_CONTACT , user.getContact());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_EMAIL , user.getEmail());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_USERIDENTIFIER, user.getUserIdentifier());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_NAME , user.getName());
        jsonObject.put(CommonConstants.USER_CREATION_TOPIC_USERIDENTIFIER_VALUE , user.getUserIdentifierValue());
        kafkaTemplate.send(CommonConstants.USER_CREATION_TOPIC, objectMapper.writeValueAsString(jsonObject));
        return user;
    }

    @Override
    public User loadUserByUsername(String contact) throws UsernameNotFoundException {
        return userRepository.findByContact(contact);
    }
}