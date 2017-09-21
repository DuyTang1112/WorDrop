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

public class Ball extends Circle{
    float startTime;
    Vector2 position, velocity;
    Texture ballImage;
    String currentLetter;
    static float width = Gdx.graphics.getWidth() / 10, height = Gdx.graphics.getWidth() / 10;

    public Ball(String s) {
        super(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 8 / 9 + height/2,width/2);
        //initial position
        position = new Vector2(Gdx.graphics.getWidth() / 2 - width / 2, Gdx.graphics.getHeight() * 8 / 9 - height);
        velocity = new Vector2(600, 600);//initial velocity
        currentLetter = s;
        ballImage = new Texture("PlayState\\ball1.png");
        this.startTime = 0;

    }

    public Ball(String s, Vector2 startPos) {
        //initial position
        position = startPos;
        velocity = new Vector2(0, 500);//initial velocity
        currentLetter = s;
        ballImage = new Texture("PlayState\\ball1.png");
        this.startTime = 0;
    }

    public void reset(String s) {
        currentLetter = s;
        startTime = 0;
    }

    public void draw(SpriteBatch sb, float deltatime) {
        if (y-radius <= 0 || y+radius>=Gdx.graphics.getHeight()) {
            velocity.y=-velocity.y;
        }
        if (x+radius>=Gdx.graphics.getWidth()|| x-radius<=0){
            velocity.x=-velocity.x;
        }
        deltatime=Math.min(deltatime,1f/45f);
        sb.draw(ballImage, position.x, position.y, width, height);
        //update velocity
        velocity.y += PlayState.gravity.y * (deltatime);
        velocity.x += PlayState.gravity.x * (deltatime);
        //update position
        position.y += velocity.y * deltatime;
        position.x += velocity.x * deltatime;
        //update circle
        setX(position.x+radius);
        setY(position.y+radius);
        //debug
        Gdx.app.error("Velocity", velocity.x+";"+velocity.y );
        Gdx.app.error("Position",position.x+";"+position.y);

    }

    public void dispose() {
        ballImage.dispose();
    }

}
