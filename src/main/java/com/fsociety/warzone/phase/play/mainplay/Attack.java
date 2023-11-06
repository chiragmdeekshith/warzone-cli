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
    public void advance(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        String l_confirmation = p_troopsCount + " will advance to " + p_targetCountryId + " from " + p_sourceCountryId + ".";
        IssueOrder.d_availableTroopsOnMap.put(p_sourceCountryId, IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) - p_troopsCount);
        IssueOrder.getCurrentPlayer().addOrder(new Advance(p_sourceCountryId, p_targetCountryId, p_troopsCount, IssueOrder.getCurrentPlayer().getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void bomb(int p_targetCountryId) {
        String l_confirmation = p_targetCountryId + " will be bombed.";
        IssueOrder.getCurrentPlayer().addOrder(new Bomb(p_targetCountryId, IssueOrder.getCurrentPlayer().getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void blockade(int p_countryId) {
        String l_confirmation = p_countryId + " will be blockaded.";
        IssueOrder.getCurrentPlayer().addOrder(new Blockade(p_countryId, IssueOrder.getCurrentPlayer().getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void airlift(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {
        String l_confirmation = p_troopsCount + " will be airlifted to " + p_targetCountryId + " from " + p_sourceCountryId + ".";
        IssueOrder.d_availableTroopsOnMap.put(p_sourceCountryId, IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) - p_troopsCount);
        IssueOrder.getCurrentPlayer().addOrder(new Airlift(p_sourceCountryId, p_targetCountryId, p_troopsCount, IssueOrder.getCurrentPlayer().getId()));
        Console.print(l_confirmation);
    }

    @Override
    public void negotiate(int p_targetPlayerId) {
        String l_confirmation = p_targetPlayerId + " will be negotiated with.";
        IssueOrder.getCurrentPlayer().addOrder(new Diplomacy(IssueOrder.getCurrentPlayer().getId(), p_targetPlayerId));
        Console.print(l_confirmation);
    }

    @Override
    public void commit() {
        IssueOrder.getCurrentPlayer().commit();
    }
}
