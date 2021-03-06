package com.github.tadaskay.lunar.logger.api.dto;

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

    public RegisterCelebritiesRequest withEntry(Entry entry) {
        entries.add(entry);
        return this;
    }

    @Getter
    @Setter
    public static class Entry {
        private String sceneName;
        private String firstName;
        private String lastName;
    }
}
