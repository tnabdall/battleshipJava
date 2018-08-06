package Test;

import Main.*;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.Vector;

public class AITest {

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

        assertEquals("Expected random fire. Got logical fire.", false,logicalHit);

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
        assertEquals("Expected first row to be 4", 4, AI.getRow());
        assertEquals("Expected first column to be 4", 4, AI.getCol());

    }

    @Test
    public void test_Challenge_Difficulty_and_Probability_HalfBoardWipedSearchForCarrier(){
        PlayerBoardTest pTest = new PlayerBoardTest();
        PlayerBoard p =pTest.getTestBoard();
        AI AI = new AI(p,1);
        for (int i = 0; i<10; i++){
            for (int j = 5; j<10; j++){
                p.fire(i,j);
            }
        }
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





}
