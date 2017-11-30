package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.WorDropGame;

/**
 * Created by Duy Anh Tang on 9/21/2017.
 */

public class PauseState extends State {
    Texture background,backgame,backmain;
    Rectangle backgame_rec,backmain_rec;

    public PauseState(final WorDropGame ga) {
        super(ga);
        background = new Texture("image\\background.png");
        backgame=new Texture("buttonImage\\backgame_up.png");
        backmain = new Texture("buttonImage\\mainmenubutton.png");
        backgame_rec=new Rectangle(game.WIDTH/2-game.WIDTH/4,game.HEIGHT*4/10,
                game.WIDTH/2, (float)(game.WIDTH/2)*40/172);
        backmain_rec=new Rectangle(game.WIDTH/2-game.WIDTH/4,game.HEIGHT*6/10,
                game.WIDTH/2, (float)(game.WIDTH/2)*40/151);
        resetListener();
    }

    public void resetListener() {
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                Gdx.app.error("touched in", x + "/" + y);
                if (backgame_rec.contains(x, game.HEIGHT - y)) {
                    game.getStateManager().pop();
                    game.setScreen(game.getStateManager().peek());
                }
                else if (backmain_rec.contains(x, game.HEIGHT - y)) {
                    while (!(game.getStateManager().peek() instanceof  WelcomeState)){
                        game.getStateManager().pop();
                    }
                    game.setScreen(game.getStateManager().peek());
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.getBatch().begin();
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().draw(background, 0, 0, game.WIDTH, game.HEIGHT);
        game.getBatch().draw(backgame,backgame_rec.getX(),backgame_rec.getY(),backgame_rec.getWidth(),backgame_rec.getHeight());
        game.getBatch().draw(backmain,backmain_rec.getX(),backmain_rec.getY(),backmain_rec.getWidth(),backmain_rec.getHeight());
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
        backgame.dispose();
        backmain.dispose();
    }
}
