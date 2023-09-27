package com.fsociety.warzone.game.order;

public class Deploy implements IOrder {

    private int l_troops_count;
    private int l_country_ID;

    public Deploy(int p_country_ID, int p_troops_count) {
        this.l_country_ID = p_country_ID;
        this.l_troops_count = p_troops_count;
    }

    @Override
    public void execute() {
        /**
         * @TODO add armies to map
         */
    }
}
