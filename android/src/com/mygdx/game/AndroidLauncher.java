package com.mygdx.game;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.Database.DBHelper;
import com.mygdx.game.WorDropGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import AdapterClass.GameAdapter;

public class AndroidLauncher extends AndroidApplication implements GameAdapter {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    String res="";
    OrientationData orientationData;
    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        orientationData=new OrientationData(this);
        DBHelper.writeData(this,5,15);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new WorDropGame(this), config);
	}

    @Override
    protected void onPause() {
        super.onPause();
        //orientationData.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        orientationData.register();
    }

    @Override
	public void showToast(final String s) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(AndroidLauncher.this,s,Toast.LENGTH_SHORT).show();
			}
		});
	}

    @Override
    public void voiceInput() {
        promptSpeechInput();
    }

    @Override
    public String getVoiceInput() {
        return res;
    }

    @Override
    public float getRollOrientation() {
        if (orientationData.getOrientation()!=null && orientationData.getStartOrientation()!=null){
            return orientationData.getOrientation()[2]-orientationData.getStartOrientation()[2];
        }
        return 0;
    }

    @Override
    public String getAWord(int length, int lower, int higher) {
        return DBHelper.getAWord(this,length,lower,higher);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {//if there is voice input

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Iterator<String> it=result.iterator();
                    Log.d("Reading input list",result.toString());
                    while (it.hasNext()){
                        String s=it.next();
                        // only choosing a letter input
                        if (s.length()==1){
                            res=s;
                            break;
                        }
                    }


                }
                else{ //if there is no input
                    res="";
                }
                break;
            }

        }
    }
}
