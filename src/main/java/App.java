import java.io.Console;
import java.util.Arrays;

public class App {
  public static void main(String[] args) {
    Console console = System.console();
    Hangman hangman = new Hangman();
    String guess = "";
    int endCondition = 0;

    System.out.println("----------------------");
    System.out.println("Welcome to HAAAAANGMAAAAAAN!!!!one!!alsoOne!!!!!EXCLAMATIONpOINT!");
    System.out.println("One man enters, sometimes one man leaves. but also maybe no men leave.");
    System.out.println("(You know how Hangman works, right?)");
    do{
      System.out.println("----------------------");
      System.out.println("You have " + (hangman.mMAX_COUNTER - hangman.getGameEndCounter()) + " guesses remaining to SAVE THIS RANDO'S LIFE!");
      System.out.println("So far, this is what you know of the word: " + Arrays.toString(hangman.getProgress()));
      System.out.println("Whatchu wanna guess???questionMark???");
      guess = String.valueOf(console.readLine().trim().toLowerCase().charAt(0));
      if(hangman.isGuessed(guess)){
        System.out.println("You already guessed " + guess + ". Go home. Reevaluate all of the life-choices that led you to this point.");
      }
      else{
        if(hangman.guess(guess)){
          if(hangman.endTurn() != 1){
            System.out.println("Congratulations! You are one step closer to saving this rando!");
          }
        }
        else{
          System.out.println("WHAT?!?!?!?!?!outrage? Why did you guess " + guess + "? Everyone knows that no word ever has contained " + guess + "! GOD.");
        }
      }
      endCondition = hangman.endTurn();
    }while(endCondition == 0);
    if(endCondition == -1){
      System.out.println("Oh nos! The rando is ded! You killed him. All you. No other factors were responsible for his death. You. The answer was " + hangman.getAnswer() + " by the way. A child could have guessed that.");
    }
    else{
      System.out.println("Congratulations! You successfully guessed that the word is " + hangman.getAnswer() + ". That must have been SOOOOOO hard. You are SOOOO smart and special. I guess the rando lives too. FOR NOW.");
    }
  }
}
