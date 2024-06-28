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

public class Ladder{
    private int topPosition;
    private int bottomPosition;

    public Ladder(int t, int b){
        this.topPosition = t;
        this.bottomPosition = b;
    }

    public void setTopPosition(int t){
        this.topPosition = t;
    }

    public void setBottomPosition(int b){
        this.bottomPosition = b;
    }

    public int getTopPosition() {
        return this.topPosition ;
    }

    public int getBottomPosition() {
        return this.bottomPosition ;
    }
}