package com.fsociety.warzone.asset.order;

/**
 * This interface is the foundation for all executable orders.
 */
public interface Order {

    /**
     * After implementation, this function is used for executing an order
     */
    void execute();

    /**
     * This function returns the ID of the player who issued the order.
     * @return The player ID
     */
    int getIssuerId();

}
