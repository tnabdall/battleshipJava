package Main;

import java.util.Scanner;

/**
 * Class runs the battleship program and the game logic.
 */
public class runBattleship{
    public static void main(String[] args){
        runBattleship battle = new runBattleship();
        System.out.print("Would you like to play two players? (Y or N): ");
        Scanner keyboard = new Scanner(System.in);
        String answer = keyboard.next();
        if (answer.equalsIgnoreCase("Y")){
        	battle.run2Player();
		}
		else{
			battle.runGame(battle.Demo());
		}
    }

	/**
	Function to call to setup player board with a non-random arrangement.
	 @return returns the Players Board
	*/
    private PlayerBoard setupPlayerBoard(){
        PlayerBoard board = new PlayerBoard();
        System.out.println("Player 1.");
        board.placeAllShips();
        return board;

    }
	/** 
	Function to call to setup the enemy board with a random arrangement.
	 @return Returns the enemys board.
	*/
	private EnemyBoard setupEnemyBoard(){
		EnemyBoard board = new EnemyBoard();
		board.makeRandomBoard();
		return board;
		
	}

	/**
	 * Sets up the 2nd player board.
	 * @return A PlayerBoard Object containing the second player board.
	 */
	private PlayerBoard setupPlayer2Board(){
		clearScreen();
		PlayerBoard board2 = new PlayerBoard();
		System.out.println("Player 2.");
		board2.placeAllShips();
		clearScreen();
		return board2;
	}

	/**
	 * Clears screen in console version
	 */
	private void clearScreen(){
		for(int i = 0; i< 100; i++) {
			System.out.println();
		}
	}

	/**
	 * Prints an introduction for the battleship game.
	 */
	private void Introduction(){
		System.out.println("Welcome to Battleship.\nThe goal of the game is to sink all of the other oppenent's ships.\n" +
				"Each player will take turns firing at a coordinate on the other's board.\n" +
				"Your opponent must announce whether your shot was a Hit or Miss.\nIf it was a Hit, your opponent must announce which ship was hit.\n" +
				"Once all of the opponent's ships are sunk, the game is over.\nOn the game board, O represents Hit, X represents Miss, and + represents your unhit ships.\n" +
				"Have fun!");
	}

	/**
	 * Function to ask to run demo for demoing purposes. Player randomly fires.
	 * @return Boolean true or false depending on demo to run.
	 */
	private boolean Demo(){
		System.out.print("Would you like to run the demo (player randomly fires) (y or n): ");
		Scanner keyboard = new Scanner(System.in);
		String ans;
		do {
			ans = keyboard.next().toLowerCase().trim();
			if (!ans.matches("^[y|n]$")){
				System.out.print("\nNot a valid input.\nWould you like to run the demo (y or n): ");
			}

		}while(!ans.matches("^[y|n]$"));
		return ans.equalsIgnoreCase("y");
	}
	/**
	Main function to call to run Game.
	@param randomPlayerFire For testing/demo purposes, does random fire as player's move.
	*/
	private void runGame(boolean randomPlayerFire){
		Introduction();
		PlayerBoard playerBoard = setupPlayerBoard();
		EnemyBoard enemyBoard = setupEnemyBoard();
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Which difficulty would you like to play on (0 for Normal, 1 for Challenge, 2 for Impossible, 3 for Random): ");
		AI AI = new AI(playerBoard, keyboard.nextInt());
		do{
			if(randomPlayerFire == true){
				enemyBoard.randomFire(); //Just for quick testing. Random firing from player.
			}
			else{
				enemyBoard.fire(); // User input fire
			}
			AI.runDifficulty(); //Get next move from Main.AI.

			enemyBoard.printBoard(); //Show enemy board.
			playerBoard.fire(AI.getRow(),AI.getCol()); //Enemy fires at player.
			playerBoard.printBoard(); //Show player's board.

			if(randomPlayerFire == true){ //Function to cause code to sleep. Purely for demoing. https://stackoverflow.com/questions/24104313/how-to-delay-in-java (Ann Ragg)
				try{
						Thread.sleep(850);
				}
				catch(InterruptedException ex){
						Thread.currentThread().interrupt();
				}
			}

			
			
		}while(enemyBoard.getNumberOfShipElements()>0 && playerBoard.getNumberOfShipElements()>0); //When one player loses all ships, games over.
		if(playerBoard.getNumberOfShipElements()==0&&enemyBoard.getNumberOfShipElements()==0){
			System.out.println("A tie! Play again.");
		}
		else if (playerBoard.getNumberOfShipElements()==0){
			System.out.println("The Main.AI won in " + AI.getTurn() + " turns.");
			System.out.println("You lose. Better luck next time.");
		}
		else{
			System.out.println("You win. Congratulations!");
		}
	}

	/**
	 * Run this to play 2 players.
	 */
	private void run2Player(){
		Introduction();
		PlayerBoard player1 = setupPlayerBoard();
		PlayerBoard player2 = setupPlayer2Board();

		do{
			System.out.println("\n\nIt is player 1's turn. Fire at player 2's board!");
			player2.fire();
			player2.printBoard(2,true);
			System.out.println("\n\nIt is player 2's turn. Fire at player 1's board!");
			player1.fire();
			player1.printBoard(1,true);
		}while(player1.getNumberOfShipElements()>0 && player2.getNumberOfShipElements()>0);
		if(player2.getNumberOfShipElements()==0 && player1.getNumberOfShipElements() ==0) {
			System.out.println("A Tie! Play again!");
		}
		else if (player2.getNumberOfShipElements()>0){
			System.out.println("Player 2 wins!");
		}
		else{
			System.out.println("Player 1 wins!");
		}
	}



}