package org.erikasv.automaticcallresponses;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddProfileActivity extends Activity implements View.OnClickListener {

    private Button bOk, bCancel;
    private EditText editName, editSms;
    private String name, sms;
    private int RESULTADO;

    private DataBaseController dbController;
    private Profile profile;

    private static final String TAG = "ACTIVIDAD_ADDPROFILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profile);

        editName = (EditText) findViewById(R.id.editName);
        editSms = (EditText) findViewById(R.id.editSms);
        bOk = (Button) findViewById(R.id.bOk);
        bCancel = (Button) findViewById(R.id.bCancel);
        profile=null;
        dbController = new DataBaseController(this);

        bOk.setOnClickListener(this);
        bCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bOk:
                name = editName.getText().toString();
                sms = editSms.getText().toString();

                dbController.openDb();
                profile= dbController.createProfile(name,sms);
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
            datos.putExtra("newProfile",profile);
        }
        setResult(RESULTADO, datos);
        super.finish();
    }
}
