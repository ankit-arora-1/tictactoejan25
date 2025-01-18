import controller.GameController;
import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountException;
import models.*;
import strategies.ColWinningStrategy;
import strategies.DiagWinningStrategy;
import strategies.RowWinningStrategy;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws PlayerCountException, DuplicateSymbolException {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        List<Player> players = new ArrayList<>();
        players.add(
                new Player(1L,
                        new Symbol('X'),
                        "Nimish",
                        PlayerType.HUMAN)
        );

        players.add(
                new Bot(2L,
                        new Symbol('O'),
                        "Subhajit", BotDifficultyLevel.EASY)

        );

        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new ColWinningStrategy());
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new DiagWinningStrategy());

        Game game = gameController.startGame(players, 3, winningStrategies);

        while(gameController.checkStatus(game) == GameState.IN_PROGRESS) {
            gameController.printBoard(game);

            System.out.println("Do you want to undo? (y/n");
            String answer = scanner.next();

            if(answer.equalsIgnoreCase("y")) {
                gameController.undo(game);
                continue;
            }


            gameController.makeMove(game);
        }

        gameController.printBoard(game);

        if(gameController.checkStatus(game) == GameState.WINNER) {
            System.out.println("Winner is: " + gameController.getWinner(game).getName());
        } else {
            System.out.println("Game has drawn");
        }
    }
}