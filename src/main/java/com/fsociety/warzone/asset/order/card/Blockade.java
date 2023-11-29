package com.fsociety.warzone.asset.order.card;
import com.fsociety.warzone.asset.order.Order;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.view.Console;

/**
 * This class handles everything related to Blockade cards
 */
public class Blockade implements Order {

    /**
     * The country ID
     */
    private final int d_countryId;
    /**
     * The player ID
     */
    private final int d_playerId;

    /**
     * Parameterised constructor to initialise a Blockade order.
     * @param p_countryId the country to be blockaded
     * @param p_playerId the ID of the player issuing the order
     */
    public Blockade (int p_countryId , int p_playerId) {
        this.d_playerId = p_playerId;
        this.d_countryId = p_countryId;
    }

    /**
     * This method implements the Blockade card as per the Warzone rules and updates the map accordingly: troops on
     * the target country are tripled.
     * The Blockade command will go through as long as the source country still belongs to the player
     * and the country still has troops.
     */
    @Override
    public void execute() {
        Country l_country = GameplayController.getPlayMap().getCountryState(d_countryId);
        Player l_neutralPlayer = GameplayController.getNeutralPlayer();
        if (l_country.getPlayerId() == d_playerId && l_country.getArmies() > 0) {
            //Fetch the current troops count
            int l_troopsCount = GameplayController.getPlayMap().getCountryState(d_countryId).getArmies();

            //Triple the number of troops in the country
            GameplayController.getPlayMap().updateCountry(d_countryId, l_troopsCount * 3);

            //Remove country from player object's country list
            Player l_player = GameplayController.getPlayerFromId(d_playerId);
            l_player.removeCountry(d_countryId);

            //Set the player ID to neutral (-1) in the country
            l_neutralPlayer.addCountry(l_country);
            l_country.setPlayer(l_neutralPlayer);
            l_country.setPlayerId(l_country.getPlayer().getId());

            // Log the event
            String l_outcome = GameplayController.getPlayerNameFromId(d_playerId) + " blockaded " + d_countryId + ".";
            Console.print(l_outcome,true);
        }
    }

    /**
     * This function returns the ID of the player who issued the order.
     * @return The player ID
     */
    @Override
    public int getIssuerId() {
        return this.d_playerId;
    }

}