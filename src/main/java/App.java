import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  static Hangman hangman = null;
  static int difficulty = 0;

  public static void main(String[] args) {
    int endCondition = 0;
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      reset();
      model.put("template", "templates/start.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/hangman", (request, response)->{
      HashMap<String, Object> model;
      if(checkFirstTime()){
        int diffInput = Integer.parseInt(request.queryParams("difficulty"));
        model = createHangmanModel(diffInput);
      } else {
        String guess = request.queryParams("guessInput");
        model = checkGuess(guess);
      }
      // hangman = new Hangman(difficulty);
      // model.put("hangmanWord", Arrays.toString(hangman.getProgress()));
      // model.put("alreadyGuessed", hangman.getGuesses());
      // model.put("numGuesses", hangman.mMAX_COUNTER - hangman.getGameEndCounter());
      // model.put("template", "templates/hangman.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  //   System.out.println("----------------------");
  //   System.out.println("Welcome to HAAAAANGMAAAAAAN!!!!one!!alsoOne!!!!!EXCLAMATIONpOINT!");
  //   System.out.println("One man enters, sometimes one man leaves. but also maybe no men leave.");
  //   System.out.println("(You know how Hangman works, right?)");
  //   System.out.println("If not you should probably choose easy.");
  //   System.out.println("Enter a number indicating your desired difficulty level, OR have a friend enter a word for you directly!\n1 - easy\n2 - medium\n3 - hard");
  //   String userInput = new String(console.readPassword());
  //   try {
  //     difficulty = Integer.parseInt(userInput.trim());
  //   } catch (NumberFormatException e){
  //     hangman = new Hangman(userInput);
  //   }
  //   if(hangman == null){
  //     if(difficulty < 1 || difficulty > 3){
  //       System.out.println("Joke's on you, sucka!!!!!!");
  //     }
  //     hangman = new Hangman(difficulty);
  //   }
  //   do{
  //     System.out.println("----------------------");
  //     System.out.println("You have " + (hangman.mMAX_COUNTER - hangman.getGameEndCounter()) + " guesses remaining to SAVE THIS RANDO'S LIFE!");
  //     if(hangman.getGameEndCounter() > 0){
  //       System.out.println("");
  //       System.out.println(" O");
  //     }
  //     if(hangman.getGameEndCounter() > 3){
  //       System.out.println("/|\\");
  //     } else if(hangman.getGameEndCounter() > 2){
  //       System.out.println("/|");
  //     } else if (hangman.getGameEndCounter() > 1){
  //       System.out.println(" |");
  //     }
  //     if(hangman.getGameEndCounter() > 4){
  //       System.out.println("/");
  //     }
  //     System.out.println("");
  //     System.out.println("So far, this is what you know of the word: " + Arrays.toString(hangman.getProgress()));
  //     System.out.println("Additionally, these be your guesses: " + hangman.getGuesses());
  //     System.out.println("Whatchu wanna guess???questionMark???");
  //     guess = String.valueOf(console.readLine().trim().toLowerCase().charAt(0));
  //     if(hangman.isGuessed(guess)){
  //       System.out.println("You already guessed " + guess + ". Go home. Reevaluate all of the life-choices that led you to this point.");
  //     }
  //     else{
  //       if(hangman.guess(guess)){
  //         if(hangman.endTurn() != 1){
  //           System.out.println("Congratulations! You are one step closer to saving this rando!");
  //         }
  //       }
  //       else{
  //         System.out.println("WHAT?!?!?!?!?!outrage? Why did you guess " + guess + "? Everyone knows that no word ever has contained " + guess + "! GOD.");
  //       }
  //     }
  //     endCondition = hangman.endTurn();
  //   }while(endCondition == 0);
  //   System.out.println("----------------------");
  //   if(endCondition == -1){
  //     System.out.println("_______");
  //     System.out.println("|  |   ");
  //     System.out.println("|  O   ");
  //     System.out.println("| /|\\  ");
  //     System.out.println("| / \\  ");
  //     System.out.println("|______");
  //     System.out.println("");
  //     System.out.println("Oh nos! The rando is ded! You killed him. All you. No other factors were responsible for his death. You. The answer was \"" + hangman.getAnswer() + "\" by the way. A child could have guessed that.");
  //   }
  //   else{
  //     System.out.println("");
  //     System.out.println("\\O/");
  //     System.out.println(" | ");
  //     System.out.println("/ \\");
  //     System.out.println("");
  //     System.out.println("Congratulations! You successfully guessed that the answer is \"" + hangman.getAnswer() + "\". That must have been SOOOOOO hard. You are SOOOO smart and special. I guess the rando lives too. FOR NOW.");
  //   }
  //   System.out.println("----------------------");
   }

   public static HashMap createHangmanModel(int diffInput){
     HashMap<String, Object> model = new HashMap<String, Object>();
     difficulty = diffInput;
     hangman = new Hangman(difficulty);
     model.put("guessMsg", "");
     model.put("gameEndMsg", "");
     model.put("hangmanImg", "");
     model.put("hangmanWord", Arrays.toString(hangman.getProgress()));
     model.put("alreadyGuessed", hangman.getGuesses());
     model.put("numGuesses", hangman.mMAX_COUNTER - hangman.getGameEndCounter());
     model.put("template", "templates/hangman.vtl");
     return model;
   }

   public static HashMap checkGuess(String guess){
     HashMap<String, Object> model = new HashMap<String, Object>();
     String guessMsg;
     String hangmanImg="";
     if(!hangman.isGuessed(guess)){
       if(hangman.guess(guess)){
         guessMsg = "Congratulations! You are one step closer to saving this rando!";
       } else {
         guessMsg = "WHAT?!?!?!?!?!outrage? Why did you guess " + guess + "? Everyone knows that no word ever has contained " + guess + "! GOD.";

       }
     } else {
       guessMsg = "You already guessed " + guess + ". Go home. Reevaluate all of the life-choices that led you to this point.";
     }
     int endCondition = hangman.endTurn();
     String gameEndMsg = "";
     if(hangman.getGameEndCounter() > 0){
      hangmanImg = "<img class='img-responsive' src='images/hangman"+hangman.getGameEndCounter()+".png'>";
     }
     if(endCondition == 1){
       gameEndMsg = "Congratulations! You successfully guessed that the answer is <strong>" + hangman.getAnswer() + "</strong>. That must have been SOOOOOO hard. You are SOOOO smart and special. I guess the rando lives too. FOR NOW.";
       guessMsg="";
       hangmanImg = "<img class='img-responsive' src='images/hangman_win.jpg'>";
       reset();
     } else if (endCondition == -1){
       gameEndMsg = "Oh nos! The rando is ded! You killed him. All you. No other factors were responsible for his death. You. The answer was <strong>" + hangman.getAnswer() + "</strong> by the way. A child could have guessed that.";
       reset();
     }
     model.put("gameEndMsg", gameEndMsg);
     model.put("guessMsg", guessMsg);
     model.put("hangmanImg", hangmanImg);
     model.put("hangmanWord", Arrays.toString(hangman.getProgress()));
     model.put("alreadyGuessed", hangman.getGuesses());
     model.put("numGuesses", hangman.mMAX_COUNTER - hangman.getGameEndCounter());
     model.put("template", "templates/hangman.vtl");
     return model;
   }

   public static boolean checkFirstTime(){
     return difficulty == 0;
   }

   public static void reset(){
     difficulty = 0;
   }
}
