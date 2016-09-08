import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Hangman{
  private String mAnswer = "";
  private String[] mAnswerPool_Easy = {"hangman", "answer", "password", "random", "water"};
  // private String[] mAnswerPool_Medium = {"cakemonger", "hippocampus", "smorgasbord", "lumberjack", "astronaut"};
  // private String[] mAnswerPool_Hard = {"avuncular", "loquacious", "eschatological", "rhythm", "xenoanalysis"};
  //private String[] mAnswerPool_Nouns = {"answer", "water", "hippocampus"};
  Map<Integer, String[]> mAnswerPool_Nouns = new HashMap();
  Map<Integer, String[]> mAnswerPool_Verbs = new HashMap();
  String[] mAnswerPool_Prepositions = {"upon the", "excepting the", "save the", "within the", "via the"};
  private String[] mProgress;
  private int mGameEndCounter;
  public final int mMAX_COUNTER = 6;
  private List<String> mAlreadyGuessed= new ArrayList<String>();


  public Hangman(String answer){
    mAnswer = answer;
    initializeProgress();
    mGameEndCounter = 0;
  }
  public Hangman(){
    Random random = new Random();
    mAnswer = mAnswerPool_Easy[random.nextInt(mAnswerPool_Easy.length)];
    initializeProgress();
    mGameEndCounter = 0;
  }
  public Hangman(int difficulty){
    mAnswerPool_Nouns.put(1, new String[] {"cakemonger", "hippocampus", "lumberjack", "aqueduct", "smorgasbord"});
    mAnswerPool_Nouns.put(2, new String[] {"oncology", "genome", "cadenza", "block", "knife"});
    mAnswerPool_Nouns.put(3, new String[] {"rhythm", "fox", "jazz", "quiz", "xylophone"});
    mAnswerPool_Verbs.put(1, new String[] {"whisks", "leavens", "defenestrates", "deplanes", "devours"});
    mAnswerPool_Verbs.put(2, new String[] {"coiffed", "plays", "hangs", "enters", "eats"});
    mAnswerPool_Verbs.put(3, new String[] {"zapped", "will box", "is shivving", "was doffing", "shall razz"});
    Random random = new Random();
    switch(difficulty){
      case 1:
        mAnswer = mAnswerPool_Nouns.get(1)[random.nextInt(mAnswerPool_Nouns.get(1).length)];
        mAnswer += " ";
        mAnswer += mAnswerPool_Verbs.get(1)[random.nextInt(mAnswerPool_Nouns.get(1).length)];
        break;
      case 2:
        mAnswer = mAnswerPool_Nouns.get(2)[random.nextInt(mAnswerPool_Nouns.get(2).length)];
        mAnswer += " ";
        mAnswer += mAnswerPool_Verbs.get(2)[random.nextInt(mAnswerPool_Verbs.get(2).length)];
        break;
      case 3:
        mAnswer = mAnswerPool_Nouns.get(3)[random.nextInt(mAnswerPool_Nouns.get(3).length)];
        mAnswer += " ";
        mAnswer += mAnswerPool_Verbs.get(3)[random.nextInt(mAnswerPool_Verbs.get(3).length)];
        mAnswer += " ";
        mAnswer += mAnswerPool_Prepositions[random.nextInt(mAnswerPool_Prepositions.length)];
        mAnswer += " ";
        String possibility = "";
        do{
           possibility = mAnswerPool_Nouns.get(3)[random.nextInt(mAnswerPool_Nouns.get(3).length)];
        }while(mAnswer.contains(possibility));
        mAnswer += possibility;
        break;
      default:
        mAnswer = "however little known the feelings or views of such a man may be on his first entering a neighbourhood this truth is so well fixed in the minds of the surrounding families that he is considered as the rightful property of some one or other of their daughters";
        break;
    }
    initializeProgress();
    mGameEndCounter = 0;
  }

  public void initializeProgress(){
    mProgress = new String[mAnswer.length()];
    for(int i = 0; i < mProgress.length; i++){
      if(mAnswer.charAt(i) == ' '){
        mProgress[i] = " ";
      }
      else {
        mProgress[i] = "_";
      }
    }
  }

  public String getAnswer(){
    return mAnswer;
  }

  public String[] getProgress(){
    return mProgress;
  }

  public List<String> getGuesses(){
    return mAlreadyGuessed;
  }

  public int getGameEndCounter(){
    return mGameEndCounter;
  }

  public boolean isGuessed(String _guess){
    if(mAlreadyGuessed.contains(_guess)){
      mGameEndCounter++;
      return true;
    }
    return false;
  }

  public boolean guess(String _guess){
    mAlreadyGuessed.add(_guess);
    if(mAnswer.contains(_guess)){
      for(int i = 0; i < mAnswer.length(); i++){
        if(String.valueOf(mAnswer.charAt(i)).equals(_guess)){
          mProgress[i] = _guess;
        }
      }
      return true;
    }
    mGameEndCounter++;
    return false;
  }

  public int endTurn(){
    if(Arrays.equals(mAnswer.split(""), mProgress)){
      return 1;
    }
    if(mGameEndCounter >= mMAX_COUNTER){
      return -1;
    }
    return 0;
  }

}
