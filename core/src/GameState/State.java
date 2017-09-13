package GameState;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.WorDropGame;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

public abstract class State implements Screen {
    WorDropGame game;
    public State(WorDropGame ga){
        game=ga;
    }

    /**
     * Enable any listener in case if turned off
     */
    public abstract void resetListener();

}
