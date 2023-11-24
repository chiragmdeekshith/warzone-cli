package com.fsociety.warzone.model.player.strategy;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.asset.phase.play.mainplay.Attack;
import com.fsociety.warzone.asset.phase.play.mainplay.Reinforcement;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Country;
import com.fsociety.warzone.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Cheater strategy for a computer player.
 * A cheater computer player strategy that focuses on occupying neighbours in the create order phase directly.
 */
public class Cheater implements Strategy {

    /**
     * Issue order for a cheater
     * @param p_playerName The name of the player that is to be prompted for an input. (Applicable only for Human players)
     */
    @Override
    public void issueOrder(String p_playerName) {
        // Get the player ID from player name
        Player l_player = GameplayController.getPlayerNameMap().get(p_playerName);

        // Get all countries the player currently owns
        List<Country> l_playerCountries = GameplayController.getPlayMap().getCountriesOwnedByPlayer(l_player.getId());

        // Get the current phase of the game and then decide what to do
        Phase l_currentPhase = GameEngine.getPhase();

        // Deploy in case the game is in the Reinforcement phase
        if(l_currentPhase instanceof Reinforcement) {
            issueReinforcementOrder(l_currentPhase, l_playerCountries, l_player.getId());
        }
        // Attack in case the game is in the Attack phase
        if (l_currentPhase instanceof Attack) {
            issueAdvanceOrder(l_currentPhase, l_player, l_playerCountries);
        }
    }

    /**
     * This function takes over all immediate neighbours,
     * and then doubles the number of armies on countries with enemy neighbours.
     * @param p_currentPhase The current game phase
     * @param p_player the player object
     * @param p_playerCountries The list of countries that the player owns
     */
    private void issueAdvanceOrder(Phase p_currentPhase, Player p_player, List<Country> p_playerCountries) {
        // Take over all immediate neighbours
        p_playerCountries.forEach(p_playerCountry -> {
            List<Country> l_enemyNeighbours = GameplayController.getPlayMap().getListOfEnemyNeighbours(p_playerCountry);
            l_enemyNeighbours.forEach(l_enemyNeighbour -> {
                l_enemyNeighbour.setPlayerId(p_player.getId());
                l_enemyNeighbour.setPlayer(p_player);
            });
        });

        // Double troops in countries with enemy neighbours
        List<Country> l_playerCountries = GameplayController.getPlayMap().getCountriesOwnedByPlayer(p_player.getId());
        List<Country> l_countriesWithEnemyNeighbours = new ArrayList<>();
        l_playerCountries.forEach(l_playerCountry -> l_countriesWithEnemyNeighbours.addAll(GameplayController.getPlayMap().getListOfEnemyNeighbours(l_playerCountry)));
        l_countriesWithEnemyNeighbours.forEach(l_countryWithEnemyNeighbours -> l_countryWithEnemyNeighbours.setArmies(l_countryWithEnemyNeighbours.getArmies() * 2));

        // Perform commit to go forward.
        p_currentPhase.commit();
    }

    /**
     * This function issues a deploy order normally, like a random player.
     * @param p_currentPhase The current phase of the game
     * @param p_playerCountries The list of countries the player owns
     * @param p_playerId The player ID
     */
    private void issueReinforcementOrder(Phase p_currentPhase, List<Country> p_playerCountries, int p_playerId) {
        java.util.Random l_random = new java.util.Random();
        int l_troopsAvailableForDeployment = GameplayController.getPlayerFromId(p_playerId).getAvailableReinforcements();
        int l_countryIndex = l_random.nextInt(p_playerCountries.size());
        Country l_countryForDeployment = p_playerCountries.get(l_countryIndex);
        int l_troopsForDeployment = l_random.nextInt(l_troopsAvailableForDeployment) + 1;
        p_currentPhase.deploy(l_countryForDeployment.getCountryId(), l_troopsForDeployment);
    }
}
