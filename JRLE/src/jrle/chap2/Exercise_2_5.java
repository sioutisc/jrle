/*
 * Exercise_2_5.java
 *
 * Created on 29 February 2004, 17:17
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
public class Exercise_2_5 extends Exercise_2_2{

    public Exercise_2_5(double e, int choices, int bandits, int plays) {
        super(e,choices, bandits, plays);
    }

    
    protected void updateArrayValues(int bandit, int play, int choise)
    {
        q[bandit][choise] = q[bandit][choise] + ( (1/(play+1)) * (rewards[bandit][play] - q[bandit][choise]));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Exercise_2_5 tmp;

        System.err.println("Playing Epsilon=one");
        tmp = new Exercise_2_5(0.01, 10, 2000, 1000);
        tmp.play();
        double[] oneRewards = tmp.getAverageRewards();
        double[] oneOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Epsilon=ten");
        tmp = new Exercise_2_5(0.1, 10, 2000, 1000);
        tmp.play();
        double[] tenRewards = tmp.getAverageRewards();
        double[] tenOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Epsilon=fifty");
        tmp = new Exercise_2_5(0.5, 10, 2000, 1000);        
        tmp.play();
        double[] fiftyRewards = tmp.getAverageRewards();
        double[] fiftyOptimal = tmp.getAveragePercentOptimal();        
       
        
        //print the average rewards
        System.out.println("Average Rewards plot\nPlay\tZero\tOne\tTen\tFifty");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + oneRewards[play]
                                    + "\t" + tenRewards[play] + "\t" + fiftyRewards[play]);

        }
        
        
        System.out.println("\n\nAverage Percent Optimal plot\nPlay\tZero\tOne\tTen\tFifty");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + oneOptimal[play]
                                    + "\t" + tenOptimal[play] + "\t" + fiftyOptimal[play]);
            
        }
    }
}
