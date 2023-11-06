package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.game.order.card.Bomb;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.game.order.Advance;
import com.fsociety.warzone.util.Console;

public class Attack extends MainPlay {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.ADVANCE, Command.BOMB, Command.BLOCKADE, Command.AIRLIFT,
            Command.NEGOTIATE, Command.COMMIT};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: " +
                getValidCommands() +
                "Tip - use the following general format for commands: command [arguments]";
        Console.print(help);
    }

    @Override
    public void advance(Player l_issuer, int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        String l_confirmation = p_troopsCount + " will advance to " + p_targetCountryId + " from " + p_sourceCountryId + ".";
        l_issuer.addOrder(new Advance(p_sourceCountryId, p_targetCountryId, p_troopsCount, l_issuer.getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void bomb(Player l_issuer, int p_targetCountryId) {
        String l_confirmation = p_targetCountryId + " will be bombed.";
        l_issuer.addOrder(new Bomb(p_targetCountryId, l_issuer.getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void blockade() {

    }

    @Override
    public void airlift() {

    }

    @Override
    public void negotiate() {

    }

    @Override
    public void commit() {

    }
}
