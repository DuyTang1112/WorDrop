package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;

import AdapterClass.GameAdapter;
import GameState.State;
import GameState.WelcomeState;
import Manager.StateManager;

public class WorDropGame extends Game {
    private SpriteBatch batch;
    private GameAdapter gameAdapter;
    private StateManager stateManager;

    public WorDropGame(GameAdapter ga) {
        gameAdapter = ga;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        stateManager = new StateManager();
        stateManager.push(new WelcomeState(this));
        this.setScreen(stateManager.peek());
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
        //Gdx.input.setCatchBackKey(true); // void the back key on android
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        super.render();// call the render() from the current screen
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public GameAdapter getGameAdapter() {
        return gameAdapter;
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}
