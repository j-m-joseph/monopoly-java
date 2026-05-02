package com.cm6123.monopoly;

/**
 * Gets purchaseable item.
 */
public interface Purchasable {

    /**
     * Gets the property purchase price.
     * @return the purchase price
     */
    int getPropertyPurchasePrice();
    /**
     * Gets the property rental price.
     * @return the rental price
     */
    int getPropertyRentalPrice();
}
