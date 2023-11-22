package com.fsociety.warzone.asset.phase.play.mainplay;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.asset.order.card.*;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.player.Player;
import com.fsociety.warzone.controller.gameplay.IssueOrder;
import com.fsociety.warzone.asset.order.Advance;
import com.fsociety.warzone.view.Console;

/**
 * This Class implements the commands that are valid during the Issue Orders phase of gameplay.
 */
public class Attack extends MainPlay {

    /**
     * This method compiles and prints a help message of valid commands for the MainPlay phase when the 'help' command
     * is entered.
     */
    @Override
    public void help() {
        Command[] l_validCommands = {Command.SHOW_MAP, Command.ADVANCE, Command.BOMB, Command.BLOCKADE, Command.AIRLIFT,
                Command.NEGOTIATE, Command.COMMIT, Command.SHOW_CARDS, Command.SHOW_AVAILABLE_ARMIES, Command.SHOW_PLAYERS};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands) +
                "Tip - use the following general format for commands: command [arguments]";
        Console.print(l_help);
    }

    /**
     * This method creates and adds an order of type Advance to the player's list of orders when they enter the
     * 'advance' command during the Attack phase. The method checks any relevant conditions and prints an error message
     * if the order cannot be created.
     * @param p_sourceCountryId the country sending the troops
     * @param p_targetCountryId the country receiving the troops
     * @param p_troopsCount the number of troops to be moved
     */
    @Override
    public void advance(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {

        boolean l_sourceCountryExists = GameplayController.getPlayMap().getCountryState(p_sourceCountryId) != null;
        boolean l_targetCountryExists = GameplayController.getPlayMap().getCountryState(p_targetCountryId) != null;
        if (!l_sourceCountryExists) {
            Console.print("Country " + p_sourceCountryId + " doesn't exist!");
            return;
        }
        if (!l_targetCountryExists) {
            Console.print("Country " + p_targetCountryId + " doesn't exist!");
            return;
        }

        boolean l_playerOwnsCountry = GameplayController.getPlayMap().getCountryState(p_sourceCountryId).getPlayerId() == IssueOrder.getCurrentPlayer().getId();
        boolean l_countriesAreDifferent = p_sourceCountryId != p_targetCountryId;
        boolean l_countriesAreNeighbours = GameplayController.getPlayMap().getNeighbours().get(p_sourceCountryId).contains(p_targetCountryId);
        boolean l_sourceCountryHasEnoughTroops = IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) >= p_troopsCount;
        if (l_playerOwnsCountry && l_countriesAreDifferent && l_countriesAreNeighbours && l_sourceCountryHasEnoughTroops) {
            String l_confirmation = p_troopsCount + " troops will advance to country " + p_targetCountryId + " from country " + p_sourceCountryId + ".";
            IssueOrder.d_availableTroopsOnMap.put(p_sourceCountryId, IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) - p_troopsCount);
            IssueOrder.getCurrentPlayer().addOrder(new Advance(p_sourceCountryId, p_targetCountryId, p_troopsCount, IssueOrder.getCurrentPlayer().getId()));
            Console.print(l_confirmation);
            IssueOrder.getCurrentPlayer().setOrderIssued();
            return;
        }

        if (!l_playerOwnsCountry) {
            Console.print("You do not own this country!");
        }
        if (!l_countriesAreDifferent) {
            Console.print("Source and target countries must be different!");
        }
        if (!l_countriesAreNeighbours) {
            Console.print("Source and target countries must be neighbours!");
        }
        if (!l_sourceCountryHasEnoughTroops) {
            Console.print("Country has insufficient troops!");
        }
    }

    /**
     * This method creates and adds an order of type Bomb to the player's list of orders when they enter the 'bomb'
     * command during the Attack phase. The method checks any relevant conditions and prints an error message
     * if the order cannot be created.
     *
     * @param p_targetCountryId the country to be bombed
     */
    @Override
    public void bomb(int p_targetCountryId) {

        boolean l_targetCountryExists = GameplayController.getPlayMap().getCountryState(p_targetCountryId) != null;
        if (!l_targetCountryExists) {
            Console.print("Country " + p_targetCountryId + " doesn't exist!");
            return;
        }

        boolean l_isEnemyCountry = GameplayController.getPlayMap().getCountryState(p_targetCountryId).getPlayerId() != IssueOrder.getCurrentPlayer().getId();
        boolean l_isNeighbour = GameplayController.getPlayMap().isNeighbourOf(p_targetCountryId, IssueOrder.getCurrentPlayer().getId());

        if (l_isEnemyCountry && l_isNeighbour) {
            if (IssueOrder.getCurrentPlayer().getHandOfCards().playCard(HandOfCards.Card.BOMB)) {
                String l_confirmation = p_targetCountryId + " will be bombed.";
                IssueOrder.getCurrentPlayer().addOrder(new Bomb(p_targetCountryId, IssueOrder.getCurrentPlayer().getId()));
                Console.print(l_confirmation);
                IssueOrder.getCurrentPlayer().setOrderIssued();
            } else {
                Console.print("You do not have a Bomb card!");
            }
            return;
        }

        if (!l_isEnemyCountry) {
            Console.print("You cannot bomb your own country!");
        }
        if (!l_isNeighbour) {
            Console.print("You can only bomb neighbouring countries!");
        }
    }

    /**
     * This method creates and adds an order of type Blockade to the player's list of orders when they enter the
     * 'blockade' command during the Attack phase. The method checks any relevant conditions and prints an error message
     *  if the order cannot be created.
     * @param p_countryId the country to be blockaded
     */
    @Override
    public void blockade(int p_countryId) {

        boolean l_CountryExists = GameplayController.getPlayMap().getCountryState(p_countryId) != null;
        if (!l_CountryExists) {
            Console.print("Country " + p_countryId + " doesn't exist!");
            return;
        }

        boolean l_playerOwnsCountry = GameplayController.getPlayMap().getCountryState(p_countryId).getPlayerId() == IssueOrder.getCurrentPlayer().getId();
        boolean l_countryHasTroops = IssueOrder.d_availableTroopsOnMap.get(p_countryId) > 0;

        if (l_playerOwnsCountry && l_countryHasTroops) {
            if (IssueOrder.getCurrentPlayer().getHandOfCards().playCard(HandOfCards.Card.BLOCKADE)) {
                String l_confirmation = p_countryId + " will be blockaded.";
                IssueOrder.getCurrentPlayer().addOrder(new Blockade(p_countryId, IssueOrder.getCurrentPlayer().getId()));
                Console.print(l_confirmation);
                IssueOrder.getCurrentPlayer().setOrderIssued();
            } else {
                Console.print("You do not have a Blockade card!");
            }
            return;
        }

        if (!l_playerOwnsCountry) {
            Console.print("You do not own this country!");
        }
        if (!l_countryHasTroops) {
            Console.print("Country has insufficient troops!");
        }
    }

    /**
     * This method creates and adds an order of type Advance to the player's list of orders when they enter the
     * 'advance' command during the Attack phase. The method checks any relevant conditions and prints an error message
     * if the order cannot be created.
     * @param p_sourceCountryId the country sending the troops
     * @param p_targetCountryId the country receiving the troops
     * @param p_troopsCount the number of troops to be moved
     */
    @Override
    public void airlift(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {

        boolean l_sourceCountryExists = GameplayController.getPlayMap().getCountryState(p_sourceCountryId) != null;
        boolean l_targetCountryExists = GameplayController.getPlayMap().getCountryState(p_targetCountryId) != null;
        if (!l_sourceCountryExists) {
            Console.print("Country " + p_sourceCountryId + " doesn't exist!");
            return;
        }
        if (!l_targetCountryExists) {
            Console.print("Country " + p_targetCountryId + " doesn't exist!");
            return;
        }

        boolean l_playerOwnsCountry = GameplayController.getPlayMap().getCountryState(p_sourceCountryId).getPlayerId() == IssueOrder.getCurrentPlayer().getId();
        boolean l_countriesAreDifferent = p_sourceCountryId != p_targetCountryId;
        boolean l_sourceCountryHasEnoughTroops = IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) >= p_troopsCount;

        if (l_playerOwnsCountry && l_countriesAreDifferent && l_sourceCountryHasEnoughTroops) {
            if (IssueOrder.getCurrentPlayer().getHandOfCards().playCard(HandOfCards.Card.AIRLIFT)) {
                String l_confirmation = p_troopsCount + " will be airlifted to " + p_targetCountryId + " from " + p_sourceCountryId + ".";
                IssueOrder.d_availableTroopsOnMap.put(p_sourceCountryId, IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) - p_troopsCount);
                IssueOrder.getCurrentPlayer().addOrder(new Airlift(p_sourceCountryId, p_targetCountryId, p_troopsCount, IssueOrder.getCurrentPlayer().getId()));
                Console.print(l_confirmation);
                IssueOrder.getCurrentPlayer().setOrderIssued();
            } else {
                Console.print("You do not have an Airlift card!");
            }
            return;
        }

        if (!l_playerOwnsCountry) {
            Console.print("You do not own this country!");
        }
        if (!l_countriesAreDifferent) {
            Console.print("Source and target countries must be different!");
        }
        if (!l_sourceCountryHasEnoughTroops) {
            Console.print("Country has insufficient troops!");
        }

    }

    /**
     * This method creates and adds an order of type Diplomacy to the player's list of orders when they enter the
     * 'negotiate' command during the Attack phase. The method checks any relevant conditions and prints an error
     * message if the order cannot be created.
     * @param p_targetPlayerName the name of the player to be negotiated with
     */
    @Override
    public void negotiate(String p_targetPlayerName) {

        Player l_enemy = GameplayController.getPlayerNameMap().get(p_targetPlayerName);
        boolean l_playerExists = l_enemy != null;
        if (!l_playerExists) {
            Console.print("Player does not exist!");
            return;
        }

        boolean l_playersAreDifferent = l_enemy.getId() != IssueOrder.getCurrentPlayer().getId();

        if (l_playersAreDifferent) {
            if (IssueOrder.getCurrentPlayer().getHandOfCards().playCard(HandOfCards.Card.DIPLOMACY)) {
                String l_confirmation = p_targetPlayerName + " will be negotiated with.";
                IssueOrder.getCurrentPlayer().addOrder(new Diplomacy(IssueOrder.getCurrentPlayer().getId(), l_enemy.getId()));
                Console.print(l_confirmation);
                IssueOrder.getCurrentPlayer().setOrderIssued();
            } else {
                Console.print("You do not have a Diplomacy card!");
            }
        } else {
            Console.print("You cannot negotiate with yourself!");
        }
    }

    /**
     * This method allows a player to commit their orders when using the 'commit' command, meaning they will no longer
     * be asked to issue orders during the current turn.
     */
    @Override
    public void commit() {
        IssueOrder.getCurrentPlayer().commit();
        IssueOrder.getCurrentPlayer().setOrderIssued();
    }

    /**
     * This method allows the player to print their available armies per country they own using the 'showtroops'
     * command.
     */
    @Override
    public void showAvailableArmies() {
        IssueOrder.showAvailableTroops(IssueOrder.getCurrentPlayer());
    }

}
