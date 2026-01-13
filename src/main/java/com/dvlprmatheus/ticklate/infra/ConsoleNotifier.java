package com.dvlprmatheus.ticklate.infra;

import org.springframework.stereotype.Component;

import com.dvlprmatheus.ticklate.domain.Notifier;

@Component
public class ConsoleNotifier implements Notifier {
    
    @Override
    public void sendReminder(String message) {
        System.out.println(message);
    }
}
