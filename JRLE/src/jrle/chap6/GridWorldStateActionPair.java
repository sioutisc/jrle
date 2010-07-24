/*
 * GridWorldStateActionPair.java
 *
 * Created on June 30, 2004, 2:56 PM
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
/**
 *
 * @author  Christos Sioutis
 */
public class GridWorldStateActionPair {
    public GridWorldState state;
    public int action;
    /** Creates a new instance of GridWorldStateActionPair */
    public GridWorldStateActionPair(GridWorldState state, int action){
        this.state = state;
        this.action = action;
    }
    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        GridWorldStateActionPair tmp = (GridWorldStateActionPair) obj;
        return (tmp.state.equivalent(state) && tmp.action == action);
    }
    
    public int hashCode(){
        int hash = state.hashCode();
        hash += action * 101;
        return hash;
    }
    
}
