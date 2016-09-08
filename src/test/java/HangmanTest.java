import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class HangmanTest{

  @Test
  public void newHangman_instantiatesCorrectly() {
    Hangman hangman = new Hangman();
    assertEquals(true, hangman instanceof Hangman);
  }

  @Test
  public void getAnswer_returnsAnswer_String() {
    Hangman hangman = new Hangman();
    assertEquals(true, !hangman.getAnswer().isEmpty());
  }

  @Test
  public void isGuessed_firstGuessReturnFalse_boolean() {
    Hangman hangman = new Hangman();
    boolean expectedOutput = false;
    assertEquals(expectedOutput, hangman.isGuessed("a"));
  }

  @Test
  public void guess_guessSingleLetterNotAlreadyGuessedNotInAnswer_boolean() {
    Hangman hangman = new Hangman();
    boolean expectedOutput = false;
    assertEquals(expectedOutput, hangman.guess("q"));
  }

  @Test
  public void guess_guessSingleLetterNotAlreadyGuessedInAnswer_boolean() {
    Hangman hangman = new Hangman();
    boolean expectedOutput = true;
    assertEquals(expectedOutput, hangman.guess("a"));
  }

  @Test
  public void isGuessed_guessSingleLetterAlreadyGuessed_boolean() {
    Hangman hangman = new Hangman();
    boolean expectedOutput = true;
    String[] guesses = {"q"};
    for(int i = 0; i < guesses.length; i++){
      hangman.guess(guesses[i]);
    }
    assertEquals(expectedOutput, hangman.isGuessed("q"));
  }

  @Test
  public void getProgress_returnsProgress_StringArray() {
    Hangman hangman = new Hangman("hangman");
    String[] expectedOutput = {"_", "_", "_", "_", "_", "_", "_"};
    assertEquals(expectedOutput, hangman.getProgress());
  }

  @Test
  public void getProgress_progressUpdatedOnRightGuess_StringArray() {
    Hangman hangman = new Hangman("hangman");
    hangman.guess("a");
    String[] expectedOutput = {"_", "a", "_", "_", "_", "a", "_"};
    assertEquals(expectedOutput, hangman.getProgress());
  }

  @Test
  public void getGameEndCounter_returnsGameEndCounter_int() {
    Hangman hangman = new Hangman();
    int expectedOutput = 0;
    assertEquals(expectedOutput, hangman.getGameEndCounter());
  }

  @Test
  public void getGameEndCounter_incrementsOnWrongGuess_int() {
    Hangman hangman = new Hangman();
    hangman.guess("q");
    int expectedOutput = 1;
    assertEquals(expectedOutput, hangman.getGameEndCounter());
  }

  @Test
  public void getGameEndCounter_incrementsOnAlreadyGuessed_int() {
    Hangman hangman = new Hangman();
    hangman.guess("a");
    hangman.isGuessed("a");
    int expectedOutput = 1;
    assertEquals(expectedOutput, hangman.getGameEndCounter());
  }

  @Test
  public void endTurn_returns0toContinue_int() {
    Hangman hangman = new Hangman();
    int expectedOutput = 0;
    assertEquals(expectedOutput, hangman.endTurn());
  }

  @Test
  public void endTurn_returns1toWin_int() {
    Hangman hangman = new Hangman("a");
    hangman.guess("a");
    int expectedOutput = 1;
    assertEquals(expectedOutput, hangman.endTurn());
  }

  @Test
  public void endTurn_returnsNeg1toWin_int() {
    Hangman hangman = new Hangman("a");
    for(int i = 0; i < 6; i++){
      hangman.guess("z");
    }
    int expectedOutput = -1;
    assertEquals(expectedOutput, hangman.endTurn());
  }

}
