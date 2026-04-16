package com.cm6123.monopoly;

/**
 * purchase property in Carl Monopoly.
 */
public final class Property {

    /**
     * The name of the property.
     */
    private final String propertyName;

    /**
     * The purchase price of the property.
     */
    private final int propertyPurchasePrice;

    /**
     * The rental price of the property.
     */
    private final int propertyRentalPrice;
    /**
     * The player who owns the property if property is not owned = null.
     */
    private Player propertyOwner;

    /**
     * Constructs a new property.
     *  @param name the name of the property
     *  @param purchasePrice the purchase price
     *  @param rentalPrice the rental price
     */
    public Property(final String name, final int purchasePrice, final int rentalPrice) {

        this.propertyName = name;
        this.propertyPurchasePrice = purchasePrice;
        this.propertyRentalPrice = rentalPrice;
        this.propertyOwner = null;
        //this field belonging to this object
    }

    /**
     * Getter get property name.
     * @return the property name
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * getter get property purchase price.
     * @return the property puchase price
     */
    public int getPropertyPurchasePrice() {
        return propertyPurchasePrice;
    }

    /**
     * getter get property rental price.
     * @return the property rental price
     */
    public int getPropertyRentalPrice() {
        return propertyRentalPrice;
    }

    /**
     * Gets the owner of the property.
     * @return the player who owns the property ornull if unowned
     */
    public Player getPropertyOwner() {
        return propertyOwner;
    }

    /**
     * Sets the owner of the property if there is one.
     * @param newPropertyOwner the player who owns the property
     */
    public void setPropertyOwner(final Player newPropertyOwner) {
        this.propertyOwner = newPropertyOwner;
        //added paramater Player newPropertyOwner
    }
}
