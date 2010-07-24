/*
 * Exercise_4_8.java
 *
 * Created on May 31, 2004, 3:32 PM
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

package jrle.chap4;
import java.util.Random;
/**
 *
 * @author  Christos Sioutis
 */
public class Exercise_4_8 {
    Random uniform = new Random();
    int money=1; //start with only 1 coin
    int policy[];
    double value[];
    double p = 0.4;
    double gamma = 0.9;
    double threshold = 0.00000000001;
    double history[][];
    int iterations=0;
    
    /** Creates a new instance of Exercise_4_8 */
    public Exercise_4_8() {
        policy = new int[101];
        value = new double[101];
        history = new double[101][101];
        
    }
    
    public void valueIteration(){
        double delta=0.0;
        double v;
        boolean first = true;
        while(delta > threshold || first){
            iterations++;
            delta = 0.0;
            if(first)
                first = false;
            
            for(int i=1; i<100; i++){
                v = value[i];
                value[i]= calculateValue(i);
                delta = Math.max(delta, modulus(v - value[i]));
                //System.out.println(value[i]);
            }
            //System.out.println(delta);
            //keep a copy of the value function in history
            history[iterations] = cloneValueFunction();
            
        }
        
        //find a policy for this value function
        for(int i=1; i<100; i++){
            policy[i]= getAction(i);
        }
    }
    
    double[] cloneValueFunction(){
        double tmp[] = new double[101];
        for(int i=1; i<=100; i++)
            tmp[i] = value[i];
        return tmp;
    }
    
    int getAction(int cash){
        double tmp = 0.0;
        double max = 0.0;
        int action = 0;
        for(int i=1; i<=cash; i++){   //all the possible actions when we wave this amount of cash
            tmp = Math.max(tmp, possibleStatesValueSum(cash,i));
            if(max < tmp){
                max = tmp;
                action = i;
            }
        }
        return action;
    }
    
    double calculateValue(int cash){
        double tmp = 0.0;
        for(int i=1; i<=cash; i++){   //all the possible actions when we wave with this amount of cash
            tmp = Math.max(tmp, possibleStatesValueSum(cash,i));
        }
        return tmp;
    }
    
    double possibleStatesValueSum(int cash, int bet){
        double tmp = 0.0;
        
        tmp = p * (getReward(cash+bet)+gamma*getValue(cash+bet));
        tmp += (1-p) * (getReward(cash-bet)+gamma*getValue(cash-bet));
        
        //System.out.println(tmp);
        return tmp;
    }
    
    double getValue(int index){
        if(index < 100)
            if(index > 0)
                return value[index];
            else
                return 0;   //dummy state when cash < 0
        
        return 1;   //dummy state when cash >= 100
    }
    
    
    int getReward(int cash){
        if(cash>=100)
            return 1;
        return 0;
    }
    
    double modulus(double number) {
        if(number<0)
            return 1-number;
        return number;
    }
    
    
    public String getGraphs(){
        StringBuffer sb = new StringBuffer();
        sb.append("Capital\tFinal Policy\tValue Estimates");
        for(int i=1; i<100; i++){
            sb.append("\n"+i+"\t"+policy[i]);
            for(int j=1; j<=iterations; j++)
                sb.append("\t"+history[i][j]);
        }
        return sb.toString();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Exercise_4_8 app = new Exercise_4_8();
        app.valueIteration();
        System.out.println(app.getGraphs());
    }
    
}
