import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  //static Hangman hangman = null;

  public static void main(String[] args) {
    //int endCondition = 0;
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/start.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/hangStart", (request, response)->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int difficulty = Integer.parseInt(request.queryParams("difficulty"));
      Hangman hangman = new Hangman(difficulty);
      request.session().attribute("hangman", hangman);
      model.put("gameEndMsg", "");
      model.put("hangmanImg", "");
      model.put("hangmanWord", hangman.getProgress());
      model.put("numGuesses", hangman.mMAX_COUNTER - hangman.getGameEndCounter());
      model.put("template", "templates/hangman.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/hangman", (request, response)->{
       Hangman hangman = request.session().attribute("hangman");
       HashMap<String, Object> model = new HashMap<String, Object>();
       String guess = request.queryParams("guessInput");
       String guessMsg = "";
       String hangmanImg="";
       if(!hangman.isGuessed(guess)){
         if(hangman.guess(guess)){
           guessMsg = "yes";
         } else {
           guessMsg = "no";

         }
       }
       int endCondition = hangman.endTurn();
       String gameEndMsg = "";
       if(hangman.getGameEndCounter() > 0){
        hangmanImg = "<img class='img-responsive center-block' src='images/hangman"+hangman.getGameEndCounter()+".png'>";
       }
       if(endCondition == 1){
         gameEndMsg = "win";
         hangmanImg = "<img class='img-responsive center-block' src='images/hangman_win.jpg'>";
       } else if (endCondition == -1){
         gameEndMsg = "lose";
       }
       model.put("hangman", hangman);
       model.put("gameEndMsg", gameEndMsg);
       model.put("guessMsg", guessMsg);
       model.put("hangmanImg", hangmanImg);
       model.put("hangmanWord", hangman.getProgress());
       model.put("alreadyGuessed", hangman.getGuesses());
       model.put("numGuesses", hangman.mMAX_COUNTER - hangman.getGameEndCounter());
       model.put("template", "templates/hangman.vtl");
       return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
