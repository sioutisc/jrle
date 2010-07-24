/*
 * BlackJackValues.java
 *
 * Created on June 16, 2004, 1:58 PM
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
/**
 *
 * @author  Christos Sioutis
 */
public class BlackJackValues {
    Integer[] integerValues  = new Integer[11];  //we will reuse these objects instead of creating a new integer for each card drawn
    Boolean[] booleanValues = new Boolean[2];
    BlackJackState[][][] stateValues = new BlackJackState[22][22][2];  //contains all the possible blackjack states
    
    /** Creates a new instance of BlackJackValues */
    public BlackJackValues() {
        
        for(int i=1; i<=10; i++)
            integerValues[i] = new Integer(i);
        
        booleanValues[0] = new Boolean(false);
        booleanValues[1] = new Boolean(true);
        
        for(int playerSum=1; playerSum<22; playerSum++)
            for(int dealerSum=1; dealerSum<22; dealerSum++){
                    stateValues[playerSum][dealerSum][0] = new BlackJackState(playerSum,dealerSum,false);
                    stateValues[playerSum][dealerSum][1] = new BlackJackState(playerSum,dealerSum,true);
            }
        
        
    }
    
    public BlackJackState getBlackJackState(BlackJackState state){
        return getBlackJackState(state.playerSum, state.dealerSum, state.useableAce);
    }
    
    public BlackJackState getBlackJackState(int playerSum, int dealerSum, boolean useableAce){
        if(playerSum > 0 && playerSum < 22 && dealerSum > 0 && dealerSum < 22){
            if(useableAce)
                return stateValues[playerSum][dealerSum][1];
            else
                return stateValues[playerSum][dealerSum][0];
        }
        return null;
    }
    
    public Boolean getBooleanValue(boolean value){
        if(value)
            return booleanValues[1];
        return booleanValues[0];
    }    
    
    public Integer getIntegerValue(int value){
        if(value > 0 && value < 11)
            return integerValues[value];
        return null;
    }
}
