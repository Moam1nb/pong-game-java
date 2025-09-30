package com.example.model;



public class GameState {
    private final Paddle player1;
    private final Paddle player2;
    private final Ball ball;
    private int score1;
    private int score2;
    private final double width;
    private final double height;

    // Constants
    public static final double PADDLE_WIDTH = 15;
    public static final double PADDLE_HEIGHT = 100;
    public static final double BALL_SIZE = 15;

    public GameState(double width, double height) {
        this.width = width;
        this.height = height;
        
        this.player1 = new Paddle(0, height / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.player2 = new Paddle(width - PADDLE_WIDTH, height / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.ball = new Ball(width / 2, height / 2, BALL_SIZE);
        
        this.score1 = 0;
        this.score2 = 0;
    }

    // Getters only - external classes can't modify state directly
    public Paddle getPlayer1() { return player1; }
    public Paddle getPlayer2() { return player2; }
    public Ball getBall() { return ball; }
    public int getScore1() { return score1; }
    public int getScore2() { return score2; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    // Controlled methods to modify state
    public void incrementScore1() { score1++; }
    public void incrementScore2() { score2++; }

    public void resetBall() {
        ball.setPosition(width / 2, height / 2);
        ball.setSpeed((Math.random() > 0.5 ? 1 : -1) * 5, (Math.random() - 0.5) * 6);
    }

    // Game rule methods
    public boolean isBallOutLeft() { return ball.getX() < 0; }
    public boolean isBallOutRight() { return ball.getX() > width; }
    public boolean isBallHittingTopWall() { return ball.getY() <= 0; }
    public boolean isBallHittingBottomWall() { return ball.getY() >= height - ball.getSize(); }
    
    public boolean isBallHittingPaddle1() {
        return ball.getX() <= PADDLE_WIDTH && 
               ball.getY() + ball.getSize() >= player1.getY() && 
               ball.getY() <= player1.getY() + PADDLE_HEIGHT;
    }
    
    public boolean isBallHittingPaddle2() {
        return ball.getX() >= width - PADDLE_WIDTH - ball.getSize() && 
               ball.getY() + ball.getSize() >= player2.getY() && 
               ball.getY() <= player2.getY() + PADDLE_HEIGHT;
    }
}