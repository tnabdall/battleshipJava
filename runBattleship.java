class runBattleship{
    public static void main(String[] args){
        runBattleship runGame = new runBattleship();
        GameBoard p1 = runGame.setupGameBoard();
    }

    public GameBoard setupGameBoard(){
        GameBoard board = new GameBoard();
        board.placeAllShips();
        board.printBoard();
        return board;

    }
	
	public GameBoard setupEnemyBoard(){
		GameBoard board = new GameBoard();
		board.makeRandomBoard();
		board.printBoard();
		return board;
		
	}



}