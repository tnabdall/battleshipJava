package Test;

import Main.*;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShipTest {
	
	@Test
	public void test_Constructor(){
		Ship test = new Ship("Bob", 5);
		Assert.assertEquals("Name should be Bob", "Bob",test.getName());
		Assert.assertEquals("Length should be 5", 5, test.getLength());
		int[][] coords = new int[2][2];
		coords[0][0]=5;
		coords[0][1]=4;
		coords[1][0]=5;
		coords[1][0]=5;
		Ship test2 = new Ship("Betty", 2, coords);
		Assert.assertEquals("Name should be Betty", "Betty",test2.getName());
		Assert.assertEquals("Length should be 2", 2, test2.getLength());
		
		boolean equals = true;
		int[][] shipcoords = test2.getAllCoords();
		for (int i = 0; i<2; i++){
			for (int j = 0; j<2; j++){
				if(coords[i][j]!= shipcoords[i][j]){
					equals = false;
				}
			}
		}
		assertEquals("Coordinates should be set to (5,4), (5,5)", true, equals);
		
	}
	
	@Test
	public void test_Copy_Constructor(){
		int[][] coords = new int[2][2];
		coords[0][0]=5;
		coords[0][1]=4;
		coords[1][0]=5;
		coords[1][0]=5;
		Ship test = new Ship("Betty", 2, coords);
		Ship testCopy = new Ship(test);
		
		boolean nameEquals = test.getName().equals(testCopy.getName());
		boolean lengthEquals = test.getLength() == testCopy.getLength();
		boolean coordsEquals = true;
		
		int[][] testCoords = test.getAllCoords();
		int[][] copyCoords = testCopy.getAllCoords();
		for (int i = 0; i<2; i++){
			for (int j = 0; j<2; j++){
				if(testCoords[i][j]!= copyCoords[i][j]){
					coordsEquals = false;
				}
			}
		}
		assertEquals("Both names should be Betty",true,nameEquals);
		assertEquals("Both lengths should be 2", true, lengthEquals);
		assertEquals("Both sets of coordinates should be equal", true, coordsEquals);
		
	}
	
	@Test
	public void test_SetAndGetLength(){
		int[][] coords = new int[2][2];
		coords[0][0]=5;
		coords[0][1]=4;
		coords[1][0]=5;
		coords[1][0]=5;
		Ship test = new Ship("Betty", 2, coords);
		test.setLength(4);

		Assert.assertEquals("Both lengths should be 4", 4, test.getLength());

	}

	@Test
	public void test_SetAndGetCoords(){
		int[][] coords = new int[2][2];
		coords[0][0]=5;
		coords[0][1]=4;
		coords[1][0]=5;
		coords[1][0]=5;
		Ship test = new Ship("Betty", 2, coords);

		int[] newCoords = new int[2];
		newCoords[0] = 5;
		newCoords[1] = 6;

		test.setCoords(newCoords,0);

		boolean equals = true;
		if(test.getCoords(0)[0]!= 5 || test.getCoords(0)[1]!=6){
			equals = false;
		}

		assertEquals("Should have updated first set of coords to (5,6)",true,equals);



	}

	@Test
	public void test_set_and_getName(){
		int[][] coords = new int[2][2];
		coords[0][0]=5;
		coords[0][1]=4;
		coords[1][0]=5;
		coords[1][0]=5;
		Ship test = new Ship("Betty", 2, coords);

		test.setName("John");

		Assert.assertEquals("Name should now be John", "John",test.getName());

	}

	@Test
	public void test_isAlive_true_0hits(){
		PlayerBoardTest pTest = new PlayerBoardTest();
		PlayerBoard testBoard = pTest.getTestBoard();
		Ship carrier = testBoard.getShips()[0];
		Assert.assertEquals("Carrier should be alive 0 hits", true, carrier.isAlive(testBoard));

	}

	@Test
	public void test_isAlive_true_4hits(){
		PlayerBoardTest pTest = new PlayerBoardTest();
		PlayerBoard testBoard = pTest.getTestBoard();
		Ship carrier = testBoard.getShips()[0];
		testBoard.fire(0,0);
		testBoard.fire(0,1);
		testBoard.fire(0,2);
		testBoard.fire(0,3);
		Assert.assertEquals("Carrier should be alive 4 hits", true, carrier.isAlive(testBoard));

	}

	@Test
	public void test_isAlive_false_5hits(){
		PlayerBoardTest pTest = new PlayerBoardTest();
		PlayerBoard testBoard = pTest.getTestBoard();
		Ship carrier = testBoard.getShips()[0];
		testBoard.fire(0,0);
		testBoard.fire(0,1);
		testBoard.fire(0,2);
		testBoard.fire(0,3);
		testBoard.fire(0,4);
		Assert.assertEquals("Carrier should be dead 5 hits", false, carrier.isAlive(testBoard));

	}






}
