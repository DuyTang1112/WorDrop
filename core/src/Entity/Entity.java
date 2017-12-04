package Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Duy Anh Tang on 9/29/2017.
 */


/**
 * An interface to represent dynamic entities
 */
public interface Entity {
    /* Velocity is pixel/s */
    /**
     * Update the entity position
     */
    void update(float dt);

    /**
     * Draw the entity (must call update() after )
     * @param sb- aka the canvas
     * @param dt- interval time between frame
     */
    void draw(SpriteBatch sb,float dt);

    /**
     * dispose the texture
     */
    void dispose();

}
