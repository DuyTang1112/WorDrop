package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.WorDropGame;

import Entity.Ball;
import Entity.BucketWagon;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

public class PlayState extends State {
    public static int wordlength;
    public static final Vector2 gravity = new Vector2(0, -800);
    Texture background, heartLife;
   // Ball ball;
    BucketWagon bucketWagon;
    Rectangle pauseBttnRect;
    Texture pauseBttn;


    public PlayState(WorDropGame ga, int wl) {
        super(ga);
        wordlength = wl;
        background = new Texture("image\\background2.jpg");
        heartLife = new Texture("image\\heart.png");
        pauseBttn = new Texture("PlayState\\PauseBttn.png");
        pauseBttnRect = new Rectangle((float) (game.WIDTH * 4.9 / 6), game.HEIGHT - game.WIDTH / 5,
                game.WIDTH / 6, game.WIDTH / 6);
        Gdx.graphics.setContinuousRendering(true);
        bucketWagon = new BucketWagon(5);
        //ball = new Ball("s");
        resetListener();
    }

    @Override
    public void resetListener() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (pauseBttnRect.contains(screenX,game.HEIGHT-screenY)){
                    // set the screen to pause state
                    game.getStateManager().push(new PauseState(game));
                    game.setScreen(game.getStateManager().peek());
                    Gdx.input.setInputProcessor(null);
                }
                else{
                    game.getGameAdapter().voiceInput();
                }
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });
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
        //draw background
        game.getBatch().draw(background, 0, 0, game.WIDTH, game.HEIGHT);
        //draw the heart counter
        game.getBatch().draw(heartLife, game.WIDTH / 20, game.HEIGHT * 8 / 9,
                game.WIDTH / 7, game.WIDTH / 7);
        //draw it 90 degree rotating
        /*game.getBatch().draw(heartLife,game.WIDTH/20,game.HEIGHT*8/9,
                125/2,125/2,
                game.WIDTH/7,game.WIDTH/7,
                1,1,
                -90,
                0,0,
                125,125,
                false,false);*/
        // draw the pause button
        game.getBatch().draw(pauseBttn, pauseBttnRect.getX(), pauseBttnRect.getY(), pauseBttnRect.getWidth(), pauseBttnRect.getHeight());
        //draw the ball
        //ball.draw(game.getBatch(), delta);
        //draw the wagon
        bucketWagon.draw(game.getBatch(), delta);
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
        String result=game.getGameAdapter().getVoiceInput();
        Gdx.app.error("String",result+" blahblah");
        if (result.split(" ").length>1){
            game.getGameAdapter().showToast("Say a letter, not a whole sentence!");
        }
        else{
            Gdx.input.setInputProcessor(null);
            game.getGameAdapter().showToast("You said the letter: "+result.toUpperCase().charAt(0));
            game.getStateManager().push(new PlayState2(result.charAt(0)));
            game.setScreen(game.getStateManager().peek());
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //ball.dispose();
        bucketWagon.dispose();
        pauseBttn.dispose();
        heartLife.dispose();
        background.dispose();
    }

    public class PlayState2 extends State{
        Ball ball;

        public PlayState2(char c) {
            super(PlayState.this.game);
            ball=new Ball(c);
            resetListener();
        }

        @Override
        public void resetListener() {
            Gdx.input.setInputProcessor(new InputAdapter() {
                @Override
                public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                    if (pauseBttnRect.contains(screenX,game.HEIGHT-screenY)){
                        // set the screen to pause state
                        game.getStateManager().push(new PauseState(game));
                        game.setScreen(game.getStateManager().peek());
                        Gdx.input.setInputProcessor(null);
                    }
                    return super.touchDown(screenX, screenY, pointer, button);
                }
            });
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
            //draw background
            game.getBatch().draw(background, 0, 0, game.WIDTH, game.HEIGHT);
            //draw the heart counter
            game.getBatch().draw(heartLife, game.WIDTH / 20, game.HEIGHT * 8 / 9,
                    game.WIDTH / 7, game.WIDTH / 7);
            // draw the pause button
            game.getBatch().draw(pauseBttn, pauseBttnRect.getX(), pauseBttnRect.getY(), pauseBttnRect.getWidth(), pauseBttnRect.getHeight());
            //draw the ball
            ball.draw(game.getBatch(), delta);
            //draw the wagon
            bucketWagon.draw(game.getBatch(), delta);
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
            ball.dispose();
        }
    }
}
