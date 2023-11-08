package com.fsociety.warzone.asset.order;

/**
 * This interface is to have a common function for all executable actions
 */
public interface Order {

    /**
     * After implementation, this function is used for executing an order
     */
    void execute();

    int getIssuerId();

}
