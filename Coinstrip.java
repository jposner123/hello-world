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
    numCoins = 3 + rng.nextInt(4);

    if(numCoins >= board.length || numCoins + 3 >= board.length){
      numCoins -= 3;
    }
    //Populate board with 1 through numCoins with at least one 0 between each
    //coin and some pairs of coins
    /*
    int n = 1;
    for(int i = 0; i < board.length; i++){
      if(rng.nextInt(2) == 1){
        board[i] = n;
        n++;
      }
      if(n == numCoins){
        i = board.length;
      }
    }
      */
    //Populate board with numCoin 1s with at least one 0 between each
    //coin and some pairs of coins
    int n = 0;
    int i = 0;
    int probNext = 2;
    while(n < numCoins){
      if(rng.nextInt(probNext) == 1 && board[i] == 0){
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
      System.out.print(i + " ");
    }
  }

  public int getPlayer(){
    //Return player number whose turn it is
    return player;
  }

  public void takeTurn(){

  }

  public boolean won(){
    return true;
  }
  public static void main(String args[]){
    //Print out contents of board
    Coinstrip b = new Coinstrip();
    b.displayBoard();
  }
}
