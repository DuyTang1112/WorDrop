package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.WorDropGame;

/**
 * Created by Duy Anh Tang on 9/21/2017.
 */

public class PauseState extends State {
    Texture background;
    public PauseState(WorDropGame game){
        super(game);
        background=new Texture("image\\background.png");

    }
    public void resetListener() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.getBatch().begin();
        game.getBatch().draw(background,0,0,game.WIDTH,game.HEIGHT);
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
