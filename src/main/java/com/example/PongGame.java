package com.example;

import java.util.HashSet;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PongGame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 15;
    private static final int PADDLE_SPEED = 8;
    
    private double player1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private double player2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private double ballX = WIDTH / 2;
    private double ballY = HEIGHT / 2;
    private double ballSpeedX = 5;
    private double ballSpeedY = 3;
    
    private Set<KeyCode> keysPressed = new HashSet<>();
    private Canvas canvas;
    private GraphicsContext gc;
    
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        setupInputHandlers(scene);
        
        primaryStage.setTitle("Pong Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        startGameLoop();
    }
    
    private void setupInputHandlers(Scene scene) {
        scene.setOnKeyPressed(e -> keysPressed.add(e.getCode()));
        scene.setOnKeyReleased(e -> keysPressed.remove(e.getCode()));
    }
    
    private void startGameLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        }.start();
    }
    
    private void update() {
        // Player 1 controls (W/S keys)
        if (keysPressed.contains(KeyCode.W) && player1Y > 0) {
            player1Y -= PADDLE_SPEED;
        }
        if (keysPressed.contains(KeyCode.S) && player1Y < HEIGHT - PADDLE_HEIGHT) {
            player1Y += PADDLE_SPEED;
        }
        
        // Player 2 controls (Up/Down arrows)
        if (keysPressed.contains(KeyCode.UP) && player2Y > 0) {
            player2Y -= PADDLE_SPEED;
        }
        if (keysPressed.contains(KeyCode.DOWN) && player2Y < HEIGHT - PADDLE_HEIGHT) {
            player2Y += PADDLE_SPEED;
        }
        
        // Update ball position
        ballX += ballSpeedX;
        ballY += ballSpeedY;
        
        // Ball collision with walls
        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballSpeedY = -ballSpeedY;
        }
        
        // Ball collision with paddles
        if (ballX <= PADDLE_WIDTH && ballY + BALL_SIZE >= player1Y && ballY <= player1Y + PADDLE_HEIGHT) {
            ballSpeedX = Math.abs(ballSpeedX);
        }
        
        if (ballX >= WIDTH - PADDLE_WIDTH - BALL_SIZE && 
            ballY + BALL_SIZE >= player2Y && ballY <= player2Y + PADDLE_HEIGHT) {
            ballSpeedX = -Math.abs(ballSpeedX);
        }
        
        // Reset ball if it goes out
        if (ballX < 0 || ballX > WIDTH) {
            ballX = WIDTH / 2;
            ballY = HEIGHT / 2;
            ballSpeedX = (Math.random() > 0.5 ? 1 : -1) * 5;
            ballSpeedY = (Math.random() - 0.5) * 6;
        }
    }
    
    private void render() {
        // Clear canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        
        // Draw center line
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        for (int i = 0; i < HEIGHT; i += 20) {
            gc.strokeLine(WIDTH / 2, i, WIDTH / 2, i + 10);
        }
        
        // Draw paddles
        gc.setFill(Color.WHITE);
        gc.fillRect(0, player1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        gc.fillRect(WIDTH - PADDLE_WIDTH, player2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        
        // Draw ball
        gc.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
        
        // Draw scores
        gc.fillText("Use W/S and UP/DOWN keys", WIDTH / 2 - 80, 30);
    }
}