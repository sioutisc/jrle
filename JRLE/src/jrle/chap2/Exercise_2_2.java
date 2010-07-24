/*
 * Exercise_2_2.java
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
import java.util.Random;
/**
 *
 * @author  Christos Sioutis
 */
public class Exercise_2_2 {
    Random uniform;
    Random gaussian;
    int n = 10;
    int numBandits = 2000;
    int numPlays = 1000;
    double epsilon = 0.1;
    
    //optimal actions
    double qStar[][];
    int optimal[] = new int[numBandits];
    
    double q[][];
    double rewards[][];
    int optimalChoice[][];
    
    double averageRewards[];
    double averagePercentOptimal[];
    
    //int choise;
    
    /** Creates a new instance of Exercise_2_2 */
    public Exercise_2_2(double e, int choices, int bandits, int plays) {
        epsilon = e;
        n=choices;
        numBandits = bandits;
        numPlays = plays;
        
        //initialise all the arrays
        uniform = new Random();
        gaussian = new Random();
        qStar = new double [numBandits][n];
        q = new double [numBandits][n];
        rewards = new double[numBandits][numPlays];
        optimalChoice = new int[numBandits][numPlays];
        averageRewards = new double[numPlays];
        averagePercentOptimal = new double[numPlays];
        
        //initialise the QStar Array with the required random variables
        initialiseQArrays(true,true);
        findOptimalActions();
    }

    protected void initialiseQArrays(boolean qStarArray, boolean qArray)
    {
        for(int i=0; i< numBandits; i++)
            for(int j=0; j<n; j++)
            {
                if(qStarArray)
                    qStar[i][j] = uniform.nextDouble();
                if(qArray)
                    q[i][j] = 0.0;
            }
    }
    
    protected void findOptimalActions()
    {
        for(int bandit=0; bandit<numBandits; bandit++)
        {
            optimal[bandit] = getOptimalChoice(bandit);
        }
    }
        
    public void play()
    {
        int choise;
       // int optimalCounter;
        for(int bandit=0; bandit<numBandits; bandit++)
        {
            for(int play=0; play<numPlays; play++) //choose whether to explore next
            {
                choise = getNextAction(bandit,play);
                rewards[bandit][play]= qStar[bandit][choise] + Math.abs(gaussian.nextGaussian());
                updateArrayValues(bandit, play, choise);
                if(choise == getOptimalChoice(bandit))
                    optimalChoice[bandit][play] = 1;
                else
                    optimalChoice[bandit][play] = 0;
            }
        }
        
        calculateAverages();
    }
    
    protected int getNextAction(int bandit, int play)
    {
        int choise = 0;
        
        if(uniform.nextDouble() < epsilon || play == 0)
            choise = uniform.nextInt(n); //explore
        else
            choise = getBestChoice(bandit); //greedy    
        
        return choise;
    }
    
    protected void updateArrayValues(int bandit, int play, int choise)
    {
        double tmp=0.0;
        
        for(int i=0; i<=play; i++) //add all previous rewards
            tmp += rewards[bandit][play];
        
        q[bandit][choise] = tmp/play;  //divide by number of plays to ge the average      
    }
    
    protected void calculateAverages()
    {
        //System.err.println("\nCalculating averages");        
        for(int play=0; play<numPlays; play++)
        {
            double totalRewards=0.0;
            double totalOptimalChoices = 0.0;
            
            for(int bandit=0; bandit < numBandits; bandit++)
            {
                totalRewards += rewards[bandit][play];
                totalOptimalChoices += optimalChoice[bandit][play];
            }
            averageRewards[play] = totalRewards / numBandits;
            averagePercentOptimal[play] = totalOptimalChoices / numBandits * 100;
        } 
    }
        
    protected int getBestChoice(int bandit)
    {
        double max = 0.0;
        int choise = 0;
        
        for( int i=0; i<n; i++)
        {
            if(q[bandit][i] > max)
            {
                max = q[bandit][i];
                choise = i;
            }
        }
        return(choise);
    }

    protected int getOptimalChoice(int bandit)
    {
        double max = 0.0;
        int choise = 0;
        
        for( int i=0; i<n; i++)
        {
            if(qStar[bandit][i] > max)
            {
                max = qStar[bandit][i];
                choise = i;
            }
        }
        return(choise);
    }
 
    public double[] getAverageRewards()
    {
        return averageRewards;
    }
    
    public double[] getAveragePercentOptimal()
    {
        return averagePercentOptimal;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Exercise_2_2 tmp;

        System.err.println("Playing Epsilon=zero");        
        tmp = new Exercise_2_2(0.0, 10, 2000, 1000);
        tmp.play();
        double[] zeroRewards = tmp.getAverageRewards();
        double[] zeroOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Epsilon=one");
        tmp = new Exercise_2_2(0.01, 10, 2000, 1000);
        tmp.play();
        double[] oneRewards = tmp.getAverageRewards();
        double[] oneOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Epsilon=ten");
        tmp = new Exercise_2_2(0.1, 10, 2000, 1000);
        tmp.play();
        double[] tenRewards = tmp.getAverageRewards();
        double[] tenOptimal = tmp.getAveragePercentOptimal();
        
        System.err.println("Playing Epsilon=fifty");
        tmp = new Exercise_2_2(0.5, 10, 2000, 1000);        
        tmp.play();
        double[] fiftyRewards = tmp.getAverageRewards();
        double[] fiftyOptimal = tmp.getAveragePercentOptimal();        
       
        
        //print the average rewards
        System.out.println("Average Rewards plot\nPlay\tZero\tOne\tTen\tFifty");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + zeroRewards[play] + "\t" + oneRewards[play]
                                    + "\t" + tenRewards[play] + "\t" + fiftyRewards[play]);
        }
        
        
        System.out.println("\n\nAverage Percent Optimal plot\nPlay\tZero\tOne\tTen\tFifty");
        for(int play=0; play<1000; play++)
        {
            System.out.println(play + "\t" + zeroOptimal[play] + "\t" + oneOptimal[play]
                                    + "\t" + tenOptimal[play] + "\t" + fiftyOptimal[play]);
        }
    }
}
