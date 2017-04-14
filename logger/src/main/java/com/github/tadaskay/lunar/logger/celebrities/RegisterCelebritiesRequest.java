package com.github.tadaskay.lunar.logger.celebrities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RegisterCelebritiesRequest {

    @NotNull
    private List<Entry> entries = new ArrayList<>();

    RegisterCelebritiesRequest withEntry(Entry entry) {
        entries.add(entry);
        return this;
    }

    @Getter
    @Setter
    static class Entry {
        private String sceneName;
        private String firstName;
        private String lastName;
    }
}
