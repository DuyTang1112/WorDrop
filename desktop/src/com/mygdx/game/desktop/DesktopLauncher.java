package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.WorDropGame;

import AdapterClass.GameAdapter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=480;
		config.height=800;
		new LwjglApplication(new WorDropGame(new GameAdapter() {
			@Override
			public void showToast(String s) {

			}

			@Override
			public void voiceInput() {

			}

			@Override
			public String getVoiceInput() {
				return null;
			}

			@Override
			public float getRollOrientation() {
				return 0;
			}

			@Override
			public String getAWord(int length, int lower, int higher) {
				return null;
			}
		}), config);
	}
}
