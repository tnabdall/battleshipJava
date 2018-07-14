# Battleship

Make a UML diagram sort of thing for each class.

Class
-variables
-variables
+methods
+methods

Description

GameBoard
Array (2d) 10x10 gameboard (1s or 0s to indicate hit)
Ship variables for 5 different ship pieces
Method: PlaceShip
Method: MarkHitOrMiss
Method: RevealDestroyedShip
Method: GetGameBoard

RunBattleship
Method: GetGuess
Method: PrintBoard
Method: RunGame1Player
Method: RunGame2Player
Method: GameDone

Ship
Length
Name

EnemyAI
Int x, Int y (Coordinates for last move)
Int LengthofSmallestShipRemaining
Boolean Last Move Hit, Boolean 2ndLastMoveHit, Boolean ShipDestroyed
Method: StrategyEasy
Method: StrategyMedium
Method: ChooseHitXY

Graphics


