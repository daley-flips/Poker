import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.*;
public class Tie{
//this class is used when 2 or more players have the same poker hand and a tie needs to be broken
//methods return a String of the player num who won or if there is more than one winner it will return something like "1-3-6" meaning players 1, 3, and 6 all have the same hand and will split the pot 
    
    public StringBuilder breakTheTie(ArrayList<Integer> playerIndexes, int score, ArrayList<String> playerHands2, ArrayList<String> cardsOnTable){
        StringBuilder output = new StringBuilder();
        Poker poker = new Poker();
        PokerHands pokerHands = new PokerHands();
        Game game = new Game();
        ArrayList<String> the7Cards = new ArrayList<>(7);
        the7Cards = cardsOnTable;
        //System.out.println(the7Cards + "key debug");
       
        //*****I think i need getter methods to fix this
//-----------------------------------------------------------------------------------------------------------------------------------
//royal flush, split pot    
        if(score == 100){
            for(int i=0; i<playerIndexes.size(); i++){
                output.append(playerIndexes.get(i)+1 + "-");
            }    
            return output;
        }  
//end royal flush tester
//-----------------------------------------------------------------------------------------------------------------------------------
//straight flush, find highest 5 cards within the straight flush
        if(score == 90){
            output.append("balls");
            System.out.println("OMG I DIDNT IMPLEMENT STRAIGHT FLUSH OOF");
            return output;//fuck
            //implement the straight and the flush first
            
        }
//end straight flush tester
//-----------------------------------------------------------------------------------------------------------------------------------   
//4 of a kind, find highest non-4 of a kind card and thats the winner
//also possible for 2 players to have 2 different values in their 4 of a kind, then whoever's 4 is higher
        if(score == 80){
            int[] compareForWinner = new int[playerIndexes.size()*2];//this array will look like {8,14,8,10,4,12}, 2 indexes for each player inside the playerIndexes array, the first being the value in their 4 of a kind, and the second being their outside high card
            for(int i=0; i<playerIndexes.size(); i++){
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2));
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2+1));
                pokerHands.populateValuesAndSuitsArrayLists(the7Cards);                
                HashMap<Integer, Integer> frequencyMap = new HashMap<>();
                // Iterate through the ArrayList and update the frequency map
                for (Integer num : pokerHands.values) {
                frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
                }
                // Find the four-of-a-kind value
                int fourOfAKindValue = -1; // Default value if not found
                for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                    if (entry.getValue() == 4) {
                        fourOfAKindValue = entry.getKey();
                        break; // Exit the loop once four-of-a-kind value is found
                    }
                }
                int highCardValue=0;
                for(int j=0; j<7; j++){
                    if(pokerHands.values.get(j) != fourOfAKindValue && pokerHands.values.get(j)>highCardValue)
                        highCardValue = pokerHands.values.get(j);
                }
                compareForWinner[i*2] = fourOfAKindValue; 
                compareForWinner[i*2+1] = highCardValue;       
                //reset before next iteration
//---------------------------------------------------------------------- 
                
                the7Cards.remove(6);
                the7Cards.remove(5);
                pokerHands.values.clear();
                pokerHands.suits.clear();                                            
            }//end for loop to iterate through players            
            int valOf4 = compareForWinner[0];
            int valOfHighCard = compareForWinner[1];
            
            for(int i=2; i<compareForWinner.length; i+=2){
                if(compareForWinner[i] > valOf4){
                    valOf4 = compareForWinner[i];
                    valOfHighCard = compareForWinner[i+1];//origonal high card value is irrelevant if one player had a higher 4 of a kind
                }
                else if(compareForWinner[i] == valOf4 && compareForWinner[i+1] > valOfHighCard){
                    valOfHighCard = compareForWinner[i+1];
                }
            }
            System.out.println(valOf4 + " val of 4");
            System.out.println(valOfHighCard + " val of high card");
            String IntegerToString;
            for(int i=0; i<compareForWinner.length; i+=2){
                if(compareForWinner[i] == valOf4 && compareForWinner[i+1] == valOfHighCard){
                    //go into playerindex and get index then append to output
                    IntegerToString = playerIndexes.get(i / 2).toString();;
                    output.append(IntegerToString +"-");                              
                }
            }
            return output;            
        }
//end 4 of a kind
//----------------------------------------------------------------------------------------------------------------------------------   
//full house        
        if(score == 70){
            int[] compareForWinner = new int[playerIndexes.size()*2];//2 indexes for each player, first is 3 of a kind val then pair val
            for(int i=0; i<playerIndexes.size(); i++){
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2));
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2+1));
                pokerHands.populateValuesAndSuitsArrayLists(the7Cards);                
                HashMap<Integer, Integer> frequencyMap = new HashMap<>();
                // Iterate through the ArrayList and update the frequency map
                for (Integer num : pokerHands.values) {
                    frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
                }
                // Find the three-of-a-kind and pair values
                int threeOfAKindValue = -1; // Default value if not found
                int pairValue = -1; // Default value if not found
                for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                    if (entry.getValue() == 3) {
                        threeOfAKindValue = entry.getKey();
                    } 
                    else if (entry.getValue() == 2) {
                        pairValue = entry.getKey();
                    }
                }
                compareForWinner[i*2] = threeOfAKindValue; 
                compareForWinner[i*2+1] = pairValue;       
                //reset before next iteration
//----------------------------------------------------------------------                 
                the7Cards.remove(6);
                the7Cards.remove(5);
                pokerHands.values.clear();
                pokerHands.suits.clear();                                            
            }//end for loop to iterate through players            
            int valOf3 = compareForWinner[0];     
            int valOf2 = compareForWinner[1];
            for(int i=0; i<compareForWinner.length; i++){
            }
            for(int i=2; i<compareForWinner.length; i+=2){
                if(compareForWinner[i] > valOf3){
                    valOf3 = compareForWinner[i];
                    valOf2 = compareForWinner[i+1];//origonal high card value is irrelevant if one player had a higher 4 of a kind
                }
                else if(compareForWinner[i] == valOf3 && compareForWinner[i+1] > valOf2){
                    valOf2 = compareForWinner[i+1];
                }
            }
            String IntegerToString;
            for(int i=0; i<compareForWinner.length; i+=2){
                if(compareForWinner[i] == valOf3 && compareForWinner[i+1] == valOf2){
                    //go into playerindex and get index then append to output
                    IntegerToString = playerIndexes.get(i / 2).toString();;
                    output.append(IntegerToString +"-");                              
                }
            }
            return output;            
        }//end full house               
//----------------------------------------------------------------------------------------------------------------------------------
//flush
        if(score == 60){
            double[] compareForWinner = new double[playerIndexes.size()];//1 index for each player, highest card in flush
            for(int i=0; i<playerIndexes.size(); i++){
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2));
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2+1));
                pokerHands.populateValuesAndSuitsArrayLists(the7Cards);                
                Character suit = pokerHands.searchForFlush().charAt(1);
                ArrayList<Integer> best5Cards = new ArrayList<Integer>(5);
                for(int j=0; j<7; j++){
                    if(the7Cards.get(j).charAt(1) == suit){
                        Integer val = pokerHands.values.get(j);
                        best5Cards.add(val);
                    
                    }
                    else if(the7Cards.get(j).length() == 3 && the7Cards.get(j).charAt(2) == suit){
                        Integer val = pokerHands.values.get(j);
                        best5Cards.add(val);
                    }
                }
                
                    // Sort the ArrayList in descending order
                Collections.sort(best5Cards, Collections.reverseOrder());
                //System.out.println(best5Cards);
                for (int j = 0; j < 5; j++) {
                    int x = best5Cards.get(j);
                    compareForWinner[i] +=  Math.pow(10, x);//therefore each single card higher will hold a new magnitude, this fixes a logic error from earlier. You cannot simply add them because a flush A,K,4,3,2 beats a flush A,Q,J,10,9 even thought the second one will add to a higher number it ranks by highest card then second highest card down, not by sum
                }
                //reset before next iteration
//----------------------------------------------------------------------
                the7Cards.remove(6);
                the7Cards.remove(5);
                pokerHands.values.clear();
                pokerHands.suits.clear();                                            
            }//end for loop to iterate through players            
            double highestSumOf5 = compareForWinner[0];     
            for(int i=1; i<compareForWinner.length; i++){
                if(compareForWinner[i] > highestSumOf5){
                    highestSumOf5 = compareForWinner[i];
                }    
            }
            
            String IntegerToString;
            for(int i=0; i<compareForWinner.length; i++){
                if(compareForWinner[i] == highestSumOf5){
                    //go into playerindex and get index then append to output
                    IntegerToString = playerIndexes.get(i).toString();;
                    output.append(IntegerToString +"-");                              
                }
            }
            return output;            
        }//end flush              
//----------------------------------------------------------------------------------------------------------------------------------
//straight
        if(score == 50){
            double[] compareForWinner = new double[playerIndexes.size()];//1 index for each player, highest card in flush
            for(int i=0; i<playerIndexes.size(); i++){
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2));
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2+1));
                pokerHands.populateValuesAndSuitsArrayLists(the7Cards);   
           
                 
                Map<Integer, Integer> freq = new HashMap<>();
                for (int value : pokerHands.values) {
                    freq.put(value, freq.getOrDefault(value, 0) + 1);
                }

                // Find the max value in the array to define the range for straights
                int maxVal = Collections.max(pokerHands.values);

                // Iterate over possible straights in descending order
                for (int high = maxVal; high >= 5; high--) {
                    if (freq.getOrDefault(high, 0) > 0
                        && freq.getOrDefault(high-1, 0) > 0
                        && freq.getOrDefault(high-2, 0) > 0
                        && freq.getOrDefault(high-3, 0) > 0
                        && freq.getOrDefault(high-4, 0) > 0) {

                        ArrayList<Integer> result = new ArrayList<>();
                        for (int j = high; j > high - 5; j--) {
                            result.add(j);
                        }
                        Collections.sort(result);
                        result = new ArrayList<>(new LinkedHashSet<>(result)); //remove duplicates
                        pokerHands.values = result;
                        break; // Found the highest straight, so break the loop
                    }
                }
                
                for (int j = 0; j < 5; j++) {
                    int x = pokerHands.values.get(j);
                    compareForWinner[i] +=  Math.pow(10, x);
                }
            
                the7Cards.remove(6);
                the7Cards.remove(5);
                pokerHands.values.clear();
                pokerHands.suits.clear();        
            }//end for loop
            double highestSumOf5 = compareForWinner[0];     
            for(int i=1; i<compareForWinner.length; i++){
                if(compareForWinner[i] > highestSumOf5){
                    highestSumOf5 = compareForWinner[i];
                }    
            }
            
            String IntegerToString;
            for(int i=0; i<compareForWinner.length; i++){
                if(compareForWinner[i] == highestSumOf5){
                    //go into playerindex and get index then append to output
                    IntegerToString = playerIndexes.get(i).toString();;
                    output.append(IntegerToString +"-");                              
                }
            }
            return output;            
        }
//end straight        
//----------------------------------------------------------------------------------------------------------------------------------
//3 of a kind
    if(score == 40){
            double[] compareForWinner = new double[playerIndexes.size()];//1 index for each player, highest card in flush
            for(int i=0; i<playerIndexes.size(); i++){
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2));
                the7Cards.add(playerHands2.get(playerIndexes.get(i)*2+1));
                pokerHands.populateValuesAndSuitsArrayLists(the7Cards);   
                HashMap<Integer, Integer> frequencyMap = new HashMap<>();
                // Iterate through the ArrayList and update the frequency map
                for (Integer num : pokerHands.values) {
                    frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
                }
                // Find the three-of-a-kind and pair values
                int threeOfAKindValue = -1; // Default value if not found
                for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                    if (entry.getValue() == 3) {
                        threeOfAKindValue = entry.getKey();
                    } 
                }
                double x =  Math.pow(10, 15);
                compareForWinner[i] += threeOfAKindValue*x;
                int highestOutsideCard=0;
                int secondOutsideCard=0;
                for(int l=0; l<7; l++){
                    if(pokerHands.values.get(l) != threeOfAKindValue && pokerHands.values.get(l)>highestOutsideCard){
                        highestOutsideCard=pokerHands.values.get(l);
                    }
                    else if(pokerHands.values.get(l) != threeOfAKindValue && pokerHands.values.get(l)<highestOutsideCard && pokerHands.values.get(l)>secondOutsideCard){
                        secondOutsideCard=pokerHands.values.get(l);
                    }
                }
                x=highestOutsideCard;
                compareForWinner[i] +=  Math.pow(10, x);
                x=secondOutsideCard;
                compareForWinner[i] +=  Math.pow(10, x);
                //reset
                the7Cards.remove(6);
                the7Cards.remove(5);
                pokerHands.values.clear();
                pokerHands.suits.clear();        
            }//end for loop
            double highestSumOf5 = compareForWinner[0];     
            for(int i=1; i<compareForWinner.length; i++){
                if(compareForWinner[i] > highestSumOf5){
                    highestSumOf5 = compareForWinner[i];
                }    
            }
            
            String IntegerToString;
            for(int i=0; i<compareForWinner.length; i++){
                if(compareForWinner[i] == highestSumOf5){
                    //go into playerindex and get index then append to output
                    IntegerToString = playerIndexes.get(i).toString();;
                    output.append(IntegerToString +"-");                              
                }
            }
            return output;            
        }        
//end 3 of a kind
//----------------------------------------------------------------------------------------------------------------------------------
//two pair
    if(score == 30){
        double[] compareForWinner = new double[playerIndexes.size()];//1 indexes for each, 10^highest pair + 10^second highest pair + high card
        for(int i=0; i<playerIndexes.size(); i++){
            the7Cards.add(playerHands2.get(playerIndexes.get(i)*2));
            the7Cards.add(playerHands2.get(playerIndexes.get(i)*2+1));
            pokerHands.populateValuesAndSuitsArrayLists(the7Cards);  
                
            ArrayList<Integer> pairValues = new ArrayList<Integer>();
            ArrayList<Integer> checkForPairs = new ArrayList<Integer>();
            checkForPairs.add(pokerHands.values.get(0));
            int index=0;
            for(int j=1; j<7; j++){
                if(false == checkForPairs.contains(pokerHands.values.get(j))){
                    checkForPairs.add(pokerHands.values.get(j));
                }
                else{
                    pairValues.add(pokerHands.values.get(j));
                    index++;
                }                                   
            }
                //pair values could be length either 2 or 3 since its possible to have 3 pairs
            int outsideValue = 0;
            for(int j=1; j<7; j++){
                if(outsideValue< pokerHands.values.get(j) && pokerHands.values.get(j) != pairValues.get(0) && pokerHands.values.get(j) != pairValues.get(1)){
                    outsideValue = pokerHands.values.get(j);
                }
            }
            Collections.sort(pairValues, Collections.reverseOrder());
            compareForWinner[i] = Math.pow(10, pairValues.get(0));
            compareForWinner[i] += Math.pow(10, pairValues.get(1));
            compareForWinner[i] += outsideValue;
            //reset
            the7Cards.remove(6);
            the7Cards.remove(5);
            pokerHands.values.clear();
            pokerHands.suits.clear(); 
        }//end main for
        
        double highesTwoPair = compareForWinner[0];     
        for(int i=1; i<compareForWinner.length; i++){
            if(compareForWinner[i] > highesTwoPair){
                highesTwoPair = compareForWinner[i];
            }    
        }
            
        String IntegerToString;
        for(int i=0; i<compareForWinner.length; i++){
            if(compareForWinner[i] == highesTwoPair){
                //go into playerindex and get index then append to output
                IntegerToString = playerIndexes.get(i).toString();;
                output.append(IntegerToString +"-");                              
            }
        }
        return output;            
        
    }
//end two pair
//----------------------------------------------------------------------------------------------------------------------------------
// pair
    if(score == 20){
        double[] compareForWinner = new double[playerIndexes.size()];//1 indexes for each, 10^highest pair + 2^highest outside card + 2^second outside + 2^third outside highest
        for(int i=0; i<playerIndexes.size(); i++){
            the7Cards.add(playerHands2.get(playerIndexes.get(i)*2));
            the7Cards.add(playerHands2.get(playerIndexes.get(i)*2+1));
            pokerHands.populateValuesAndSuitsArrayLists(the7Cards);  
            //
            ArrayList<Integer> pairValues = new ArrayList<Integer>();
            ArrayList<Integer> checkForPairs = new ArrayList<Integer>();
            checkForPairs.add(pokerHands.values.get(0));
            int index=0;
            for(int j=1; j<7; j++){
                if(false == checkForPairs.contains(pokerHands.values.get(j))){
                    checkForPairs.add(pokerHands.values.get(j));
                }
                else{
                    pairValues.add(pokerHands.values.get(j));
                    index++;
                }                                   
            }
                //pair values could be length either 2 or 3 since its possible to have 3 pairs
            int outsideValue = 0;
            int secondOutsideValue = 0;
            int thirdOutsideValue = 0;
            for(int j=1; j<7; j++){
                if(outsideValue< pokerHands.values.get(j) && pokerHands.values.get(j) != pairValues.get(0)){
                    //found a new high, therefor shift all them down
                    outsideValue = pokerHands.values.get(j);
                }
            }
            for(int j=1; j<7; j++){
                if(secondOutsideValue< pokerHands.values.get(j) && pokerHands.values.get(j) != pairValues.get(0) && pokerHands.values.get(j) != outsideValue){
                    //found a new high, therefor shift all them down
                    secondOutsideValue = pokerHands.values.get(j);
                }
            }
            for(int j=1; j<7; j++){
                if(thirdOutsideValue< pokerHands.values.get(j) && pokerHands.values.get(j) != pairValues.get(0) && pokerHands.values.get(j) != outsideValue && pokerHands.values.get(j) != secondOutsideValue){
                    //found a new high, therefor shift all them down
                    thirdOutsideValue = pokerHands.values.get(j);
                }
            }
            Collections.sort(pairValues, Collections.reverseOrder());

            compareForWinner[i] = Math.pow(10, pairValues.get(0));
            compareForWinner[i] += Math.pow(2, outsideValue);
            compareForWinner[i] += Math.pow(2, secondOutsideValue);
            compareForWinner[i] += Math.pow(2, thirdOutsideValue);
            //reset
            the7Cards.remove(6);
            the7Cards.remove(5);
            pokerHands.values.clear();
            pokerHands.suits.clear(); 
        }//end for loop
        double highestPair = compareForWinner[0];     
        for(int i=1; i<compareForWinner.length; i++){
            if(compareForWinner[i] > highestPair){
                highestPair = compareForWinner[i];
            }    
        }
        String IntegerToString;
        for(int i=0; i<compareForWinner.length; i++){
            if(compareForWinner[i] == highestPair){
                //go into playerindex and get index then append to output
                IntegerToString = playerIndexes.get(i).toString();;
                output.append(IntegerToString +"-");                              
            }
        }
        return output;       
    }//end pair    
//----------------------------------------------------------------------------------------------------------------------------------     
// high card
    if(score < 20){
        double[] compareForWinner = new double[playerIndexes.size()];//1 indexes for each, 10^highest card, 10^ second highest card and so on for the top top
        for(int i=0; i<playerIndexes.size(); i++){
            the7Cards.add(playerHands2.get(playerIndexes.get(i)*2));
            the7Cards.add(playerHands2.get(playerIndexes.get(i)*2+1));
            pokerHands.populateValuesAndSuitsArrayLists(the7Cards);  
            //           
            Collections.sort(pokerHands.values, Collections.reverseOrder());
            compareForWinner[i] = Math.pow(10, pokerHands.values.get(0));
            compareForWinner[i] += Math.pow(10, pokerHands.values.get(1));
            compareForWinner[i] += Math.pow(10, pokerHands.values.get(2));
            compareForWinner[i] += Math.pow(10, pokerHands.values.get(3));
            compareForWinner[i] += Math.pow(10, pokerHands.values.get(4));
            //reset
            the7Cards.remove(6);
            the7Cards.remove(5);
            pokerHands.values.clear();
            pokerHands.suits.clear(); 
        }//end for loop 
        double highestHigh5 = compareForWinner[0];     
        for(int i=1; i<compareForWinner.length; i++){
            if(compareForWinner[i] > highestHigh5){
                highestHigh5 = compareForWinner[i];
            }    
        }
        String IntegerToString;
        for(int i=0; i<compareForWinner.length; i++){
            if(compareForWinner[i] == highestHigh5){
                //go into playerindex and get index then append to output
                IntegerToString = playerIndexes.get(i).toString();;
                output.append(IntegerToString +"-");                              
            }
        }
        return output;       
    }//end high card   
//----------------------------------------------------------------------------------------------------------------------------------        
        
        output.append("that shouldnt happen lol");
        return output;    
    }//end breakTheTie
}//end Tie class