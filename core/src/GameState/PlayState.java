package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.WorDropGame;

/**
 * Created by Duy Anh Tang on 9/13/2017.
 */

public class PlayState extends State {
    Texture background,heartLife;
    int wordlength;
    Animation<TextureRegion> wheelsAnimation;

    Rectangle wheelRect;
    float wheelDistance;
    float velocity;
    Texture whole;
    float stateTime=0;
    public PlayState(WorDropGame ga,int wl){
        super(ga);
        wordlength=wl;
        background=new Texture("image\\background2.jpg");
        heartLife=new Texture("image\\heart.png");
        Gdx.graphics.setContinuousRendering(true);
        whole=new Texture(Gdx.files.internal("PlayState\\wheels.png"));

        TextureRegion[][] tmp= TextureRegion.split(whole,512/16,256/8);
        TextureRegion[] frames= new TextureRegion[16*8];
        int index=0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        wheelsAnimation = new Animation<TextureRegion>(1f/10f, frames);
        stateTime=0f;
        wheelRect =new Rectangle(0,20,Gdx.graphics.getWidth()/10,Gdx.graphics.getWidth()/10);
        velocity=5;// velocity of the bucket wagon
        wheelDistance=Gdx.graphics.getWidth()/4;
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
        stateTime+=Gdx.graphics.getDeltaTime();
        TextureRegion temp=wheelsAnimation.getKeyFrame(stateTime,true);
        game.getBatch().begin();
        game.getBatch().draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.getBatch().draw(heartLife,Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()*8/9,
                Gdx.graphics.getWidth()/7,Gdx.graphics.getWidth()/7);
        game.getBatch().draw(temp, wheelRect.x, wheelRect.y, wheelRect.getWidth(), wheelRect.getHeight());
        game.getBatch().draw(temp, wheelRect.x+wheelDistance, wheelRect.y, wheelRect.getWidth(), wheelRect.getHeight());
        wheelRect.x+=velocity;
        if (wheelRect.x+wheelDistance+wheelRect.getWidth()>=Gdx.graphics.getWidth() || wheelRect.x<0){
            velocity=-velocity;
        }
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
