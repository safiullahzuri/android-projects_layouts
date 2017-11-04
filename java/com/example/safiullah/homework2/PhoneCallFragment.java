package com.example.safiullah.homework2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Safiullah on 10/13/2017.
 */

public class PhoneCallFragment extends Fragment {

    private EditText numberEditText;
    private Button dialButton;
    private SharedPreferences sharedPreferences;


    public PhoneCallFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phonecalllayout, null);
        numberEditText = (EditText) view.findViewById(R.id.call_edittext);
        dialButton = (Button) view.findViewById(R.id.dial_button);
        dialButton.setOnClickListener(dialButtonListener);
        getActivity().setTitle("Dialing Fragment");
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("dials", Context.MODE_APPEND);
    }

    View.OnClickListener dialButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+numberEditText.getText().toString()));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(numberEditText.getText().toString(), "some phone number");
            Toast.makeText(getContext(), "Your phone number was saved!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    };
}
