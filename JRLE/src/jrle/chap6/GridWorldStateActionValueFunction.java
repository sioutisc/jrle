/*
 * GridWorldStateActionValueFunction.java
 *
 * Created on June 30, 2004, 3:16 PM
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
 */
public class GridWorldStateActionValueFunction extends HashMap{
    GridWorldStateActionPair pair = new GridWorldStateActionPair(new GridWorldState(0,0),0);
    /** Creates a new instance of GridWorldStateActionValueFunction */
    public GridWorldStateActionValueFunction() {
    }
    
    public void addStateActionValue(GridWorldStateActionPair pair, Double value){
        put(pair, value);
    }
    
    public double getStateActionValue(GridWorldState state, int action){
        pair.state = state;
        pair.action = action;
        
        if(containsKey(pair))
            return ((Double)get(pair)).doubleValue();
        return 0.0;
    }
    
    public int getBestAction(GridWorldState state){
        double values[] = new double[GridWorldWKM.NUMACTIONS+1];
        Double value;
        
        GridWorldStateActionPair pair = new GridWorldStateActionPair(state,0);
        for (int i=1; i<= GridWorldWKM.NUMACTIONS; i++){
            pair.action = i;
            value = (Double)get(pair);
            if(value != null)
                values[i] = value.doubleValue();
        }
        return argmaxStartOne(values);
    }

    
   int argmaxStartOne(double values[]){
        int maxIndex=1;
        double maxValue=1;
        for(int i=1; i<values.length; i++){
            if(values[i] > maxValue){
                maxValue = values[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
   
   
   
 
   /*
    public boolean containsState(GridWorldState state){
        GridWorldStateActionPair pair = new GridWorldStateActionPair(state,false);
        if(containsKey(pair))
            return true;
        pair.action = true;
        if(containsKey(pair))
            return true;
        return false;
    }*/
       
}
