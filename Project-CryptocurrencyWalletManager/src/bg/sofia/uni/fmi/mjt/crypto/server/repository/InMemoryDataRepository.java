package bg.sofia.uni.fmi.mjt.crypto.server.repository;

import bg.sofia.uni.fmi.mjt.crypto.api.CoinApi;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.ApiException;

import java.net.http.HttpClient;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InMemoryDataRepository implements DataRepository {

    private static final int PERIOD = 30;
    private static final int START_AFTER = 0;

    private final CoinApi coinApi;
    private CacheData cacheData;
    private final ScheduledExecutorService executor;

    public InMemoryDataRepository(String apiKey) {
        coinApi = new CoinApi(HttpClient.newBuilder().build(), apiKey);
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            try {
                cacheData = coinApi.getResponse();
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }, START_AFTER, PERIOD, TimeUnit.MINUTES);
    }

    @Override
    public CacheData getCacheData() {
        return cacheData;
    }

    public void shutdown() {
        executor.shutdown();
    }

}
