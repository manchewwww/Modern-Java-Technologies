package bg.sofia.uni.fmi.mjt.crypto.wallet;

import com.google.gson.annotations.SerializedName;

public record Crypto(@SerializedName(ASSET_ID) String assetId, @SerializedName(PRICE_USD) double priceUsd,
                     @SerializedName(TYPE_IS_CRYPTO) int typeIsCrypto, double quantity) {

    private static final String ASSET_ID = "asset_id";
    private static final String PRICE_USD = "price_usd";
    private static final String TYPE_IS_CRYPTO = "type_is_crypto";

}
