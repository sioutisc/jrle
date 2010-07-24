/*
 * Exercise_2_7.java
 *
 * Created on March 29, 2004, 4:49 PM
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

package jrle.chap2;
/**
 *
 * @author  Christos Sioutis
 */
public class Exercise_2_7 extends Exercise_2_2{
    double alpha = 0.1;
    /** Creates a new instance of Exercise_2_7 */
    public Exercise_2_7(double e, int choices, int bandits, int plays, double a) 
    {
        super(e,choices,bandits,plays);
        alpha = a;
    }
    
    
    protected void updateArrayValues(int bandit, int play, int choise)    
    {
        if(play % 200 == 0) 
            initialiseQArrays(true, false); //re-initialise the qStar every 200 plays
        
        if(alpha<0)
            q[bandit][choise] = q[bandit][choise] + ( (1/(play+1)) * (rewards[bandit][play] - q[bandit][choise]));
        else
            q[bandit][choise] = q[bandit][choise] + ( alpha * (rewards[bandit][play] - q[bandit][choise]));
    }

    
    public static void main(String[] args) {
        Exercise_2_7 tmp;

        System.err.println("Playing Alpha = 1/k");        
        tmp = new Exercise_2_7(0.1, 10, 2000, 1000, -1.0);
        tmp.play();
        double[] fracRewards = tmp.getAverageRewards();
        double[] fracOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Alpha = 0.1");
        tmp = new Exercise_2_7(0.1, 10, 2000, 1000, 0.1);
        tmp.play();
        double[] alphRewards = tmp.getAverageRewards();
        double[] alphOptimal = tmp.getAveragePercentOptimal();
        
   
        
        //print the average rewards
        System.out.println("Average Rewards plot\nPlay\tZero\tOne\tTen\tFifty");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + fracRewards[play] + "\t" + alphRewards[play]);
        }
        
        
        System.out.println("\n\nAverage Percent Optimal plot\nPlay\tZero\tOne\tTen\tFifty");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + fracOptimal[play] + "\t" + alphOptimal[play]);
        }
    }
    
    
}
