class Ship{


    private int length;
    private String name;
    private boolean isAlive = true;
    private int[][] coords;


    public Ship(String name, int length){
        this.name = name;
        this.length = length;
    }

    public Ship(String name, int length, int[][] newCoords){
        this.name = name;
        this.length = length;
        this.coords = newCoords;
    }

    public void setCoords(int[][] newCoords){
        coords = newCoords;
    }

    public int[][] getCoords() { return coords; }

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