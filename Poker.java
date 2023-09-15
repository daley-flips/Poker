import java.util.ArrayList;
import java.util.Collections;
//-----------------------------------------------------------------------------------------------------------------------------------
public class Poker{
//instance variables
    public int numOfPlayers;
    public ArrayList<String> playerHands = new ArrayList<>(); //this array will look like {"AH, "KH", "2S", "3S"}
    public double[] chips;
    public double[] tempChips;//this array is used into the betting process to remember how much people aleady put in
    public double startMoney;
    public double littleBlind;
    public double bigBlind;
    public int dealNum;
    public double pot;
    public double sidePot;//when someone runs out of money ALSO NOT IMPLEMENTED YET
    public ArrayList<String> deckOfCards = new ArrayList<>(52);//52 cards in a deck
    public ArrayList<String> cardsOnTable = new ArrayList<>(5);//the 5 cards all players see on the table
    public ArrayList<Boolean> isGood;//true when all players have called or folded
    public boolean[] hasFolded;//true if player has folded false if not
    public double currBet;
    public int currPlayerIndex;
    
//end instance variables
//-----------------------------------------------------------------------------------------------------------------------------------    
    public void createDeck(){
//creates a full deck of cards 2 through Ace of all 4 suits inside the deckOfCards ArrayList       
        String[] suits = {"H", "D", "C", "S"}; // Hearts, Diamonds, Clubs, Spades
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String suit : suits) {
            for (String rank : ranks) {
                String card = rank + suit;
                this.deckOfCards.add(card);
            }
        }
    }
//end creatDeck
//----------------------------------------------------------------------------------------------------------------------------------- 
//gives money to players and initializes arrays and currentBet
    public void giveMoneyToPlayers(){
        this.chips = new double[this.numOfPlayers];    
        this.tempChips = new double[this.numOfPlayers];    
        for(int i=0; i< this.chips.length; i++){
            this.chips[i] = this.startMoney;
            this.tempChips[i] = 0;
        }
    }
//end giveMoneyToPlayers
//-----------------------------------------------------------------------------------------------------------------------------------    
    public void shuffle(){
//since the deck will start in order, this methood will randomize the array
        Collections.shuffle(deckOfCards);
    }
//end shuffle()    
//-----------------------------------------------------------------------------------------------------------------------------------    
    public void deal(){
//this deal will simulate a realistic deal where each player recives their first card, the deal goes around and then everyone gets their second card. Hence the loopAgain int        
        for(int u=0; u<numOfPlayers; u++){
            for(int i=0; i<this.numOfPlayers*2; i+=this.numOfPlayers){//to iterate across the deck
                this.playerHands.add(this.deckOfCards.get(i+u));
            }//end for
        }//end for        
    }
//end deal
//-----------------------------------------------------------------------------------------------------------------------------------    
    public void payBlinds (int dealerPlayerNum){
//this method makes the little and big blinds auto-pay
//note player numbers start at 1
//assume players sit player number 1,2,3,4,5, counter clock wise, (if player 1 is the dealer then player 2 is to player 1's left and is the little blind)
//remember poker moves CCW, a player would get Big blind, next hand they get litte blind, then they deal
        this.pot = this.bigBlind + this.littleBlind;
        this.currBet = this.bigBlind;
        this.isGood = new ArrayList<Boolean>(this.numOfPlayers);
        this.hasFolded = new boolean[this.numOfPlayers];
        for(int i=0; i< hasFolded.length; i++){
            this.hasFolded[i] = false;
            this.isGood.add(false);
        }
        if(dealerPlayerNum == numOfPlayers-1){//special case when dealer is the 2nd highest number
            chips[this.numOfPlayers-1] -= this.littleBlind;
            chips[0] -= this.bigBlind;
            tempChips[this.numOfPlayers-1] += this.littleBlind;
            tempChips[0] += this.bigBlind;
            
        }
        else{
            chips[dealerPlayerNum%this.numOfPlayers] -= this.littleBlind;//immediately to the left of dealer
            chips[dealerPlayerNum%this.numOfPlayers+1] -= this.bigBlind;
            tempChips[dealerPlayerNum%this.numOfPlayers] += this.littleBlind;
            tempChips[dealerPlayerNum%this.numOfPlayers+1] += this.bigBlind;
        }
    }
//end payBlinds
//-----------------------------------------------------------------------------------------------------------------------------------
    public void check(int indexInArrayPlayerNum){
//basically 1 less than the playerNum so we can plug directly into the arrays    
        this.isGood.set(indexInArrayPlayerNum, true);
    }
//end check
//-----------------------------------------------------------------------------------------------------------------------------------
    public void call(int indexInArrayPlayerNum){
//implement this later aha   
        this.isGood.set(indexInArrayPlayerNum, true);
        if(this.tempChips[indexInArrayPlayerNum] != 0){
            this.chips[indexInArrayPlayerNum] -= (this.currBet-this.tempChips[indexInArrayPlayerNum]);//so if the player already has money on the line, they're only calling up to the amount of the call (for when multiple calls are made in one round at a higher bet)
            this.pot += (this.currBet-this.tempChips[indexInArrayPlayerNum]);//same idea with the pot
        }
        else{
            this.chips[indexInArrayPlayerNum] -= this.currBet;
            this.pot += this.currBet;   
        }
        this.tempChips[indexInArrayPlayerNum] = this.currBet;//remember how much the player already put in
        
        
    }
//end call
//-----------------------------------------------------------------------------------------------------------------------------------    
    public void raise(double raisedValue, int indexInArrayPlayerNum){
//implement this later aha    
        this.isGood.set(indexInArrayPlayerNum, true);
        this.currBet = raisedValue;
        for(int i=0; i<numOfPlayers; i++){
            if(i!=indexInArrayPlayerNum && this.hasFolded[i] == false){
                this.isGood.set(i,false);//when someone raises, everyone is not good anymore. unless they've folded 
            }
        }
        if(this.tempChips[indexInArrayPlayerNum] != 0){
            this.chips[indexInArrayPlayerNum] -= (this.currBet-this.tempChips[indexInArrayPlayerNum]);//so if the player already has money on the line, they're only raising up to the amount of the raise (for when multiple calls are made in one round at a higher bet)
            this.pot += (this.currBet-this.tempChips[indexInArrayPlayerNum]);//same idea with the pot
        }
        else{
            this.chips[indexInArrayPlayerNum] -= this.currBet;
            this.pot += this.currBet;   
        }
        this.tempChips[indexInArrayPlayerNum] = this.currBet;//remember how much the player already put in
    }
//end raise
//-----------------------------------------------------------------------------------------------------------------------------------    
    public void fold(int indexInArrayPlayerNum){
//implement this later aha    
        this.isGood.set(indexInArrayPlayerNum, true);
        this.hasFolded[indexInArrayPlayerNum] = true;
    }
//end fold
//----------------------------------------------------------------------------------------------------------------------------------- 
//resetting the isGood arrayList between rounds
    public void resetIsGood(){
        for(int i=0; i<this.numOfPlayers; i++){
            if(this.hasFolded[i] == false){
                this.isGood.set(i, false);
            }
        }
    }
//-----------------------------------------------------------------------------------------------------------------------------------     
    public void resetTempChips(){
        for(int i=0; i<this.numOfPlayers; i++){
            this.tempChips[i] = 0;
        }
    }
//-----------------------------------------------------------------------------------------------------------------------------------        
    public void flop(){
//will burn and then show 3 cards to everyone
//starts with a burn at index 2*numOfPlayers in the deckOfCards array
//so the cards we show to users is from 2*numOfPlayers+1 (and +2 and +3)
        System.out.println("The flop is:");
        for(int i= 2*this.numOfPlayers+1; i<= 2*this.numOfPlayers+3; i++){
            System.out.println(this.translateForUsers(this.deckOfCards.get(i)) + " ");
            this.cardsOnTable.add(this.deckOfCards.get(i));
        }   
    }
//end flop
//-----------------------------------------------------------------------------------------------------------------------------------    
    public void turn(){//will burn and then show 1 more card, this is called the turn in poker
    //starts with a burn at index 2*numOfPlayers+4 in the deckOfCards array
    //the card show to the users will be at 2*numOfPlayers+5
        System.out.println("The turn is:");
        System.out.println(this.translateForUsers(this.deckOfCards.get(numOfPlayers*2+5)) + " ");
        this.cardsOnTable.add(this.deckOfCards.get(numOfPlayers*2+5));            
    }
//end turn
//-----------------------------------------------------------------------------------------------------------------------------------    
    public void river(){//will burn and then show 1 more card, this is called the river in poker
    //starts with a burn at index 2*numOfPlayers+6 in the deckOfCards array
    //the card show to the users will be at 2*numOfPlayers+7
        System.out.println("The river is:");
        System.out.println(this.translateForUsers(this.deckOfCards.get(numOfPlayers*2+7)) + " ");
        this.cardsOnTable.add(this.deckOfCards.get(numOfPlayers*2+7));           
    }
//end river
//-----------------------------------------------------------------------------------------------------------------------------------    
    public void determineWinner(){
    //determines the winner based on whoever has the best hand
        
        int[] playerScores = new int[numOfPlayers];
        //System.out.println(this.playerHands);
        PokerHands pokerhands = new PokerHands(); // Create an instance of the PokerHands class
        Tie tie = new Tie(); //create instance of Tie class
        ArrayList<String> cardsOnTableAndOnePlayersHand = new ArrayList<>(7);
        cardsOnTableAndOnePlayersHand = this.cardsOnTable;
        
        
        //*****HARDCODE TEST CASSES****** royal flush works, straight flush UNTESTED AND UNIMPLEMENTED, four of a kind works,
        /*
        cardsOnTableAndOnePlayersHand.set(0, "AH");
        cardsOnTableAndOnePlayersHand.set(1, "KH");
        cardsOnTableAndOnePlayersHand.set(2, "QH");
        cardsOnTableAndOnePlayersHand.set(3, "JH");        
        cardsOnTableAndOnePlayersHand.set(4, "10H");
        playerHands.set(0, "2S");
        playerHands.set(1, "JS");
        playerHands.set(2, "5S");
        playerHands.set(3, "9C");
        playerHands.set(4, "7C");
        playerHands.set(5, "9H");
        playerHands.set(6, "3D");
        playerHands.set(7, "6H");
        playerHands.set(8, "5C");
        playerHands.set(9, "4S");
        */
       
   
        //**********************************
        
        
        int countPlayerNum=1;
        for(int j=0; j<this.numOfPlayers*2; j+=2){
            cardsOnTableAndOnePlayersHand.add(this.playerHands.get(j));
            cardsOnTableAndOnePlayersHand.add(this.playerHands.get(j+1));
            
            pokerhands.populateValuesAndSuitsArrayLists(cardsOnTableAndOnePlayersHand);
              
            int[] individualPlayerResults = new int[10];
            individualPlayerResults[0] = pokerhands.checkForRoyalFlush();
            individualPlayerResults[1] = pokerhands.checkForStraightFlush();
            individualPlayerResults[2] = pokerhands.checkForXOfAKind(4);
            individualPlayerResults[3] = pokerhands.checkForFullHouse();
            individualPlayerResults[4] = pokerhands.checkForFlush();
            individualPlayerResults[5] = pokerhands.checkForStraight();
            individualPlayerResults[6] = pokerhands.checkForXOfAKind(3);
            individualPlayerResults[7] = pokerhands.checkForTwoPairs();
            individualPlayerResults[8] = pokerhands.checkForXOfAKind(2);
            individualPlayerResults[9] = pokerhands.checkForHighCard();
            int playerScore =0;
            for(int i=0; i<10; i++){
                if(individualPlayerResults[i]> playerScore){
                    playerScore = individualPlayerResults[i];
                }
            }
            playerScores[countPlayerNum-1] = playerScore;
            String theHand;
            if(playerScore<20){
                theHand = "High Card";
            }
            else if(playerScore==20){
                theHand = "Pair";
            }
            else if(playerScore==30){
                theHand = "Two Pair";
            }
            else if(playerScore==40){
                theHand = "3 of a kind";
            }
            else if(playerScore==50){
                theHand = "Straight";
            }
            else if(playerScore==60){
                theHand = "Flush";
            }
            else if(playerScore==70){
                theHand = "Full House";
            }
            else if(playerScore==80){
                theHand = "4 of a Kind";
            }
            else if(playerScore==90){
                theHand = "Straight Flush😄😄😄😄😄";
            }
            else if(playerScore==100){
                theHand = "ROYAL FLUSH🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟";
            }
            else{
                theHand= "lmao that's a bug";
            }  
            
            if(this.hasFolded[countPlayerNum-1] == true){
                playerScore = 0;
                theHand = "Folded";
                playerScores[countPlayerNum-1] = 0;
            }
            
            System.out.println("Hand for player " + (countPlayerNum) + ": " + theHand);
            countPlayerNum++;
            cardsOnTableAndOnePlayersHand.remove(6);
            cardsOnTableAndOnePlayersHand.remove(5);
            pokerhands.values.clear();
            pokerhands.suits.clear();
        }//end for loop
        
        int winnerScore=0;
        int indexOfWinner=0;
        for(int i=0; i<numOfPlayers; i++){
            if(playerScores[i]>winnerScore){
                winnerScore=playerScores[i];
                indexOfWinner = i;
            }
        }
        
        //System.out.println("Player " + (indexOfWinner+1) + " wins!"); //takeout due to ties
        ArrayList<Integer> tieIndexes = new ArrayList<>();
        tieIndexes.add(indexOfWinner);
        for(int i=0; i<numOfPlayers; i++){
            if( i!=indexOfWinner && playerScores[i] == winnerScore){
                tieIndexes.add(i);
            }
        }
        //System.out.println(tieIndexes);
        //System.out.println(winnerScore);
        //System.out.println("yeet" + playerHands);
        StringBuilder winnerOrWinners = tie.breakTheTie(tieIndexes, winnerScore, playerHands, cardsOnTable);
        if(winnerOrWinners.length() > 2){//multiple winners
            double splitPot = this.pot/winnerOrWinners.length();
            splitPot*=2;//this splits the pot between the tied up players
            System.out.println("Winners are:");
            for(int i=0; i< winnerOrWinners.length(); i+=2){
                char playerIndex = winnerOrWinners.charAt(i);
                int playerNumIntValue = Character.getNumericValue(playerIndex);//convert to add one
                System.out.println("Player " + playerNumIntValue);
                chips[playerNumIntValue-1] += splitPot;
            }
        }
        else{
            System.out.println("Winner is:");
            char playerIndex = winnerOrWinners.charAt(0);
            int playerNumIntValue = Character.getNumericValue(playerIndex)+1;//convert to add one
            System.out.println("Player " + playerNumIntValue);
            this.chips[playerNumIntValue-1] += this.pot;
        }
        
        
        //System.out.println(this.playerHands);        
        //System.out.println(cardsOnTable);                
    }
//end determineWinner
//-----------------------------------------------------------------------------------------------------------------------------------    
    public StringBuilder translateForUsers(String card5H){
//String 5H is just an example of how the cards currently look in the program, the idea of this method is to take a card such as 5H, and translate it to the user to say "5 of Hearts        
        StringBuilder outPut = new StringBuilder();
        char value = card5H.charAt(0);
        switch (value) {
        //checking card value
            case 'A':
                outPut.append("Ace");
            break;
            case 'K':
                outPut.append("King");
            break;
                case 'Q':
                    outPut.append("Queen");
            break;
            case 'J':
                outPut.append("Jack");
            break;
            default:
                outPut.append(value);
            break;
        }        
        int properIndexPerValue = 1; //this is for when the value is 10, using charAt is a special case
        //set to 1 as defualt to get the suit
        if(card5H.charAt(0) == '1' && card5H.charAt(1) == '0'){
            outPut.append(card5H.charAt(1));
            properIndexPerValue = 2;
            //set to 2 in special case for 10 valued cards
        }        
        outPut.append(" of ");
        char suit = card5H.charAt(properIndexPerValue);
        switch (suit) {
        //checking suit
        case 'H':
            outPut.append("Hearts ♥");
            break;
        case 'D':
            outPut.append("Diamonds ♦");
            break;
        case 'S':
            outPut.append("Spades ♠");
            break;
        case 'C':
            outPut.append("Clubs ♣");
            break;
        }
        return outPut;        
    }
//end translateForUsers
//---------------------------------------------------------------------------------------------------------------------------------
    
    
    
    
    
    
    
    
    
    
}//end Poker    