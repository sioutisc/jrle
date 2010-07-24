/*
 * BlackJackActionValueFunction.java
 *
 * Created on June 16, 2004, 12:15 AM
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

package jrle.chap5;
import java.util.HashMap;
/**
 *
 * @author  Christos Sioutis
 */
public class BlackJackActionValueFunction extends HashMap{
    
    /** Creates a new instance of BlackJackActionValueFunction */
    public BlackJackActionValueFunction() {
    }
    
    public void addStateActionValue(BlackJackStateActionPair pair, Double value){
        put(pair, value);
    }
    
    public double getStateActionValue(BlackJackStateActionPair pair){
        if(containsKey(pair))
            return ((Double)get(pair)).doubleValue();
        return 0.0;
    }
    
    public boolean getBestAction(BlackJackState state){
        BlackJackStateActionPair pair = new BlackJackStateActionPair(state,false);
        double stickValue = getStateActionValue(pair);
        pair.action = true;
        double hitValue = getStateActionValue(pair);
        if(stickValue>hitValue)
            return false;  //should stick
        return true;  //should hit
    }
    
    public boolean containsState(BlackJackState state){
        BlackJackStateActionPair pair = new BlackJackStateActionPair(state,false);
        if(containsKey(pair))
            return true;
        pair.action = true;
        if(containsKey(pair))
            return true;
        return false;
    }
    
}
