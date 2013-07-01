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
                android.R.layout.simple_list_item_1, list);
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

    private class StableArrayAdapter extends BaseAdapter implements View.OnClickListener {

        private Context context;
        private Button bActivate,bEdit,bRemove;
        private ArrayList<Profile> list;

        public StableArrayAdapter(Context context, int textViewResourceId, List<Profile> objects) {
            this.context = context;
            this.list=(ArrayList<Profile>)objects;
        }

        public void add(Profile prof){
            list.add(prof);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return list.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.item_list_profile, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.profile_name);

            textView.setText(((Profile)getItem(position)).getName());
            if(((Profile)getItem(position)).isActive()) rowView.setBackgroundColor(Color.GREEN);

            bActivate = (Button)rowView.findViewById(R.id.bActivate);
            bEdit =(Button)rowView.findViewById(R.id.bEdit);
            bRemove=(Button)rowView.findViewById(R.id.bRemove);

            bActivate.setOnClickListener(this);
            bEdit.setOnClickListener(this);
            bRemove.setOnClickListener(this);

            bActivate.setTag(position);
            bEdit.setTag(position);
            bRemove.setTag(position);

            return rowView;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bActivate:
                    Profile profile = list.get((Integer)v.getTag());
                    applicationObject.openDb();
                    applicationObject.activateProfile(profile);
                    applicationObject.closeDb();
                    this.notifyDataSetChanged();
                    break;
                case R.id.bEdit:
                    break;
                case R.id.bRemove:
                    break;
            }
        }
    }
}
