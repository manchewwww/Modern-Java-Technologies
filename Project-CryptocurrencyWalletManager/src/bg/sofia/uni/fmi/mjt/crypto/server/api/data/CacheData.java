package bg.sofia.uni.fmi.mjt.crypto.server.api.data;

import bg.sofia.uni.fmi.mjt.crypto.exceptions.CryptoNotFoundException;
import bg.sofia.uni.fmi.mjt.crypto.messages.ErrorMessages;
import bg.sofia.uni.fmi.mjt.crypto.wallet.Crypto;
import com.google.gson.Gson;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CacheData {

    private static final int MAX_ELEMENTS_FOR_LIST = 100;
    private static final Gson GSON = new Gson();
    private final Map<String, Crypto> availableCryptos;

    public CacheData(List<Crypto> availableCryptos) {
        this.availableCryptos = availableCryptos.stream()
            .filter(x -> x.typeIsCrypto() == 1)
            .collect(Collectors.toMap(Crypto::assetId, cr -> new Crypto(cr.assetId(), cr.priceUsd(),
                cr.typeIsCrypto(), 0)));
    }

    public Map<String, Crypto> getAvailableCryptos() {
        return availableCryptos;
    }

    public String listOfferings() {
        return GSON.toJson(
            availableCryptos.values().stream()
                .sorted(Comparator.comparing(Crypto::priceUsd).reversed())
                .limit(MAX_ELEMENTS_FOR_LIST)
                .collect(Collectors.toList()));
    }

    public double getPriceFromAssetId(String assetId) throws CryptoNotFoundException {
        if (!availableCryptos.containsKey(assetId)) {
            throw new CryptoNotFoundException(ErrorMessages.CRYPTO_DOES_NOT_EXIST_MESSAGE);
        }
        return availableCryptos.get(assetId).priceUsd();
    }

    public Map<String, Double> getPrices() {
        return availableCryptos.values().stream()
            .collect(Collectors.toMap(Crypto::assetId, Crypto::priceUsd));
    }

}
