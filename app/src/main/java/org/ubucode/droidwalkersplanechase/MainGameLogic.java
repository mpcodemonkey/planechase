package org.ubucode.droidwalkersplanechase;

import android.animation.Animator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Random;


public class MainGameLogic extends Activity {

    private DeckController dc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_logic);
        //this.getActionBar().setTitle("Droidwalkers: Planechase");
        //this.getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);
        dc = new DeckController(this.getApplicationContext());
        while(dc.getCurrentPlane() == null){}
        refreshUI();
    }

    private void refreshUI() {
        //TextView top = (TextView)findViewById(R.id.planeName);
        //TextView mid = (TextView)findViewById(R.id.effectText);
        //TextView bot = (TextView)findViewById(R.id.chaosText);
        ImageView img = (ImageView)findViewById(R.id.pImage);
        //top.setText(dc.getCurrentPlane().getName());
        //mid.setText(dc.getCurrentPlane().getEffect());
        //bot.setText(dc.getCurrentPlane().getChaos());

        Context context = img.getContext();
        int resID = context.getResources().getIdentifier(dc.getCurrentPlane().getImgPath(), "drawable", context.getPackageName());
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resID);
        img.setImageBitmap(bmp);
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

    public void tempRollFunction(View view){
        String text = null;
        Random r = new Random();
        int rollOfTheFie = r.nextInt(6)+1;
        switch (rollOfTheFie){
            case 1:
            case 2:{
                //planeswalk
                dc.nextPlane();
                refreshUI();
                text = "Planeswalk to " + dc.getCurrentPlane().getName();
            }
                break;
            case 3:
            case 4:{
                //chaos
                text = "Chaos woo!";
            }
                break;
            case 5:
            case 6:{
                //nothing
                text = "nothing happened";
            }
                break;
        }
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_game_logic, menu);
        return super.onCreateOptionsMenu(menu);
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
