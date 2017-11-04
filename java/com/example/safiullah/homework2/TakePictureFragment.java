package com.example.safiullah.homework2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Output;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Safiullah on 10/13/2017.
 */

public class TakePictureFragment extends Fragment {

    private Context context;
    private Button takeImageBtn;
    private ImageView takenImageView;


    public TakePictureFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.takepicturelayout, null);
        takeImageBtn = (Button) view.findViewById(R.id.takePicBtn);
        takenImageView = (ImageView) view.findViewById(R.id.taken_imageview);
        takeImageBtn.setOnClickListener(takeImage);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Take Picture");
    }

    View.OnClickListener takeImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 111);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK){
            takenImageView.setImageURI(data.getData());
            takenImageView.setImageURI(data.getData());
            //saveImage(data.getData());
        }
    }

}
