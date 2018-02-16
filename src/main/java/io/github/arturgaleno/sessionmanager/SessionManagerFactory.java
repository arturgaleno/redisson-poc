package io.github.arturgaleno.sessionmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by arturgaleno on 16/02/2018.
 */
@Configuration
public class SessionManagerFactory {

    @Bean
    public SessionManager sessionManager() {
        return new RedissonSessionManager();
    }
}
