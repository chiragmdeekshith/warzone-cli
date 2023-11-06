package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.order.Deploy;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.Console;
import com.fsociety.warzone.command.Command;

public class Reinforcement extends MainPlay {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.DEPLOY};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: \n" + getValidCommands() +
                "Tip - use the following general format for commands: command [arguments]\n";
        Console.print(help);
    }

    @Override
    public void deploy(Player p_issuer, int p_countryId, int p_troopsCount) {
        String l_confirmation = p_troopsCount + " reinforcement armies will be deployed to " + p_countryId + ".";
        GameEngine.getPlayers().get(p_issuer.getId()).setAvailableReinforcements(GameEngine.getPlayers().get(p_issuer.getId()).getAvailableReinforcements() - p_troopsCount);
        p_issuer.addOrder(new Deploy(p_countryId, p_troopsCount, p_issuer.getId()));
        Console.print(l_confirmation);
    }

}
