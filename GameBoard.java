public class GameBoard{
    private Ship carrier = new Ship("Carrier",5);
    private Ship battleship = new Ship("Battleship", 4);
    private Ship submarine = new Ship("Submarine", 3);
    private Ship cruiser = new Ship("Cruiser",3);
    private Ship destroyer = new Ship("Destroyer",2);



    private int[][] board = new int[10][10];

    public GameBoard(){
        InitializeBoard();
    }

    public static void main(String[] args){
        GameBoard b1 = new GameBoard();
        System.out.print(b1.getBoard()[9][9]);
    }

    public int[][] getBoard() {
        return board;
    }

    private void InitializeBoard(){
        for(int i=0; i<this.board.length;i++){
            for(int j=0; j<this.board[i].length;j++){
                this.board[i][j]=1;
            }
        }
    }

    private String boardMarker(int num){
        String marker;
        switch(num){
            case 0:
                marker = " ";
                break;
            case 1:
                marker = "X";
                break;
            case 2:
                marker = "O";
                break;
            default:
                marker = "?";
                break;
        }
        return marker;

    }

    public void printBoard(){
        System.out.println("   A B C D E F G H I J"); // Column letters
        //System.out.println(" --------------------"); // Top border
        for (int i = 0; i<10; i++){
            if (i<9){
                System.out.print(i+1+" "); // Print row number
            }
            else{
                System.out.print(i+1); // So that all rows are aligned as 10 takes an extra character space.
            }
            for (int j = 0; j<10; j++){
                System.out.print("|"); // Print border
                System.out.print(boardMarker(this.board[i][j])); //Print marker according to board
            }
            System.out.print("|\n"); // End row with border and new line
        }
        System.out.println("   --------------------"); // Bottom border

    }

}