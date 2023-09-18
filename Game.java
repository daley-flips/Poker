import java.util.Scanner;
import java.util.InputMismatchException;
public class Game{

    public static void main(String[] args) {
        
        Poker poker = new Poker(); // Create an instance of the Poker class
        PokerHands pokerhands = new PokerHands();
        Tie tie = new Tie();
        
//followind hard coded values are used for testing, should be input by user
//poker.numOfPlayers = 2;
//poker.startMoney = 100;
//poker.littleBlind = 1;
//poker.bigBlind = 2;
poker.dealNum = 1;// randomize this later
//end hardcode
        Scanner initGame = new Scanner(System.in);
        boolean completePreGame = false;
        boolean isValidPlayers = false;
        boolean stMoney = false;
        boolean littleBl = false;
        while(!completePreGame){
            try{
                while(!isValidPlayers){   
                    System.out.println("How many players? (minimum 2)");     
                    int numPlayers = initGame.nextInt();
                    if(numPlayers >= 2){
                        poker.numOfPlayers = numPlayers;
                        isValidPlayers = true;
                    }
                    else{
                        System.out.println("That's not a valid input. Please try again.");
                    };
                }
                while(!stMoney){
                    System.out.println("How much should starting money be? (minimum: 50, recommended is 100+)");     
                    int startMon = initGame.nextInt();
                    if(startMon >= 50){
                        poker.startMoney = startMon;
                        stMoney = true;
                    }
                    else{
                        System.out.println("That's not a valid input. Please try again.");
                    }
                }
                while(!littleBl){
                    System.out.println("How much should little blind be? (big blind will be double) (recommended is 1-5)");     
                    int lilb = initGame.nextInt();
                    if(lilb >= 1){
                        poker.littleBlind = lilb;
                        poker.bigBlind = 2*lilb;
                        initGame.nextLine();//just clearing scanner for later use
                        littleBl = true;
                    }
                    else{
                        System.out.println("That's not a valid input. Please try again.");
                    }
                }
                completePreGame = true;
            }
            catch (InputMismatchException e) {
                System.out.println("That's not a valid input. Please try again.");
                initGame.nextLine();  // Clear the invalid input
            }
        }
        
        for(int i=0; i<10; i++){
            System.out.println();
        }     
        //^just for aesthetics
        System.out.println("Let's begin, give each player a number and hand the computer to one person, we will now be showing the cards, hit enter when ready");
        String itDoesntMatterWhatTheyPut = initGame.nextLine();
        poker.createDeck();       
        poker.giveMoneyToPlayers();     
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
                if(i+2 < poker.numOfPlayers*2){
                    for(int hideCards=0; hideCards<100; hideCards++){
                        System.out.println();
                    }
                    int incrementBidder = bidder+1;
                    System.out.println("Hand the computer to player " + incrementBidder);
                    System.out.println("Hi player " + incrementBidder + " Hit enter when you're ready to see your cards");
                    itDoesntMatterWhatTheyPut = initGame.nextLine();
                    for(int hideCards=0; hideCards<100; hideCards++){
                        System.out.println();
                    }//
                    //
                }
                else{//WHEN WE GET TO THE LAST PLAYER
                    System.out.println("hit enter and then let everyone see the computer after");
                    itDoesntMatterWhatTheyPut = initGame.nextLine();
                    for(int hideCards=0; hideCards<100; hideCards++){
                        System.out.println();
                    }
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
        System.out.println("Current balance per player:");
        for(int i=0; i<poker.numOfPlayers; i++){
            int plNum = i+1;
            System.out.println("Player " + plNum + ": " + poker.chips[i] + " ");
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
    System.out.println("Let's begin, hand the computer to the player left of the big blind (or whoever bids first), we will now be showing the cards, hit enter when ready");
    itDoesntMatterWhatTheyPut = initGame.nextLine();
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