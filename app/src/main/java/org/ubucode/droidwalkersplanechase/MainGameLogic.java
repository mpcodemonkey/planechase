package org.ubucode.droidwalkersplanechase;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainGameLogic extends Activity {

    private DeckController dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_logic);
        this.getActionBar().setTitle("Droidwalkers: Planechase");
        dc = new DeckController(this.getApplicationContext());
        refreshUI();
    }

    private void refreshUI() {
        TextView top = (TextView)findViewById(R.id.planeName);
        TextView mid = (TextView)findViewById(R.id.effectText);
        TextView bot = (TextView)findViewById(R.id.chaosText);
        top.setText(dc.getCurrentPlane().getName());
        mid.setText(dc.getCurrentPlane().getEffect());
        bot.setText(dc.getCurrentPlane().getChaos());
    }

    public void nextPlane(View view)
    {
        dc.nextPlane();
        refreshUI();
    }

    public void prevPlane(View view){
        dc.prevPlane();
        refreshUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_game_logic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
