package Test;

import Main.*;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.Vector;

public class AITest {

    private Vector<int[]> shipvect = new Vector<int[]>();

    private static int bRows = GameBoard.getNUMROWS();
    private static int bCols = GameBoard.getNUMCOLS();

    @Test
    public void test_Random_Difficulty(){
        PlayerBoardTest pTest = new PlayerBoardTest();
        PlayerBoard p =pTest.getTestBoard();
        AI AI = new AI(p,3);
        int firstHitRow, firstHitCol;

        do{
            AI.runDifficulty();
            p.fire(AI.getRow(),AI.getCol());
            firstHitRow = AI.getRow();
            firstHitCol = AI.getCol();
        }while(p.getBoardElement(AI.getRow(),AI.getCol())!=2);

        AI.runDifficulty();


        boolean logicalHit = Math.abs(AI.getRow() - firstHitRow) + Math.abs(AI.getCol() - firstHitCol) == 1;
        p.fire(AI.getRow(),AI.getCol());

        AI.runDifficulty();

        // Second check to make sure first check wasnt randomly lucky. Checks that second hit is within 2 spots from the first hit.
        boolean logicalHit2 = Math.abs(AI.getRow() - firstHitRow) + Math.abs(AI.getCol() - firstHitCol) == 2;

        assertEquals("Expected random fire. Got logical fire.", false,logicalHit||logicalHit2);

    }

    @Test
    public void test_Normal_Difficulty_CheckAroundShip(){
        PlayerBoardTest pTest = new PlayerBoardTest();
        PlayerBoard p =pTest.getTestBoard();
        AI AI = new AI(p,0);
        int firstHitRow, firstHitCol;

        do{
            AI.runDifficulty();
            p.fire(AI.getRow(),AI.getCol());
            firstHitRow = AI.getRow();
            firstHitCol = AI.getCol();
        }while(p.getBoardElement(AI.getRow(),AI.getCol())!=2);

        AI.runDifficulty();

        boolean logicalHit = Math.abs(AI.getRow() - firstHitRow) + Math.abs(AI.getCol() - firstHitCol) == 1;

        assertEquals("Expected logical fire. Should search around the ship after hitting it.", true,logicalHit);

    }

    @Test
    public void test_Normal_Difficulty_KillShipInReasonableHits(){
        PlayerBoardTest pTest = new PlayerBoardTest();
        PlayerBoard p =pTest.getTestBoard();
        AI AI = new AI(p,0);
        int firstHitRow, firstHitCol;
        int shipLength;

        do{
            AI.runDifficulty();
            p.fire(AI.getRow(),AI.getCol());
            firstHitRow = AI.getRow();
            firstHitCol = AI.getCol();
        }while(p.getBoardElement(AI.getRow(),AI.getCol())!=2);

        shipLength = p.getShipFiredOnLength(firstHitRow,firstHitCol);

        for(int i = 0; i<shipLength-1+3; i++) { //Already hit ship once. At max should hit (length-1)+3 times again.
            AI.runDifficulty();
            p.fire(AI.getRow(), AI.getCol());
        }


        assertEquals("Expected ship fired on should be killed.", false,p.getShipObject(firstHitRow,firstHitCol).isAlive(p));

    }

    @Test
    public void test_Challenge_Difficulty_and_Probability_fireAtMostProbableLocationFirst(){
        PlayerBoardTest pTest = new PlayerBoardTest();
        PlayerBoard p =pTest.getTestBoard();
        AI AI = new AI(p,1);
        AI.runDifficulty();
        System.out.println(AI.getRow()+" "+AI.getCol());
        boolean rowCheck = AI.getRow()==4 || AI.getRow() == 5;
        boolean colCheck = AI.getCol() == 4 || AI.getCol() == 5;
        assertEquals("Expected first row to be 4 or 5", true, rowCheck);
        assertEquals("Expected first column to be 4 or 5", true, colCheck);

    }

    @Test
    public void test_Challenge_Difficulty_and_Probability_HalfBoardWipedSearchForCarrier(){
        PlayerBoardTest pTest = new PlayerBoardTest();
        PlayerBoard p =pTest.getTestBoard();
        AI AI = new AI(p,1);
        for (int i = 0; i<bRows; i++){
            for (int j = 5; j<bCols; j++){
                p.fire(i,j);
            }
        }
        p.fire(9,0);
        p.fire(9,1);
        p.fire(9,2);
        p.fire(9,3);
        p.fire(9,4);
        AI.runDifficulty();
        System.out.println(AI.getRow()+" "+AI.getCol());
        assertEquals("Expected row to be 4", 4, AI.getRow());
        assertEquals("Expected column to be 2", 2, AI.getCol());

    }

    @Test
    public void test_Impossible_Difficulty_First5HitsTrue(){
        PlayerBoardTest pTest = new PlayerBoardTest();
        PlayerBoard p =pTest.getTestBoard();
        AI AI = new AI(p,2);
        int counter = 0;
        for (int i = 0; i<5; i++){

            AI.runDifficulty();
            p.fire(AI.getRow(),AI.getCol());
            if (p.getBoardElement(AI.getRow(),AI.getCol())==2){
                counter+=1;
            }
        }
        assertEquals("Should have hit all 5 times on impossible", 5, counter);
    }

    /** Sets up all the ships on the edges of the playerboard.
     * @return the playerboard for the AI to use.
     */
    public PlayerBoard getBoardEdge(){
        PlayerBoard p = new PlayerBoard();
        Ship[] ships = p.getShips();
        for (int i = 0; i < ships[0].getLength(); i++) {
            int[] coords = new int[2];
            coords[0] = 0;
            coords[1] = 0 + i;
            shipvect.add(coords);
        }
        p.placeShip(shipvect, ships[0]);
        shipvect.clear();

        for (int i = 0; i < ships[1].getLength(); i++) {
            int[] coords = new int[2];
            coords[0] = 1 + i;
            coords[1] = 0;
            shipvect.add(coords);
        }
        p.placeShip(shipvect, ships[1]);
        shipvect.clear();

        for (int i = 0; i < ships[2].getLength(); i++) {
            int[] coords = new int[2];
            coords[0] = bRows - 1;
            coords[1] = 0 + i;
            shipvect.add(coords);
        }
        p.placeShip(shipvect, ships[2]);
        shipvect.clear();

        for (int i = 0; i < ships[3].getLength(); i++) {
            int[] coords = new int[2];
            coords[0] = bRows - 1 - i;
            coords[1] = bCols - 1;
            shipvect.add(coords);
        }
        p.placeShip(shipvect, ships[3]);
        shipvect.clear();

        for (int i = 0; i < ships[4].getLength(); i++) {
            int[] coords = new int[2];
            coords[0] = 0 + i;
            coords[1] = bCols - 1;
            shipvect.add(coords);
        }
        p.placeShip(shipvect, ships[4]);
        shipvect.clear();
        return new PlayerBoard(p);
    }

    /** Checks to see if the normal difficulty runs successfully.
     * Also checks if the AI goes out of bounds.
     */
    @Test
    public void test_normalDifficulty() {
        PlayerBoard p = getBoardEdge();
        EnemyBoard e = new EnemyBoard();
        AI a = new AI(p, 0);
        boolean success = false;
        boolean outOfBounds = false;
        while (p.getNumberOfShipElements() != 0 && e.getNumberOfShipElements() != 0) {
            e.randomFire();
            a.runDifficulty();
            if (p.locStatus(a.getRow(), a.getCol()) == 4) {
                outOfBounds = true;
                assertEquals("AI went out of bounds.", false, outOfBounds);
                break;
            }
            p.fire(a.getRow(), a.getCol());
            if (p.getNumberOfShipElements() == 0) {
                success = true;
            }
        }
        assertEquals("AI normal difficulty should have played the game successfully.", true, success);

    }

    /** Checks to see if the challenge difficulty runs successfully.
     * Also checks if the AI goes out of bounds.
     */
    @Test
    public void test_challengeDifficulty() {
        PlayerBoard p = getBoardEdge();
        EnemyBoard e = new EnemyBoard();
        AI a = new AI(p, 1);
        boolean success = false;
        boolean outOfBounds = false;
        while (p.getNumberOfShipElements() != 0 && e.getNumberOfShipElements() != 0) {
            e.randomFire();
            a.runDifficulty();
            if (p.locStatus(a.getRow(), a.getCol()) == 4) {
                outOfBounds = true;
                assertEquals("AI went out of bounds.", false, outOfBounds);
                break;
            }
            p.fire(a.getRow(), a.getCol());

            if (p.getNumberOfShipElements() == 0) {
                success = true;
            }
        }
        assertEquals("AI challenge difficulty should have played the game successfully.", true, success);


    }

    /** Checks to see if the impossible difficulty runs correctly.
     */
    @Test
    public void test_impossibleDifficulty() {
        PlayerBoard p = new PlayerBoard();
        EnemyBoard e = new EnemyBoard();
        p.makeRandomBoard();
        AI a = new AI(p, 2);
        boolean success = false;
        while (p.getNumberOfShipElements() != 0 && e.getNumberOfShipElements() != 0) {
            e.randomFire();
            a.runDifficulty();
            p.fire(a.getRow(), a.getCol());
            if (p.getNumberOfShipElements() == 0 && a.getTurn() == 17) {
                success = true;
            }
        }
        assertEquals("AI impossible difficulty should have played the game successfully in 17 turns.",
                true, success);
    }

    /** Checks to see if the AI runs on normal difficulty by default when given
     * a negative difficulty setting.
     */
    @Test
    public void test_runDifficulty_negative() {
        PlayerBoard p = new PlayerBoard();
        p.makeRandomBoard();
        AI a = new AI(p, -1);
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());
        boolean success = false;
        for (int i = 0; i < bRows; i++) {
            for (int j = 0; j < bCols; j++) {
                if (p.locStatus(i, j) == 1 || p.locStatus(i, j) == 2) {
                    success = true;
                }
            }
        }
        assertEquals("Initialized AI with -1 difficulty. AI should play normal difficulty.", true, success);
    }

    /** Checks to see if the AI runs on normal difficulty by default when given
     * a difficulty setting that is too high.
     */
    @Test
    public void test_runDifficulty_tooHigh() {
        PlayerBoard p = new PlayerBoard();
        p.makeRandomBoard();
        AI a = new AI(p, 4);
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());
        boolean success = false;
        for (int i = 0; i < bRows; i++) {
            for (int j = 0; j < bCols; j++) {
                if (p.locStatus(i, j) == 1 || p.locStatus(i, j) == 2) {
                    success = true;
                }
            }
        }
        assertEquals("Initialized AI with 4 difficulty. AI should play normal difficulty.", true, success);
    }

    /** Checks to see if the AI runs on a valid difficulty.
     */
    @Test
    public void test_runDifficulty_valid() {
        PlayerBoard p = new PlayerBoard();
        p.makeRandomBoard();
        AI a = new AI(p, 2);

        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());

        assertEquals("Ran three turns with impossible AI difficulty (2). Ship elements should be 14",
                14, p.getNumberOfShipElements());
    }

    /** Checks if using the setter to the set AI to a negative difficulty sets the AI to
     * the default normal difficulty.
     */
    @Test
    public void test_setDifficulty_negative() {
        PlayerBoard p = new PlayerBoard();
        p.makeRandomBoard();
        AI a = new AI(p);
        a.setDifficulty(-1);
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());

        boolean success = false;
        for (int i = 0; i < bRows; i++) {
            for (int j = 0; j < bCols; j++) {
                if (p.locStatus(i, j) == 1 || p.locStatus(i, j) == 2) {
                    success = true;
                }
            }
        }
        assertEquals("Initialized AI with 4 difficulty. AI should play normal difficulty.", true, success);
    }

    /** Checks if using the setter to set the AI to a difficulty that is higher than 3
     *  defaults the AI to normal difficulty.
     */
    @Test
    public void test_setDifficulty_tooHigh() {
        PlayerBoard p = new PlayerBoard();
        p.makeRandomBoard();
        AI a = new AI(p);
        a.setDifficulty(4);
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());

        boolean success = false;
        for (int i = 0; i < bRows; i++) {
            for (int j = 0; j < bCols; j++) {
                if (p.locStatus(i, j) == 1 || p.locStatus(i, j) == 2) {
                    success = true;
                }
            }
        }
        assertEquals("Initialized AI with 4 difficulty. AI should play normal difficulty.", true, success);
    }

    /** Checks if using the setter to set the AI to impossible difficulty functions properly.
     *
     */
    @Test
    public void test_setDifficulty_impossible() {
        PlayerBoard p = new PlayerBoard();
        p.makeRandomBoard();
        AI a = new AI(p);
        a.setDifficulty(2);
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());

        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());

        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());

        assertEquals("Ran three turns with impossible AI difficulty (2). Ship elements should be 14",
                14, p.getNumberOfShipElements());
    }

    /** Checks if the turn count is correct.
     */
    @Test
    public void test_checkTurn() {
        PlayerBoard p = new PlayerBoard();
        p.makeRandomBoard();
        AI a = new AI(p, 3);

        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());
        a.runDifficulty();
        p.fire(a.getRow(), a.getCol());

        assertEquals("AI played 4 turns. Turn should be 4.", 4, a.getTurn());
    }





}
