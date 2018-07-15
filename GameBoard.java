import java.util.Scanner;
public class GameBoard{

    private Ship[] ships = new Ship[5];




    private int[][] board = new int[10][10];

    public GameBoard(){
        InitializeBoard();
        ships[0] = new Ship("Carrier",5);
        ships[1] = new Ship("Battleship", 4);
        ships[2] = new Ship("Submarine", 3);
        ships[3] = new Ship("Cruiser",3);
        ships[4] = new Ship("Destroyer",2);
    }

    public int[][] getBoard() {
        return board;
    }

    private void InitializeBoard(){
        for(int i=0; i<this.board.length;i++){
            for(int j=0; j<this.board[i].length;j++){
                this.board[i][j]=0;
            }
        }
    }

    public int[] getCoordinates(){
        int[] coords = new int[2];
        Scanner keyboard = new Scanner(System.in);
        String scoords;
        do{
            System.out.print("Which coordinate would you like to place this ship? (eg. A3): ");
            scoords = keyboard.next();
            scoords = scoords.trim();
        } while(!scoords.matches("^[A-J|a-j]([1-9]|10)$")); // Forces string to be within the range of Game Coordinates
        scoords = scoords.toLowerCase();
        char letter = scoords.charAt(0);
        int column = (int) letter;
        column-=97;
        int row = Integer.parseInt(scoords.substring(1));
        row-=1;
        coords[0] = row;
        coords[1] = column;
        return coords;
    }

    public boolean isValidPlacement(int[] firstcoord, String direction, Ship battleship){
        int row = firstcoord[0];
        int col = firstcoord[1];
        int length = battleship.getLength();
        //System.out.println(row);
        //System.out.println(col);
        //System.out.println(length);
        boolean isValid = true;

        //First check to see if last extended coordinate exists on board
        if (direction.equals("r") && col+length>9){
            isValid = false;
        }
        else if(direction.equals("l") && col-length<0){
            isValid = false;
        }
        else if(direction.equals("u") && row-length<0){
            isValid = false;
        }
        else if(direction.equals("d") && row+length>9){
            isValid = false;
        }

        // Returns false here so as to not execute the rest of the function which may cause an error.
        if(!isValid){
            System.out.print("Not a valid placement.");
            return isValid;
        }

        //Second check if the board already has a ship placed there, so they don't intersect.
        if(direction.equals("r")){
            for (int i = 0; i<length; i++){
                System.out.print(this.board[row][col+i]);
                if(this.board[row][col+i]==3){
                    isValid = false;
                }
            }
        }
        else if(direction.equals("l")){
            for (int i = 0; i<length; i++){
                if(this.board[row][col-i]==3){
                }
                isValid = false;
            }
        }
        else if(direction.equals("u")){
            for (int i = 0; i<length; i++){
                if(this.board[row-i][col]==3){
                    isValid = false;
                }
            }
        }
        else if(direction.equals("d")){
            for (int i = 0; i<length; i++){
                if(this.board[row+i][col]==3){
                    isValid = false;
                }
            }
        }

        return isValid;
    }

    public void placeShip(Ship battleship){
        int[] firstcoord;
        Scanner keyboard = new Scanner(System.in);
        String direction;
        do {
            firstcoord = getCoordinates();
            do {
                System.out.print("What direction would you like to place the rest of the ship?\n Type u,r,l,d for up, right, left, or down: ");
                direction = keyboard.next();
                direction = direction.trim().toLowerCase();
            }while(!direction.matches("^(u|r|d|l)$"));
        }while(!isValidPlacement(firstcoord,direction,battleship));

        board[firstcoord[0]][firstcoord[1]] = 3; // 3 means ship is there but hidden to enemy?
        for (int i = 0; i<battleship.getLength()-1; i++){
            if (direction.equalsIgnoreCase("u")){
                board[firstcoord[0]-i-1][firstcoord[1]] = 3;
            }
            else if (direction.equalsIgnoreCase("d")){
                board[firstcoord[0]+i+1][firstcoord[1]] = 3;
            }
            else if (direction.equalsIgnoreCase("l")){
                board[firstcoord[0]][firstcoord[1]-i-1] = 3;
            }
            else if (direction.equalsIgnoreCase("r")){
                board[firstcoord[0]][firstcoord[1]+i+1] = 3;
            }
        }
        printBoard();

    }

    public void placeAllShips(){
        for (int i = 0; i<this.ships.length; i++){
            System.out.println("Please place the " + ships[i].getName() + " with length of " + ships[i].getLength() +".");
            placeShip(ships[i]);
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
            case 3:
                marker = "+"; //just for testing
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