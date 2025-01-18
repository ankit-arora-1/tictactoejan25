package controller;

import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountException;
import models.Game;
import models.GameState;
import models.Player;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    public Game startGame(List<Player> players,
                          int size,
                          List<WinningStrategy> winningStrategies) throws PlayerCountException, DuplicateSymbolException {

        return Game.getBuilder()
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .setSize(size)
                .build();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public GameState checkStatus(Game game) {
        return game.getGameState();
    }

    public void printBoard(Game game) {
        game.printBoard();
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }

    public void undo(Game game) {
        game.undo();
    }
}
