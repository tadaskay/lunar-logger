package com.github.tadaskay.lunar.logger.celebrities;

import java.util.List;

public interface CelebritiesRegistrar {

    List<Celebrity> list(String urlId);

    void register(String urlId, List<Celebrity> celebrities);
}
