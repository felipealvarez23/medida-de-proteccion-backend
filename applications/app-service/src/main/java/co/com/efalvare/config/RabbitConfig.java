package co.com.efalvare.config;

import co.com.efalvare.model.secret.SecretModelRabbit;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.async.rabbit.config.ConnectionFactoryProvider;
import org.reactivecommons.async.rabbit.config.RabbitProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Slf4j
//@Configuration
public class RabbitConfig {

    private static final Logger LOGGER = Logger.getLogger(RabbitConfig.class.getName());

    @Value("${rabbit.host}")
    private String host;

    @Value("${rabbit.port}")
    private int port;

    @Value("${rabbit.username}")
    private String username;

    @Value("${rabbit.password}")
    private String password;

    @Bean
    public SecretModelRabbit getRabbitCredential() {
        return this.buildSecretModelLocal();
    }

    private SecretModelRabbit buildSecretModelLocal() {
        return new SecretModelRabbit(username, password);
    }

    @Bean
    @Primary
    public RabbitProperties rabbitProperties(SecretModelRabbit model) {
        RabbitProperties rabbitProperties = new RabbitProperties();
        rabbitProperties.setHost(host);
        rabbitProperties.setPort(port);
        rabbitProperties.setUsername(model.getUsername());
        rabbitProperties.setPassword(model.getPassword());
        return rabbitProperties;
    }

    @Bean
    @Profile({"dev", "qa", "pdn"})
    public ConnectionFactoryProvider connection(RabbitProperties rabbitProperties) {
        ConnectionFactory connectionFactory = connectionGlobal(rabbitProperties);
        configureSsl(connectionFactory);
        return () -> connectionFactory;
    }

    @Bean
    @Profile({"dev-local", "test"})
    public ConnectionFactoryProvider connectionTest(RabbitProperties rabbitProperties) {
        ConnectionFactory connectionFactory = connectionGlobal(rabbitProperties);
        configureSsl(connectionFactory);
        return () -> connectionFactory;
    }

    public ConnectionFactory connectionGlobal(RabbitProperties rabbitProperties) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        return connectionFactory;
    }

    private void configureSsl(ConnectionFactory connectionFactory) {
        try {
            connectionFactory.useSslProtocol();
        } catch (NoSuchAlgorithmException | KeyManagementException exception) {
            LOGGER.info(exception.getMessage());
        }
    }

}
