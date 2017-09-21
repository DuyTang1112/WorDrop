package Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import GameState.PlayState;

/**
 * Created by Duy Anh Tang on 9/19/2017.
 */

public class Ball extends Actor{
    private float startTime;
    private Vector2 position,velocity;
    private Texture ballImage;
    private String currentLetter;
    private static float width= Gdx.graphics.getWidth()/10,height=Gdx.graphics.getWidth()/10;
    public Ball(String s){
        //initial position
        position=new Vector2(Gdx.graphics.getWidth()/2-width/2,Gdx.graphics.getHeight()*8/9-height);
        velocity=new Vector2(0,500);//initial velocity
        currentLetter=s;
        ballImage=new Texture("PlayState\\ball1.png");
        this.startTime=0;
    }
    public Ball(String s,Vector2 startPos){
        //initial position
        position=startPos;
        velocity=new Vector2(0,500);//initial velocity
        currentLetter=s;
        ballImage=new Texture("PlayState\\ball1.png");
        this.startTime=0;
    }
    public void reset(String s){
        currentLetter=s;
        startTime=0;
    }
    public void draw(SpriteBatch sb,float deltatime){
        if (position.y<0) {
            position.y=Gdx.graphics.getHeight()*8/9-height;
            velocity=new Vector2(0,200);
        }
        sb.draw(ballImage,position.x,position.y,width,height);
        //update velocity
        velocity.y+=PlayState.gravity.y*(deltatime);
        velocity.x+=PlayState.gravity.x*(deltatime);
        //update position
        position.y+= velocity.y*deltatime;
        position.x+=velocity.x*deltatime;
        Gdx.app.error("Velocity",velocity.y+"");

    }
    public void dispose(){
        ballImage.dispose();
    }

}
