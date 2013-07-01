package org.erikasv.automaticcallresponses;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button bNewProfile;
    private int NEW_PROFILE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bNewProfile = (Button) findViewById(R.id.bAdd);
        bNewProfile.setOnClickListener(this);
        //TODO: Cargar la lista de perfiles.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.prefsPerfiles) {
            Intent intent = new Intent(this, PrefsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bAdd) {
            Intent intent = new Intent(this, AddProfileActivity.class);
            startActivityForResult(intent, NEW_PROFILE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==NEW_PROFILE && resultCode==RESULT_OK){
            //TODO Actualizar lista de perfiles!
        }
    }
}
