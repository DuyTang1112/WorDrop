package GameState;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.WorDropGame;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

public class WelcomeState extends State {
    Texture title, background, playButton;
    Rectangle playBttnArea;

    public WelcomeState(WorDropGame g) {
        super(g);
        title = new Texture("image\\gamelogo.png");
        background = new Texture("image\\background.png");
        playButton = new Texture("image\\PlayButton.png");
        playBttnArea = new Rectangle();
        playBttnArea.setX(game.WIDTH / 2 - game.WIDTH / 8);
        playBttnArea.setY(game.HEIGHT * 2 / 4);
        playBttnArea.setWidth(game.WIDTH / 4);
        playBttnArea.setHeight(game.WIDTH / 4);
        resetListener();
    }

    @Override
    public void resetListener() {
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                Gdx.app.error("PlayButton", playBttnArea.getX() + "/" + playBttnArea.getY());
                //Gdx.app.error("Delta time", ""+Gdx.graphics.getDeltaTime());

                Gdx.app.error("touched in", x + "/" + y);
                if (playBttnArea.contains(x, game.HEIGHT - y)) {
                    Gdx.app.error("PLAYED!!!", "right now");
                    game.getStateManager().push(new PromptState(game));
                    game.setScreen(game.getStateManager().peek());
                    Gdx.input.setInputProcessor(null);// remove the listener
                }
                return super.touchDown(x, y, pointer, button);
            }

            @Override
            public boolean longPress(float x, float y) {
                return super.longPress(x, y);
            }
        }));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.getBatch().begin();
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().draw(background, 0, 0, game.WIDTH, game.HEIGHT);

        game.getBatch().draw(title, game.WIDTH / 2 - game.WIDTH * 3 / 8,
                game.HEIGHT * 3 / 4, game.WIDTH * 3 / 4, game.HEIGHT / 7);// Title has width=screenwidth/2
        game.getBatch().draw(playButton, playBttnArea.getX(), playBttnArea.getY(), playBttnArea.getWidth(), playBttnArea.getHeight());
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
        title.dispose();
        background.dispose();
        playButton.dispose();
    }


}
