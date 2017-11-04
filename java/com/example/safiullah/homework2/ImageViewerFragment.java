package com.example.safiullah.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Safiullah on 10/13/2017.
 */

class ImageViewerFragment extends Fragment {
    private ImageView imageView;
    private Button button;

    public ImageViewerFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Select image");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imageviewfragment, null);
        imageView = (ImageView) view.findViewById(R.id.myimageview);
        button = (Button) view.findViewById(R.id.choose_image_button);
        button.setOnClickListener(chooseImageListener);
        return view;
    }

    View.OnClickListener chooseImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 121);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 121 && resultCode == RESULT_OK){
            imageView.setImageURI(data.getData());
        }
    }
}
