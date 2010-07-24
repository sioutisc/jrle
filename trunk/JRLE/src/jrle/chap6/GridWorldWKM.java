/*
 * GridWorldWKM.java
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

package jrle.chap6;
import java.util.*;
/**
 *
 * @author  Christos Sioutis
 *
 *  The GridWorldWKM
 *
 *
 *  0,0 0,1  0,2  0,3  0,4  0,5  0,6  0,7  0,8  0,9
 *  1,0 1,1  1,2  1,3  1,4  1,5  1,6  1,7  1,8  1,9 
 *  2,0 2,1  2,2  2,3  2,4  2,5  2,6  2,7  2,8  2,9
 *  3,0 3,1  3,2  3,3  3,4  3,5  3,6  3,7  3,8  3,9
 *  4,0 4,1  4,2  4,3  4,4  4,5  4,6  4,7  4,8  4,9
 *  5,0 5,1  5,2  5,3  5,4  5,5  5,6  5,7  5,8  5,9
 *  6,0 6,1  6,2  6,3  6,4  6,5  6,6  6,7  6,8  6,9
 *
 *
 */
public class GridWorldWKM {
    
    final static int GRID_ROWS = 7;
    final static int GRID_COLS = 10;
    
    //actions and placements
    final static int NORTH = 1;
    final static int SOUTH = 2;
    final static int EAST = 3;
    final static int WEST = 4;
    final static int NORTHWEST = 5;
    final static int NORTHEAST = 6;
    final static int SOUTHWEST = 7;
    final static int SOUTHEAST = 8;
    final static int NUMACTIONS = 8;
    
    final static int REWARD_STEP = -1;
    
    GridWorldState start = new GridWorldState(3,0);
    GridWorldState goal = new GridWorldState(3,7);
    //GridWorldState current = new GridWorldState(start);
    
    final static double EPSILON = 0.1;
    final static double ALPHA = 0.1;
    final static double GAMMA = 0.9;
    final static double THRESHOLD = 0.01;
    
    GridWorldStateActionValueFunction valueFunction = new GridWorldStateActionValueFunction();
    Random uniform = new Random();
    
    /** Creates a new instance of GridWorldWKM */
    public GridWorldWKM(){
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Creating the GridWorldWKM");
        GridWorldWKM gw = new GridWorldWKM();
        System.out.println("Finding the optimal path");
        gw.findOtimalPath();
        System.out.println(gw.toStringOptimalPath());
    }
    
    /** uses E-Greedy to select the next action based on Q */
    public int getNextAction(GridWorldState current){
        double r = uniform.nextDouble();
        
        if(r <= EPSILON)
            //return 1+uniform.nextInt(NUMACTIONS+1);
            return 1+uniform.nextInt(4);
        
        return valueFunction.getBestAction(current);
    }
    //uses the sarsa algorithm to find the optimal path
    public void findOtimalPath(){
        GridWorldState state, nextState;
        int action, nextAction, reward, step;
        double v, vNext, value;
        for(int i=0; i<100; i++){
            step = 0;
            state = new GridWorldState(start);
            action = getNextAction(state);
            do{
                nextState = getNextState(state, action);
                reward = getReward(state, action);
                nextAction = getNextAction(nextState);
                
                v = valueFunction.getStateActionValue(state,action);
                vNext = valueFunction.getStateActionValue(nextState,nextAction);

                value = v + ALPHA * (reward + GAMMA * vNext - v);
                state = nextState;
                action = nextAction;
                
                step++;
                if(step % 1000 == 0){
                    System.out.println(step);
                }
            }
            while(!state.equals(goal));
        }
    }
    
    
    
    
    double modulus(double number) {
        if(number<0)
            return 1-number;
        return number;
    }
    
    int getReward(GridWorldState current, int action) {
        if(current.equals(goal))
            return 0;
        return REWARD_STEP;
    }
    
    GridWorldState getNextState(GridWorldState current, int action) {
        //define what happens at boundary
        GridWorldState toReturn = new GridWorldState(current);
        int boundary = current.isOnBoundary();
        
        switch(action) {
            case NORTH:
                if(boundary != NORTH && boundary != NORTHWEST && boundary != NORTHEAST)
                    toReturn.row -= 1;
                break;
            case SOUTH:
                if(boundary != SOUTH && boundary != SOUTHWEST && boundary != SOUTHEAST)
                    toReturn.row += 1;
                break;
            case EAST:
                if(boundary != EAST && boundary != NORTHEAST && boundary != SOUTHEAST)
                    toReturn.column += 1;
                break;
            case WEST:
                if(boundary != WEST && boundary != NORTHWEST && boundary != SOUTHWEST)
                    toReturn.column -= 1;
                break;
            case NORTHWEST:
                if(boundary != NORTH && boundary != NORTHWEST && boundary != NORTHEAST &&
                boundary != WEST && boundary != NORTHWEST && boundary != SOUTHWEST){
                    toReturn.row -= 1;
                    toReturn.column -= 1;
                }
            case NORTHEAST:
                if(boundary != NORTH && boundary != NORTHWEST && boundary != NORTHEAST &&
                boundary != EAST && boundary != NORTHEAST && boundary != SOUTHEAST){
                    toReturn.row -= 1;
                    toReturn.column += 1;
                }
            case SOUTHWEST:
                if(boundary != SOUTH && boundary != SOUTHWEST && boundary != SOUTHEAST &&
                boundary != WEST && boundary != NORTHWEST && boundary != SOUTHWEST){
                    toReturn.row += 1;
                    toReturn.column -= 1;
                }
            case SOUTHEAST:
                if(boundary != SOUTH && boundary != SOUTHWEST && boundary != SOUTHEAST &&
                boundary != EAST && boundary != NORTHEAST && boundary != SOUTHEAST){
                    toReturn.row += 1;
                    toReturn.column += 1;
                }
                break;
        }
        
        // add the wind
        toReturn.row = Math.max(toReturn.row - getWind(toReturn.column),0);
        
        return toReturn;
    }
    
    public String toStringOptimalPath(){
        //GridWorldState state;
        //int action;
        StringBuffer sb = new StringBuffer();
        int gridWorld[][] = new int[GRID_ROWS+1][GRID_COLS+1];
        GridWorldState state = new GridWorldState(start);
        int action, step=2;

        //put a 1 in the start position of the gridWorld array
        gridWorld[3][0] = 1;
        //fill in the gridWorld 2D array
        do{
            action = valueFunction.getBestAction(state);
            state = getNextState(state, action);
            gridWorld[state.row][state.column] = step;
            step++;
        }while(!state.equals(start) && step < 99);
        
        //print the gridWorld 2D array
        for(int i=0; i<GRID_ROWS; i++){
            sb.append("\n"+i+"|");
            for(int j=0; j<GRID_COLS; j++){
                if(gridWorld[i][j] == 0)
                    sb.append("  |");
                else{
                    if(gridWorld[i][j] < 10)
                        sb.append(" "+gridWorld[i][j]+"|");
                    else
                        sb.append(gridWorld[i][j]+"|");
                }
            }
        }
        sb.append("\n   0  1  2  3  4  5  6  7  8  9");        
        return sb.toString();
    }
    
    int getWind(int column){
        if(column == 6 || column == 7)
            return 2;
        if(column == 3 || column == 4 || column == 5 || column == 8)
            return 1;
        return 0;
    }
}
