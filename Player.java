/**
 * -----------------------------------------------------
 * ES234211 - Programming Fundamental
 * Genap - 2023/2024
 * Group Capstone Project: Snake and Ladder Game
 * -----------------------------------------------------
 * Class    : D
 * Group    : 3
 * Members  :
 * 1. 5026231179 - Muhammad Hammam Aditama
 * 2. 5026231127 - Rafi Zahfran Putra Wibowo
 * 3. 5026231021 - Zaskia Muazatun Mahmud
 * ------------------------------------------------------
 */
public class Player {
    private String name;
    private int position;
    private int score;

    Player(String name){
        this.name=name;
        this.position=0;
        this.score = 0;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPosition(int position){
        this.position = position;
    }
    public String getName(){
        return this.name;
    }
    public int getPosition(){
        return this.position;
    }

    public int rollDice() {
        return (int)(Math.random()*6+1);
    }

    public void moveAround(int y, int boardSize) {
        int newPosition = this.position + y;
        if (newPosition > boardSize) {
            this.position = boardSize - (newPosition - boardSize);
        } else {
            this.position = newPosition;
        }
    }
    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }
    public void resetPosition() {
        this.position = 0;
    }
}