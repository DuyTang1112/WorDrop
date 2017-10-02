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
     * @param s- String to be shown
     */
    void showToast(String s);

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
}
