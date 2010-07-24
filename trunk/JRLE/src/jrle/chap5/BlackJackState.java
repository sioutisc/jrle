/*
 * BlackJackState.java
 *
 * Created on June 8, 2004, 11:53 PM
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
public class BlackJackState {
    public int playerSum;
    public int dealerSum;
    public boolean useableAce;
    
    
    public BlackJackState(){
        playerSum = 0;
        dealerSum = 0;
        useableAce = false;
    }
    
    public void clear(){
        playerSum = 0;
        dealerSum = 0;
        useableAce = false;
    }
    
    public BlackJackState(BlackJackState other){
        playerSum = other.playerSum;
        dealerSum = other.dealerSum;
        useableAce = other.useableAce;
    }
    
    
    /** Creates a new instance of BlackJackState */
    public BlackJackState(int player, int dealer, boolean ace) {
        playerSum = player;
        dealerSum = dealer;
        useableAce = ace;
    }
    
    public boolean equivalent(BlackJackState other){
        if(this == other)
            return true;
        if(other.playerSum == playerSum &&
        other.dealerSum == dealerSum &&
        other.useableAce == useableAce)
            return true;
        return false;
    }
    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        BlackJackState tmp = (BlackJackState) obj;
        return equivalent(tmp);
    }
    
    public int hashCode(){
        int hash = 1;
        
        hash += 2 * playerSum + 20 * dealerSum;
        if(useableAce)
            hash += 400;
        
        return hash;
    }
    
    public String toString(){
        return ("{"+playerSum+","+dealerSum+","+useableAce+"}");
    }
    
}
