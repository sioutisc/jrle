/*
 * Returns.java
 *
 * Created on June 9, 2004, 5:17 PM
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
public class Returns extends HashMap{
    Integer[] rewardValues = new Integer[3];
    
    /** Creates a new instance of Returns */
    public Returns() {
        rewardValues[0] = new Integer(0);
        rewardValues[1] = new Integer(1);
        rewardValues[2] = new Integer(-1);
    }
    
    public void addReward(BlackJackState state, int reward){
        Vector tmp;
        //System.out.println(state.equals((Object)state));
        if(containsKey(state)){
            tmp = (Vector) get(state);
            tmp.add(getRewardInteger(reward));
        }
        else{
            tmp = new Vector();
            tmp.add(getRewardInteger(reward));
            put(state,tmp);
        }
    }
    
    public Double getAverageReturns(BlackJackState state){
        Vector tmp;
        int sum = 0;
        if(containsKey(state)){
            tmp = (Vector) get(state);
            for(int i=0; i<tmp.size(); i++)
                sum+= ((Integer)tmp.get(i)).intValue();
            return new Double(1.0*sum/tmp.size());
        }
        return new Double(0.0);
    }
    
    public Vector getRewards(BlackJackState state){
        return (Vector)get(state);
    }
    
    public int getReward(BlackJackState state, int index){
        Vector tmp;
        if(containsKey(state)){
            tmp = getRewards(state);
            return((Integer)tmp.get(index)).intValue();
        }
        return 0;
    }
    
    Integer getRewardInteger(int reward){
        switch(reward){
            case 1:  return rewardValues[1];
            case -1: return rewardValues[2];
            default: return rewardValues[0]; 
        }
    }
    
    public String plot(boolean useableAce){
        StringBuffer sb = new StringBuffer();
        BlackJackState state;
        
        sb.append("\nPlayer\tDealer\tValue");
        
        Iterator states = keySet().iterator();
        while(states.hasNext()){
            state = (BlackJackState) states.next();
            if(state.useableAce == useableAce)
                //sb.append("\n"+state.playerSum+"\t"+state.dealerSum+"\t"+useableAce+"\t"+valueFunction.get(state));
                sb.append("\n"+state.playerSum+"\t"+state.dealerSum+"\t"+getAverageReturns(state));
        }
        return sb.toString();
        
    }

}
