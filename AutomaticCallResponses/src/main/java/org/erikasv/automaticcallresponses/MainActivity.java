package org.erikasv.automaticcallresponses;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

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
        else if(requestCode==StableArrayAdapter.EDIT_PROFILE && resultCode==RESULT_OK){
            int pos=data.getIntExtra("pos",0); //ESto debería cambiar
            Profile editProfile = (Profile) data.getSerializableExtra("editProfile");

            Profile oldPrifle=(Profile)((StableArrayAdapter)listProfiles.getAdapter()).getItem(pos);
            oldPrifle.setName(editProfile.getName());
            oldPrifle.setSms(editProfile.getSms());
            ((StableArrayAdapter) listProfiles.getAdapter()).notifyDataSetChanged();
        }
    }
}
