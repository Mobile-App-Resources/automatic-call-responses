package org.erikasv.automaticcallresponses;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profile);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_profile, menu);
        return true;
    }
    
}
