/*
 * MonteCarlo_FirstVisit.java
 *
 * Created on June 9, 2004, 2:35 PM
 * ---------------------------------------------------------------------
 * This file is part of JRL.
 *
 * JRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JRL.  If not, see <http://www.gnu.org/licenses/>.
 * --------------------------------------------------------------------- 
 */

package jrle.chap5;
import java.util.*;
/**
 *
 * @author  Christos Sioutis
 */
public class MonteCarlo_FirstVisit {
    int EPISODES = 1000;
    BlackJackValues values = new BlackJackValues();
    BlackJackPolicy policy = new BlackJackPolicy(values);
   // Map valueFunction = new HashMap();
    Returns returns = new Returns();
    BlackJack environment = new BlackJack(policy, values);
    
    
    /** Creates a new instance of MonteCarlo_FirstVisit */
    public MonteCarlo_FirstVisit() {
    }
    
    public MonteCarlo_FirstVisit(int episodesToDo) {
        EPISODES = episodesToDo;
    }
    
    
    public void execute(){
        BlackJackState state;
        int reward = 0;
        
        for(int i=0; i<EPISODES; i++){
            environment.generateEpisode();
            for(int j=0; j<environment.stateActionCount(); j++){
                state = environment.getStateActionPair(j).state;
                reward = environment.getReward(j);
                returns.addReward(state,reward);
                //valueFunction.put(state,returns.getAverageReturns(state));
            }
            if(i%100 == 0) System.err.println(i);
        }
        
    }
    
    public String plot(boolean useableAce){
        return returns.plot(useableAce);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MonteCarlo_FirstVisit app;
        if(args.length > 0)
            app = new MonteCarlo_FirstVisit(Integer.parseInt(args[0]));
        else
            app = new MonteCarlo_FirstVisit();
        
        app.execute();
        System.out.println("Useable Ace");
        System.out.println(app.plot(true));
        System.out.println("\nNo Useable Ace");
        System.out.println(app.plot(false));
    }
}
