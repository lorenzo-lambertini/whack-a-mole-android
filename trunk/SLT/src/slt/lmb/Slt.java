package slt.lmb;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Slt extends Activity {


	private GraphicsMole im;
	private int currentMolePos = -1;
	private Score score;
	private int scoreCurr = 0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startgame);
		score = new Score();    
	    final TextView tv=(TextView) findViewById(R.string.Score);	
		final GridView gw = (GridView) findViewById(R.id.gridview);
		im = new GraphicsMole(this);
		gw.setAdapter(im);
		/*
		 * 
		 * */
		gw.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (currentMolePos == position) {
					scoreCurr = score.AddScore();					
					tv.setText("Score: " + scoreCurr);				
					tv.refreshDrawableState();
				}
			}
		});
		Handler step = new Step();
		MoleGame mg = new MoleGame(step);
		mg.start();
	}
/*
 * 
 * 
 * */
	private class Step extends Handler {

		Mole mole = new Mole();
		Hole hole = new Hole();
		private int oldPosition = -1;
/*
 * 
 * 
 * */
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			currentMolePos = bundle.getInt("newPosition");
			im.setItem(currentMolePos, mole.getTalpa());
			
			if (oldPosition != -1 && currentMolePos != oldPosition) {
				im.setItem(oldPosition, hole.getHole());
			}
			
			oldPosition = currentMolePos;
			im.notifyDataSetChanged();
		}
	}
}