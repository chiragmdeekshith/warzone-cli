package com.fsociety.warzone.model.player.strategy;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.asset.phase.play.mainplay.Attack;
import com.fsociety.warzone.asset.phase.play.mainplay.Reinforcement;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Country;

import java.util.List;
import java.util.Random;

/**
 * This class implements the aggressive strategy for a computer player.
 * An aggressive computer player strategy that focuses on centralization of forces and then attack
 */
public class Aggressive implements Strategy {
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
            issueAttackOrder(l_currentPhase, l_countryWithMaximumTroops);
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
     */
    private void issueAttackOrder(Phase p_currentPhase, Country p_countryToAttackFrom) {
        List<Country> l_enemyNeighbours = GameplayController.getPlayMap().getListOfEnemyNeighbours(p_countryToAttackFrom);
        Country l_enemyToAttack = chooseEnemyToAttackAggressively(l_enemyNeighbours);
        Integer l_troopsToAttackWith = chooseNumberOfTroopsToAttackWith(p_countryToAttackFrom);
        p_currentPhase.advance(p_countryToAttackFrom.getCountryId(), l_enemyToAttack.getCountryId(), l_troopsToAttackWith);
        //TODO manage this behaviour of advancing within allied forces.
        p_currentPhase.commit();
    }

    /**
     * This function randomly chooses an enemy country to attack. We could've used different strategies here,
     * like attacking the weakest country, or the strongest. But we've gone with a random selection.
     * @param p_enemyNeighbours - a list of possible target for launching an attack
     * @return a country that should be attacked in the aggressive strategy
     */
    private Country chooseEnemyToAttackAggressively(List<Country> p_enemyNeighbours) {
        int l_sizeOfList = p_enemyNeighbours.size();
        Random l_random = new Random();
        int l_countryIndex = l_random.nextInt(l_sizeOfList);
        return p_enemyNeighbours.get(l_countryIndex);
    }

    /**
     * Randomly chooses the number of troops to attack with.
     * @param p_countryToAttackFrom The country which is attacking
     * @return the number of troops to attack with. Greater than 1 and <= number of troops on country
     */
    private Integer chooseNumberOfTroopsToAttackWith(Country p_countryToAttackFrom) {
        Random l_random = new Random();
        return l_random.nextInt(p_countryToAttackFrom.getArmies()-1) + 1;
    }
}
