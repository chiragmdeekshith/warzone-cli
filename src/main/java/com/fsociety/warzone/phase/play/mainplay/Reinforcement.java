package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.mainloop.IssueOrder;
import com.fsociety.warzone.game.order.Deploy;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.Console;
import com.fsociety.warzone.command.Command;

public class Reinforcement extends MainPlay {

    @Override
    public void help() {
        Command[] l_validCommands = {Command.SHOW_MAP, Command.DEPLOY};
        String help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands) +
                "Tip - use the following general format for commands: command [arguments]\n";
        Console.print(help);
    }

    @Override
    public void deploy(int p_countryId, int p_troopsCount) {
        boolean l_playerOwnsCountry = GameEngine.getPlayMap().getCountryState(p_countryId).getPlayerId() == IssueOrder.getCurrentPlayer().getId();
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
