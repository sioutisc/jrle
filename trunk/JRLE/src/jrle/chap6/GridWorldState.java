/*
 * GridWorldState.java
 *
 * Created on June 30, 2004, 2:54 PM
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
public class GridWorldState{
    public int row;
    public int column;
    
    /** Creates a new instance of GridWorldState */
    public GridWorldState(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    public GridWorldState(GridWorldState other){
        this.row = other.row;
        this.column = other.column;
    }
    
    public int isOnBoundary() {
        if(column == 0) {
            if (row == GridWorldWKM.GRID_ROWS-1)
                return GridWorldWKM.SOUTHWEST;
            else if (row == 0)
                return GridWorldWKM.NORTHWEST;
            return GridWorldWKM.WEST;
        }
        else if (column == GridWorldWKM.GRID_COLS-1) {
            if (row == GridWorldWKM.GRID_ROWS-1)
                return GridWorldWKM.SOUTHEAST;
            else if (row == 0)
                return GridWorldWKM.NORTHEAST;
            return GridWorldWKM.EAST;
        }
        else {
            if(row==GridWorldWKM.GRID_ROWS-1)
                return GridWorldWKM.SOUTH;
            if(row==0)
                return GridWorldWKM.NORTH;
        }
        return -1;
    }
    
    public boolean equivalent(GridWorldState other){
        if(this == other)
            return true;
        if(other.row == row &&
        other.column == column)
            return true;
        return false;
    }
    
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        GridWorldState tmp = (GridWorldState) obj;
        return equivalent(tmp);
    }
    
    public int hashCode(){
        int hash = 1;
        hash += 1 * row + 20 * column;
        return hash;
    }
    
    public String toString(){
        return "GWS{"+row+","+column+"}";
    }
    
}
