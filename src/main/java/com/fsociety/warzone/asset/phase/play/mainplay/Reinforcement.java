package com.fsociety.warzone.asset.phase.play.mainplay;

import com.fsociety.warzone.asset.command.Command;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.controller.gameplay.IssueOrder;
import com.fsociety.warzone.asset.order.Deploy;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.view.Console;

/**
 * This Class implements the commands that are valid during the Assign Reinforcements phase of gameplay.
 */
public class Reinforcement extends MainPlay {

    /**
     * This method compiles and prints a help message of valid commands for the MainPlay phase when the 'help'
     * command is entered.
     */
    @Override
    public void help() {
        Command[] l_validCommands = {Command.SHOW_MAP, Command.DEPLOY, Command.BACK, Command.EXIT, Command.SHOW_PLAYERS};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands) +
                "Tip - use the following general format for commands: command [arguments]\n";
        Console.print(l_help);
    }

    /**
     * This method creates and adds an order of type Deploy to the player's list of orders when they enter the
     * 'deploy' command during the Reinforcement phase. The method checks any relevant conditions and prints an
     * error message if the order cannot be created.
     * @param p_countryId the country to be deployed to
     * @param p_troopsCount the number of troops to be deployed
     */
    @Override
    public void deploy(int p_countryId, int p_troopsCount) {

        boolean l_countryExists = GameplayController.getPlayMap().getCountryState(p_countryId) != null;
        if (!l_countryExists) {
            Console.print("Country " + p_countryId + " does not exist!");
            return;
        }

        boolean l_playerOwnsCountry = GameplayController.getPlayMap().getCountryState(p_countryId).getPlayerId() == IssueOrder.getCurrentPlayer().getId();
        boolean l_playerHasEnoughReinforcements = IssueOrder.getCurrentPlayer().getAvailableReinforcements() >= p_troopsCount;

        if (l_playerOwnsCountry && l_playerHasEnoughReinforcements) {
            Player l_currentPlayer = IssueOrder.getCurrentPlayer();
            String l_confirmation = p_troopsCount + " reinforcement armies will be deployed to " + p_countryId + ".";
            l_currentPlayer.setAvailableReinforcements(l_currentPlayer.getAvailableReinforcements() - p_troopsCount);
            l_currentPlayer.addOrder(new Deploy(p_countryId, p_troopsCount, l_currentPlayer.getId()));
            Console.print(l_confirmation);
            IssueOrder.getCurrentPlayer().setOrderIssued();
            return;
        }

        if (!l_playerOwnsCountry) {
            Console.print("You do not own this country!");
        }
        if (!l_playerHasEnoughReinforcements) {
            Console.print("Insufficient reinforcements!");
        }
    }

}