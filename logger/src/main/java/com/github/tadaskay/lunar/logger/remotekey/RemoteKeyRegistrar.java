package com.github.tadaskay.lunar.logger.remotekey;

public interface RemoteKeyRegistrar {

    RemoteKey get(String urlId);

    void register(String urlId, RemoteKey remoteKey);

}
