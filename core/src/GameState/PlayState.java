package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
    private static String theWord;
    public static final Vector2 gravity = new Vector2(0, -800);
    Texture background, heartLife, arrow;
    BucketWagon bucketWagon;
    Rectangle pauseBttnRect, arrowRect;
    Texture pauseBttn;

    public PlayState(WorDropGame ga, String word) {
        super(ga);
        wordlength = word.length();
        theWord = word;

        /*setting up the graphics components*/
        background = new Texture("image\\background2.jpg");
        heartLife = new Texture("image\\heart.png");
        //pause button
        pauseBttn = new Texture("PlayState\\PauseBttn2.png");
        pauseBttnRect = new Rectangle((float) (game.WIDTH * 4.9 / 6), game.HEIGHT - game.WIDTH / 6,
                game.WIDTH / 6, game.WIDTH / 6);
        //arrow set up
        arrow = new Texture("image\\arrow.png");
        arrowRect = new Rectangle();
        arrowRect.setWidth(game.WIDTH / 15);
        arrowRect.setHeight(game.HEIGHT / 11);
        arrowRect.setX(10);
        arrowRect.setY(game.HEIGHT * 8 / 9 - arrowRect.getHeight()*7);
        game.arrowVelocity = new Vector2(500, 0);

        Gdx.graphics.setContinuousRendering(true);

        bucketWagon = new BucketWagon(wordlength, theWord);
        //ball = new Ball("s");
        resetListener();
        game.getGameAdapter().showToast("CLICK THE SCREEN!");
    }

    @Override
    public void resetListener() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (pauseBttnRect.contains(screenX, game.HEIGHT - screenY)) {
                    // set the screen to pause state
                    game.getStateManager().push(new PauseState(game));
                    game.setScreen(game.getStateManager().peek());
                    Gdx.input.setInputProcessor(null);
                } else {
                    //getting voice input
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
        //draw the arrow
        game.getBatch().draw(arrow, arrowRect.x, arrowRect.y, arrowRect.getWidth(), arrowRect.getHeight());
        //draw the wagon
        bucketWagon.draw(game.getBatch(), delta);
        game.getBatch().end();
        Gdx.app.error("Roll:", game.getGameAdapter().getRollOrientation() + " /");
        //update the arrow position
        arrowRect.setX(arrowRect.x + game.arrowVelocity.x * delta);
        if (arrowRect.x <= 0) {
            game.arrowVelocity.x = Math.abs(game.arrowVelocity.x);
        } else if (arrowRect.x + arrowRect.getWidth() >= game.WIDTH) {
            game.arrowVelocity.x = -Math.abs(game.arrowVelocity.x);
        }
        /*if (arrowRect.x<=0||arrowRect.x+arrowRect.getWidth()>=game.WIDTH){
            game.arrowVelocity.x=-game.arrowVelocity.x;
        }*/
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    /**
     * Getting the voice input
     */
    @Override
    public void resume() {
        String result = game.getGameAdapter().getVoiceInput().toLowerCase();
        Gdx.app.error("String", result);
        if (result.length() == 1 && result.charAt(0) >= 'a') {
            Gdx.input.setInputProcessor(null);
            game.getGameAdapter().showToast("You said the letter: " + result.toUpperCase().charAt(0));
            game.getStateManager().push(new PlayState2(result.charAt(0), new Vector2(arrowRect.x, arrowRect.y)));
            game.setScreen(game.getStateManager().peek());
        } else {
            game.getGameAdapter().showToast("SAY AGAIN!!");
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
        arrow.dispose();
    }

    /**
     * Inner class because of reasons
     */
    public class PlayState2 extends State {
        Ball ball;
        char currentLetter;

        public PlayState2(char c, Vector2 pos) {
            super(PlayState.this.game);
            ball = new Ball(c, pos, game.getGameAdapter().getRollOrientation());
            currentLetter = c;
            resetListener();
        }

        @Override
        public void resetListener() {
            Gdx.input.setInputProcessor(new InputAdapter() {
                @Override
                public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                    if (pauseBttnRect.contains(screenX, game.HEIGHT - screenY)) {
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

            Gdx.app.error("Roll:", game.getGameAdapter().getRollOrientation() + " /");
            if (ball.y <= bucketWagon.getBucketBound() && ballHitsWagon()) {
                //remove this state
                game.getStateManager().pop();
                game.setScreen(game.getStateManager().peek());
            }
            else if(ball.y<0){
                //remove this state
                game.getStateManager().pop();
                game.setScreen(game.getStateManager().peek());
            }
        }

        public boolean ballHitsWagon() {
            Rectangle[] wagon = bucketWagon.getBucketPos();
            for (int i = 0; i < wagon.length; i++) {
                if (wagon[i].contains(new Vector2(ball.x,ball.y))) {
                    if (!bucketWagon.checkAndSetHit(i, currentLetter)) {
                        game.getGameAdapter().showToast("Not the right position!");
                    }
                    return true;
                }
            }
            return false;
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
