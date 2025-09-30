package com.example;

import com.example.controller.GameController;
import com.example.model.GameState;
import com.example.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    private GameController gameController;
    
    @Override
    public void start(Stage primaryStage) {
        // 1. Create Model (game data)
        GameState gameState = new GameState(WIDTH, HEIGHT);
        
        // 2. Create View (UI presentation)
        GameView gameView = new GameView(gameState);
        
        // 3. Create Controller (game logic)
        gameController = new GameController(gameState, gameView);
        
        // 4. Set up JavaFX window (Main handles UI framework)
        Scene scene = new Scene(gameView.getRoot(), WIDTH, HEIGHT);
        setupStage(primaryStage, scene);
        
        // 5. Initialize game (Controller handles game-specific setup)
        gameController.initialize(scene);
    }
    
    private void setupStage(Stage primaryStage, Scene scene) {
        primaryStage.setTitle("Pong Game - Professional MVC Architecture");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        if (gameController != null) {
            gameController.stop();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}