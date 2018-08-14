package Test;
import Main.*;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

public class GameBoardTest {
    private PlayerBoard g = new PlayerBoard();
    private Vector<int[]> shipvect = new Vector<int[]>();


    @Test
    public void test_GameBoard_Constructor() {
        GameBoard g = new PlayerBoard();
        Assert.assertEquals("There should be 5 ships.", 5, g.getShips().length, 0.00001);
        Assert.assertEquals("Number of ship elements should be 17", 17, g.getNumberOfShipElements(), 0.00001);
    }

    @Test
    public void test_GameBoard_Copy_Constructor() {
        PlayerBoard g = new PlayerBoard();
        Ship[] ships = g.getShips();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < ships[i].getLength(); j++) {
                int[] coords = new int[2];
                coords[0] = i * 2;
                coords[1] = j;
                shipvect.add(coords);
            }
            g.placeShip(shipvect, ships[i]);
            shipvect.clear();
        }

        GameBoard gCopy = new PlayerBoard(g);

        boolean equals = true;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (g.getBoardElement(i, j) != gCopy.getBoardElement(i, j)) {
                    equals = false;
                }
            }
        }
        if (g.getNumberOfShipElements() != gCopy.getNumberOfShipElements()) {
            equals = false;
        }
        for (int i = 0; i < 5; i++) {
            if (g.getShips()[i].getLength() != gCopy.getShips()[i].getLength() ||
                    !g.getShips()[i].getName().equals(gCopy.getShips()[i].getName())) {
                equals = false;

            }
        }

        assertEquals("The copy constructor has not copied all the field variables", true, equals);
    }


    @Test
    public void test_getShipFiredOn_and_getShipFiredOnLength_Valid() {
        Ship[] ships = g.getShips();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < ships[i].getLength(); j++) {
                int[] coords = new int[2];
                coords[0] = i * 2;
                coords[1] = j;
                shipvect.add(coords);
            }
            g.placeShip(shipvect, ships[i]);
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


        int[] coords = new int[2];
        coords[0] = 0;
        coords[1] = 0;
        Assert.assertEquals("This should be Carrier", "Carrier", g.getShipFiredOn(0, 0));
        Assert.assertEquals("This should be Carrier", "Carrier", g.getShipFiredOn(coords));
        Assert.assertEquals("Carrier should have a length of 5", 5, g.getShipFiredOnLength(coords), 0.0001);
        Assert.assertEquals("Carrier should have a length of 5", 5, g.getShipFiredOnLength(0, 0), 0.0001);
        Assert.assertEquals("This should be Destroyer", "Destroyer", g.getShipFiredOn(8, 0));
        Assert.assertEquals("This should be Destroyer", "Destroyer", g.getShipFiredOn(8, 0));
        Assert.assertEquals("Destroyer should have a length of 2", 2, g.getShipFiredOnLength(8, 0), 0.0001);
    }

    @Test
    public void test_getShipFiredOn_and_getShipFiredOnLength_Invalid() {
        Ship[] ships = g.getShips();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < ships[i].getLength(); j++) {
                int[] coords = new int[2];
                coords[0] = i * 2;
                coords[1] = j;
                shipvect.add(coords);
            }
            g.placeShip(shipvect, ships[i]);
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
        int[] coords = new int[2];
        coords[0] = 9;
        coords[1] = 9;
        Assert.assertEquals("This should be Unknown", "Unknown", g.getShipFiredOn(9, 9));
        Assert.assertEquals("This should be Unknown", "Unknown", g.getShipFiredOn(coords));
        Assert.assertEquals("Should return -1 because no ship at (9,9)", -1, g.getShipFiredOnLength(coords), 0.0001);
        Assert.assertEquals("Should return -1 because no ship at (9,9)", -1, g.getShipFiredOnLength(9, 9), 0.0001);
    }

    @Test
    public void test_getShipFiredOn_and_getShipFiredOnLength_OutOfBounds() {
        Ship[] ships = g.getShips();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < ships[i].getLength(); j++) {
                int[] coords = new int[2];
                coords[0] = i * 2;
                coords[1] = j;
                shipvect.add(coords);
            }
            g.placeShip(shipvect, ships[i]);
            shipvect.clear();
        }
        Assert.assertEquals("Should be Unknown because out of bounds", "Unknown", g.getShipFiredOn(-1, -1));
        Assert.assertEquals("Should be Unkown because out of bounds", "Unknown", g.getShipFiredOn(new int[]{-1, -1}));
        Assert.assertEquals("Should be -1 because out of bounds", -1, g.getShipFiredOnLength(new int[]{-1, -1}));
        Assert.assertEquals("Should be -1 because out of bounds", -1, g.getShipFiredOnLength(10, 5));
    }

    @Test
    public void test_makeRandomBoard_NumberOfShipElements() {
        GameBoard g = new PlayerBoard();
        g.makeRandomBoard();
        Assert.assertEquals("Expected to get 17 elements placed on the random board.", 17, g.getNumberOfShipElements(), 0.0001);
    }

    @Test
    public void test_isValidPlacement_OutOfBoard() {
        GameBoard g = new PlayerBoard();
        // Empty game Board
        int[] firstcoord = new int[2];
        firstcoord[0] = 1;
        firstcoord[1] = 1;
        Assert.assertEquals("Expected false. Out of bounds.", false, g.isValidPlacement(firstcoord, "l", g.getShips()[0]));

    }

    @Test
    public void test_isValidPlacement_InsideEmptyBoard() {
        GameBoard g = new PlayerBoard();
        // Empty game Board
        int[] firstcoord = new int[2];
        firstcoord[0] = 1;
        firstcoord[1] = 1;
        Assert.assertEquals("Expected true. Inside bounds.", true, g.isValidPlacement(firstcoord, "d", g.getShips()[0]));

    }

    @Test
    public void test_isValidPlacement_ShipPlacedToEdges() {
        GameBoard g = new PlayerBoard();
        // Empty game Board
        int[] firstcoord = new int[2];
        firstcoord[0] = 4;
        firstcoord[1] = 4;
        Assert.assertEquals("Expected true. Ship inside bounds. (4,4) up ", true, g.isValidPlacement(firstcoord, "u", g.getShips()[0]));
        Assert.assertEquals("Expected true. Ship inside bounds. (4,4) left ", true, g.isValidPlacement(firstcoord, "l", g.getShips()[0]));
        firstcoord[0] = 5;
        firstcoord[1] = 5;
        Assert.assertEquals("Expected true. Ship inside bounds. (5,5) right ", true, g.isValidPlacement(firstcoord, "r", g.getShips()[0]));
        Assert.assertEquals("Expected true. Ship inside bounds. (5,5) down ", true, g.isValidPlacement(firstcoord, "d", g.getShips()[0]));

    }

    @Test
    public void test_isValidPlacement_TowardsAnotherShip() {
        PlayerBoard g = new PlayerBoard();

        for (int i = 0; i < 5; i++) {
            int[] coords = new int[2];
            coords[0] = i;
            coords[1] = 2;
            shipvect.add(coords);
        }
        g.placeShip(shipvect, g.getShips()[0]); // Places carrier from top edge of second column straight down.
        shipvect.clear();
        g.printBoard();
        // Empty game Board
        int[] firstcoord = new int[2];
        firstcoord[0] = 1;
        firstcoord[1] = 1;
        //Test for battleship placement into carrier
        Assert.assertEquals("Expected false. Battleship going into carrier.", false, g.isValidPlacement(firstcoord, "r", g.getShips()[1]));
        //Test for destroyer placement into carrier
        Assert.assertEquals("Expected false. Destroyer going into carrier.", false, g.isValidPlacement(firstcoord, "r", g.getShips()[4]));
        firstcoord[1] = 6;
        //Test for battleship placement just before carrier.
        Assert.assertEquals("Expected true. Battleship not going into carrier.", true, g.isValidPlacement(firstcoord, "l", g.getShips()[1]));
        //Test for destroyer placement just before carrier
        firstcoord[1] = 0;
        Assert.assertEquals("Expected false. Destroyer not going into carrier.", true, g.isValidPlacement(firstcoord, "r", g.getShips()[4]));
    }

    @Test
    public void test_BoardMarker() {
        GameBoard g = new PlayerBoard();
        Assert.assertEquals("0 Should be blank", " ", g.boardMarker(0));
        Assert.assertEquals("1 Should be X", "X", g.boardMarker(1));
        Assert.assertEquals("2 should be O", "O", g.boardMarker(2));
        Assert.assertEquals("3 should be + on player board", "+", g.boardMarker(3));
        Assert.assertEquals("3 should be blank on enemy board", " ", g.boardMarker(3, true));
    }

    @Test
    public void test_setBoardElement_and_getBoardElement_Valid() {
        GameBoard g = new PlayerBoard();
        g.setBoardElement(2, 2, 3);
        g.setBoardElement(3, 3, 1);
        Assert.assertEquals("Expected 3 at (2,2)", 3, g.getBoardElement(2, 2));
        Assert.assertEquals("Expected 1 at (3,3)", 1, g.getBoardElement(3, 3));
        Assert.assertEquals("Expected 0 at (8,8)", 0, g.getBoardElement(8, 8));
    }

    @Test
    public void test_setBoardElement_and_getBoardElement_InValid() {
        GameBoard g = new PlayerBoard();
        g.setBoardElement(-1, 2, 3);
        g.setBoardElement(2, 2, 3);
        Assert.assertEquals("Test should run without exceptions.", 3, g.getBoardElement(2, 2));
    }


    @Test
    public void test_DecrementShipElements() {
        GameBoard g = new PlayerBoard();
        //Starts with 17 ship elements
        Assert.assertEquals("Should start with 17 ship elements", 17, g.getNumberOfShipElements());
        g.decrementShipElements();
        g.decrementShipElements();
        Assert.assertEquals("Should now have 15 ship elements", 15, g.getNumberOfShipElements());
        for (int i = 0; i < 17; i++) {
            g.decrementShipElements();
        }
        Assert.assertEquals("Should now have 0 ship elements", 0, g.getNumberOfShipElements());

    }

    @Test
    public void test_GetCoordinates_A1() {
        GameBoard g = new PlayerBoard();
        ByteArrayInputStream in = new ByteArrayInputStream("A1".getBytes());
        System.setIn(in);

        int[] testcoords = g.getCoordinates();
        assertEquals("Expected row 0", 0, testcoords[0]);
        assertEquals("Expected column 0", 0, testcoords[1]);
    }

    @Test
    public void test_GetCoordinates_J10() {
        GameBoard g = new PlayerBoard();
        ByteArrayInputStream in = new ByteArrayInputStream("J10".getBytes());
        System.setIn(in);
        int[] testcoords = g.getCoordinates();
        assertEquals("Expected row 9", 9, testcoords[0]);
        assertEquals("Expected column 9", 9, testcoords[1]);
    }

    @Test
    public void test_GetCoordinates_F5() {
        GameBoard g = new PlayerBoard();
        ByteArrayInputStream in = new ByteArrayInputStream("F5".getBytes());
        System.setIn(in);
        int[] testcoords = g.getCoordinates();
        assertEquals("Expected row 4", 4, testcoords[0]);
        assertEquals("Expected column 5", 5, testcoords[1]);
    }

    @Test
    public void test_hasFired_True() {
        Ship[] ships = g.getShips();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < ships[i].getLength(); j++) {
                int[] coords = new int[2];
                coords[0] = i * 2;
                coords[1] = j;
                shipvect.add(coords);
            }
            g.placeShip(shipvect, ships[i]);
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

        g.fire(2, 2);
        Assert.assertEquals("Should have been true after firing at 2,2", true, g.hasFired(2, 2));


    }

    @Test
    public void test_hasFired_False() {
        Ship[] ships = g.getShips();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < ships[i].getLength(); j++) {
                int[] coords = new int[2];
                coords[0] = i * 2;
                coords[1] = j;
                shipvect.add(coords);
            }
            g.placeShip(shipvect, ships[i]);
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

        g.fire(2, 2);
        Assert.assertEquals("Should have been false since we did not fire at 0,0", false, g.hasFired(0, 0));
        Assert.assertEquals("Should have been false since we did not fire at 1,0", false, g.hasFired(1, 0));


    }

}
