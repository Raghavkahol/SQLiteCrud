package com.example.oyo.scrummaster.createscrum;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.content.Context;
import android.view.LayoutInflater;
import  android.view.View;
import com.example.oyo.scrummaster.R;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddMemberDialog extends AppCompatDialogFragment {
    private EditText editTextMemberName;
    private MemberDialogListener listener;
    private String membername,memberdesignation;
    private Context context;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_add_people,null);
        editTextMemberName=view.findViewById(R.id.newName);
        Spinner spinner = (Spinner) view.findViewById(R.id.newDesignation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.designations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                memberdesignation=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                 membername=editTextMemberName.getText().toString();

                 listener.addMember(membername,memberdesignation);

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MemberDialogListener) context;
        }catch(Exception e){
            throw new ClassCastException(context.toString()+" must implement memberDialogListener");
        }
    }

    public interface MemberDialogListener{
        void addMember(String membername,String memberdesignation);
    }
}
