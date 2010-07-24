/*
 * Exercise_2_11.java
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
public class Exercise_2_11 extends Exercise_2_2_Gibbs{
    double alpha = 0.1;
    boolean includeFactor = false;
    
    //reinforcement comparison reference reward
    double refRewards[] = new double[numBandits];
    
    /** Creates a new instance of Exercise_2_11 */
    public Exercise_2_11(double e, int choices, int bandits, int plays, double a, boolean incFact) {
        super(1,choices,bandits,plays); //if temperature of gibbs is 1 we get softmax calculation
        epsilon = e;
        alpha = a;
        includeFactor = incFact;
    }
    
    protected void updateArrayValues(int bandit, int play, int choise)
    {
        if(includeFactor)
            q[bandit][choise] = (1-actionValue(bandit,choise)) * (q[bandit][choise] + alpha * (rewards[bandit][play]-refRewards[bandit]));
        else
            q[bandit][choise] = q[bandit][choise] + alpha * (rewards[bandit][play]-refRewards[bandit]);
        
        refRewards[bandit] = refRewards[bandit] + alpha * (rewards[bandit][play]-refRewards[bandit]);
    }

   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Exercise_2_2 tmp;

        System.err.println("Playing Epsilon=0.1, Alpha=0.1");        
        tmp = new Exercise_2_7(0.1, 10, 1000, 1000, 0.1);
        tmp.play();
        double[] zeroRewards = tmp.getAverageRewards();
        double[] zeroOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Epsilon=0.1, Alpha=1/k");
        tmp = new Exercise_2_7(0.1, 10, 1000, 1000, -1.0);  //negative denotes using the fraction
        tmp.play();
        double[] oneRewards = tmp.getAverageRewards();
        double[] oneOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Reinforcement Comparison");
        tmp = new Exercise_2_11(0.1, 10, 1000, 1000, 0.1, false);
        tmp.play();
        double[] rcRewards = tmp.getAverageRewards();
        double[] rcOptimal = tmp.getAveragePercentOptimal();

        System.err.println("Playing Reinforcement Comparison including (1-P(a)) factor");
        tmp = new Exercise_2_11(0.1, 10, 1000, 1000, 0.1, true);
        tmp.play();
        double[] rcfRewards = tmp.getAverageRewards();
        double[] rcfOptimal = tmp.getAveragePercentOptimal();

        
        //print the average rewards
        System.out.println("Average Rewards plot\nPlay\tAlpha=0.1\tAlpha=1/k\tReinfComp\tReingCompFactored");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + zeroRewards[play] + "\t" + oneRewards[play] + "\t" + rcRewards[play] + "\t" + rcfRewards[play]);
        }
        
        
        System.out.println("\n\nAverage Percent Optimal plot\nPlay\tAlpha=0.1\tAlpha=1/k\tReinfComp");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + zeroOptimal[play] + "\t" + oneOptimal[play] + "\t" + rcOptimal[play] + "\t" + rcfOptimal[play]);
        }
    }
}
