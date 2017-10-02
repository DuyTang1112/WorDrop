package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import GameState.PlayState;

/**
 * Created by Duy Anh Tang on 9/19/2017.
 */

public class Ball extends Circle implements Entity {
    float startTime;
    Vector2 position, velocity;
    Texture ballImage;
    char currentLetter;
    static float width = Gdx.graphics.getWidth() / 12, height = width;

    public Ball(char s) {
        super(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 8 / 9 + height / 2, width / 2);
        //initial position
        position = new Vector2(Gdx.graphics.getWidth() / 2 - width / 2, Gdx.graphics.getHeight() * 8 / 9 - height);
        velocity = new Vector2(600, 600);//initial velocity
        currentLetter = s;
        ballImage = new Texture("PlayState\\ball1.png");
        this.startTime = 0;

    }

    public Ball(char s, Vector2 startPos, float roll) {
        super(startPos.x + width / 2, startPos.y + height / 2, width / 2);
        //initial position
        position = startPos;
        velocity = new Vector2((roll > .1 || roll < -.1) ? 700 * roll : 0, 0);//Math.random()>0.5?-200:200, 0);//initial velocity
        currentLetter = s;
        ballImage = new Texture("PlayState\\ball1.png");
        this.startTime = 0;
    }

    public void reset(char s) {
        currentLetter = s;
        startTime = 0;
    }

    @Override
    public void update(float deltatime) {
        //check if the ball hits any where
        if (y - radius <= 0) {
            velocity.y = Math.abs(velocity.y);
        } else if (y + radius >= Gdx.graphics.getHeight()) {
            velocity.y = -Math.abs(velocity.y);
        }

        if (x - radius <= 0) {
            velocity.x = Math.abs(velocity.x);
        } else if (x + radius >= Gdx.graphics.getWidth()) {
            velocity.x = -Math.abs(velocity.x);
        }
        //update velocity
        velocity.y += PlayState.gravity.y * (deltatime);
        velocity.x += PlayState.gravity.x * (deltatime);
        //update position
        position.y += velocity.y * deltatime;
        position.x += velocity.x * deltatime;
        //update circle
        setX(position.x + radius);
        setY(position.y + radius);
        //debug
        //Gdx.app.error("Velocity", velocity.x+";"+velocity.y );
        //Gdx.app.error("Position",position.x+";"+position.y);
    }

    public void draw(SpriteBatch sb, float deltatime) {
        //deltatime=Math.min(deltatime,1f/45f);
        sb.draw(ballImage, position.x, position.y, width, height);
        update(deltatime);

    }

    public void dispose() {
        ballImage.dispose();
    }

}
