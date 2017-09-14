package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.WorDropGame;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

public class PlayState extends State {
    Texture background,heartLife;
    int wordlength;
    public PlayState(WorDropGame ga,int wl){
        super(ga);
        wordlength=wl;
        background=new Texture("image\\background.png");
        heartLife=new Texture("image\\heart.png");
        Gdx.graphics.setContinuousRendering(true);
    }

    @Override
    public void resetListener() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        game.getBatch().draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.getBatch().draw(heartLife,Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()*8/9,
                Gdx.graphics.getWidth()/7,Gdx.graphics.getWidth()/7);
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

    }
}
