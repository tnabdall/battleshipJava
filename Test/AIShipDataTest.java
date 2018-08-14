
package Test;

import Main.*;
import static org.junit.Assert.*;

import org.junit.Test;
import java.io.*;

/** JUnit test for the AIShipDataTest class.
 */
public class AIShipDataTest {

    private static int bRows = GameBoard.getNUMROWS(); // The number of rows on the board.
    private static int bCols = GameBoard.getNUMCOLS(); // The number of columns on the board.

    /** Checks to see if setting the initial row of a hit ship is set correctly.
     */
    @Test
    public void test_initRow_valid() {
        Ship s = new Ship("Carrier", 4);
        AIShipData t = new AIShipData(s, 4, 0);

        assertEquals("Initialized AIShipData object with row 4. Row should be 4", 4, t.getInitRow());
    }

    /** Checks to see if setting the initial row of a hit ship with a negative defaults it to 0.
     */
    @Test
    public void test_initRow_negative() {
        Ship s = new Ship("Carrier", 4);
        AIShipData t = new AIShipData(s, -1, 0);

        assertEquals("Initialized AIShipData object with row -1. Row should be 0", 0, t.getInitRow());
    }

    /** Chedks to see if setting the initial row of a hit ship with a value that is too high
     * defaults it to 0.
     */
    @Test
    public void test_initRow_tooHigh () {
        Ship s = new Ship("Carrier", 4);
        AIShipData t = new AIShipData(s, bRows, 0);

        assertEquals("Initialized AIShipData object with row " + bRows + ". Row should be 0", 0, t.getInitRow());
    }

    /** Checks to see if setting the initial column of a hit ship is set correctly.
     */
    @Test
    public void test_initCol_valid() {
        Ship s = new Ship("Carrier", 4);
        AIShipData t = new AIShipData(s, 0, 4);

        assertEquals("Initialized AIShipData object with column 4. Column should be 4", 4, t.getInitCol());
    }

    /** Checks to see if setting the initial column of a hit ship with a negative defaults it to 0.
     */
    @Test
    public void test_initCol_negative() {
        Ship s = new Ship("Carrier", 4);
        AIShipData t = new AIShipData(s, 0, -1);

        assertEquals("Initialized AIShipData object with column -1. Column should be 0", 0, t.getInitCol());
    }

    /** Chedks to see if setting the initial column of a hit ship with a value that is too high
     * defaults it to 0.
     */
    @Test
    public void test_initCol_tooHigh() {
        Ship s = new Ship("Carrier", 4);
        AIShipData t = new AIShipData(s, 0, bCols);

        assertEquals("Initialized AIShipData object with column " + bCols + ". Column should be 0", 0, t.getInitCol());
    }

    /** Checks to see if an invalid direction is set for a ship that it defaults it to null.
     */
    @Test public void test_direction_invalidDirection() {
        Ship s = new Ship("Carrier", 4);
        AIShipData t = new AIShipData(s, 0, 0);

        t.setDirection("invalid");
        assertEquals("Set direction to invalid. Direction should be null.", null, t.getDirection());
    }

    /** Checks to see if a valid direction is set correctly for a hit ship.
     */
    @Test
    public void test_direction_validDirection() {
        Ship s = new Ship("Carrier", 4);
        AIShipData t = new AIShipData(s, 0, 0);

        t.setDirection("left");
        assertEquals("Set direction to left. Direction should be left.", "left", t.getDirection());

        t.setDirection("right");
        assertEquals("Set direction to right. Direction should be right.", "right", t.getDirection());

        t.setDirection("up");
        assertEquals("Set direction to up. Direction should be up.", "up", t.getDirection());

        t.setDirection("down");
        assertEquals("Set direction to down. Direction should be down.", "down", t.getDirection());
    }

    /** Checks if a ship with 0 health is properply set to isSunk = true.
     */
    @Test
    public void test_isSunk_true() {
        Ship s = new Ship("Submarine", 3);
        AIShipData t = new AIShipData(s, 0, 0);

        t.minusShipHealth();
        t.minusShipHealth();
        t.minusShipHealth();
        assertEquals("Ship should be sunk.", true, t.getIsSunk());
    }

    /** Checks if a ship with health remaining is properly set to isSunk = false.
     */
    @Test public void test_isSunk_false() {
        Ship s = new Ship("Submarine", 3);
        AIShipData t = new AIShipData(s, 0, 0);

        t.minusShipHealth();
        t.minusShipHealth();
        assertEquals("Ship should not be sunk.", false, t.getIsSunk());
    }


    /** Checks to see if timesHit count is as expected.
     */
    @Test
    public void test_getTimesHit() {
        Ship s = new Ship("Submarine", 3);
        AIShipData t = new AIShipData(s, 0, 0);

        t.minusShipHealth();
        assertEquals("Ship should be hit once", 1, t.getTimesHit());
    }
}