package com.example.safiullah.homework2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_APPEND;

/**
 * Created by Safiullah on 10/13/2017.
 */

class PlayMusicFragment extends Fragment {
    private Button musicButton;
    private Context context;
    private MediaPlayer mediaPlayer;
    private MediaController.MediaPlayerControl mediaPlayerControl;
    private Button stopButton;
    private Button resumeButton;

    private SQLiteDatabase sqLiteDatabase;

    public PlayMusicFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        getActivity().setTitle("Music Playing Activity");

        sqLiteDatabase = getContext().openOrCreateDatabase("musics_database",MODE_APPEND,null);
        String sql = "CREATE TABLE IF NOT EXISTS musics (id integer primary key autoincrement, name varchar(32))";
        sqLiteDatabase.execSQL(sql);
        Toast.makeText(context, "successfully table created", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.musicfragmentlayout, null);
        musicButton = (Button) view.findViewById(R.id.play_music_button);
        musicButton.setOnClickListener(musicButtonListener);
        stopButton  = (Button) view.findViewById(R.id.stop_music_button);
        resumeButton = (Button) view.findViewById(R.id.resume_music_button);
        stopButton.setVisibility(View.GONE); stopButton.setOnClickListener(generalListener);
        resumeButton.setVisibility(View.GONE); resumeButton.setOnClickListener(generalListener);
        return view;
    }

    View.OnClickListener musicButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");
            startActivityForResult(intent, 21);
        }
    };

    View.OnClickListener generalListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.stop_music_button){
                //stop music
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
            }else if (v.getId() == R.id.resume_music_button){
                //resume button
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    resumeButton.setText("Resume");
                }else {
                    mediaPlayer.start();
                    resumeButton.setText("Pause");
                }
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 21){
            try {
                Toast.makeText(context, "reached here", Toast.LENGTH_SHORT).show();
                sqLiteDatabase.execSQL("INSERT INTO musics(name) VALUES( '"+data.getData().getLastPathSegment()+"') ");
                Toast.makeText(context, data.getData().getLastPathSegment()+" was saved to database!", Toast.LENGTH_SHORT).show();
                mediaPlayer = MediaPlayer.create(context, data.getData());
                mediaPlayer.start();
                stopButton.setVisibility(View.VISIBLE);
                resumeButton.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                Toast.makeText(context, "line 67, couldn't start mediaplayer", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

