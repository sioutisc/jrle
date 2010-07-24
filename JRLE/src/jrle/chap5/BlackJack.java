/*
 * BlackJack.java
 *
 * Created on June 5, 2004, 12:53 AM
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
import java.util.Vector;
import java.util.Random;

/**
 *
 * @author  Christos Sioutis
 */
public class BlackJack {
    Random uniform = new Random();
    Vector dealerCards = new Vector();
    Vector learnerCards = new Vector();
    BlackJackPolicy policy;
    Vector pairs = new Vector();
    int reward = 0;
    
    //added for optimisation
    BlackJackStateActionPair currentPair = new BlackJackStateActionPair(new BlackJackState(),true);
    BlackJackValues values;
    int numdealerCards = 0, numLearnerCards = 0;
    
    /** Creates a new instance of BlackJack */
    public BlackJack(BlackJackPolicy learnerPolicy, BlackJackValues v) {
        policy = learnerPolicy;
        values = v;
    }
    
    
    /** only counts cards when a new card has been added */
    void updateCurrentStateActionPair(){
        if(numdealerCards != dealerCards.size()){
            currentPair.state.dealerSum = countCards(dealerCards);
            numdealerCards = dealerCards.size();
        }
        if(numLearnerCards != learnerCards.size()){
            currentPair.state.playerSum = countCards(learnerCards);
            numLearnerCards = learnerCards.size();
        }
        
        currentPair.state.useableAce = hasUseableAce(learnerCards);
    }
    
    void initialize(){
        learnerCards.clear();
        dealerCards.clear();
        currentPair.state.clear();
        pairs.clear();
        policy.resetEpisodeStart();
        reward = 0;
        
        //draw two cards for the learner and one card for the dealer
        draw(learnerCards);
        draw(learnerCards);
        draw(dealerCards);
        
    }
    
    int drawOne(){
        return 1 + uniform.nextInt(9);
    }
    
    void draw(Vector player){
        int tmp = drawOne();
        if(countSpecificCards(player,tmp) < 4){  //only add upto 4 of the same type of card
            player.add(values.getIntegerValue(tmp));
        }
        else
            draw(player);
        
        updateCurrentStateActionPair();
    }
    
    boolean containsAce(Vector playerCards){
        for(int i=0; i<playerCards.size(); i++){
            if(((Integer)playerCards.get(i)).intValue() == 1)
                return true;
        }
        return false;
    }
    
    
    int countCards(Vector playerCards){
        int count=0;
        int card=0;
        int aces=0;
        //count all cards other than aces but increment the ace count if an ace is found
        for(int i=0; i<playerCards.size(); i++) {
            card = ((Integer)playerCards.get(i)).intValue();
            if(card == 1)
                aces++;
            else
                count+=card;
        }
        if(aces>0){
            if(count+11+(aces-1) <= 21)
                count += 11+(aces-1);
            else
                count += aces;
        }
        return count;
    }
    
    boolean hasUseableAce(Vector playerCards){
        int count=0;
        int card=0;
        int aces=0;
        //count all cards other than aces but increment the ace count if an ace is found
        for(int i=0; i<playerCards.size(); i++) {
            card = ((Integer)playerCards.get(i)).intValue();
            if(card == 1)
                aces++;
            else
                count+=card;
        }
        if(aces>0 && count+11+(aces-1) <= 21)
            return true;
        return false;
    }
    
    int countSpecificCards(Vector playerCards, int card){
        int count=0;
        Integer tmp;
        for(int i=0; i<playerCards.size(); i++){
            tmp = (Integer) playerCards.get(i);
            if(tmp.intValue() == card)
                count++;
        }
        return count;
    }
    
    
    //returns true of the learner is the winner
    public void generateEpisode(){
        initialize();
        while(!gameFinished()){
            if(learnerHit())
                pairs.add(new BlackJackStateActionPair(currentPair));
            step();
        }

        if(currentPair.state.playerSum > 21)
            reward = -1;
        else if(currentPair.state.dealerSum > 21)
            reward = 1;
        else if(currentPair.state.playerSum > currentPair.state.dealerSum) //the learner won
            reward = 1;
        else if(currentPair.state.playerSum < currentPair.state.dealerSum) //the learner lost
            reward = -1;
        
        //return 0;  //a draw
    }
    
    boolean gameFinished(){
        //someone went bust
        if(currentPair.state.playerSum > 21 || currentPair.state.dealerSum > 21)
            return true;
        
        //the dealer has won or drawn, no need to continue
        if(currentPair.state.dealerSum == 21)
            return true;
        
        if(!learnerHit() && currentPair.state.dealerSum >= currentPair.state.playerSum)
            return true;
        
        return false;
    }
    
    void step(){
        if(learnerHit())
            draw(learnerCards);
        else
            draw(dealerCards);
    }
    
    boolean learnerHit(){
        return policy.hit(currentPair.state);
    }
    
    public int stateActionCount(){
        return pairs.size();
    }
    
    public Vector getStateActionPairs(){
        return pairs;
    }
    
    public BlackJackStateActionPair getStateActionPair(int index){
        return (BlackJackStateActionPair)pairs.get(index);
    }
    
    public int getReward(int pairIndex){
        return reward;
    }
}
