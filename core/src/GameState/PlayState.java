package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
    public static final Vector2 gravity= new Vector2(0,-400);
    Texture background,heartLife;
    Ball ball;
    BucketWagon bucketWagon;
    Rectangle pauseBttnRect;
    Texture pauseBttn;
    public PlayState(WorDropGame ga,int wl){
        super(ga);
        wordlength=wl;
        background=new Texture("image\\background2.jpg");
        heartLife=new Texture("image\\heart.png");
        pauseBttn=new Texture("PlayState\\PauseBttn.png");
        pauseBttnRect=new Rectangle((float) (Gdx.graphics.getWidth()*4.9/6),Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/5,
                Gdx.graphics.getWidth()/6,Gdx.graphics.getWidth()/6);
        Gdx.graphics.setContinuousRendering(true);
        bucketWagon=new BucketWagon(5);
        ball=new Ball("s");
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
        game.getBatch().begin();
        //draw background
        game.getBatch().draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //draw the heart counter
        game.getBatch().draw(heartLife,Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()*8/9,
                Gdx.graphics.getWidth()/7,Gdx.graphics.getWidth()/7);
        // draw the pause button
        game.getBatch().draw(pauseBttn,pauseBttnRect.getX(),pauseBttnRect.getY(),pauseBttnRect.getWidth(),pauseBttnRect.getHeight());
        //draw the ball
        ball.draw(game.getBatch(),delta);
        //draw the wagon
        bucketWagon.draw(game.getBatch(),delta);
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
        bucketWagon.dispose();
        pauseBttn.dispose();
        heartLife.dispose();
        background.dispose();
    }
}
