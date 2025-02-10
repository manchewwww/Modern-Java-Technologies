package bg.sofia.uni.fmi.mjt.crypto.server.repository;

import bg.sofia.uni.fmi.mjt.crypto.api.CoinApi;
import bg.sofia.uni.fmi.mjt.crypto.server.CryptoApiServer;
import bg.sofia.uni.fmi.mjt.crypto.server.data.CacheData;
import bg.sofia.uni.fmi.mjt.crypto.api.exceptions.ApiException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InMemoryDataRepository implements DataRepository {

    private static final String USERS_NOT_SAVE_IN_FILE = "Users are not saved in file.";

    private static final int PERIOD = 30;
    private static final int START_AFTER = 0;

    private final CryptoApiServer cryptoApiServer;
    private final CoinApi coinApi;
    private CacheData cacheData;
    private final ScheduledExecutorService executor;

    public InMemoryDataRepository(CryptoApiServer cryptoApiServer) throws ApiException {
        this.cryptoApiServer = cryptoApiServer;

        coinApi = new CoinApi(HttpClient.newBuilder().build());
        executor = Executors.newScheduledThreadPool(1);
    }

    @Override
    public CacheData getCacheData() {
        return cacheData;
    }

    @Override
    public void startScheduleAtFixedRate() {
        executor.scheduleAtFixedRate(() -> {
            try {
                cacheData = coinApi.getResponse();
            } catch (ApiException e) {
                try {
                    cryptoApiServer.stop();
                    System.out.println("Api can not load data!");
                    System.exit(1);
                } catch (IOException ex) {
                    System.out.println(USERS_NOT_SAVE_IN_FILE);
                    startScheduleAtFixedRate();
                }
            }
        }, START_AFTER, PERIOD, TimeUnit.MINUTES);
    }

    public void shutdown() {
        executor.shutdown();
    }

}
