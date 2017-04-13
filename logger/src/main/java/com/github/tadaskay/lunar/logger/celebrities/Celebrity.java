package com.github.tadaskay.lunar.logger.celebrities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Celebrity {
    @Id
    private String id;
    private String sceneName;
    private String firstName;
    private String lastName;
}
