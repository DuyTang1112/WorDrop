package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import AdapterClass.GameAdapter;
import GameState.PlayState;

/**
 * Created by Duy Anh Tang on 9/19/2017.
 */

public class Ball extends Circle implements Entity {
    float startTime;
    Vector2 position, velocity;
    Texture ballImage;
    char currentLetter;
    static float width = Gdx.graphics.getWidth() / 17, height = width;
    BitmapFont font;
    GameAdapter gameAdapter;
    public Ball(char s) {
        super(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 8 / 9 + height / 2, width / 2);
        //initial position
        position = new Vector2(Gdx.graphics.getWidth() / 2 - width / 2, Gdx.graphics.getHeight() * 8 / 9 - height);
        velocity = new Vector2(600, 600);//initial velocity
        currentLetter = s;
        ballImage = new Texture("PlayState\\ball1.png");
        this.startTime = 0;

        font=new BitmapFont();
        font.setColor(Color.YELLOW);
        font.getData().setScale(4);
    }

    public Ball(char s, Vector2 startPos,GameAdapter adapter) {
        super(startPos.x + width / 2, startPos.y + height / 2, width / 2);
        gameAdapter=adapter;
        //initial position
        position = startPos;
        float roll=gameAdapter.getRollOrientation();
        //velocity = new Vector2((roll > .1 || roll < -.1) ? 700 * roll : 0, 0);//Math.random()>0.5?-200:200, 0);//initial velocity
        velocity = new Vector2(0, 0);
        currentLetter = s;
        ballImage = new Texture("PlayState\\ball1.png");
        this.startTime = 0;

        font=new BitmapFont();
        font.setColor(Color.YELLOW);
        font.getData().setScale(4);
    }

    public void reset(char s) {
        currentLetter = s;
        startTime = 0;
    }

    @Override
    public void update(float deltatime) {
        //check if the ball hits any where
         if (y + radius >= Gdx.graphics.getHeight()) {
            velocity.y = -Math.abs(velocity.y);
        }

        if (x - radius <= 0) {
            velocity.x = Math.abs(velocity.x*8/10);
        } else if (x + radius >= Gdx.graphics.getWidth()) {
            velocity.x = -Math.abs(velocity.x*8/10);
        }
        //update velocity
        velocity.y += PlayState.gravity.y * (deltatime);
        velocity.x += PlayState.gravity.x * (deltatime)+ 50*gameAdapter.getRollOrientation();
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
        font.draw(sb,currentLetter+"",x,y+height);
        update(deltatime);

    }

    public void dispose() {
        ballImage.dispose();
    }

}
