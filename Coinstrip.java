import java.util.Random;
import java.util.Scanner;

public class Coinstrip{

  private int board[];
  private int player;
  private int numCoins;
  private static Random rng = new Random();
  private static Scanner in = new Scanner(System.in);

  public Coinstrip(){
    //Initialize board
    board = new int[5 + rng.nextInt(11)];
    if(board.length < 7){
      numCoins = 3;
    } else if (board.length < 9){
      numCoins = 4;
    } else if (board.length < 11){
      numCoins = 5;
    } else if (board.length < 13){
      numCoins = 6;
    } else {
      numCoins = 7;
    }

    player = 1;

    //Populate board with numCoin 1s with at least one 0 between each
    //coin and some pairs of coins

    int n = 0;
    int i = 0;
    while(n < numCoins){
      if(rng.nextInt(2) == 1 && board[i] == 0){
        board[i] = 1;
        i++;
        n++;
      } else{
        i++;
      }
      if(i >= board.length - 1){
        i = 0;
        System.out.println("x");
      }
    }

    //Get rid of long sequences of coins
    for(int x = 0; x < board.length; x++){
      if(board[x] != 0 && board[x + 1] != 0 && board[x + 2] != 0){
        board[x + rng.nextInt(3)] = 0;
      }
      System.out.println(board[x]);
    }

    //Assign locations to coins
    int y = 1;
    for(int z = 0; z < board.length - 1; z++){
      if(board[z] == 1){
        board[z] = y;
        y++;
      }
    }
  }

  public void displayBoard(){
    //Print out representation of current game board
    for(int i: board){
      System.out.print(" | " + i + " |");
    }
  }

  public int getPlayer(){
    //Return player number whose turn it is
    return player;
  }

  public void takeTurn(){
    //Accept input from user and allow players 1 and 2 to (1) move a specificed coin
    //(2) a certain number of spaces
    int coin = 0;
    int numMoves = 0;
    boolean error = false;
    int coinLoc = 0;

    do {
      error = false;
      System.out.println();
      System.out.println("Player " + player + ", choose which coin to move:");
      coin = in.nextInt();

      //Check if coin exists in board
      boolean coinExists = false;
      for(int i = 1; i < numCoins+1; i++){
        if(coin == i){
          i = numCoins;
          coinExists = true;
        }
      }

      //Find location of selected coin
      if(!error){
        for(int i = 0; i < board.length; i++){
          if(board[i] == coin){
            coinLoc = i;
          }
        }
      }
      if(!coinExists){
        System.out.println("That coin doesn't exist on the game board!");
        error = true;
      }

      //Check if coin has another coin to its left
      if(!error && (coinLoc == 0 || board[coinLoc - 1] != 0)){
        System.out.println("You can't move this coin!");
        error = true;
      }
    } while(error);

    do {
      error = false;
      System.out.println("Choose the number of spaces to move this coin:");
      numMoves = in.nextInt();

      //Check that the move is nonzero and won't cause an outOfBound error
      if(numMoves < 1 || (coinLoc - numMoves) < 0){
        System.out.println("This move doesn't make sense! It would take you off the game board or is zero");
        error = true;
      }

      //Check if the move will land the coin on a space which already holds another coin
      if(!error && board[coinLoc - numMoves] != 0){
        System.out.println("Two coins can't occupy the same space!");
        error = true;
      }

      //Check that the coin doesn't pass another
      if(!error){
        int nextCoinLoc = 0;
        for(int i = 0; i < board.length; i++){
          if(coin == 1){
            nextCoinLoc = 0;
            i = board.length;
          }
          if(coin - 1 == board[i]){
            nextCoinLoc = i;
            i = board.length;
          }
        }

        if(numMoves >= (coinLoc - nextCoinLoc)){
          System.out.println("Two coins can't pass!");
          error = true;
        }
      }
    } while(error);

    //Edit board according to above variables
    board[coinLoc - numMoves] = coin;
    board[coinLoc] = 0;

    //Change turn
    if(player == 1){
      player = 2;
    } else {
      player = 1;
    }

  }

  public boolean won(){;
    //Look through board and see if the first numCoins spaces are nonzero
    int z = numCoins;
    boolean won = false;
    for(int i = 0; i < board.length; i++){
      if(board[i] == i+1){
        z--;
      }
      if(z == 0){
        won = true;
      }
    }
    return won;
  }
  public static void main(String args[]){
    //Print out contents of board
    Coinstrip b = new Coinstrip();
    boolean won = false;
    while(!won){
      b.displayBoard();
      b.takeTurn();
      won = b.won();
    }
    System.out.println("player " + (b.getPlayer() - 1) + " won");
  }
}
