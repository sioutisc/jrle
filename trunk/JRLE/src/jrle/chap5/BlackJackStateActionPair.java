/*
 * BlackJackStateActionPair.java
 *
 * Created on June 16, 2004, 12:05 AM
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
public class BlackJackStateActionPair {
    public BlackJackState state;
    public boolean action;
    /** Creates a new instance of BlackJackStateActionPair */
    public BlackJackStateActionPair(BlackJackState s, boolean a){
        state = s;
        action = a;
    }
    
    
    public BlackJackStateActionPair(BlackJackStateActionPair pair){
        state = new BlackJackState(pair.state);
        action = pair.action;
    }
    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        BlackJackStateActionPair tmp = (BlackJackStateActionPair) obj;
        return (tmp.state.equivalent(state) && tmp.action == action);
    }
    
    public int hashCode(){
        int hash = state.hashCode();
        if(action)
            hash += 500;
        else
            hash += 600;
        
        return hash;
    }
}
