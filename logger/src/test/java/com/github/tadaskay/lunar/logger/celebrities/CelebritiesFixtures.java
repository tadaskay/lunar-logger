package com.github.tadaskay.lunar.logger.celebrities;

import com.github.tadaskay.lunar.logger.api.RegisterCelebritiesRequest;
import com.github.tadaskay.lunar.logger.util.Randoms;

class CelebritiesFixtures {

    static RegisterCelebritiesRequest.Entry registrableEntry() {
        RegisterCelebritiesRequest.Entry entry = new RegisterCelebritiesRequest.Entry();
        entry.setSceneName("Scene-" + Randoms.string());
        entry.setFirstName("First-" + Randoms.string());
        entry.setLastName("Last-" + Randoms.string());
        return entry;
    }
}
