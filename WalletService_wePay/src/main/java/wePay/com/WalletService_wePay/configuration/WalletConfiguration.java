package wePay.com.WalletService_wePay.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Properties;

@Configuration
public class WalletConfiguration {

    public Properties properties(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }

    public ConsumerFactory getConsumerFactory(){
        return new DefaultKafkaConsumerFactory(properties());

    }
    @Bean
    public ObjectMapper objectMapper(){
        return  new ObjectMapper();
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory getListernerFactory(){
        ConcurrentKafkaListenerContainerFactory c= new ConcurrentKafkaListenerContainerFactory();
        c.setConsumerFactory(getConsumerFactory());
        return c;
    }

}
