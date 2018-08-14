package Test;

import Main.*;
import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

public class PlayerBoardTest {

    private PlayerBoard p = new PlayerBoard();
    private Vector<int[]> shipvect= new Vector<int[]>();

    public PlayerBoard getTestBoard(){
        // 10x10 PlayerBoard for testing.
        PlayerBoard p = new PlayerBoard();
        Ship[] ships = p.getShips();
        for (int i = 0; i< 5; i++){
            for (int j =0; j< ships[i].getLength(); j++){
                int[] coords = new int[2];
                coords[0] = i*2;
                coords[1] = j;
                getShipvect().add(coords);
            }
            p.placeShip(getShipvect(),ships[i]);
            getShipvect().clear();
        }
        return new PlayerBoard(p);
    }

    @Test
    public void test_Constructor(){
        PlayerBoard p = new PlayerBoard();
        int[][] emptyBoard = new int[10][10];


        boolean elementsEqual = true;
        for (int i = 0; i<emptyBoard.length; i++){
            for (int j = 0; j<emptyBoard[0].length; j++){
                if(p.getBoardElement(i,j)!= emptyBoard[i][j]){
                    elementsEqual = false;
                }
            }
        }
        assertEquals("All elements should have been empty in the board.",true,elementsEqual);

    }

    @Test
    public void test_Copy_Constructor(){
        PlayerBoard p = getTestBoard();

        PlayerBoard copyP = new PlayerBoard(p);
        //


        boolean elementsEqual = true;
        for (int i = 0; i<10; i++){
            for (int j = 0; j<10; j++){
                if(p.getBoardElement(i,j)!= copyP.getBoardElement(i,j)){
                    elementsEqual = false;
                }
            }
        }
        assertEquals("All elements should have been equal in the board.",true,elementsEqual);
    }

    @Test
    public void test_loc_status_Boundary(){
        PlayerBoard p = getTestBoard();
        Assert.assertEquals("Should have returned a 3 at (0,0)", 3, p.locStatus(0,0));
        Assert.assertEquals("Should have returned a 0 at (9,9)", 0, p.locStatus(GameBoard.getNUMROWS()-1,GameBoard.getNUMCOLS()-1));
        Assert.assertEquals("Should have returned a 4 at (10,9) because out of bounds", 4,p.locStatus(GameBoard.getNUMROWS(),GameBoard.getNUMCOLS()-1));
    }

    @Test
    public void test_loc_status_Invalid(){
        PlayerBoard p = getTestBoard();
        Assert.assertEquals("Should have returned a 4 at (-1,0) because out of bounds", 4,p.locStatus(-1,0));
        Assert.assertEquals("Should have returned a 4 at (10,9) because out of bounds", 4,p.locStatus(GameBoard.getNUMROWS(),GameBoard.getNUMCOLS()-1));
    }

    @Test
    public void test_loc_status_average(){
        PlayerBoard p = getTestBoard();
        p.fire(0,0);
        p.fire(1,0);
        Assert.assertEquals("Should have returned a 2 after firing at (0,0)", 2, p.locStatus(0,0));
        Assert.assertEquals("Should have returned a 1 after firing at (1,0)", 1, p.locStatus(1,0));
        Assert.assertEquals("Should have returned a 3 at (0,1)", 3, p.locStatus(0,1));
    }

    @Test
    public void test_fire_Boundaries(){
        p = getTestBoard();
        p.fire(0,0);
        p.fire(GameBoard.getNUMROWS()-1,GameBoard.getNUMCOLS()-1);
        Assert.assertEquals("Fired at (0,0). Expected a 2 (O) at (0,0)",2,p.locStatus(0,0));
        Assert.assertEquals("Fired at (9,9). Expected a 1 (X) at (9,9)",1,p.locStatus(GameBoard.getNUMROWS()-1,GameBoard.getNUMCOLS()-1));
    }

    @Test
    public void test_fireInBounds(){
        p = getTestBoard();
        /*
                 PLAYER BOARD
               A B C D E F G H I J
            1 |+|+|+|+|+| | | | | |
            2 | | | | | | | | | | |
            3 |+|+|+|+| | | | | | |
            4 | | | | | | | | | | |
            5 |+|+|+| | | | | | | |
            6 | | | | | | | | | | |
            7 |+|+|+| | | | | | | |
            8 | | | | | | | | | | |
            9 |+|+| | | | | | | | |
            10| | | | | | | | | | |
               --------------------
         */

        p.fire(0,4);
        p.fire(0,5);
         /*
                 PLAYER BOARD
               A B C D E F G H I J
            1 |+|+|+|+|O|X| | | | |
            2 | | | | | | | | | | |
            3 |+|+|+|+| | | | | | |
            4 | | | | | | | | | | |
            5 |+|+|+| | | | | | | |
            6 | | | | | | | | | | |
            7 |+|+|+| | | | | | | |
            8 | | | | | | | | | | |
            9 |+|+| | | | | | | | |
            10| | | | | | | | | | |
               --------------------
         */

         Assert.assertEquals("Expected a 3 (+) at (2,3)", 3, p.locStatus(2,3));
         Assert.assertEquals("Fired at (0,4). Expected a 2 (O) at (0,4)",2,p.locStatus(0,4));
         Assert.assertEquals("Fired at (0,5). Expected a 1 (X) at (0,5)",1,p.locStatus(0,5));
         Assert.assertEquals("Expected a 0 at (8,8)",0,p.locStatus(8,8));

    }

    @Test
    public void test_fire_OutofBounds(){
        p = getTestBoard();
        /*
                 PLAYER BOARD
               A B C D E F G H I J
            1 |+|+|+|+|+| | | | | |
            2 | | | | | | | | | | |
            3 |+|+|+|+| | | | | | |
            4 | | | | | | | | | | |
            5 |+|+|+| | | | | | | |
            6 | | | | | | | | | | |
            7 |+|+|+| | | | | | | |
            8 | | | | | | | | | | |
            9 |+|+| | | | | | | | |
            10| | | | | | | | | | |
               --------------------
         */
        p.fire(-1,-1); // will have exception if not taken care of in fire method
        p.fire(10,10);
        p.fire(0,0);
        Assert.assertEquals("Expected JUnit test to run without an exception.",2,p.getBoardElement(0,0));

    }

    @Test
    public void test_getShipLoc_shipPlaced(){
        p = getTestBoard();
        int[] firstShip = new int[2];
        firstShip[0] = 0;
        firstShip[1] = 0;
        int[] shipLoc = p.getShipLoc();
        boolean equals = true;
        if (firstShip[0] != shipLoc[0] || firstShip[1] != shipLoc[1]){
            equals = false;
        }
        assertEquals("First should should be at (0,0)", true, equals);
        p.fire(0,0);
        shipLoc = p.getShipLoc();
        System.out.println(shipLoc[0] + " "+ shipLoc[1]);
        firstShip[1] = 1;
        if (firstShip[0] != shipLoc[0] || firstShip[1] != shipLoc[1]){
            equals = false;
        }
        assertEquals("After firing at (0,0), first ship element should be at (0,1)", true, equals);
    }

    @Test
    public void test_getShipLoc_NoShipPlaced(){
        p = new PlayerBoard();
        int[] shipLoc = p.getShipLoc();
        assertEquals("No ships placed. Reference should be null.", null, shipLoc);
    }

    @Test
    public void test_decrementedShipElementsAfterFiring(){
        PlayerBoard p = new PlayerBoard();
        Ship[] ships = p.getShips();
        for (int i = 0; i< 5; i++){
            for (int j =0; j< ships[i].getLength(); j++){
                int[] coords = new int[2];
                coords[0] = i*2;
                coords[1] = j;
                getShipvect().add(coords);
            }
            p.placeShip(getShipvect(),ships[i]);
            getShipvect().clear();
        }
        /*
                 PLAYER BOARD
               A B C D E F G H I J
            1 |+|+|+|+|+| | | | | |
            2 | | | | | | | | | | |
            3 |+|+|+|+| | | | | | |
            4 | | | | | | | | | | |
            5 |+|+|+| | | | | | | |
            6 | | | | | | | | | | |
            7 |+|+|+| | | | | | | |
            8 | | | | | | | | | | |
            9 |+|+| | | | | | | | |
            10| | | | | | | | | | |
               --------------------
         */

        p.fire(0,0);
        Assert.assertEquals("Expect 16 elements since a ship was hit after firing at A1", 16, p.getNumberOfShipElements());
        p.fire(0,0);
        Assert.assertEquals("Expect 16 elements since I tried to fire at A1 which has already been hit", 16, p.getNumberOfShipElements());
        p.fire(4,9);
        Assert.assertEquals("Expect 16 elements since no ship was hit after firing at J5", 16, p.getNumberOfShipElements());

    }

    @Test
    public void test_isValidPlacement_TowardsAnotherShip(){
        PlayerBoard p = new PlayerBoard();
        Ship[] ships = p.getShips();
        //Places all ships but battleship.
        for (int i = 0; i< 5; i++){
            if(i==1){
                continue;
            }
            for (int j =0; j< ships[i].getLength(); j++){
                int[] coords = new int[2];
                coords[0] = i*2;
                coords[1] = j;
                getShipvect().add(coords);
            }
            p.placeShip(getShipvect(),ships[i]);
            getShipvect().clear();
        }
        /*
                 PLAYER BOARD
               A B C D E F G H I J
            1 |+|+|+|+|+| | | | | |
            2 | | | | | | | | | | |
            3 | | | | | | | | | | |
            4 | | | | | | | | | | |
            5 |+|+|+| | | | | | | |
            6 | | | | | | | | | | |
            7 |+|+|+| | | | | | | |
            8 | | | | | | | | | | |
            9 |+|+| | | | | | | | |
            10| | | | | | | | | | |
               --------------------
         */

        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            getShipvect().add(coords);
        } //D1-D4 ship


        Assert.assertEquals("Expected to be false since ship exists at D1" , false, p.isValidPlacement(getShipvect()));
        getShipvect().clear();
        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i+1;
            coords[1] = 3;
            getShipvect().add(coords);
        } //D2-D5 ship

        Assert.assertEquals("Expected to be true since no ships at D2-D5", true, p.isValidPlacement(getShipvect()));
        getShipvect().clear();
    }

    @Test
    public void test_isValidPlacement_InArrayBounds(){
        PlayerBoard p = new PlayerBoard();
        // All squares empty
        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            getShipvect().add(coords);
        } //D1-D4 ship

        Assert.assertEquals("Expected to be a valid placement since it is inside bounds", true, p.isValidPlacement(getShipvect()));
        getShipvect().clear();

    }

    @Test
    public void test_isValidPlacement_OutOfArrayBounds(){
        PlayerBoard p = new PlayerBoard();
        // All squares empty

        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i-1;
            coords[1] = 3;
            getShipvect().add(coords);
        } //D0-D3 ship out of bounds
        Assert.assertEquals("Expected to be invalid because E0 does not exist.", false, p.isValidPlacement(getShipvect()));
        getShipvect().clear();

        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i+9;
            coords[1] = 10;
            getShipvect().add(coords);
        } //K10-K13 ship out of bounds
        Assert.assertEquals("Expected to be invalid because column K does not exist.", false, p.isValidPlacement(getShipvect()));
        getShipvect().clear();
    }

    @Test
    public void test_isValidPlacement_ContinuousShip(){

        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            getShipvect().add(coords);
        } //D1-D4 ship

        Assert.assertEquals("Expected to be a valid placement since ship is continuous", true, getP().isValidPlacement(getShipvect()));
        getShipvect().clear();

        for (int i = 0; i< 5; i++){
            if(i ==2){
                continue;
            }
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            getShipvect().add(coords);
        } //D1-D2 and D4-D5 invalid ship

        Assert.assertEquals("Expected to be invalid because ship is not continuous vertically.", false, getP().isValidPlacement(getShipvect()));
        getShipvect().clear();

        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = 1;
            coords[1] = i;
            getShipvect().add(coords);
        } //A2-D2 ship

        Assert.assertEquals("Expected to be a valid placement since ship is continuous", true, getP().isValidPlacement(getShipvect()));
        getShipvect().clear();

        for (int i = 0; i< 5; i++){
            if(i ==2){
                continue;
            }
            int[] coords = new int[2];
            coords[0] = 1;
            coords[1] = i;
            getShipvect().add(coords);
        } //A2-B2 and D2-E2 invalid ship

        Assert.assertEquals("Expected to be invalid because ship is not continuous horizontally.", false, getP().isValidPlacement(getShipvect()));
        getShipvect().clear();

    }

    @Test
    public void test_Place_Ship_valid(){ //GUI Version. In GUI code, checks for valid placement before calling function.
        PlayerBoard p = new PlayerBoard();
        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            getShipvect().add(coords);
        } //D1-D4 ship
        p.placeShip(getShipvect(), p.getShips()[1]);

        Assert.assertEquals("Expected D1 to be filled with a ship",3,p.getBoardElement(0,3));
        Assert.assertEquals("Expected D2 to be filled with a ship",3,p.getBoardElement(1,3));
        Assert.assertEquals("Expected D3 to be filled with a ship",3,p.getBoardElement(2,3));
        Assert.assertEquals("Expected D4 to be filled with a ship",3,p.getBoardElement(3,3));
        Assert.assertEquals("Expected D5 to be empty.",0,p.getBoardElement(4,3));
        getShipvect().clear();

    }

    @Test
    public void test_PlaceShip_Invalid(){ //GUI Version. In GUI code, checks for valid placement before calling function.
        PlayerBoard p = new PlayerBoard();
        for (int i = -1; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            getShipvect().add(coords);
        } //D0-D4 ship
        p.placeShip(getShipvect(), p.getShips()[1]);

        Assert.assertEquals("Expected D1 to be empty",0,p.getBoardElement(0,3));
        Assert.assertEquals("Expected D2 to be empty",0,p.getBoardElement(1,3));
        Assert.assertEquals("Expected D3 to be empty",0,p.getBoardElement(2,3));
        getShipvect().clear();
    }


    private PlayerBoard getP() {
        return p;
    }


    public Vector<int[]> getShipvect() {
        return shipvect;
    }


}
