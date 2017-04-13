package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.celebrities.Celebrity;
import com.github.tadaskay.lunar.logger.remotekey.RemoteKey;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
class CrawledUrl {
    @Id
    private String id;
    @NonNull
    private URL url;
    private List<Celebrity> celebrities = new ArrayList<>();
    private RemoteKey remoteKey;
}
