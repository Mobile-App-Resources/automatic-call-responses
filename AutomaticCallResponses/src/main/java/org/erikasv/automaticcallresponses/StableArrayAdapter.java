package org.erikasv.automaticcallresponses;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class StableArrayAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private Button bActivate,bDesactivate, bEdit,bRemove;
    private ArrayList<Profile> list;

    private CallResponsesApplication applicationObject;

    public StableArrayAdapter(Context context, int textViewResourceId, List<Profile> objects, CallResponsesApplication appObj) {
        this.context = context;
        this.list=(ArrayList<Profile>)objects;
        this.applicationObject=appObj;
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

        bActivate = (Button)rowView.findViewById(R.id.bActivate);
        bDesactivate= (Button)rowView.findViewById(R.id.bDesactivate);
        bEdit =(Button)rowView.findViewById(R.id.bEdit);
        bRemove=(Button)rowView.findViewById(R.id.bRemove);

        bActivate.setOnClickListener(this);
        bDesactivate.setOnClickListener(this);
        bEdit.setOnClickListener(this);
        bRemove.setOnClickListener(this);

        bActivate.setTag(position);
        bDesactivate.setTag(position);
        bEdit.setTag(position);
        bRemove.setTag(position);

        if(((Profile)getItem(position)).isActive()) {
            rowView.setBackgroundColor(Color.GREEN);
            bActivate.setVisibility(View.GONE);
            bDesactivate.setVisibility(View.VISIBLE);
        }

        return rowView;
    }

    @Override
    public void onClick(View v) {
        Profile profile = list.get((Integer)v.getTag());
        switch (v.getId()){
            case R.id.bActivate:
                applicationObject.openDb();
                applicationObject.activateProfile(profile);
                applicationObject.closeDb();
                //TODO Desactivar el otro perfil que esté activo
                this.notifyDataSetChanged();
                break;
            case R.id.bDesactivate:
                applicationObject.openDb();
                applicationObject.desActivateProfile(profile);
                applicationObject.closeDb();
                this.notifyDataSetChanged();
                break;
            case R.id.bEdit:
                break;
            case R.id.bRemove:
                applicationObject.openDb();
                applicationObject.deleteProfile(profile);
                applicationObject.closeDb();
                list.remove(profile);
                this.notifyDataSetChanged();
                break;
        }
    }
}