package bg.sofia.uni.fmi.mjt.crypto.server.data;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.wallet.Crypto;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CacheDataTest {

    private static final Gson GSON = new Gson();
    private CacheData cacheData;
    private String cryptosToJSON;

    @BeforeEach
    public void setUp() {
        List<Crypto> cryptos = List.of(
            Crypto.of("BTC", 10000.20, 1, 0),
            Crypto.of("ETH", 10000.20, 1, 0));

        cacheData = new CacheData(cryptos);
        cryptosToJSON = GSON.toJson(cryptos);
    }

    @Test
    public void testGetAvailableCryptos() {
        assertEquals(2, cacheData.getAvailableCryptos().size(),
            "There should be two cryptos");
    }

    @Test
    public void testListOfferings() {
        String output = cacheData.listOfferings();
        assertEquals(cryptosToJSON, output, "ListOfferings return incorrect result");
    }

    @Test
    public void testGetPriceFromAssetIdWhenAssetIdDoesNotExist() {
        assertThrows(CryptoNotFoundException.class, () -> cacheData.getPriceFromAssetId("B"),
            "When assetId does not exist getPriceFromAssetId should throw CryptoNotFoundException");
    }

    @Test
    public void testGetPriceFromAssetId() throws CryptoNotFoundException {
        assertEquals(10000.20, cacheData.getPriceFromAssetId("BTC"),
            "GetPriceFromAssetId return incorrect price");
    }

    @Test
    public void testGetPrices() throws CryptoNotFoundException {
        Map<String, Double> result = Map.of("BTC", 10000.20, "ETH", 10000.20);
        Map<String, Double> output = cacheData.getPrices();

        assertEquals(result, output, "GetPrices return incorrect prices");
    }

}
