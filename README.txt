// From Windows. Change ; to : for other OS
// Run from root folder

// To compile the code
javac GUI/*.java
javac Main/*.java
javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar Test/*.java

// To run the game
java Main.runBattleship
java GUI.BattleshipApp

// To do Junit Tests
java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore Test.EnemyBoardTest
java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore Test.GameBoardTest
java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore Test.PlayerBoardTest
java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore Test.ShipTest
java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore Test.AITest
java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore Test.AIShipDataTest

