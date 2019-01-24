package Chess;

//used primarily to simplify selection logic
class Position{
    int x;
    int y;

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    Position copy(){
        return new Position(x,y);
    }

    boolean equals(Position pos){
        return pos.x == x && pos.y == y;
    }
    
    boolean equals(int x, int y){
        return this.x == x && this.y == y;
    }

    void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    void set(Position pos){
        this.x = pos.x;
        this.y = pos.y;
    }
}