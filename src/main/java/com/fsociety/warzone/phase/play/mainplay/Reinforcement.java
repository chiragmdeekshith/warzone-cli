package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.game.order.Deploy;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.Console;
import com.fsociety.warzone.command.Command;

public class Reinforcement extends MainPlay {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.DEPLOY};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: " +
                getValidCommands() +
                "Tip - use the following general format for commands: command [arguments]\n";
        Console.print(help);
    }

    @Override
    public void deploy(Player l_issuer, int p_countryId, int p_troopsCount) {
        String l_confirmation = p_troopsCount + " reinforcement armies will be deployed to " + p_countryId + ".";
        l_issuer.addOrder(new Deploy(p_countryId, p_troopsCount, l_issuer.getId()));
        l_issuer.setAvailableReinforcements(l_issuer.getAvailableReinforcements() - p_troopsCount);
        Console.print(l_confirmation);
    }

}
