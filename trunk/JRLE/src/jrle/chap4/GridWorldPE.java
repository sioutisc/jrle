/*
 * GridWorldPE.java
 *
 * Created on May 19, 2004, 12:26 PM
 * ---------------------------------------------------------------------
 * This file is part of JRLE.
 *
 * JRLE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JRLE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JRLE.  If not, see <http://www.gnu.org/licenses/>.
 * --------------------------------------------------------------------- 
 */

package jrle.chap4;
/**
 *
 * @author  Christos Sioutis
 *
 *  The GridWorldPE
 *
 *  0,0 0,1  0,2  0,3  0,4
 *  1,0 1,1  1,2  1,3  1,4
 *  2,0 2,1  2,2  2,3  2,4
 *  3,0 3,1  3,2  3,3  3,4
 *  4,0 4,1  4,2  4,3  4,4
 *
 */
public class GridWorldPE {
    
    final static int GRID_ROWS = 5;
    final static int GRID_COLS = 5;
    
    //actions and positions
    final static int NORTH = 1;
    final static int SOUTH = 2;
    final static int EAST = 3;
    final static int WEST = 4;
    final static int NORTHWEST = 5;
    final static int NORTHEAST = 6;
    final static int SOUTHWEST = 7;
    final static int SOUTHEAST = 8;
    
    final static int REWARD_A = 10;
    final static int REWARD_B = 5;
    final static int REWARD_BOUNDARY_HIT = -1;
    final static int REWARD_STEP = 0;
    
    final Position A;
    final Position A1;
    final Position B;
    final Position B1;
    
    final static double GAMMA = 0.9;
    final static double THRESHOLD = 0.01;
    
    double stateValueFunction[][];
    
    /** Creates a new instance of GridWorldPE */
    public GridWorldPE() {
        stateValueFunction = new double[GRID_ROWS][GRID_COLS];
        A = new Position(0,1);
        A1 = new Position(4,1);
        B = new Position(0,3);
        B1 = new Position(2,3);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Creating the GridWorldPE");
        GridWorldPE gw = new GridWorldPE();
        System.out.println("Finding the state-value function");
        gw.findStateValueFunction();
        System.out.println(gw);
    }
    
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        int itmp;
        for(int i=0; i<GRID_ROWS; i++){
            sb.append("\n");
            for(int j=0; j<GRID_COLS; j++) {
                stateValueFunction[i][j] = stateValueFunction[i][j]*10;
                itmp = (int) stateValueFunction[i][j];
                stateValueFunction[i][j] = 1.0*itmp/10;
                sb.append("\t["+i+","+j+"]="+stateValueFunction[i][j]);
            }
        }
        return sb.toString();
    }
    
    public double[][] getStateValueFunction(){
        return stateValueFunction;
    }
    
    public void findStateValueFunction(){
        double v=0, delta=0;
        while(delta == 0 || delta > THRESHOLD) {  
            delta = 0;
            for(int i=0; i<GRID_ROWS; i++)
                for(int j=0; j<GRID_COLS; j++){
                    v = stateValueFunction[i][j];
                    stateValueFunction[i][j] = calculateValue(i,j);
                    delta += Math.max(delta, modulus(v - stateValueFunction[i][j]));
                }
            //System.out.println(delta + " ");
        }
    }
    
    double calculateValue(int x, int y) {
        double toReturn=0.0;
        toReturn += 0.25 * calculateRelationalValue(new Position(x,y),NORTH);
        toReturn += 0.25 * calculateRelationalValue(new Position(x,y),SOUTH);
        toReturn += 0.25 * calculateRelationalValue(new Position(x,y),EAST);
        toReturn += 0.25 * calculateRelationalValue(new Position(x,y),WEST);        
        return toReturn;
    }
    
    
    double modulus(double number) {
        if(number<0)
            return 1-number;
        return number;
    }
    
    double getValue(Position state) {
        return stateValueFunction[state.x][state.y];
    }
    
    /* we are meant to sum the value of all the value of all possible state that can follow
       this action, however for this world, only one state can follow an action, hence there
       is no need to have an addition */
    double calculateRelationalValue(Position current, int action) {
        double toReturn = 0.0;
        Position nextState = getNextState(current,action);
        return 1.0 * (getReward(current, action) + (0.9 * getValue(nextState)));
    }
    
    int getReward(Position current, int action) {
        if(current.equals(A))
            return REWARD_A;
        else if(current.equals(B))
            return REWARD_B;
        else if(current.equals(getNextState(current,action)))
            return REWARD_BOUNDARY_HIT;
        return REWARD_STEP;
    }
    
    Position getNextState(Position current, int action) {
        //define what happens at boundary
        Position toReturn = new Position(current);
        int boundary = current.isOnBoundary();
        
        if(current.equals(A))
            toReturn = A1;
        else if(current.equals(B))
            toReturn = B1;
        else {
            switch(action) {
                case NORTH:
                    if(boundary != NORTH && boundary != NORTHWEST && boundary != NORTHEAST)
                        toReturn.y -= 1;
                    break;
                case SOUTH:
                    if(boundary != SOUTH && boundary != SOUTHWEST && boundary != SOUTHEAST)
                        toReturn.y += 1;
                    break;
                case EAST:
                    if(boundary != EAST && boundary != NORTHEAST && boundary != SOUTHEAST)
                        toReturn.x += 1;
                    break;
                case WEST:
                    if(boundary != WEST && boundary != NORTHWEST && boundary != SOUTHWEST)
                        toReturn.x -= 1;
                    break;
            }
        }
        return toReturn;
    }
    
    
    
    class Position {
        public int x;
        public int y;
        
        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public Position(Position other) {
            this.x = other.x;
            this.y = other.y;
        }
        
        boolean equals(Position other) {
            return (other.x == x && other.y == y);
        }
        
        public int isOnBoundary() {
            if(x == 0) {
                if (y == 0)
                    return GridWorldPE.NORTHWEST;
                else if (y == 4)
                    return GridWorldPE.SOUTHWEST;
                return GridWorldPE.WEST;
            }
            else if (x == 4) {
                if (y == 0)
                    return GridWorldPE.NORTHEAST;
                else if (y == 4)
                    return GridWorldPE.SOUTHEAST;
                return GridWorldPE.EAST;
            }
            else {
                if(y==0)
                    return GridWorldPE.NORTH;
                if(y==4)
                    return GridWorldPE.SOUTH;
            }
            return -1;
        }
        
        public String toString(){
            return (""+x+","+y);
        }
    }
}
