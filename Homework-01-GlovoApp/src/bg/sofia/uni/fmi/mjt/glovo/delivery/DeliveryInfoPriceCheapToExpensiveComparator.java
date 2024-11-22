package bg.sofia.uni.fmi.mjt.glovo.delivery;

import java.util.Comparator;

public class DeliveryInfoPriceCheapToExpensiveComparator implements Comparator<DeliveryInfo> {

    @Override
    public int compare(DeliveryInfo o1, DeliveryInfo o2) {
        int comparePrice = Double.compare(o1.price(), o2.price());
        if (comparePrice != 0) {
            return comparePrice;
        }
        int compareTime = Integer.compare(o1.estimatedTime(), o2.estimatedTime());
        if (compareTime != 0) {
            return compareTime;
        }
        return o1.deliveryType().compareTo(o2.deliveryType());
    }

}
