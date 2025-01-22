package bg.sofia.uni.fmi.mjt.crypto.wallet;

import com.google.gson.annotations.SerializedName;

public class WalletSummary {
    @SerializedName("invested_amount")
    private double investedAmountInCrypto;
    @SerializedName("current_amount")
    private double currentAmountInCrypto ;
    private double difference;

    public WalletSummary(double investedAmountInCrypto, double currentAmountInCrypto, double difference) {
        this.investedAmountInCrypto = investedAmountInCrypto;
        this.currentAmountInCrypto = currentAmountInCrypto;
        this.difference = difference;
    }
}
