import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.Vector;

public class EnemyBoardTest {
    EnemyBoard e = new EnemyBoard();
    Vector<int[]> shipvect= new Vector<int[]>();


    @Test
    public void test_fire_Hit_And_Miss_and_NumberOfShipElements(){
        EnemyBoard e = new EnemyBoard();
        e.makeRandomBoard();
        Ship[] ships = e.getShips();

        int rowHasShip = 0;
        int colHasShip = 0;
        boolean shipExists = false;
        int rowNoShip = 0;
        int colNoShip = 0;
        boolean shipDoesNotExist = false;

        // Gets a coordinate of a ship element on the random enemy board.
        while(!shipExists){
            if(e.getBoardElement(rowHasShip,colHasShip)==3){
                shipExists = true;
            }
            else{
                rowHasShip= new Random().nextInt(10);
                colHasShip= new Random().nextInt(10);
            }
        }

        while (!shipDoesNotExist) {
            if(e.getBoardElement(rowNoShip,colNoShip)==0){
                shipDoesNotExist = true;
            }
            else{
                rowNoShip = new Random().nextInt(10);
                colNoShip = new Random().nextInt(10);
            }
        }

        assertEquals("Expected a ship at "+rowHasShip+","+colHasShip,3,e.getBoardElement(rowHasShip,colHasShip));
        e.fire(rowHasShip,colHasShip);
        assertEquals("Expected a hit ship at "+rowHasShip+","+colHasShip,2,e.getBoardElement(rowHasShip,colHasShip));
        assertEquals("Should have 16 ship elements.",16,e.getNumberOfShipElements());
        assertEquals("Expected no ship at "+rowNoShip+","+colNoShip,0,e.getBoardElement(rowNoShip,colNoShip));
        e.fire(rowNoShip,colNoShip);
        assertEquals("Expected a miss at "+rowNoShip+","+colNoShip,1,e.getBoardElement(rowNoShip,colNoShip));
        assertEquals("Should have 16 ship elements.",16,e.getNumberOfShipElements());

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
    }

    @Test
    public void test_hasFired(){
        // Validity of coordinates to fire on is ensured by the fire() methods,
        // First method asks for console input from user by calling getCoordinates() which ensures proper input.
        // Second methods ensures proper input by assigning in bound row and columns to GUI buttons.
        EnemyBoard e = new EnemyBoard();
        e.makeRandomBoard();
        e.fire(1,1);

        assertEquals("Expected hasFired to be true for (1,1)", true, e.hasFired(1,1));
        assertEquals("Expected hasFired to be false for (0,0)", false, e.hasFired(0,0));

    }




}
