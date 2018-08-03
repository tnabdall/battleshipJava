public class Probability extends PlayerBoard{


  private PlayerBoard prob;//copy of playerboard
  private int[][] probBoard = new int[10][10];
  private Ship[] ships;


/*
  public Probability(){
    PlayerBoard prob = new Probability();
    highest = 0;
  }

  public Probability(Playerboard prob){
    this.prob = prob;
    highest = 0;
	}
	*/

	public Probability(PlayerBoard prob) {
		this.prob = prob;
		this.ships = prob.getShips();
	}
/*
  public void setProbSize(){
    for (int i = 0; i < 10; i++){
      i++;
    }
      this.i = i;
    for (int j = 0; j < 10; j++){
      j++;
    }
    this.j = j;
  }

  public int getLength(){
    return this.i;
  }

  public int getHeight(){
    return this.j;
  }
*/
  public boolean canPlay(int row, int col){
    if (prob.locStatus(row, col) == 0 || prob.locStatus(row, col) == 3){
     return true;
    }else{
      return false;
    }
  }

  public int[] setCoordToPlay(){
	int probability = 0;
	int highest = 0;
	int[] coords = {0, 0};

  	 for (int i = 0; i < 10; i++) {
  			for(int j = 0; j < 10; j++){

          if(canPlay(i,j)){

              if (ships[4].isAlive(prob)) {
							if (canPlay(i-1, j) ) {
								probability++;
                }
    						if (canPlay(i+1,j) ){
    							probability++;
                }
    						if (canPlay(i, j-1) ){
    							probability++;
                }
    						if (canPlay(i, j+1) ){
    							probability++;
                }
    					}

    					if (ships[3].isAlive(prob)) {
    						if (canPlay(i-2,j)  &&
    								canPlay(i-2,j) 	){
    							probability++;
                }
    						if (canPlay(i+2,j)  &&
    								canPlay(i+2,j) ){
    							probability++;
                }
    						if (canPlay(i, j-2)  &&
    								canPlay(i, j-2) ){
    							probability++;
                }
    						if (canPlay(i, j+2)  &&
    								canPlay(i, j+2) ){
    							probability++;
                }
    					}

    					if (ships[2].isAlive(prob)) {
    						if (canPlay(i-2,j)  &&
    								canPlay(i-1,j) 	){
    							probability++;
                }
    						if (canPlay(i+2,j)  &&
    								canPlay(i+1,j) ){
    							probability++;
                }
    						if (canPlay(i, j-2)  &&
    								canPlay(i, j-1) ){
    							probability++;
                }
    						if (canPlay(i, j+2)  &&
    								canPlay(i, j+1) ){
    							probability++;
                }
    					}
    					//Battleship
    					if (ships[1].isAlive(prob)) {
    						if (canPlay(i-3,j)  &&
    								canPlay(i-2,j)  &&
    								canPlay(i-1,j) ){
    							probability++;
                }
    						if (canPlay(i+3,j)  &&
    								canPlay(i+2,j)  &&
    								canPlay(i+1,j) ){
    							probability++;
                }
    						if (canPlay(i, j-3)  &&
    								canPlay(i, j-2)  &&
    								canPlay(i, j-1) ){
    							probability++;
                }
    						if (canPlay(i, j+3)  &&
    								canPlay(i, j+2)  &&
    								canPlay(i, j+1) ){
    							probability++;
                }
    					}
    					//Aircraft carrier
    					if (ships[0].isAlive(prob)) {
    						if (canPlay(i-4,j)  &&
    								canPlay(i-3,j)  &&
    								canPlay(i-2,j)  &&
    								canPlay(i-1,j) ){
    							probability++;
                }
    						if (canPlay(i+4,j)  &&
    								canPlay(i+3,j)  &&
    								canPlay(i+2,j)  &&
    								canPlay(i+1,j) ){
    							probability++;
                }
    						if (canPlay(i, j-4)  &&
    								canPlay(i, j-3)  &&
    								canPlay(i, j-2)  &&
    								canPlay(i, j-1) ){
    							probability++;
                }
    						if (canPlay(i, j+4)  &&
    								canPlay(i, j+3)  &&
    								canPlay(i, j+2)  &&
    								canPlay(i, j+1) ){
    							probability++;
                }

              }
              probBoard[i][j] = probability;
						}
			if (probability > highest){
			  highest = probability;
			  coords[0] = i;
			  coords[1] = j;
              }

	 }


        }
    System.out.println(highest + " This is from Prob");
    printBoard();


		return coords;

  }

  public void printBoard(){
		System.out.println("      PROBABILITY BOARD     ");
		System.out.println("   A B C D E F G H I J"); // Column letters
        //System.out.println(" --------------------"); // Top border
        for (int i = 0; i<10; i++){
            if (i<9){
                System.out.print(i+1+" "); // Print row number
            }
            else{
                System.out.print(i+1); // So that all rows are aligned as 10 takes an extra character space.
            }
            for (int j = 0; j<10; j++){
                System.out.print("|"); // Print border
				// Replaces '+' printout for own ship with " " to indicate unknown space.
				        System.out.print(prob);
            }
            System.out.print("|\n"); // End row with border and new line
        }
        System.out.println("   --------------------"); // Bottom border

	}

/*
//Setter and Getter for probability
  public void setProb(int highest){
    this.highest = highest;
  }
// actual value for the spot
  public int getProb(){
    return this.highest;
    System.out.println(this.highest);
  }

//
  public Coordinate getCoordToPlay(){
    return coordToPlay;
  }
*/
//multiplier for the edge for the board
  private boolean isEdge(int i, int j){
    return true;
  }

//multiplier for corners
  private boolean isCorner(int i, int j){
    return true;
  }
}
