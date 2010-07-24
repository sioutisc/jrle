/*
 * MonteCarlo_FirstVisit.java
 *
 * Created on June 9, 2004, 2:35 PM
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
import java.util.*;
/**
 *
 * @author  Christos Sioutis
 */
public class MonteCarlo_ES {
    int EPISODES = 1000;
    BlackJackValues values = new BlackJackValues();
    BlackJackPolicy policy = new BlackJackPolicy(values);
    BlackJackActionValueFunction actionValueFunction = new BlackJackActionValueFunction();
    ReturnsSA returns = new ReturnsSA();
    BlackJack environment = new BlackJack(policy, values);
    
    
    /** Creates a new instance of MonteCarlo_FirstVisit */
    public MonteCarlo_ES() {
    }
    
    public MonteCarlo_ES(int episodesToDo) {
        EPISODES = episodesToDo;
    }
    
    
    public void execute(){
        BlackJackStateActionPair pair;
        int reward = 0;
        
        for(int i=0; i<EPISODES; i++){
            environment.generateEpisode();
            for(int j=0; j<environment.stateActionCount(); j++){
                pair = environment.getStateActionPair(j);
                reward = environment.getReward(j);
                returns.addReward(pair,reward);
                actionValueFunction.addStateActionValue(pair,returns.getAverageReturns(pair));
            }
            
            for(int j=0; j<environment.stateActionCount(); j++){
                pair = environment.getStateActionPair(j);
                policy.addStateActionPolicy(pair.state, actionValueFunction.getBestAction(pair.state));
            }
            
            
         //   if(i%100 == 0) System.err.println(i);
        }
    }
    
    public String plot(boolean useableAce){
        return policy.toString();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MonteCarlo_ES app;
        if(args.length > 0)
            app = new MonteCarlo_ES(Integer.parseInt(args[0]));
        else
            app = new MonteCarlo_ES();
        
        app.execute();
        System.out.println("Useable Ace");
        System.out.println(app.plot(true));
        System.out.println("\nNo Useable Ace");
        System.out.println(app.plot(false));
    }
}
