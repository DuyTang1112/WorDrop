package GameState;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.WorDropGame;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

public abstract class State implements Screen {
    WorDropGame game;
    OrthographicCamera camera;
    public State(WorDropGame ga){

        game=ga;
        camera=new OrthographicCamera(game.WIDTH,game.HEIGHT);
        camera.translate(camera.viewportWidth/2,camera.viewportHeight/2);
    }

    /**
     * Reset input listener in case if turned off
     */
    public abstract void resetListener();

}
