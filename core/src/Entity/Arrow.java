package Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.WorDropGame;

import java.util.Arrays;


/**
 * Created by Duy Tang on 11/5/2017.
 */

public class Arrow implements Entity {
    Texture arrow;
    Rectangle arrowRect;
    WorDropGame gameInstance;

    public Arrow(WorDropGame ga){
        gameInstance=ga;
        //arrow set up
        arrow = new Texture("image\\arrow.png");
        arrowRect = new Rectangle();
        arrowRect.setWidth(gameInstance.WIDTH / 15);
        arrowRect.setHeight(gameInstance.HEIGHT / 11);
        arrowRect.setX(10);
        //arrowRect.setY(gameInstance.HEIGHT * 8 / 9 - arrowRect.getHeight()*7);
        arrowRect.setY(gameInstance.HEIGHT * 8 / 9 - arrowRect.getHeight());
        gameInstance.arrowVelocity = new Vector2(500, 0);
    }
    @Override
    public void update(float dt) {
        arrowRect.setX(arrowRect.x + gameInstance.arrowVelocity.x * dt);
        if (arrowRect.x <= 0) {
            gameInstance.arrowVelocity.x = Math.abs(gameInstance.arrowVelocity.x);
        } else if (arrowRect.x + arrowRect.getWidth() >= gameInstance.WIDTH) {
            gameInstance.arrowVelocity.x = -Math.abs(gameInstance.arrowVelocity.x);
        }
    }

    @Override
    public void draw(SpriteBatch sb, float dt) {
        sb.draw(arrow, arrowRect.x, arrowRect.y, arrowRect.getWidth(), arrowRect.getHeight());
        update(dt);
    }

    @Override
    public void dispose() {
        arrow.dispose();
    }
    public float getX(){
        return arrowRect.x;
    }
    public float getY(){
        return arrowRect.y;
    }
}
