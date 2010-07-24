/*
 * BlackJackPolicy.java
 *
 * Created on June 10, 2004, 12:59 PM
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
import java.util.*;
/**
 *
 * @author  Christos Sioutis
 */
public class BlackJackPolicy extends HashMap{
    BlackJackValues values;
    boolean start = true;
    
    /** Creates a new instance of BlackJackPolicy */
    public BlackJackPolicy(BlackJackValues v) {
        values = v;

        //set up default policy all states where the player should stick if the playerSum >= 20
        for(int playerSum = 20; playerSum < 22; playerSum++)
            for(int dealerSum = 1; dealerSum < 22; dealerSum++) {
                put(values.getBlackJackState(playerSum,dealerSum,true),values.getBooleanValue(false));
                put(values.getBlackJackState(playerSum,dealerSum,false),values.getBooleanValue(false));
            }
    }

    public void resetEpisodeStart(){
        start = true;
    }
    
    public void addStateActionPolicy(BlackJackState state, boolean action){
        put(state, values.getBooleanValue(action));
    }
        
    /** if a policy entry exists for this state, follow it, otherwise hit anyway */
    public boolean hit(BlackJackState state){
        if(start){
            Random uniform = new Random();
            start = false;
            return uniform.nextBoolean();
        }
        else if(containsKey(state)){
            return ((Boolean)get(state)).booleanValue();
        }
        return true;
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        Iterator iterator = keySet().iterator();
        BlackJackState state;
        while(iterator.hasNext()){
            state = (BlackJackState)iterator.next();
            sb.append("\n"+state.playerSum+"\t"+state.dealerSum+"\t"+get(state));
        }
        return sb.toString();
    }
}
