package com.github.tadaskay.lunar.crawler;

import com.github.tadaskay.lunar.logger.api.dto.RegisterCelebritiesRequest;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

class Randomizer {

    private static final int URL_COUNT_MAX = 50;
    private static final int CELEBRITY_COUNT_MAX = 5;
    private static final int RANDOM_SLEEP_DURATION_MAX = 1500;
    private static final Fairy nameGenerator = Fairy.create();
    private final Random rand = new Random();

    URL url() throws MalformedURLException {
        return new URL("http://test.local/" + rand.nextInt(URL_COUNT_MAX));
    }

    int sleepDuration() {
        return rand.nextInt(RANDOM_SLEEP_DURATION_MAX + 1);
    }

    int celebrityCount() {
        return rand.nextInt(CELEBRITY_COUNT_MAX + 1);
    }

    RegisterCelebritiesRequest.Entry registrableCelebrity() {
        RegisterCelebritiesRequest.Entry entry = new RegisterCelebritiesRequest.Entry();
        Person person = nameGenerator.person();
        entry.setSceneName(person.getUsername());
        entry.setFirstName(person.getFirstName());
        entry.setLastName(person.getLastName());
        return entry;
    }

    String remoteKey() {
        return nameGenerator.textProducer().latinWord(10);
    }
}
