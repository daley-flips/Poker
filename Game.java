import java.util.Scanner;
import java.util.InputMismatchException;
public class Game{

    public static void main(String[] args) {
        
        Poker poker = new Poker(); // Create an instance of the Poker class
        PokerHands pokerhands = new PokerHands();
        Tie tie = new Tie();
        
//followind hard coded values that will be changed later to input by user
poker.numOfPlayers = 2;
poker.startMoney = 100;
poker.littleBlind = 1;
poker.bigBlind = 2;
poker.dealNum = 1;
//end hardcode
        
        
        
        
              
       
        poker.createDeck();       
        poker.giveMoneyToPlayers();
System.out.println("testing github attempt 3");        
while(true){//rounds will continue
        poker.shuffle();
        poker.deal();
        poker.payBlinds(poker.dealNum);
        poker.currBet = poker.bigBlind;
        int bidder = poker.dealNum + 3;//the player that bids first is left of big blind so 3 away from dealer
        boolean onlyPrintPlayerCardsOnce = true;
        while(poker.isGood.contains(false)){
            for(int i=0; i<poker.numOfPlayers*2; i+=2){
                if(bidder > poker.numOfPlayers){
                    bidder = 1;
                }
                if(onlyPrintPlayerCardsOnce = true){
                    System.out.print("Player " + bidder + "'s cards: ");
                }
                System.out.println(poker.translateForUsers(poker.playerHands.get(i)) + " and " + poker.translateForUsers(poker.playerHands.get(i+1)));
                if(poker.hasFolded[bidder-1] == false && poker.isGood.get(bidder-1) == false){
                    prompt(poker, poker.currBet, bidder);
                }
                //temporary to play against dad--->
                for(int j=0; j<100; j++){
                    System.out.println("hiding cards");
                }
                bidder++;
            }
            onlyPrintPlayerCardsOnce = false;
        }
        
        poker.resetIsGood();
        poker.resetTempChips();
        poker.currBet=0;
        System.out.println("pot is " + poker.pot);
        poker.flop();
        bidder = poker.dealNum + 1;
        while(poker.isGood.contains(false)){
            for(int i=0; i<poker.numOfPlayers; i++){
                if(bidder > poker.numOfPlayers){
                    bidder = 1;
                }   
                if(poker.hasFolded[bidder-1] == false && poker.isGood.get(bidder-1) == false){
                    System.out.println("Player " + bidder + ":");
                    prompt(poker, poker.currBet, bidder);
                }                
                bidder++;
            }    
        }
        poker.resetIsGood();
        poker.resetTempChips();
        poker.currBet=0;
        System.out.println("pot is " + poker.pot);
        poker.turn();
        bidder = poker.dealNum + 1;
        while(poker.isGood.contains(false)){
            for(int i=0; i<poker.numOfPlayers; i++){
                if(bidder > poker.numOfPlayers){
                    bidder = 1;
                }   
                if(poker.hasFolded[bidder-1] == false && poker.isGood.get(bidder-1) == false){
                    System.out.println("Player " + bidder + ":");
                    prompt(poker, poker.currBet, bidder);
                }                
                bidder++;
            }    
        }
        poker.resetIsGood();
        poker.resetTempChips();
        poker.currBet=0;
        System.out.println("pot is " + poker.pot);
        poker.river();
        bidder = poker.dealNum + 1;
        while(poker.isGood.contains(false)){
            for(int i=0; i<poker.numOfPlayers; i++){
                if(bidder > poker.numOfPlayers){
                    bidder = 1;
                }   
                if(poker.hasFolded[bidder-1] == false && poker.isGood.get(bidder-1) == false){
                    System.out.println("Player " + bidder + ":");
                    prompt(poker, poker.currBet, bidder);
                }                
                bidder++;
            }    
        }
        poker.determineWinner();//could add a array parameter with player indexes who are still in
        for(int i=0; i<poker.numOfPlayers; i++){
            System.out.print(poker.chips[i] + " ");
        }
        System.out.println();
        System.out.println("pot is " + poker.pot);
        
        //clear before next round
        poker.playerHands.clear();    
        poker.cardsOnTable.clear();    
        poker.isGood.clear(); 
        pokerhands.values.clear();
        pokerhands.suits.clear();
        poker.pot = 0;
    System.out.println("NEW ROUND");
    if(poker.dealNum < poker.numOfPlayers){
        poker.dealNum++;
    }
    else{
        poker.dealNum = 1;
    }

    }//end while true        
    }//end main method
        
    public static void prompt(Poker poker, double currBet, int playerNum){
    //this will take in the user inputs for gambling
        Scanner bets = new Scanner(System.in);
        
        boolean validInput = false;
        boolean isCheck;//this will determine if the c means check for calls since the same char is used for both
        while(!validInput){
            if(currBet == 0){
                System.out.println("Check (c), Raise (r), or Fold (f)?");
                isCheck = true;
            }
            else{
                System.out.println("Current bet is " + currBet);
                System.out.println("Call (c), Raise (r), or Fold (f)?");  
                isCheck=false;
            }
            String inputLine = bets.nextLine();

            char crf; //check/call, raise, fold
            if (inputLine.length() == 1) {
                crf = inputLine.charAt(0);                
                if(crf == 'c'){
                    validInput = true;
                    if(isCheck){
                        poker.check(playerNum-1);// -1 so it correlates to index not playerNum
                    }
                    else{
                        poker.call(playerNum-1);
                    }
                }
                else if(crf == 'r'){
                    while(!validInput){
                        System.out.println("How much would you like to raise?");
                    
                        try{
                            double currRaise = bets.nextDouble();
                            poker.raise(currRaise, playerNum-1);
                            validInput = true;
                        }
                        catch (InputMismatchException e) {
                            // Catch the exception if the user enters an invalid double
                            System.out.println("Invalid input, please try again");
                            bets.nextLine(); // Clear the input buffer to prevent infinite loop
                        }
                    }
                }
                else if(crf == 'f'){
                    validInput = true;
                    poker.fold(playerNum-1);
                } 
                else{
                    System.out.println("Invalid input, please try again");
                }
            }
            else {
                System.out.println("Invalid input, please try again");
            }
        }//end while    
        
        
    
    }//end prompt
    
    public boolean winByDefault(Poker poker){
//returns true when all players fold except 1, leaving them winner regardless of cards and terminates round immediately
        int count = 0;
        for(int i=0; i<poker.hasFolded.length; i++){
            if(poker.hasFolded[i] == false){
                count++;
            }
        }
        if(count == 1){
            return true;
        }
        return false;
    }//end winByDefault
    
    
    
    
    
    
    
}//end game class