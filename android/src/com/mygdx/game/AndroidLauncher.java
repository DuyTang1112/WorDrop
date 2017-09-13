package com.mygdx.game;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.WorDropGame;

import AdapterClass.GameAdapter;

public class AndroidLauncher extends AndroidApplication implements GameAdapter {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new WorDropGame(this), config);
	}

	@Override
	public void showToast(final String s) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(AndroidLauncher.this,s,Toast.LENGTH_LONG).show();
			}
		});
	}
}
