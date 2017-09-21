package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Duy Anh Tang on 9/21/2017.
 */

public class BucketWagon {
    Animation<TextureRegion> wheelsAnimation;
    float wheelDistance;
    Rectangle wheelRect;
    float velocity, stateTime;

    public BucketWagon(float velocity) {
        //setting up wheels animation
        Texture whole = new Texture(Gdx.files.internal("PlayState\\wheels.png"));
        TextureRegion[][] tmp = TextureRegion.split(whole, 512 / 16, 256 / 8);
        TextureRegion[] frames = new TextureRegion[16 * 8];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        wheelsAnimation = new Animation<TextureRegion>(1f / 10f, frames);
        stateTime = 0f;
        wheelRect = new Rectangle(0, 20, Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 10);
        this.velocity = velocity;// velocity of the bucket wagon
        wheelDistance = Gdx.graphics.getWidth() / 4;

    }

    public void draw(SpriteBatch sb, float dt) {
        dt=Math.min(dt,1f/45f);
        stateTime += dt;
        TextureRegion temp = wheelsAnimation.getKeyFrame(stateTime, true);
        //draw the wheels
        sb.draw(temp, wheelRect.x, wheelRect.y, wheelRect.getWidth(), wheelRect.getHeight());
        sb.draw(temp, wheelRect.x + wheelDistance, wheelRect.y, wheelRect.getWidth(), wheelRect.getHeight());
        //new position
        wheelRect.x += velocity;
        //check if the wagon goes out of screen
        if (wheelRect.x + wheelDistance + wheelRect.getWidth() >= Gdx.graphics.getWidth() || wheelRect.x < 0) {
            velocity = -velocity;
        }

    }

    public void dispose() {

    }
}
