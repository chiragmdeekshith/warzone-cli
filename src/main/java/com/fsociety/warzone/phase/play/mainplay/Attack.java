package com.fsociety.warzone.phase.play.mainplay;

import com.fsociety.warzone.command.Command;
import com.fsociety.warzone.game.GameEngine;
import com.fsociety.warzone.game.mainloop.IssueOrder;
import com.fsociety.warzone.game.order.card.*;
import com.fsociety.warzone.game.order.Advance;
import com.fsociety.warzone.model.Player;
import com.fsociety.warzone.util.Console;

public class Attack extends MainPlay {

    @Override
    public void help() {
        Command[] l_validCommands = {Command.SHOW_MAP, Command.ADVANCE, Command.BOMB, Command.BLOCKADE, Command.AIRLIFT,
                Command.NEGOTIATE, Command.COMMIT, Command.SHOW_CARDS, Command.SHOW_AVAILABLE_ARMIES};
        String l_help = "Please enter one of the following commands: " +
                getValidCommands(l_validCommands) +
                "Tip - use the following general format for commands: command [arguments]";
        Console.print(l_help);
    }

    @Override
    public void advance(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {

        boolean l_playerOwnsCountry = GameEngine.getPlayMap().getCountryState(p_sourceCountryId).getPlayerId() == IssueOrder.getCurrentPlayer().getId();
        boolean l_countriesAreDifferent = p_sourceCountryId != p_targetCountryId;
        boolean l_countriesAreNeighbours = GameEngine.getPlayMap().getNeighbours().get(p_sourceCountryId).contains(p_targetCountryId);
        boolean l_sourceCountryHasEnoughTroops = IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) >= p_troopsCount;
        boolean l_sourceCountryExists = GameEngine.getPlayMap().getCountryState(p_sourceCountryId) != null;
        boolean l_targetCountryExists = GameEngine.getPlayMap().getCountryState(p_targetCountryId) != null;

        if (l_playerOwnsCountry && l_countriesAreDifferent && l_countriesAreNeighbours && l_sourceCountryHasEnoughTroops && l_sourceCountryExists && l_targetCountryExists) {
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
        if (!l_sourceCountryExists) {
            Console.print("Country " + p_sourceCountryId + " doesn't exist!");
        }
        if (!l_targetCountryExists) {
            Console.print("Country " + p_targetCountryId + " doesn't exist!");
        }
    }

    @Override
    public void bomb(int p_targetCountryId) {

        boolean l_isEnemyCountry = GameEngine.getPlayMap().getCountryState(p_targetCountryId).getPlayerId() != IssueOrder.getCurrentPlayer().getId();
        boolean l_isNeighbour = GameEngine.getPlayMap().isNeighbourOf(p_targetCountryId, IssueOrder.getCurrentPlayer().getId());
        boolean l_targetCountryExists = GameEngine.getPlayMap().getCountryState(p_targetCountryId) != null;

        if (l_isEnemyCountry && l_isNeighbour && l_targetCountryExists) {
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
        if (!l_targetCountryExists) {
            Console.print("Country " + p_targetCountryId + " doesn't exist!");
        }
    }

    @Override
    public void blockade(int p_countryId) {

        boolean l_playerOwnsCountry = GameEngine.getPlayMap().getCountryState(p_countryId).getPlayerId() == IssueOrder.getCurrentPlayer().getId();
        boolean l_countryHasTroops = IssueOrder.d_availableTroopsOnMap.get(p_countryId) > 0;
        boolean l_countryExists = GameEngine.getPlayMap().getCountryState(p_countryId) != null;

        if (l_playerOwnsCountry && l_countryHasTroops && l_countryExists) {
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
        if (!l_countryExists) {
            Console.print("Country " + p_countryId + " does not exist!");
        }
    }

    @Override
    public void airlift(int p_sourceCountryId, int p_targetCountryId, int p_troopsCount) {

        boolean l_playerOwnsCountry = GameEngine.getPlayMap().getCountryState(p_sourceCountryId).getPlayerId() == IssueOrder.getCurrentPlayer().getId();
        boolean l_countriesAreDifferent = p_sourceCountryId != p_targetCountryId;
        boolean l_sourceCountryHasEnoughTroops = IssueOrder.d_availableTroopsOnMap.get(p_sourceCountryId) >= p_troopsCount;
        boolean l_sourceCountryExists = GameEngine.getPlayMap().getCountryState(p_sourceCountryId) != null;
        boolean l_targetCountryExists = GameEngine.getPlayMap().getCountryState(p_targetCountryId) != null;

        if (l_playerOwnsCountry && l_countriesAreDifferent && l_sourceCountryHasEnoughTroops && l_sourceCountryExists && l_targetCountryExists) {
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
        if (!l_sourceCountryExists) {
            Console.print("Country " + p_sourceCountryId + " doesn't exist!");
        }
        if (!l_targetCountryExists) {
            Console.print("Country " + p_targetCountryId + " doesn't exist!");
        }

    }

    @Override
    public void negotiate(String p_targetPlayerName) {

        Player l_enemy = GameEngine.getPlayerNameMap().get(p_targetPlayerName);
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

    @Override
    public void commit() {
        IssueOrder.getCurrentPlayer().commit();
        IssueOrder.getCurrentPlayer().setOrderIssued();
    }

    @Override
    public void showAvailableArmies() {
        IssueOrder.showAvailableTroops(IssueOrder.getCurrentPlayer());
    }

}
