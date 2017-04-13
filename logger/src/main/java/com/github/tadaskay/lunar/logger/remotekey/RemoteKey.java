package com.github.tadaskay.lunar.logger.remotekey;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class RemoteKey {
    @Id
    private String id;
    private String value;
}
