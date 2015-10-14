package ca.gbc.mobile.adrianpaiva.gbc_guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    public void viewMap(View view)
    {
        Intent intent = new Intent(MainActivity.this, MapOfCampusesActivity.class);
        startActivity(intent);
    }
    public void viewCasaLome(View view)
    {
        Intent intent = new Intent(MainActivity.this, CasaLomaInfoActivity.class);
        startActivity(intent);
    }
    public void viewRyerson(View view)
    {
        Intent intent = new Intent(MainActivity.this, ryersonActivity.class);
        startActivity(intent);
    }
    public void viewStJames(View view)
    {
        Intent intent = new Intent(MainActivity.this, stJamesActivity.class);
        startActivity(intent);
    }
    public void viewWaterfront(View view)
    {
        Intent intent = new Intent(MainActivity.this, waterfrontActivity.class);
        startActivity(intent);
    }
    public void viewYoungCentre(View view)
    {
        Intent intent = new Intent(MainActivity.this, youngCentreActivity.class);
        startActivity(intent);
    }

}
