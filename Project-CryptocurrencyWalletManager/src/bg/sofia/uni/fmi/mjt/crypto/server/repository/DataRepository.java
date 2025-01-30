package bg.sofia.uni.fmi.mjt.crypto.server.repository;

import bg.sofia.uni.fmi.mjt.crypto.server.api.data.CacheData;

public interface DataRepository {

    CacheData getCacheData();

    void shutdown();

}
