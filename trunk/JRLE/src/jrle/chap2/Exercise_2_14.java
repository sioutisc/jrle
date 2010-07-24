/*
 * Exercise_2_14.java
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
 * 
 */
public class Exercise_2_14 extends Exercise_2_2{
    double beta = 0.01;
    double actionPreferences[][];
    
    public Exercise_2_14(double beta, int choices, int bandits, int plays)
    {
        super(0.1, choices, bandits, plays);
        this.beta = beta;
        actionPreferences = new double[numBandits][n];
        initialiseActionPreferences();
    }
    
    /** initialises all action preferences to 1/n */
    private void initialiseActionPreferences()
    {
        for(int i=0; i<numBandits; i++)
            for(int j=0; j<n; j++)
                actionPreferences[i][j]=1/n;
    }
 
     private void updateActionPreferences(int bandit)
    {
        int bestChoise = getBestChoice(bandit);
        for(int i=0; i<n; i++)
        {
            if(n == bestChoise)
                actionPreferences[bandit][bestChoise] = actionPreferences[bandit][bestChoise] + beta * (1 - actionPreferences[bandit][bestChoise]);
            else
                actionPreferences[bandit][bestChoise] = actionPreferences[bandit][bestChoise] + beta * (0 - actionPreferences[bandit][bestChoise]);
                
        }
    }
    
    protected void updateArrayValues(int bandit, int play, int choise)
    {
        super.updateArrayValues(bandit, play, choise);
        updateActionPreferences(bandit);
    }
    

    protected int getNextAction(int bandit, int play)
    {
        double random = uniform.nextDouble();
        double range = 0.0;

        for(int action=0; action<n; action++)
        {
            range += actionPreferences[bandit][action];
            if(random < range)
                return action;
        }
        return n-1;
    }
    
    
    /*
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
        double numerator = Math.exp( qA );
        double denominator = actionSum(bandit);
        return( numerator / denominator);
    }
    
    protected double actionSum(int bandit)
    {
        double sum = 0.0;
        
        for(int i=0; i<n; i++)
            sum += Math.exp(q[bandit][i]);
            
        return sum;
    }
    
    */
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Exercise_2_2 tmp;

        System.err.println("Playing Epsilon=0.1, Alpha=1/k");
        tmp = new Exercise_2_2(0.1, 10, 2000, 1000); //uses incremental with a=1/k
        tmp.play();
        double[] oneRewards = tmp.getAverageRewards();
        double[] oneOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Persuit");
        tmp = new Exercise_2_14(0.01, 10, 2000, 1000);
        tmp.play();
        double[] purRewards = tmp.getAverageRewards();
        double[] purOptimal = tmp.getAveragePercentOptimal();
        
        //print the average rewards
        System.out.println("Average Rewards plot\nPlay\tAlpha=0.1\tAlpha=1/k\tReinfComp");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + oneRewards[play] + "\t" + purRewards[play]);
        }
        
        
        System.out.println("\n\nAverage Percent Optimal plot\nPlay\tAlpha=0.1\tAlpha=1/k\tReinfComp");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + oneOptimal[play] + "\t" + purOptimal[play]);
        }
    }
}
