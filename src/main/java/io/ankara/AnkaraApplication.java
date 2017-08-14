package io.ankara;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessagesProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
public class AnkaraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnkaraApplication.class, args);
	}

	/**
	 * Provide custom system messages to make sure the application is reloaded when the session expires.
	 */
	@Bean
	SystemMessagesProvider systemMessagesProvider() {
		return (SystemMessagesProvider) systemMessagesInfo -> {
            CustomizedSystemMessages systemMessages = new CustomizedSystemMessages();
            systemMessages.setSessionExpiredNotificationEnabled(false);
            return systemMessages;
        };
	}

}
