package com.github.tadaskay.lunar.logger.celebrities;

import com.github.tadaskay.lunar.logger.util.Randoms;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
class CelebritiesFixtures {

    public RegisterCelebritiesRequest.Entry registrableEntry() {
        RegisterCelebritiesRequest.Entry entry = new RegisterCelebritiesRequest.Entry();
        entry.setSceneName("Scene-" + Randoms.string());
        entry.setFirstName("First-" + Randoms.string());
        entry.setLastName("Last-" + Randoms.string());
        return entry;
    }

}
