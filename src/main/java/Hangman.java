import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Hangman{
  private String mAnswer = "";
  private String[] mAnswerPool = {"hangman", "manswer", "cakemonger", "oxyphenbutazone", "hippocampus", "smorgasbord", "avuncular"};
  private String[] mProgress;
  private int mGameEndCounter;
  public final int mMAX_COUNTER = 6;
  private List<String> mAlreadyGuessed= new ArrayList<String>();

  public Hangman(){
    Random random = new Random();
    mAnswer = mAnswerPool[random.nextInt(mAnswerPool.length)];
    mProgress = new String[mAnswer.length()];
    for(int i = 0; i < mProgress.length; i++){
      mProgress[i] = "_";
    }
    mGameEndCounter = 0;
  }

  public Hangman(String answer){
    mAnswer = answer;
    mProgress = new String[mAnswer.length()];
    for(int i = 0; i < mProgress.length; i++){
      mProgress[i] = "_";
    }
    mGameEndCounter = 0;
  }

  public String getAnswer(){
    return mAnswer;
  }

  public String[] getProgress(){
    return mProgress;
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
