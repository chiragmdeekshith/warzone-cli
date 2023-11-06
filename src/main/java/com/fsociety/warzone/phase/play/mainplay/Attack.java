package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.game.mainloop.IssueOrder;
import com.fsociety.warzone.game.order.card.Airlift;
import com.fsociety.warzone.game.order.card.Blockade;
import com.fsociety.warzone.game.order.card.Bomb;
import com.fsociety.warzone.game.order.card.Diplomacy;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.game.order.Advance;
import com.fsociety.warzone.util.Console;

public class Attack extends MainPlay {

    Command[] d_validCommands = {Command.SHOW_MAP, Command.ADVANCE, Command.BOMB, Command.BLOCKADE, Command.AIRLIFT,
            Command.NEGOTIATE, Command.COMMIT};

    @Override
    public void help() {
        String help = "Please enter one of the following commands: \n" + getValidCommands() +
                "Tip - use the following general format for commands: command [arguments]";
        Console.print(help);
    }

    @Override
    public void advance(Player p_issuer, int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        String l_confirmation = p_troopsCount + " will advance to " + p_targetCountryId + " from " + p_sourceCountryId + ".";
        IssueOrder.d_availableTroopsOnMap.put(p_sourceCountryId, IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) - p_troopsCount);
        p_issuer.addOrder(new Advance(p_sourceCountryId, p_targetCountryId, p_troopsCount, p_issuer.getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void bomb(Player p_issuer, int p_targetCountryId) {
        String l_confirmation = p_targetCountryId + " will be bombed.";
        p_issuer.addOrder(new Bomb(p_targetCountryId, p_issuer.getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void blockade(Player p_issuer, int p_countryId) {
        String l_confirmation = p_countryId + " will be blockaded.";
        p_issuer.addOrder(new Blockade(p_countryId, p_issuer.getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void airlift(Player p_issuer, int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        String l_confirmation = p_troopsCount + " will be airlifted to " + p_targetCountryId + " from " + p_sourceCountryId + ".";
        IssueOrder.d_availableTroopsOnMap.put(p_sourceCountryId, IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) - p_troopsCount);
        p_issuer.addOrder(new Airlift(p_sourceCountryId, p_targetCountryId, p_troopsCount, p_issuer.getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void negotiate(Player p_issuer, int p_targetPlayerId) {
        String l_confirmation = p_targetPlayerId + " will be negotiated with.";
        p_issuer.addOrder(new Diplomacy(p_issuer.getId(), p_targetPlayerId));
        Console.print(l_confirmation);
    }

    @Override
    public void commit(Player p_issuer) {
        p_issuer.commit();
    }
}
