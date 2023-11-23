package com.fsociety.warzone.model.player.strategy;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.asset.phase.play.mainplay.Attack;
import com.fsociety.warzone.asset.phase.play.mainplay.Reinforcement;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Country;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the Benevolent strategy for a computer player.
 * A benevolent computer player strategy that focuses on defending the weak countries
 */
public class Benevolent implements Strategy {

    /**
     * This variable is used to track if it's executing the first attack or not.
     */
    private boolean d_isFirstAdvanceOfTurn;

    /**
     * This map is used for executing advance orders in order to centralize and aggregate troops.
     */
    private Map<Country, Country> d_advanceOrdersForProtection;


    /**
     * Default constructor sets up the class.
     */
    public Benevolent() {
        d_isFirstAdvanceOfTurn = true;
        d_advanceOrdersForProtection = new HashMap<>();
    }

    /**
     * Issue order for a benevolent player
     * @param p_playerName The name of the player that is to be prompted for an input. (Applicable only for Human players)
     */
    @Override
    public void issueOrder(String p_playerName) {
        // Get the player ID from player name
        int l_playerId = GameplayController.getPlayerNameMap().get(p_playerName).getId();

        // Get all countries the player currently owns
        List<Country> l_playerCountries = GameplayController.getPlayMap().getCountriesOwnedByPlayer(l_playerId);

        Country l_countryWithMinimumTroops = l_playerCountries.get(0);
        for(Country l_country : l_playerCountries) {
            if(l_country.getArmies() < l_countryWithMinimumTroops.getArmies()) {
                l_countryWithMinimumTroops = l_country;
            }
        }

        // Get the current phase of the game and then decide what to do
        Phase l_currentPhase = GameEngine.getPhase();

        // Deploy in case the game is in the Reinforcement phase
        if(l_currentPhase instanceof Reinforcement) {
            issueReinforcementOrder(l_currentPhase, l_playerId, l_countryWithMinimumTroops);
        }
        // Attack in case the game is in the Attack phase
        if (l_currentPhase instanceof Attack) {
            issueAdvanceOrder(l_currentPhase, l_countryWithMinimumTroops, l_playerCountries);
        }
    }

    /**
     * Issue a Reinforcement order. This is a sub-function for issueOrder.
     * Puts all available reinforcements into the weakest country owned.
     * @param p_phase The current phase of the game to call the deploy function
     * @param p_playerId The player ID who is giving the order
     * @param l_countryToReinforce The country with the maximum number of troops
     */
    private void issueReinforcementOrder(Phase p_phase, int p_playerId, Country l_countryToReinforce) {
        // Get the number of troops available for deployment.
        int l_troopsForDeployment = GameplayController.getPlayerFromId(p_playerId).getAvailableReinforcements();
        // Issue the deploy command
        p_phase.deploy(l_countryToReinforce.getCountryId(), l_troopsForDeployment);
    }

    /**
     * Issue an Advance order. This is a sub-function for issueOrder.
     * @param p_currentPhase the current phase of the game
     * @param p_countryToAdvanceFrom the country from which an attack needs to take place
     * @param p_playerCountries The list of countries owned by the player
     */
    private void issueAdvanceOrder(Phase p_currentPhase, Country p_countryToAdvanceFrom, List<Country> p_playerCountries) {
        // Perform the first attack and populate next advance orders
        if(d_isFirstAdvanceOfTurn) {
            createOrdersForProtection(p_playerCountries);
            d_isFirstAdvanceOfTurn = false;
            return;
        }

        // If there are no more orders, issue the commit command
        if(d_advanceOrdersForProtection.isEmpty()) {
            d_advanceOrdersForProtection = new HashMap<>();
            d_isFirstAdvanceOfTurn = true;
            p_currentPhase.commit();
            return;
        }

        // Issue the advance order
        Country l_advanceFrom = d_advanceOrdersForProtection.keySet().stream().toList().get(0);
        int l_troopsToAdvance = l_advanceFrom.getArmies() / 2;
        Country l_advanceTo = d_advanceOrdersForProtection.get(l_advanceFrom);
        p_currentPhase.advance(l_advanceFrom.getCountryId(), l_advanceTo.getCountryId(), l_troopsToAdvance);
        d_advanceOrdersForProtection.remove(l_advanceFrom);
    }

    /**
     * This function initializes the List of advance orders that are to be executed.
     * @param p_playerCountries The list of countries owned by the player
     */
    private void createOrdersForProtection(List<Country> p_playerCountries) {
        Country l_weakestCountry = p_playerCountries.get(0);
        for(Country l_country : p_playerCountries) {
            if(l_country.getArmies() < l_weakestCountry.getArmies()) {
                l_weakestCountry = l_country;
            }
        }

        List<Country> l_alliedNeighbours = GameplayController.getPlayMap().getListOfAlliedNeighbours(l_weakestCountry);
        // Find out the country with the maximum number of troops among neighbours.
        Country l_allyWithLargestArmy = l_weakestCountry;
        for(Country l_alliedNeighbour : l_alliedNeighbours) {
            if(l_alliedNeighbour.getArmies() > l_allyWithLargestArmy.getArmies()) {
                l_allyWithLargestArmy = l_alliedNeighbour;
            }
        }
        // If the country is not itself, add an advance order to move half the troops to the weaker one.
        if(!l_allyWithLargestArmy.equals(l_weakestCountry)) {
            d_advanceOrdersForProtection.put(l_allyWithLargestArmy, l_weakestCountry);
        }
    }
}
