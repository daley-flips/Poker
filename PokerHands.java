import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
public class PokerHands{
//this class is to rank the poker hands
    public ArrayList<Integer> values = new ArrayList<>(7);
    public ArrayList<Character> suits = new ArrayList<>(7);
//----------------------------------------------------------------------------------------------------------------------------------- 
    
    public int checkForRoyalFlush() {       
//either returns 100 if player has a Royale Flush or 0 if they dont
        boolean isFlush = false;
        boolean isRoyalStraight = false;
        char charNumOfMaxSuit = this.searchForFlush().charAt(0);
        int intNumOfMaxSuit = Character.getNumericValue(charNumOfMaxSuit);
        if(intNumOfMaxSuit >= 5){
            isFlush = true;
        }
        else{
            return 0;
        }
        
        //check for royale straight
        boolean isThereA_Ace = false;
        boolean isThereA_King = false;
        boolean isThereA_Queen = false;
        boolean isThereA_Jack = false;
        boolean isThereA_10 = false;
        
        boolean isAceCorrectSuit = false;
        boolean isKingCorrectSuit = false;
        boolean isQueenCorrectSuit = false;
        boolean isJackCorrectSuit = false;
        boolean is10CorrectSuit = false;
        
        for(int i=0; i<=6; i++){
            Integer checkValue= this.values.get(i);
            switch(checkValue){
                case 14:
                    isThereA_Ace = true;
                break;
                case 13:
                    isThereA_King = true;
                break;
                case 12:
                    isThereA_Queen = true;
                break;    
                case 11:
                    isThereA_Jack = true;
                break;
                case 10:
                    isThereA_10 = true;
                break;                       
            }//end switch
        }//end for
        if(isThereA_Ace == true && isThereA_King == true && isThereA_Queen== true && isThereA_Jack == true && isThereA_10 == true){
            isRoyalStraight = true;
        }
        if(isRoyalStraight == true && isFlush){
            char flushSuit = this.searchForFlush().charAt(1);
            
            for(int i=0; i<=6; i++){
                if(this.suits.get(i).equals(flushSuit)){
                    Integer checkVal = values.get(i);
                    switch(checkVal){
                        case 14:
                            isAceCorrectSuit = true;
                        break;
                        case 13:
                            isKingCorrectSuit = true;
                        break;
                        case 12:
                            isQueenCorrectSuit = true;
                        break;
                        case 11:
                            isJackCorrectSuit = true;
                        break;
                        case 10:
                            is10CorrectSuit = true;
                        break;                            
                    }//end switch
                }//end if
            }//end for
        }//end if for when there is a royal straight and a flush
        if(isAceCorrectSuit == true && isKingCorrectSuit == true && isJackCorrectSuit== true && is10CorrectSuit == true &&                       isQueenCorrectSuit == true){
            return 100; //royal flush!!
        }
        return 0;
    }
//end checkForRoyalFlush    
//-----------------------------------------------------------------------------------------------------------------------------------
    public int checkForStraightFlush() {       
//either returns 90 if player has a Straiht Flush or 0 if they dont
        if(this.checkForFlush() == 60){
            char flushSuit = this.searchForFlush().charAt(1);
            ArrayList<Integer> cardsInFlushSuit = new ArrayList<>();
            for(int i=0; i<7; i++){
                if(suits.get(i).equals(flushSuit)){
                    cardsInFlushSuit.add(values.get(i));//populate cardsInFlushSuit array
                }
            }
            //now check for a straight within the flush suit
            HashSet<Integer> set = new HashSet<>();

            for (Integer num : cardsInFlushSuit) {
            set.add(num);
            }

            for (Integer num : cardsInFlushSuit) {
                if (set.contains(num + 1) && set.contains(num + 2) && set.contains(num + 3) && set.contains(num + 4)) {
                    return 90;
                }
            }

            return 0;//no straight
            }
        else{//no flush
        return 0;
        }    
    }
//end checkForStraightFlush
//-----------------------------------------------------------------------------------------------------------------------------------
    public int checkForXOfAKind(int x) {       
//either returns 80 if player has 4 of a kind, 40 for 3 of a kind, and 20 for a pair or 0 if they dont have any
        // Create a HashMap to store the frequency of each element
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();

        // Iterate through the ArrayList and update the frequency map
        for (Integer element : values) {
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
        }

        // Find the element with the maximum frequency
        int maxFrequency = 0;
        Integer mostFrequentElement = null;

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int frequency = entry.getValue();
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                mostFrequentElement = entry.getKey();
            }
        }
        if(maxFrequency==x && x==4){
            return 80;
        }
        else if(maxFrequency==x && x==3){
            return 40;
        }
        else if(maxFrequency==x && x==2){
            return 20;
        }
        else{
            return 0;
        }
    }
//end checkForFourOfAKind
//-----------------------------------------------------------------------------------------------------------------------------------
    public int checkForFullHouse() {       
//either returns 70 if player has a full house or 0 if they dont
        // Create a HashMap to store the frequency of each element
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();

        // Iterate through the ArrayList and update the frequency map
        for (Integer element : values) {
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
        }

        // Find the most frequent and second most frequent elements
        int maxFrequency = 0;
        int secondMaxFrequency = 0;
        Integer mostFrequentElement = null;
        Integer secondMostFrequentElement = null;

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int frequency = entry.getValue();

            if (frequency > maxFrequency) {
                secondMaxFrequency = maxFrequency;
                maxFrequency = frequency;
                secondMostFrequentElement = mostFrequentElement;
                mostFrequentElement = entry.getKey();
            } else if (frequency > secondMaxFrequency) {
                secondMaxFrequency = frequency;
                secondMostFrequentElement = entry.getKey();
            }
        }
        if(maxFrequency==3 && secondMaxFrequency >=2){
            return 70;
        }
        else{
            return 0;
        }
        
    }
//end checkForFullHouse
//-----------------------------------------------------------------------------------------------------------------------------------
    public int checkForFlush() {       
//either returns 60 if player has a flush or 0 if they dont
        char charNumOfMaxSuit = this.searchForFlush().charAt(0);
        int intNumOfMaxSuit = Character.getNumericValue(charNumOfMaxSuit);
        if(intNumOfMaxSuit >= 5){
            return 60;
        }
        else{
            return 0;
        } 
    }
//end checkForFlush
//-----------------------------------------------------------------------------------------------------------------------------------
    public int checkForStraight() {       
//either returns 50 if player has a straight or 0 if they dont
        HashSet<Integer> set = new HashSet<>();

        for (Integer num : values) {
            set.add(num);
        }

        for (Integer num : values) {
            if (set.contains(num + 1) && set.contains(num + 2) && set.contains(num + 3) && set.contains(num + 4)) {
                return 50;
            }
        }

        return 0;
    }
//end checkForStraight
    //-----------------------------------------------------------------------------------------------------------------------------------
    public int checkForTwoPairs() {       
//either returns 50 if player has a straight or 0 if they dont
        // Create a HashMap to store the frequency of each element
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();

        // Iterate through the ArrayList and update the frequency map
        for (Integer element : values) {
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
        }

        // Find the elements with the maximum frequency of 2
        ArrayList<Integer> twoPairs = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int frequency = entry.getValue();

            if (frequency == 2) {
                twoPairs.add(entry.getKey());
            }
        }

        if (twoPairs.size() >= 2 ) {
            return 30;
        } 
        else {
            return 0;
        }    
    }
//end checkForTwoPairs
//-----------------------------------------------------------------------------------------------------------------------------------
public int checkForHighCard() {       
//either returns highest card value
        Integer highest = values.get(0);

        for (int i = 1; i < values.size(); i++) {
            Integer current = values.get(i);
            if (current > highest) {
                highest = current;
            }
        }

        return highest;
    }
//end checkForHighCard
//-----------------------------------------------------------------------------------------------------------------------------------
    public void populateValuesAndSuitsArrayLists(ArrayList<String> hand){
//populates arrays
        for(int i=0; i<7; i++){
            int indexOfSuit = hand.get(i).length()-1;//either set to index 1 or 2 (2 if the value is a 10)
            this.suits.add(hand.get(i).charAt(indexOfSuit));
        }
        for(int i=0; i<7; i++){
            if(hand.get(i).charAt(1) == '0'){
                this.values.add(10);
            }
            else{   
                char rank = hand.get(i).charAt(0);
                Integer intRank;
                switch(rank){
                    case 'A':
                        intRank=14;
                    break;
                    case 'K':
                        intRank=13;
                    break;
                    case 'Q':
                        intRank=12;
                    break;
                    case 'J':
                        intRank=11;
                    break;
                    default:
                        char toBeConverted = hand.get(i).charAt(0);
                        intRank = Integer.valueOf(Character.toString(toBeConverted));
                        
                    break;
                }//end switch statement
                this.values.add(intRank);    
            }
        }
    }
//end populateValuesAndSuitsArrayLists
//----------------------------------------------------------------------------------------------------------------------------------- 
        public StringBuilder searchForFlush(){
//the return would look something like 5H meaning there are 5 hearts, therefore is searchForFlush returns 5 or higher
        
        int[] countSuits = {0, 0, 0, 0};//hearts, diamonds, spades clubs
        
        for(int i=0; i<=6; i++){
            Character checkSuit = this.suits.get(i);
            switch(checkSuit){
                case 'H':
                    countSuits[0]++;
                break;
                case 'D':
                    countSuits[1]++;
                break; 
                case 'S':
                    countSuits[2]++;
                break;
                case 'C':
                    countSuits[3]++;
                break;
            }//end switch
        }
        int highestSuit=countSuits[0];
        int indexOfHighestSuit=0;
        for(int i=1; i<=3; i++){
            if(countSuits[i]> highestSuit){
                highestSuit = countSuits[i];
                indexOfHighestSuit =i;
            }
        }
        StringBuilder output = new StringBuilder();
        output.append(String.valueOf(highestSuit));
        
        switch(indexOfHighestSuit){
            case 0:
                output.append("H");
            break;
            case 1:
                output.append("D");
            break;
            case 2:
                output.append("S");
            break;
            default:
                output.append("C");
            break;
        }//end switch
        
        return output;

        
    }
//end searchForFlush
//----------------------------------------------------------------------------------------------------------------------------------- 

}//end PokerHands