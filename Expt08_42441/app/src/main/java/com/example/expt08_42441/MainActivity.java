package com.example.expt08_42441;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageButton playPauseButton, nextButton, previousButton;
    private SeekBar songProgressSeekBar;
    private TextView songTitleTextView, artistNameTextView;
    private ImageView albumArtImageView;
    private List<Song> playlist;
    private int currentSongIndex = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        playPauseButton = findViewById(R.id.playPauseButton);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);
        songProgressSeekBar = findViewById(R.id.songProgressSeekBar);
        songTitleTextView = findViewById(R.id.songTitleTextView);
        artistNameTextView = findViewById(R.id.artistNameTextView);
        albumArtImageView = findViewById(R.id.albumArtImageView);

        // Setup playlist
        playlist = new ArrayList<>();
        playlist.add(new Song("Perfect", "Ed Sheeran", R.raw.perfect_ed_sheeran, R.drawable.image1));
        playlist.add(new Song("Yellow", "Coldplay", R.raw.yellow_coldplay, R.drawable.image2));

        // Initial song setup
        setupSong(playlist.get(currentSongIndex));

        // Play/Pause listener
        playPauseButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playPauseButton.setImageResource(R.drawable.ic_play);
            } else {
                mediaPlayer.start();
                playPauseButton.setImageResource(R.drawable.ic_pause);
            }
        });

        // Next song listener
        nextButton.setOnClickListener(v -> {
            mediaPlayer.stop();
            currentSongIndex = (currentSongIndex + 1) % playlist.size();
            setupSong(playlist.get(currentSongIndex));
        });

        // Previous song listener
        previousButton.setOnClickListener(v -> {
            mediaPlayer.stop();
            currentSongIndex = (currentSongIndex - 1 + playlist.size()) % playlist.size();
            setupSong(playlist.get(currentSongIndex));
        });
    }

    private void setupSong(Song song) {
        // Release previous MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Create new MediaPlayer
        mediaPlayer = MediaPlayer.create(this, song.getResourceId());

        // Update UI
        songTitleTextView.setText(song.getTitle());
        artistNameTextView.setText(song.getArtist());
        albumArtImageView.setImageResource(song.getCoverResourceId());

        // Setup SeekBar
        songProgressSeekBar.setMax(mediaPlayer.getDuration());
        songProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Start playback
        mediaPlayer.start();
        playPauseButton.setImageResource(R.drawable.ic_pause);

        // Update SeekBar
        updateSeekBar();
    }

    private void updateSeekBar() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    songProgressSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            handler.removeCallbacksAndMessages(null);
        }
    }
}