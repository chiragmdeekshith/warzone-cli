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
import java.util.Random;

/**
 * This class implements the aggressive strategy for a computer player.
 * An aggressive computer player strategy that focuses on centralization of forces and then attack
 */
public class Aggressive implements Strategy {
    /**
     * This variable is used to track if it's executing the first attack or not.
     */
    private boolean d_isFirstAttackOfTurn;

    /**
     * This map is used for executing advance orders in order to centralize and aggregate troops.
     */
    private Map<Country, Country> d_advanceOrdersForAggregation;


    /**
     * Default constructor sets up the class.
     */
    public Aggressive() {
        d_isFirstAttackOfTurn = true;
        d_advanceOrdersForAggregation = new HashMap<>();
    }

    /**
     * Issues an aggressive order.
     * It deploys on its strongest country, then always attack with its strongest country,
     * then moves its armies in order to maximize aggregation of forces in one country.
     * @param p_playerName The name of the player that is to be prompted for an input. (Applicable only for Human players)
     */
    @Override
    public void issueOrder(String p_playerName) {

        // Get the player ID from player name
        int l_playerId = GameplayController.getPlayerNameMap().get(p_playerName).getId();

        // Get all countries the player currently owns
        List<Country> l_playerCountries = GameplayController.getPlayMap().getCountriesOwnedByPlayer(l_playerId);

        // Filter out the countries which only have friendly neighbours
        List<Country> l_countriesWithEnemyNeighbours = l_playerCountries
                .stream()
                .filter(l_playerCountry -> GameplayController
                        .getPlayMap()
                        .doesCountryHaveEnemyNeighbours(l_playerCountry))
                .toList();

        // Get the country in which the number of troops is maximum
        // There MUST be at least one country with an enemy neighbour. Otherwise, the map is not working properly
        Country l_countryWithMaximumTroops = l_countriesWithEnemyNeighbours.get(0);
        for(Country l_country : l_playerCountries) {
            if(l_country.getArmies() > l_countryWithMaximumTroops.getArmies()) {
                l_countryWithMaximumTroops = l_country;
            }
        }

        // Get the current phase of the game and then decide what to do
        Phase l_currentPhase = GameEngine.getPhase();

        // Deploy in case the game is in the Reinforcement phase
        if(l_currentPhase instanceof Reinforcement) {
            issueReinforcementOrder(l_currentPhase, l_playerId, l_countryWithMaximumTroops);
        }
        // Attack in case the game is in the Attack phase
        if (l_currentPhase instanceof Attack) {
            issueAttackOrder(l_currentPhase, l_countryWithMaximumTroops, l_playerCountries);
        }
    }

    /**
     * Issue a Reinforcement order. This is a sub-function for issueOrder.
     * Puts all available reinforcements into the strongest country owned.
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
     * Issue an Attack order. This is a sub-function for issueOrder.
     * @param p_currentPhase the current phase of the game
     * @param p_countryToAttackFrom the country from which an attack needs to take place
     * @param p_playerCountries The list of countries owned by the player
     */
    private void issueAttackOrder(Phase p_currentPhase, Country p_countryToAttackFrom, List<Country> p_playerCountries) {

        // Perform the first attack and populate next advance orders
        if(d_isFirstAttackOfTurn) {
            performFirstAttack(p_currentPhase, p_countryToAttackFrom);
            createOrdersForTroopAggregation(p_playerCountries);
            d_isFirstAttackOfTurn = false;
            return;
        }

        // If there are no more orders, issue the commit command
        if(d_advanceOrdersForAggregation.isEmpty()) {
            d_isFirstAttackOfTurn = true;
            p_currentPhase.commit();
            return;
        }

        // Issue the advance order
        Country l_advanceFrom = d_advanceOrdersForAggregation.keySet().stream().toList().get(0);
        int l_troopsToAdvance = l_advanceFrom.getArmies();
        Country l_advanceTo = d_advanceOrdersForAggregation.get(l_advanceFrom);
        p_currentPhase.advance(l_advanceFrom.getCountryId(), l_advanceTo.getCountryId(), l_troopsToAdvance);
        d_advanceOrdersForAggregation.remove(l_advanceFrom);
    }

    /**
     * This function performs the first attack on an enemy
     * @param p_currentPhase the current phase of the game
     * @param p_countryToAdvanceFrom the country to perform an attack from
     */
    private void performFirstAttack(Phase p_currentPhase, Country p_countryToAdvanceFrom) {
        boolean l_isAttackingAnEnemy = true;
        List<Country> l_neighbours = GameplayController.getPlayMap().getListOfEnemyNeighbours(p_countryToAdvanceFrom);
        // Moves all the troops from the current country into a random allied neighbour because it has no enemy neighbour.
        if(l_neighbours.isEmpty()) {
            l_neighbours = GameplayController.getPlayMap().getListOfAllyNeighbours(p_countryToAdvanceFrom);
            l_isAttackingAnEnemy = false;
        }
        Country l_countryToAdvanceOn = chooseCountryToAdvanceAggressively(l_neighbours);
        Integer l_troopsToAdvanceWith = chooseNumberOfTroopsToAdvanceWith(p_countryToAdvanceFrom, l_isAttackingAnEnemy);
        p_currentPhase.advance(p_countryToAdvanceFrom.getCountryId(), l_countryToAdvanceOn.getCountryId(), l_troopsToAdvanceWith);
    }

    /**
     * This function initializes the List of advance orders that are to be executed.
     * @param p_playerCountries The list of countries owned by the player
     */
    private void createOrdersForTroopAggregation(List<Country> p_playerCountries) {
        p_playerCountries.stream()
                .filter(l_playerCountry -> l_playerCountry.getArmies() > 0)
                .forEach(l_playerCountry -> {
                    // Fetch all neighbours of this country
                    List<Country> l_alliedNeighbours = GameplayController.getPlayMap().getListOfAllyNeighbours(l_playerCountry);
                    // Find out the country with the maximum number of troops among neighbours.
                    Country l_allyWithLargestArmy = l_playerCountry;
                    for(Country l_alliedNeighbour : l_alliedNeighbours) {
                        if(l_alliedNeighbour.getArmies() > l_allyWithLargestArmy.getArmies()) {
                            l_allyWithLargestArmy = l_alliedNeighbour;
                        }
                    }
                    // If the country is not itself, add an advance order to move all troops to the greater one.
                    if(!l_allyWithLargestArmy.equals(l_playerCountry)) {
                        d_advanceOrdersForAggregation.put(l_playerCountry, l_allyWithLargestArmy);
                    }
                });
    }


    /**
     * This function randomly chooses a country to advance. We could've used different strategies here,
     * like attacking the weakest country, or the strongest. But we've opted for a random selection.
     * @param p_neighbours - a list of possible targets for advancement
     * @return a country that should be advanced on in the aggressive strategy
     */
    private Country chooseCountryToAdvanceAggressively(List<Country> p_neighbours) {
        int l_sizeOfList = p_neighbours.size();
        Random l_random = new Random();
        int l_countryIndex = l_random.nextInt(l_sizeOfList);
        return p_neighbours.get(l_countryIndex);
    }

    /**
     * Randomly chooses the number of troops to attack with.
     * @param p_countryToAdvanceFrom The country which is attacking
     * @param p_isAttackingAnEnemy A flag to determine if we are attacking an enemy or not
     * @return the number of troops to attack with based on the passed flag.
     * Greater than 1 and <= number of troops on country if the country is an enemy. All troops if it's an ally.
     */
    private Integer chooseNumberOfTroopsToAdvanceWith(Country p_countryToAdvanceFrom, boolean p_isAttackingAnEnemy) {
        if(p_isAttackingAnEnemy) {
            return p_countryToAdvanceFrom.getArmies();
        }
        Random l_random = new Random();
        return l_random.nextInt(p_countryToAdvanceFrom.getArmies()-1) + 1;
    }
}
