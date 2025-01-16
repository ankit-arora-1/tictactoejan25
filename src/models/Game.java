package models;

import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountException;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextMovePlayerIndex;
    private List<WinningStrategy> winningStrategies;

    private Game(List<Player> players,
                int size,
                List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.board = new Board(size);
        this.winningStrategies = winningStrategies;
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
    }

    public static class Builder {
        private List<Player> players;
        private int size;
        private List<WinningStrategy> winningStrategies;

        public Builder() {
            players = new ArrayList<>();
            winningStrategies = new ArrayList<>();
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        // TODO: Add more validations to check if input is correct or not
        // TODO: Move validation logic to a different class
        public void validatePlayerCount() throws PlayerCountException {
            if(players.size() != size - 1) {
                throw new PlayerCountException();
            }
        }

        public void validateSymbolUniqueness() throws DuplicateSymbolException {
            Map<Symbol, Integer> countMap = new HashMap<>();
            for(Player player: players) {
                Symbol symbol = player.getSymbol();
                countMap.put(symbol,
                        countMap.getOrDefault(symbol, 0) + 1);

                if(countMap.get(symbol) > 1) {
                    throw new DuplicateSymbolException();
                }
            }
        }

        public void validateBotCount() {
            int botCount = 0;
            for(Player player: players) {
                if(player.getPlayerType() == PlayerType.BOT) {
                    botCount++;
                }
            }

            if(botCount > 1) {
                // TODO: Add custom exception here
                throw new RuntimeException();
            }
        }

        public void validate() throws PlayerCountException, DuplicateSymbolException {
            validateBotCount();
            validatePlayerCount();
            validateSymbolUniqueness();
        }

        public Game build() throws PlayerCountException, DuplicateSymbolException {
            validate();
            return new Game(players, size, winningStrategies);
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }
}
