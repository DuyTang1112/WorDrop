package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Duy Anh Tang on 9/21/2017.
 */

public class BucketWagon implements Entity {
    String theword;
    Animation<TextureRegion> wheelsAnimation;
    float wheelDistance;
    Rectangle wheelRect;
    float velocity, stateTime;
    int numBuckets;
    Rectangle[] bucketPos;
    Texture bucketImg;
    char[] hit;
    BitmapFont font ;

    public BucketWagon(int numBuckets,String theword) {
        this(100, numBuckets,theword);
    }

    public BucketWagon(float velocity, int numBuckets,String theword) {
        font = new BitmapFont();
        font.setColor(com.badlogic.gdx.graphics.Color.RED);
        font.getData().setScale(5);

        this.theword=theword;
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

        // bucket list set up
        bucketImg = new Texture("image\\bucket.png");
        this.numBuckets = numBuckets;
        bucketPos = new Rectangle[numBuckets];
        hit = new char[numBuckets];
        Arrays.fill(hit, '_');
        bucketPos[0] = new Rectangle(wheelRect.x, wheelRect.getHeight(), //position of the first one
                Gdx.graphics.getWidth() / 9, Gdx.graphics.getWidth() / 9); //width and height of each bucket
        bucketPos[0].setX(bucketPos[0].x - (bucketPos[0].getWidth() * (numBuckets - 1) / 2 - (wheelDistance / 2)));
        //setting position for consecutive buckets
        for (int i = 1; i < bucketPos.length; i++) {
            bucketPos[i] = new Rectangle(bucketPos[i - 1].getX() + bucketPos[i - 1].getWidth(), bucketPos[i - 1].getY(),
                    bucketPos[i - 1].getWidth(), bucketPos[i - 1].getHeight());
        }
    }

    @Override
    public void update(float dt) {
        //new position for wheels
        wheelRect.x += velocity * dt;
        //new position for buckets
        for (Rectangle i : bucketPos) {
            i.setX(i.getX() + velocity * dt);
        }
        //check if the wagon goes out of screen
        if (wheelRect.x + wheelDistance + wheelRect.getWidth() >= Gdx.graphics.getWidth() || wheelRect.x < 0) {
            velocity = -velocity;
        }



    }

    @Override
    public void draw(SpriteBatch sb, float dt) {
        dt = Math.min(dt, 1f / 45f);
        stateTime += dt;
        TextureRegion temp = wheelsAnimation.getKeyFrame(stateTime, true);
        //draw the wheels
        sb.draw(temp, wheelRect.x, wheelRect.y, wheelRect.getWidth(), wheelRect.getHeight());
        sb.draw(temp, wheelRect.x + wheelDistance, wheelRect.y, wheelRect.getWidth(), wheelRect.getHeight());
        // draw the buckets and the letters above it
        for (int i=0;i<bucketPos.length;i++) {
            sb.draw(bucketImg, bucketPos[i].x, bucketPos[i].y, bucketPos[i].getWidth(), bucketPos[i].getHeight());
            font.draw(sb,""+hit[i],
                    bucketPos[i].x+bucketPos[i].getWidth()/3,
                    bucketPos[i].y+bucketPos[i].getHeight());
        }
        update(dt);
    }

    @Override
    public void dispose() {
        bucketImg.dispose();
        font.dispose();

    }

    public Rectangle[] getBucketPos() {
        return bucketPos;
    }

    /**
     *
     * @param i the position of the bucket
     * @param letter the current letter the ball hits with
     * @return true if the order the letter is hitting is correct, false if not
     */
    public boolean checkAndSetHit(int i, char letter){
        if (theword.charAt(i)==letter){
            hit[i]=letter;
            Gdx.app.log("Status","hit");
            return true;
        }
        else{
            Gdx.app.log("Status","no hit");
            return false;
        }
    }
    public float getBucketBound(){
        return bucketPos[0].y+bucketPos[0].getHeight();
    }

    public boolean gameIsFinished(){
        for (char c: hit){
            if (c=='_'){
                return false;
            }
        }
        return true;
    }
}
