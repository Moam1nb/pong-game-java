package com.example.model;

public class Ball {
    private double x;
    private double y;
    private double speedX;
    private double speedY;
    private final double size;

    public Ball(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speedX = 5;
        this.speedY = 3;
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getSpeedX() { return speedX; }
    public double getSpeedY() { return speedY; }
    public double getSize() { return size; }

    // Controlled setters
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setSpeed(double speedX, double speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void move() {
        x += speedX;
        y += speedY;
    }

    public void reverseX() {
        speedX = -speedX;
    }

    public void reverseY() {
        speedY = -speedY;
    }
}