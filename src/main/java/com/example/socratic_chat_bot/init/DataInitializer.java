package com.example.socratic_chat_bot.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DataInitializationService dataInitializationService;

    @Override public void run(String... args) throws Exception {
        dataInitializationService.initializeData();

    }
}
