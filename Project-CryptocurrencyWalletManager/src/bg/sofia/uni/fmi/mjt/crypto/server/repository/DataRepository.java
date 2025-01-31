package bg.sofia.uni.fmi.mjt.crypto.server.repository;

import bg.sofia.uni.fmi.mjt.crypto.api.data.CacheData;

public interface DataRepository {

    CacheData getCacheData();

    void shutdown();

}
