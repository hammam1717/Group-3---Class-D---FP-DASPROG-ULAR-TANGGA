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
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SnL{
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private int boardSize;
    private int gameStatus;
    private int nowPlaying;
    private int diceOption;
    private boolean gamePlayed;
    private int[] scores;
    private AudioPlayer audioPlayer;


    public SnL(int s){
        this.boardSize = s;
        this.players = new ArrayList<Player>();
        this.snakes = new ArrayList<Snake>();
        this.ladders = new ArrayList<Ladder>();
        this.gameStatus = 0;
        this.scores = new int[]{0, 0};
        this.gamePlayed = false;
        this.audioPlayer = new AudioPlayer();
    }
    public void setBoardSize(int s){
        this.boardSize = s;
    }
    public void setGameStatus(int s){
        this.gameStatus = s;
    }
    public int getGameStatus(){
        return this.gameStatus;
    }


    public void displayMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. New Game");
        System.out.println("2. Start Game");
        System.out.println("3. See Score");
        System.out.println("4. Quit");
    }

    public int getChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }



    public void play() {
        Scanner read = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getChoice(read);

            switch (choice) {
                case 1:
                    newGame(read);
                    break;
                case 2:
                    startGame(read);
                    break;
                case 3:
                    seeScore();
                    break;
                case 4:
                    running = false;
                    System.out.println("Quitting game. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        read.close();
    }

    private void seeScore() {
        if (!gamePlayed) {
            System.out.println("No game played yet. Scores are 0 - 0");
        } else {
            System.out.println("Current Scores:");
            for (Player player : players) {
                System.out.println(player.getName() + ": " + player.getScore());
            }
        }
    }
    public void newGame(Scanner read) {
        players.clear();
        System.out.println("Please enter number of players (2-4): ");
        int numPlayers = Integer.parseInt(read.nextLine());

        for (int i = 1; i <= numPlayers; i++) {
            System.out.println("Please enter Player " + i + ": ");
            String playerName = read.nextLine();
            Player player = new Player(playerName);
            addPlayer(player);
        }

        System.out.println("Choose dice option: 1 for one dice, 2 for two dice");
        this.diceOption = Integer.parseInt(read.nextLine());

        initiateGame();

        playGame(read);
        gamePlayed = true;
    }

    private void startGame(Scanner read) {
        resetPlayersPosition();

        if (players.isEmpty()) {
            System.out.println("No game played yet. Let's start with New Game first.");
            return;
        }

        this.gameStatus = 1;
        this.nowPlaying = 0;

        System.out.println("Starting game with previous players:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore());
        }

        playGame(read);
    }

    private void playGame(Scanner read) {
        Player playerInTurn;
        do {
            playerInTurn = getWhoseTurn();
            int consecutiveTurns = 0;
            boolean extraTurn;

            do {
                extraTurn = false;
                System.out.println("Now Playing " + playerInTurn.getName());
                System.out.println(playerInTurn.getName() + " please press enter to roll the dice");
                read.nextLine();

                int totalRoll = 0;
                if (diceOption == 1) {
                    totalRoll = playerInTurn.rollDice();
                    audioPlayer.playSound("./src/dice_roll1.wav");
                    System.out.println("Dice Roll: " + totalRoll);
                    if (totalRoll == 6) {
                        extraTurn = true;
                        System.out.println("You're lucky, still your turn");
                        audioPlayer.playSound("./src/double_dice.wav");
                    }
                } else if (diceOption == 2) {
                    int roll1 = playerInTurn.rollDice();
                    int roll2 = playerInTurn.rollDice();
                    audioPlayer.playSound("./src/dice_roll1.wav");
                    totalRoll = roll1 + roll2;
                    if (totalRoll == 12) {
                        extraTurn = true;
                        System.out.println("You're very lucky, still your turn");
                        audioPlayer.playSound("./src/double_dice.wav");
                    }
                    System.out.println("Dice Roll: " + roll1 + " + " + roll2 + " = " + totalRoll);
                }

                movePlayerAround(playerInTurn, totalRoll);
                System.out.println("New Position: " + playerInTurn.getPosition());
                System.out.println("==============================================");

                if (playerInTurn.getPosition() == this.boardSize) {
                    this.gameStatus = 2;
                }

                consecutiveTurns++;
            } while (extraTurn && consecutiveTurns < 3 && getGameStatus() != 2);

        } while (getGameStatus() != 2);

        if (getGameStatus() == 2) {
            playerInTurn.incrementScore();
            audioPlayer.playSound("./src/sound_winner.wav");
            System.out.println(playerInTurn.getName() + " wins this round!");
        }
        resetPlayersPosition();
    }

    private void resetPlayersPosition() {
        for (Player player : players) {
            player.resetPosition();
        }
    }

    public void addPlayer(Player s){
        this.players.add(s);
    }
    public ArrayList<Player> getPlayers(Player s){
        return this.players;
    }
    public void addSnake(Snake s){
        this.snakes.add(s);
    }

    public void addSnakes(int [][] s){
        for (int r = 0; r < s.length; r++){
            Snake snake = new Snake(s[r][0], s[r][1]);
            this.snakes.add(snake);
        }
    }

    public void addLadder(Ladder l){
        this.ladders.add(l);
    }

    public void addLadders(int [][] l){
        for (int r = 0; r < l.length; r++){
            Ladder ladder = new Ladder(l[r][1], l[r][0]);
            this.ladders.add(ladder);
        }

    }
    public int getBoardSize(){
        return this.boardSize;
    }
    public ArrayList<Snake> getSnakes(){
        return this.snakes;
    }
    public ArrayList<Ladder> getLadders(){
        return this.ladders;
    }
    public void initiateGame(){
        int [][] l = {
                {2,23},
                {8,34},
                {20,77},
                {32,68},
                {41,79},
                {74,88},
                {82,100},
                {85,95}

        };
        addLadders(l);

        int[][] s = {
                {5, 47},
                {9, 29},
                {15, 38},
                {25, 97},
                {33, 53},
                {37, 62},
                {54, 86},
                {70, 92}
        };

        addSnakes(s);
    }

    public void movePlayerAround(Player p, int x){
        p.moveAround(x, this.boardSize);
        for(Ladder l:this.ladders){
            if(p.getPosition()== l.getBottomPosition()) {
                System.out.println(p.getName() + "you got Ladder from: " + l.getBottomPosition() + " To: " + l.getTopPosition());
                audioPlayer.playSound("./src/up_stairs.wav");
                p.setPosition(l.getTopPosition());
            }
        }
        for(Snake s:this.snakes){
            if(p.getPosition()== s.getHeadPosition()){
                p.setPosition(s.getTailPosition());
                System.out.println(p.getName()+" you get snake head from "+ s.getHeadPosition() + " slide down to " + s.getTailPosition());
                audioPlayer.playSound("./src/cry.wav");
            }
        }
        if(p.getPosition()==this.boardSize){
            this.gameStatus=2;
        }
    }

    public Player getWhoseTurn(){

        if(this.gameStatus==0){
            this.gameStatus=1;
            double r= Math.random();
            if(r<=0.5){
                this.nowPlaying = 0;
                return this.players.get(0);
            }
            else {
                this.nowPlaying = 1;
                return this.players.get(1);
            }
        }
        else{
            if(this.nowPlaying == 0){
                this.nowPlaying = 1;
                return this.players.get(1);
            }

            else {
                this.nowPlaying = 0;
                return this.players.get(0);

            }
        }
    }
}