package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.PriceItemFilter;

import java.math.BigDecimal;

public class GameStore implements StoreAPI {

    private StoreItem[] availableItems;
    private boolean firstPromoCode;
    private boolean friendsPromoCode;

    private void applyDiscount(BigDecimal percentage) {
        for (StoreItem item : availableItems) {
            item.setPrice(item.getPrice().multiply(percentage));
        }
    }

    public StoreItem[] getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(StoreItem[] availableItems) {
        this.availableItems = availableItems;
    }

    public GameStore(StoreItem[] availableItems) {
        setAvailableItems(availableItems);
        firstPromoCode = false;
        friendsPromoCode = false;
    }

    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters) {
        int count = 0;
        boolean[] ignoreElements = new boolean[availableItems.length];
        for (int i = 0; i < availableItems.length; i++) {
            boolean found = true;
            for (ItemFilter itemFilter : itemFilters) {
                if (!itemFilter.matches(availableItems[i])) {
                    ignoreElements[i] = true;
                    found = false;
                    break;
                }
            }
            if (found) {
                count++;
            }
        }

        StoreItem[] passFilters = new StoreItem[count];
        int index = 0;
        for (int i = 0; i < ignoreElements.length; i++) {
            if (!ignoreElements[i]) {
                passFilters[index++] = availableItems[i];
            }
        }
        return passFilters;
    }

    @Override
    public void applyDiscount(String promoCode) {
        if (!firstPromoCode && promoCode.equals("VAN40")) {
            firstPromoCode = true;
            applyDiscount(new BigDecimal(0.6));
        }
        else if (!friendsPromoCode && promoCode.equals("100YO")) {
            friendsPromoCode = true;
            applyDiscount(new BigDecimal(0.0));
        }
    }

    @Override
    public boolean rateItem(StoreItem item, int rating) {
        if (rating < 1 || rating > 5) {
            return false;
        }
        for (StoreItem availableItem : availableItems) {
            if (availableItem.equals(item)) {
                availableItem.rate(rating);
                return true;
            }
        }
        return false;
    }
}
