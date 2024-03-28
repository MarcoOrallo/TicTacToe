import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    private static final int SIZE = 5;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private boolean playerX = true;
    private int movesLeft = SIZE * SIZE;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button button = new Button();
                button.setPrefSize(80, 80);
                button.setOnAction(e -> {
                    if (!((Button) e.getSource()).getText().equals("") || movesLeft == 0) {
                        return;
                    }
                    if (playerX) {
                        ((Button) e.getSource()).setText("X");
                    } else {
                        ((Button) e.getSource()).setText("O");
                    }
                    movesLeft--;
                    if (checkWin()) {
                        endGame(playerX ? "Player X" : "Player O");
                    } else if (movesLeft == 0) {
                        endGame("Draw");
                    } else {
                        playerX = !playerX;
                    }
                });
                buttons[i][j] = button;
                grid.add(button, j, i);
            }
        }
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }

    private boolean checkWin() {
        String symbol = playerX ? "X" : "O";
        for (int i = 0; i < SIZE; i++) {
            // Check rows
            if (checkLine(buttons[i][0].getText(), buttons[i][1].getText(), buttons[i][2].getText(), buttons[i][3].getText(), buttons[i][4].getText(), symbol)) {
                return true;
            }
            // Check columns
            if (checkLine(buttons[0][i].getText(), buttons[1][i].getText(), buttons[2][i].getText(), buttons[3][i].getText(), buttons[4][i].getText(), symbol)) {
                return true;
            }
        }
        // Check diagonals
        if (checkLine(buttons[0][0].getText(), buttons[1][1].getText(), buttons[2][2].getText(), buttons[3][3].getText(), buttons[4][4].getText(), symbol)) {
            return true;
        }
        if (checkLine(buttons[0][4].getText(), buttons[1][3].getText(), buttons[2][2].getText(), buttons[3][1].getText(), buttons[4][0].getText(), symbol)) {
            return true;
        }
        return false;
    }

    private boolean checkLine(String... symbols) {
        int count = 0;
        for (String symbol : symbols) {
            if (symbol.equals(playerX ? "X" : "O")) {
                count++;
            } else {
                count = 0;
            }
            if (count == 5) {
                return true;
            }
        }
        return false;
    }

    private void endGame(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        if (winner.equals("Draw")) {
            alert.setContentText("It's a draw!");
        } else {
            alert.setContentText(winner + " wins!");
        }
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
