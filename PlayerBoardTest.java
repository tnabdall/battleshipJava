import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.Vector;
import org.junit.Test;

public class PlayerBoardTest {

    PlayerBoard p = new PlayerBoard();
    Vector<int[]> shipvect= new Vector<int[]>();


    @Test
    public void test_fireInBounds_and_locStatus(){
        PlayerBoard p = new PlayerBoard();
        Ship[] ships = p.getShips();
        for (int i = 0; i< 5; i++){
            for (int j =0; j< ships[i].getLength(); j++){
                int[] coords = new int[2];
                coords[0] = i*2;
                coords[1] = j;
                shipvect.add(coords);
            }
            p.placeShip(shipvect,ships[i]);
            shipvect.clear();
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

         assertEquals("Expected a 3 (+) at (2,3)", 3, p.locStatus(2,3));
         assertEquals("Fired at (0,4). Expected a 2 (O) at (0,4)",2,p.locStatus(0,4));
         assertEquals("Fired at (0,5). Expected a 1 (X) at (0,5)",1,p.locStatus(0,5));
         assertEquals("Expected a 0 at (8,8)",0,p.locStatus(8,8));

    }

    @Test
    public void test_fire_OutofBounds(){
        PlayerBoard p = new PlayerBoard();
        Ship[] ships = p.getShips();
        for (int i = 0; i< 5; i++){
            for (int j =0; j< ships[i].getLength(); j++){
                int[] coords = new int[2];
                coords[0] = i*2;
                coords[1] = j;
                shipvect.add(coords);
            }
            p.placeShip(shipvect,ships[i]);
            shipvect.clear();
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
        p.fire(-1,-1); // will have exception if not taken care of in fire method
        p.fire(10,10);
        p.fire(0,0);
        assertEquals("Expected JUnit test to run without an exception.",2,p.getBoardElement(0,0));

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
                shipvect.add(coords);
            }
            p.placeShip(shipvect,ships[i]);
            shipvect.clear();
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
        assertEquals("Expect 16 elements since a ship was hit after firing at A1", 16, p.getNumberOfShipElements());
        p.fire(0,0);
        assertEquals("Expect 16 elements since I tried to fire at A1 which has already been hit", 16, p.getNumberOfShipElements());
        p.fire(4,9);
        assertEquals("Expect 16 elements since no ship was hit after firing at J5", 16, p.getNumberOfShipElements());

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
                shipvect.add(coords);
            }
            p.placeShip(shipvect,ships[i]);
            shipvect.clear();
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
            shipvect.add(coords);
        } //D1-D4 ship


        assertEquals("Expected to be false since ship exists at D1" , false, p.isValidPlacement(shipvect));
        shipvect.clear();
        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i+1;
            coords[1] = 3;
            shipvect.add(coords);
        } //D2-D5 ship

        assertEquals("Expected to be true since no ships at D2-D5", true, p.isValidPlacement(shipvect));
        shipvect.clear();
    }

    @Test
    public void test_isValidPlacement_InArrayBounds_OutOfArrayBounds(){
        PlayerBoard p = new PlayerBoard();
        // All squares empty
        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            shipvect.add(coords);
        } //D1-D4 ship

        assertEquals("Expected to be a valid placement since it is inside bounds", true, p.isValidPlacement(shipvect));
        shipvect.clear();

        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i-1;
            coords[1] = 3;
            shipvect.add(coords);
        } //D0-D3 ship out of bounds
        assertEquals("Expected to be invalid because E0 does not exist.", false, p.isValidPlacement(shipvect));
        shipvect.clear();

        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i+9;
            coords[1] = 10;
            shipvect.add(coords);
        } //K10-K13 ship out of bounds
        assertEquals("Expected to be invalid because column K does not exist.", false, p.isValidPlacement(shipvect));
        shipvect.clear();
    }

    @Test
    public void test_isValidPlacement_ContinuousShip(){

        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            shipvect.add(coords);
        } //D1-D4 ship

        assertEquals("Expected to be a valid placement since ship is continuous", true, p.isValidPlacement(shipvect));
        shipvect.clear();

        for (int i = 0; i< 5; i++){
            if(i ==2){
                continue;
            }
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            shipvect.add(coords);
        } //D1-D2 and D4-D5 invalid ship

        assertEquals("Expected to be invalid because ship is not continuous vertically.", false, p.isValidPlacement(shipvect));
        shipvect.clear();

        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = 1;
            coords[1] = i;
            shipvect.add(coords);
        } //A2-D2 ship

        assertEquals("Expected to be a valid placement since ship is continuous", true, p.isValidPlacement(shipvect));
        shipvect.clear();

        for (int i = 0; i< 5; i++){
            if(i ==2){
                continue;
            }
            int[] coords = new int[2];
            coords[0] = 1;
            coords[1] = i;
            shipvect.add(coords);
        } //A2-B2 and D2-E2 invalid ship

        assertEquals("Expected to be invalid because ship is not continuous horizontally.", false, p.isValidPlacement(shipvect));
        shipvect.clear();

    }

    @Test
    public void test_Place_Ship(){ //GUI Version. In GUI code, checks for valid placement before calling function.
        PlayerBoard p = new PlayerBoard();
        for (int i = 0; i< 4; i++){
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 3;
            shipvect.add(coords);
        } //D1-D4 ship
        p.placeShip(shipvect, p.getShips()[1]);

        assertEquals("Expected D1 to be filled with a ship",3,p.getBoardElement(0,3));
        assertEquals("Expected D2 to be filled with a ship",3,p.getBoardElement(1,3));
        assertEquals("Expected D3 to be filled with a ship",3,p.getBoardElement(2,3));
        assertEquals("Expected D4 to be filled with a ship",3,p.getBoardElement(3,3));
        assertEquals("Expected D5 to be empty.",0,p.getBoardElement(4,3));
        shipvect.clear();

    }



}
