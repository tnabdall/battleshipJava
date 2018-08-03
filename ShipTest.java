import org.junit.Test;
import static org.junit.Assert.*;

public class ShipTest {
	
	@Test
	public void test_Constructor(){
		Ship test = new Ship("Bob", 5);
		assertEquals("Name should be Bob", "Bob",test.getName());
		assertEquals("Length should be 5", 5, test.getLength());
		int[][] coords = new int[2][2];
		coords[0][0]=5;
		coords[0][1]=4;
		coords[1][0]=5;
		coords[1][0]=5;
		Ship test2 = new Ship("Betty", 2, coords);
		assertEquals("Name should be Betty", "Betty",test2.getName());
		assertEquals("Length should be 2", 2, test2.getLength());
		
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
	
	
	
}
