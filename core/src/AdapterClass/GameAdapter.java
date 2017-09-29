package AdapterClass;

/**
 * Created by Duy Anh Tang on 9/13/2017.
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
     *
     * @return the voice input result
     */
    String getVoiceInput();

}
