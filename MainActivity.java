package rob.myappcompany.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button stopButton ;
    SeekBar seekBar;
    TextView timer;
    MediaPlayer mediaPlayer;
    int min , sec;
    CountDownTimer countDownTimer;

    public void startCounting(View view) {
       stopButton.setVisibility(View.VISIBLE);
       startButton.setVisibility(View.INVISIBLE);
       seekBar.setEnabled(false);

       int milliSeconds = (min*60 + sec ) * 1000; //(seekBar.getProgress()*1000)+100(for milliseconds);
      countDownTimer= new CountDownTimer(milliSeconds , 1000){
            public void onTick(long l){
                updateTimer((int)l/1000);
            }

            public void onFinish(){
                mediaPlayer.start();
                stopCounting(stopButton);
            }
        }.start();
    }

    public void updateTimer(int secondsLeft)
    {
        min = secondsLeft/60;
        sec = secondsLeft- (min * 60);

        String secString = Integer.toString(sec);
        if(sec<=9)
            secString = "0" + secString;
        timer.setText(Integer.toString(min) + ":" + secString);

    }

    public  void stopCounting(View view) {
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
        timer.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        updateTimer(30);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        timer = (TextView)  findViewById(R.id.timerText);
        mediaPlayer =MediaPlayer.create(this , R.raw.air_horn);
        min = 0;
        sec = 30;

        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.INVISIBLE);

        seekBar.setMax(600); //10 min (60*10)
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               updateTimer(progress);
          }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
