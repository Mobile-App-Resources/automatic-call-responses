package org.erikasv.automaticcallresponses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button bNewProfile;
    private ListView listProfiles;
    private int ADD_NEW_PROFILE =10;

    private CallResponsesApplication applicationObject;
    private String TAG="ACTIVIDAD_PRINCIPAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bNewProfile = (Button) findViewById(R.id.bAdd);
        listProfiles = (ListView) findViewById(R.id.listProfiles);
        bNewProfile.setOnClickListener(this);
        applicationObject=(CallResponsesApplication) getApplication();

        //Cargar la lista de perfiles
        applicationObject.openDb();
        ArrayList<Profile> list = (ArrayList<Profile>) applicationObject.getAllProfiles();
        applicationObject.closeDb();

        StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list, applicationObject);
        listProfiles.setAdapter(adapter);
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
            startActivityForResult(intent, ADD_NEW_PROFILE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== ADD_NEW_PROFILE && resultCode==RESULT_OK){
            Profile newProfile = (Profile) data.getSerializableExtra("newProfile");
            ((StableArrayAdapter)listProfiles.getAdapter()).add(newProfile);
        }
    }
}
