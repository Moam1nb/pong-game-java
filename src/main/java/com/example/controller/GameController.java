package com.example.controller;

import java.util.HashSet;
import java.util.Set; // IMPORT ADDED

import com.example.model.GameState;
import com.example.model.Paddle;
import com.example.view.GameView;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class GameController {
    private final GameState gameState;
    private final GameView gameView;
    private final Set<KeyCode> keysPressed;
    private AnimationTimer gameLoop;

    public GameController(GameState gameState, GameView gameView) {
        this.gameState = gameState;
        this.gameView = gameView;
        this.keysPressed = new HashSet<>();
    }

    public void initialize(Scene scene) {
        setupInputHandlers(scene);
        startGameLoop();
    }

    private void setupInputHandlers(Scene scene) {
        scene.setOnKeyPressed(e -> keysPressed.add(e.getCode()));
        scene.setOnKeyReleased(e -> keysPressed.remove(e.getCode()));
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                processInput();
                updateGame();
                gameView.render();
            }
        };
        gameLoop.start();
    }

    private void processInput() {
        // Player 1 controls (W/S keys)
        if (keysPressed.contains(KeyCode.W)) {
            movePaddleUp(gameState.getPlayer1());
        }
        if (keysPressed.contains(KeyCode.S)) {
            movePaddleDown(gameState.getPlayer1());
        }

        // Player 2 controls (Up/Down arrows)
        if (keysPressed.contains(KeyCode.UP)) {
            movePaddleUp(gameState.getPlayer2());
        }
        if (keysPressed.contains(KeyCode.DOWN)) {
            movePaddleDown(gameState.getPlayer2());
        }
    }

    private void movePaddleUp(Paddle paddle) {
        double newY = paddle.getY() - paddle.getSpeed();
        if (newY >= 0) {
            paddle.setY(newY);
        }
    }

    private void movePaddleDown(Paddle paddle) {
        double newY = paddle.getY() + paddle.getSpeed();
        double maxY = gameState.getHeight() - paddle.getHeight();
        if (newY <= maxY) {
            paddle.setY(newY);
        }
    }

    private void updateGame() {
        // Update ball position
        gameState.getBall().move();

        // Check collisions
        checkWallCollisions();
        checkPaddleCollisions();
        checkScoring();
    }

    private void checkWallCollisions() {
        if (gameState.isBallHittingTopWall() || gameState.isBallHittingBottomWall()) {
            gameState.getBall().reverseY();
        }
    }

    private void checkPaddleCollisions() {
        if (gameState.isBallHittingPaddle1() || gameState.isBallHittingPaddle2()) {
            gameState.getBall().reverseX();
            // Add some randomness for more interesting gameplay
            gameState.getBall().setSpeed(
                gameState.getBall().getSpeedX(),
                gameState.getBall().getSpeedY() + (Math.random() - 0.5) * 2
            );
        }
    }

    private void checkScoring() {
        if (gameState.isBallOutLeft()) {
            gameState.incrementScore2();
            gameState.resetBall();
        } else if (gameState.isBallOutRight()) {
            gameState.incrementScore1();
            gameState.resetBall();
        }
    }

    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}