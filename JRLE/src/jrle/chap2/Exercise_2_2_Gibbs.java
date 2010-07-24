/*
 * Exercise_2_2_Gibbs.java
 *
 * Created on 19 February 2004, 15:48
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
public class Exercise_2_2_Gibbs extends Exercise_2_2{
    private double temperature = 1;

    /** Creates a new instance of Exercise_2_2_Gibbs */
    public Exercise_2_2_Gibbs(double t, int choices, int bandits, int plays) {
        super(0,choices,bandits,plays);
        temperature = t;
    }

    protected int getNextAction(int bandit, int play)
    {
        //calculate the probability of selecting each action
        double probability[] = new double[n];
        
        for(int action=0; action<n; action++)
        {
            probability[action] = actionValue(bandit, action);
        }
       
        double random = uniform.nextDouble();
        double range = 0.0;

        for(int action=0; action<n; action++)
        {
            range += probability[action];
            if(random < range)
                return action;
        }
        return n-1;
    }
    
    protected double actionValue(int bandit, int action)
    {
        double qA = q[bandit][action];
        double numerator = Math.exp( qA / temperature);
        double denominator = actionSum(bandit, temperature);
        return( numerator / denominator);
    }
    
    protected double actionSum(int bandit, double temperature)
    {
        double sum = 0.0;
        
        for(int i=0; i<n; i++)
            sum += Math.exp(q[bandit][i] / temperature);
            
        return sum;
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Exercise_2_2_Gibbs tmp;

        System.err.println("Playing Temperature=1");        
        tmp = new Exercise_2_2_Gibbs(1, 10, 2000, 1000);
        tmp.play();
        double[] zeroRewards = tmp.getAverageRewards();
        double[] zeroOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Temperature=10");
        tmp = new Exercise_2_2_Gibbs(10, 10, 2000, 1000);
        tmp.play();
        double[] oneRewards = tmp.getAverageRewards();
        double[] oneOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Temperature=0.5");
        tmp = new Exercise_2_2_Gibbs(0.5, 10, 2000, 1000);
        tmp.play();
        double[] tenRewards = tmp.getAverageRewards();
        double[] tenOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Temperature=0.1");
        tmp = new Exercise_2_2_Gibbs(0.1, 10, 2000, 1000);        
        tmp.play();
        double[] fiftyRewards = tmp.getAverageRewards();
        double[] fiftyOptimal = tmp.getAveragePercentOptimal();        
       
        
        //print the average rewards
        System.out.println("Average Rewards plot\nPlay\tT=1\tT=10\tT=0.5\tT=0.1");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + zeroRewards[play] + "\t" + oneRewards[play]
                                    + "\t" + tenRewards[play] + "\t" + fiftyRewards[play]);
        }
        
        System.out.println("\n\nAverage Percent Optimal plot\nPlay\tT=1\tT=10\tT=0.5\tT=0.1");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + zeroOptimal[play] + "\t" + oneOptimal[play]
                                    + "\t" + tenOptimal[play] + "\t" + fiftyOptimal[play]);
        }
    }
}
