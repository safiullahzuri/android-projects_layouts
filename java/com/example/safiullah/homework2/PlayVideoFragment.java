package com.example.safiullah.homework2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_APPEND;

/**
 * Created by Safiullah on 10/13/2017.
 */

class PlayVideoFragment extends Fragment {

    private Button playButton;
    private Context context;
    private VideoView videoView;
    private MediaController mediaController;

    private FileOutputStream fileOutputStream;
    private String FILE_NAME = "file_name";

    public PlayVideoFragment(){

    }

    public static PlayVideoFragment newInstance(){
        PlayVideoFragment playVideoFragment = new PlayVideoFragment();
        return playVideoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, MODE_APPEND);
            Toast.makeText(context, "new file made", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "sorry! file wasn't made", Toast.LENGTH_SHORT).show();
        }

        mediaController = new MediaController(context);
        getActivity().setTitle("Playing Video");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_video_layout, container, false);
        playButton = (Button) view.findViewById(R.id.play_video_button);
        videoView = (VideoView) view.findViewById(R.id.videoView);
        videoView.setVisibility(View.INVISIBLE);
        videoView.setMediaController(mediaController);
        playButton.setOnClickListener(playButtonListener);

        return view;
    }

    View.OnClickListener playButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent  = new Intent(Intent.ACTION_PICK);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
            startActivityForResult(intent, 99);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK){
            
            String fileName =  data.getData().getLastPathSegment().toString();
            byte[] writtenData = new byte[fileName.length()];
            for(int i=0; i<fileName.length(); i++){
                byte harf = (byte) fileName.charAt(i);
                writtenData[i] = harf;
            }
            try {
                fileOutputStream.write(writtenData);
                Toast.makeText(context, fileName+" written to output data", Toast.LENGTH_LONG).show();
            } catch (IOException e) {

                Toast.makeText(context, "couldn't write data line 103", Toast.LENGTH_SHORT).show();
            }

            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(data.getData());
            videoView.start();

        }
    }
}
