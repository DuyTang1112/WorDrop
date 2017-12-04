package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.WorDropGame;

import Entity.Arrow;
import Entity.Ball;
import Entity.BucketWagon;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

public class PlayState extends State {
    public static int wordlength;
    private static String theWord;
    public static final Vector2 gravity = new Vector2(0, -game.HEIGHT/10);
    Texture background, heartLife;
    BucketWagon bucketWagon;
    Rectangle pauseBttnRect, arrowRect;
    Texture pauseBttn;
    Arrow arrow;

    public PlayState(WorDropGame ga, String word) {
        super(ga);
        wordlength = word.length();
        theWord = word;

        /*setting up the graphics components*/
        background = new Texture("image\\background2.jpg");
        heartLife = new Texture("image\\heart.png");
        //pause button
        pauseBttn = new Texture("PlayState\\PauseBttn.png");
        pauseBttnRect = new Rectangle((float) (game.WIDTH * 6.9 / 8), game.HEIGHT - game.WIDTH / 8-20,
                game.WIDTH / 8, game.WIDTH / 8);
        //arrow set up
        arrow=new Arrow(game);

        Gdx.graphics.setContinuousRendering(true);

        //bucket wagon set up
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
        arrow.draw(game.getBatch(),delta);

        //draw the wagon
        bucketWagon.draw(game.getBatch(), delta);
        game.getBatch().end();
        Gdx.app.error("Roll:", game.getGameAdapter().getRollOrientation() + " /");

        if (bucketWagon.gameIsFinished()){
            //game.getStateManager().pop();//Playstate 2 is popped
            game.getStateManager().pop();// Playstate is popped
            game.getStateManager().pop();// PromptState is popped
            game.setScreen(game.getStateManager().peek());// back to WelcomeState
            game.getGameAdapter().showToast("Congratulations! You finished the game");
        }

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
            game.getStateManager().push(new PlayState2(result.charAt(0), new Vector2(arrow.getX(), arrow.getY())));
            game.setScreen(game.getStateManager().peek());
        } else {
            //game.getGameAdapter().showToast("SAY AGAIN!!");
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
            ball = new Ball(c, pos, game.getGameAdapter());
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

        /**
         * Check if the ball hits one of the bucket, set the letter to the bucket if it does
         * @return
         */
        public boolean ballHitsWagon() {
            Rectangle[] wagon = bucketWagon.getBucketPos();
            for (int i = 0; i < wagon.length; i++) {
                if (wagon[i].contains(new Vector2(ball.x,ball.y))) {
                    int status=bucketWagon.checkAndSetHit(i, currentLetter);
                    if (status==0) {
                        game.getGameAdapter().showToast("Not the right position!");
                    }
                    else if(status ==-1){
                        game.getGameAdapter().showToast("Letter is not in the word!");
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
