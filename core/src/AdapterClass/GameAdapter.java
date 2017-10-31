package AdapterClass;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

/**
 * An interface to use native Android components inside libgdx module
 */
public interface GameAdapter {
    /**
     * Show a toast with message
     * @param message- String to be shown
     */
    void showToast(String message);

    /**
     * Starting a voice input activity
     */
    void voiceInput();

    /**
     *  Return voice input result after calling voiceInput()
     * @return the string result
     */
    String getVoiceInput();

    /**
     * Get the orientation of the phone
     * @return the roll orientation
     */
    float getRollOrientation();

    /**
     *
     * @param length input from user
     * @param lower lower bound defined by the game
     * @param higher higher bound defined by the game
     * @return a random word with specified length, "" if can't find any
     */
    String getAWord(int length,int lower, int higher);

}
