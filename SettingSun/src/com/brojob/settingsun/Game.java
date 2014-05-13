package com.brojob.settingsun;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class Game extends ActionBarActivity {

	private GameView gameView;
	private GameThread gameThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void onResume() {
		super.onResume();
		gameView = new GameView(this);
		setContentView(gameView);
		gameThread = gameView.getThread();
	}

	public void onPause() {
		super.onPause();
		gameThread.setRunning(false);
	}
}
