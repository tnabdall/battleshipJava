package Main;

import java.util.Random;
import java.util.Vector;

/** The probability class to calculate the highest probability of there being a ship on a location
 * on the gameboard. The Main.AI uses this class for the challenge difficulty.
 *
 */
public class Probability {


    private PlayerBoard prob; // The playerboard
    private int[][] probBoard = new int[10][10]; //The board with probability calculations
    private Ship[] ships; // The list of ship objects
    private Vector<int[]> highestProbList = new Vector<int[]>();


    /** The constructor for the probability class.
     * @param prob is the player's board.
     */
    public Probability(PlayerBoard prob) {
        this.prob = prob;
        this.ships = prob.getShips();
    }

    /** The method to test if a location in the board can be played on.
     * @param row is the row of the location
     * @param col is the column of the location
     * @return if the location on the board is playable.
     */
    private boolean canPlay(int row, int col) {
        return (prob.locStatus(row, col) == 0 || prob.locStatus(row, col) == 3);
    }

    /** The method to calculate probability of there being a ship on a location on the board.
     * It checks all the lengths of the ships that are still alive. It checks the length of the
     * ships that are alive from that location in all four directions. If all the locations in one direction
     * are playable for that ship's length then that location gets 1 point in probability.
     * The location with the highest probability is then returned to the Main.AI.
     * @return the coordinates of the location with the highest probability of there being a ship.
     */
    public int[] setCoordToPlay() {
        int probability = 0;
        int highest = 0;


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                if (canPlay(i, j)) {
                    probability++;

                    if (ships[4].isAlive(prob)) {
                        if (canPlay(i - 1, j)) {
                            probability++;
                        }
                        if (canPlay(i + 1, j)) {
                            probability++;
                        }
                        if (canPlay(i, j - 1)) {
                            probability++;
                        }
                        if (canPlay(i, j + 1)) {
                            probability++;
                        }
                    }

                    if (ships[3].isAlive(prob)) {
                        if (canPlay(i - 2, j) &&
                                canPlay(i - 2, j)) {
                            probability++;
                        }
                        if (canPlay(i + 2, j) &&
                                canPlay(i + 2, j)) {
                            probability++;
                        }
                        if (canPlay(i, j - 2) &&
                                canPlay(i, j - 2)) {
                            probability++;
                        }
                        if (canPlay(i, j + 2) &&
                                canPlay(i, j + 2)) {
                            probability++;
                        }
                    }

                    if (ships[2].isAlive(prob)) {
                        if (canPlay(i - 2, j) &&
                                canPlay(i - 1, j)) {
                            probability++;
                        }
                        if (canPlay(i + 2, j) &&
                                canPlay(i + 1, j)) {
                            probability++;
                        }
                        if (canPlay(i, j - 2) &&
                                canPlay(i, j - 1)) {
                            probability++;
                        }
                        if (canPlay(i, j + 2) &&
                                canPlay(i, j + 1)) {
                            probability++;
                        }
                    }
                    //Battleship
                    if (ships[1].isAlive(prob)) {
                        if (canPlay(i - 3, j) &&
                                canPlay(i - 2, j) &&
                                canPlay(i - 1, j)) {
                            probability++;
                        }
                        if (canPlay(i + 3, j) &&
                                canPlay(i + 2, j) &&
                                canPlay(i + 1, j)) {
                            probability++;
                        }
                        if (canPlay(i, j - 3) &&
                                canPlay(i, j - 2) &&
                                canPlay(i, j - 1)) {
                            probability++;
                        }
                        if (canPlay(i, j + 3) &&
                                canPlay(i, j + 2) &&
                                canPlay(i, j + 1)) {
                            probability++;
                        }
                    }
                    //Aircraft carrier
                    if (ships[0].isAlive(prob)) {
                        if (canPlay(i - 4, j) &&
                                canPlay(i - 3, j) &&
                                canPlay(i - 2, j) &&
                                canPlay(i - 1, j)) {
                            probability++;
                        }
                        if (canPlay(i + 4, j) &&
                                canPlay(i + 3, j) &&
                                canPlay(i + 2, j) &&
                                canPlay(i + 1, j)) {
                            probability++;
                        }
                        if (canPlay(i, j - 4) &&
                                canPlay(i, j - 3) &&
                                canPlay(i, j - 2) &&
                                canPlay(i, j - 1)) {
                            probability++;
                        }
                        if (canPlay(i, j + 4) &&
                                canPlay(i, j + 3) &&
                                canPlay(i, j + 2) &&
                                canPlay(i, j + 1)) {
                            probability++;
                        }

                    }
                    probBoard[i][j] = probability;
                    probability = 0;
                }
                else{
                    probBoard[i][j]=0;
                    probability=0;
                }
                if (probBoard[i][j] > highest) {
                    highest = probBoard[i][j];
                    highestProbList.clear();
                    int[] newCoords = new int[2];
                    newCoords[0] = i;
                    newCoords[1] = j;
                    highestProbList.add(newCoords);
                }
                else if (probBoard[i][j] == highest){
                    int[] newCoords = new int[2];
                    newCoords[0] = i;
                    newCoords[1] = j;
                    highestProbList.add(newCoords);
                }

            }


        }
        /* For Testing
        printBoard();
        */


        return highestProbList.elementAt(new Random().nextInt(highestProbList.size())); // Returns a coord with the highest probability.

    }

    /* For Testing
    public void printBoard() {
        System.out.println("      PROBABILITY BOARD     ");
        System.out.println("   A B C D E F G H I J"); // Column letters
        //System.out.println(" --------------------"); // Top border
        for (int i = 0; i < 10; i++) {
            if (i < 9) {
                System.out.print(i + 1 + " "); // Print row number
            } else {
                System.out.print(i + 1); // So that all rows are aligned as 10 takes an extra character space.
            }
            for (int j = 0; j < 10; j++) {
                System.out.print("|"); // Print border
                // Replaces '+' printout for own ship with " " to indicate unknown space.
                System.out.print(probBoard[i][j]);
            }
            System.out.print("|\n"); // End row with border and new line
        }
        System.out.println("   --------------------"); // Bottom border
    }
    */
}
