class Ship{


    private int length;
    private String name;
    private boolean isAlive = true;
    private int[][] coords;


    public Ship(String name, int length){
        this.name = name;
        this.length = length;
		coords = new int[length][2];
    }

    public Ship(String name, int length, int[][] newCoords){
        this.name = name;
        this.length = length;
        this.coords = newCoords;
		coords = new int[length][2];
    }

    public void setCoords(int[] newCoords, int index){
        int[] coordsCopy = new int[2];
		coordsCopy[0] = newCoords[0];
		coordsCopy[1] = newCoords[1];
		coords[index] = coordsCopy;
    }

    public int[] getCoords(int index) { return coords[index]; }
	
	public int[][] getAllCoords() {return coords;}
	
	public void printCoords(){
		for (int i = 0; i< coords.length; i++){
			System.out.println(name + ": " + coords[i][0] + ", " + coords[i][1]);
		}
	}

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}