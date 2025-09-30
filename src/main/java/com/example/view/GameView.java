package com.example.view;

import com.example.model.GameState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final GameState gameState;

    public GameView(GameState gameState) {
        this.gameState = gameState;
        this.canvas = new Canvas(gameState.getWidth(), gameState.getHeight());
        this.gc = canvas.getGraphicsContext2D();
    }

    public Pane getRoot() {
        Pane root = new Pane();
        root.getChildren().add(canvas);
        return root;
    }

    public void render() {
        clearCanvas();
        drawCenterLine();
        drawPaddles();
        drawBall();
        drawScores();
        drawInstructions();
    }

    private void clearCanvas() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameState.getWidth(), gameState.getHeight());
    }

    private void drawCenterLine() {
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        for (int i = 0; i < gameState.getHeight(); i += 20) {
            gc.strokeLine(gameState.getWidth() / 2, i, gameState.getWidth() / 2, i + 10);
        }
    }

    private void drawPaddles() {
        gc.setFill(Color.WHITE);
        gc.fillRect(
            gameState.getPlayer1().getX(), 
            gameState.getPlayer1().getY(), 
            gameState.getPlayer1().getWidth(), 
            gameState.getPlayer1().getHeight()
        );
        gc.fillRect(
            gameState.getPlayer2().getX(), 
            gameState.getPlayer2().getY(), 
            gameState.getPlayer2().getWidth(), 
            gameState.getPlayer2().getHeight()
        );
    }

    private void drawBall() {
        gc.setFill(Color.WHITE);
        gc.fillOval(
            gameState.getBall().getX(), 
            gameState.getBall().getY(), 
            gameState.getBall().getSize(), 
            gameState.getBall().getSize()
        );
    }

    private void drawScores() {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));
        gc.fillText("Player 1: " + gameState.getScore1(), 50, 30);
        gc.fillText("Player 2: " + gameState.getScore2(), gameState.getWidth() - 150, 30);
    }

    private void drawInstructions() {
        gc.setFont(Font.font(14));
        gc.fillText("Use W/S and UP/DOWN keys", gameState.getWidth() / 2 - 80, gameState.getHeight() - 20);
    }
}