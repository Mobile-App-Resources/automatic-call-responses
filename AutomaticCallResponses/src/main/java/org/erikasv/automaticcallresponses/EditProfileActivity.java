package org.erikasv.automaticcallresponses;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfileActivity extends Activity implements View.OnClickListener {

    private Button bOk, bCancel;
    private EditText editName, editSms;
    private String name, sms;
    private Profile toEdit;
    private int posProfile;
    private int RESULTADO;

    private DataBaseController dbController;
    private Profile profile;

    private static final String TAG = "ACTIVIDAD_EDITPROFILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"abre onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profile);

        editName = (EditText) findViewById(R.id.editName);
        editSms = (EditText) findViewById(R.id.editSms);
        bOk = (Button) findViewById(R.id.bOk);
        bCancel = (Button) findViewById(R.id.bCancel);
        profile=null;
        dbController =new DataBaseController(this);

        bOk.setOnClickListener(this);
        bCancel.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        posProfile=extras.getInt("pos");
        toEdit = (Profile) extras.get("profile");
        editName.setText(toEdit.getName());
        editSms.setText(toEdit.getSms());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bOk:
                name = editName.getText().toString();
                sms = editSms.getText().toString();

                dbController.openDb();
                profile= dbController.updateProfile(toEdit, name, sms);
                dbController.closeDb();

                RESULTADO = RESULT_OK;
                finish();
                break;
            case R.id.bCancel:
                RESULTADO = RESULT_CANCELED;
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        Intent datos = new Intent();
        if (RESULTADO == RESULT_OK) {
            datos.putExtra("pos",posProfile);
            datos.putExtra("editProfile",profile);

        }
        setResult(RESULTADO, datos);
        super.finish();
    }
}
