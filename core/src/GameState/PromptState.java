package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.WorDropGame;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

public class PromptState extends State {
    Texture background;
    Input.TextInputListener listener;
    int lengthword;

    public PromptState(WorDropGame ga) {
        super(ga);
        background = new Texture("image\\background.png");
        listener = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                Gdx.app.error("Input text", text);
                try {
                    int x = Integer.parseInt(text);
                    if (x >= game.WORD_LOWER_BOUND && x <=game.WORD_HIGHER_BOUND) {
                        lengthword = x;
                        String theword=game.getGameAdapter().getAWord(lengthword,game.WORD_LOWER_BOUND,game.WORD_HIGHER_BOUND);
                        Gdx.app.log("Word Selected", theword);
                        // proceed to next state by setting the screen
                        if (theword.length()>0){
                            game.getStateManager().push(new PlayState(game, theword));
                            game.setScreen(game.getStateManager().peek());
                        }
                        else{
                            game.getGameAdapter().showToast("No word found with specified length");
                            Gdx.input.getTextInput(listener, "Input your word length", "", "");
                        }
                    } else {
                        game.getGameAdapter().showToast("Please input a proper word length");
                        Gdx.input.getTextInput(listener, "Input your word length", "", "");
                    }
                } catch (NumberFormatException e) {
                    Gdx.input.getTextInput(listener, "Input your word length", "", "");
                    game.getGameAdapter().showToast("Please input a proper word length");
                }
            }

            @Override
            public void canceled() {
                game.getStateManager().pop();
                game.setScreen(game.getStateManager().peek());
            }
        };
    }

    @Override
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
        game.getBatch().setProjectionMatrix(camera.combined);
        camera.update();
        game.getBatch().draw(background, 0, 0, game.WIDTH, game.HEIGHT);
        game.getBatch().end();
        if (Gdx.input.justTouched()) {
            Gdx.input.getTextInput(listener, "Input your word length", "", "");
        }
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
