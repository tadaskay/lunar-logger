package com.github.tadaskay.lunar.logger.celebrities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class Celebrity {
    private String sceneName;
    private String firstName;
    private String lastName;

    static List<Celebrity> create(List<RegisterCelebritiesRequest.Entry> entries) {
        return entries.stream()
            .map(entry -> {
                Celebrity c = new Celebrity();
                c.sceneName = entry.getSceneName();
                c.firstName = entry.getFirstName();
                c.lastName = entry.getLastName();
                return c;
            })
            .collect(toList());
    }
}
