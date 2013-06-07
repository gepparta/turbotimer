
package com.awg.turbotimer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.Engine;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimerActivity extends Activity {

    private static final boolean DEBUG = true;
    private static final String TAG = "AgendaListView";
    private static final String START_TIME_KEY = "startTime";

    private Handler mHandler = new Handler();
    final Time timer = new Time();
    // HH:mm:ss.SSS
    // private static final SimpleDateFormat HOURS = new SimpleDateFormat("HH");
    // private static final SimpleDateFormat MINUTES = new
    // SimpleDateFormat("mm");
    // private static final SimpleDateFormat SECONDS = new
    // SimpleDateFormat("ss");
    // private static final SimpleDateFormat MSECONDS = new
    // SimpleDateFormat("S");
    private static final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm:ss");

    private final int REFRESH_RATE = 10;
    private long startTime;
    private long elapsedTime;
    // private String hours, minutes, seconds, milliseconds;
    // private long secs, mins, hrs, msecs;

    private TextView textView;

    private static int TTS_DATA_CHECK = 1;
    private TextToSpeech tts = null;
    private boolean ttsIsInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTextToSpeech();

        setContentView(R.layout.activity_timer);
        // Show the Up button in the action bar.
        setupActionBar();
        textView = (TextView) findViewById(R.id.time);

        if (savedInstanceState != null && savedInstanceState.containsKey(START_TIME_KEY))
        {
            startTime = savedInstanceState.getLong(START_TIME_KEY);
        } else
        {
            startTime = System.currentTimeMillis();
        }

        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save current starttime for onCreate method
        outState.putLong(START_TIME_KEY, startTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Runnable startTimer = new Runnable() {
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;

            check4Countdown(elapsedTime);

            updateTimer(elapsedTime);
            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };

    private void updateTimer(long time) {

        textView.setText(TIME.format(time));
        // secs = (long) (time / 1000);
        // mins = (long) ((time / 1000) / 60);
        // hrs = (long) (((time / 1000) / 60) / 60);

        /*
         * Convert the seconds to String and format to ensure it has a leading
         * zero when required
         */
        // secs = secs % 60;
        // seconds = String.valueOf(secs);
        // if (secs == 0) {
        // seconds = "00";
        // }
        // if (secs < 10 && secs > 0) {
        // seconds = "0" + seconds;
        // }

        /* Convert the minutes to String and format the String */

        // mins = mins % 60;
        // minutes = String.valueOf(mins);
        // if (mins == 0) {
        // minutes = "00";
        // }
        // if (mins < 10 && mins > 0) {
        // minutes = "0" + minutes;
        // }

        /* Convert the hours to String and format the String */

        // hours = String.valueOf(hrs);
        // if (hrs == 0) {
        // hours = "00";
        // }
        // if (hrs < 10 && hrs > 0) {
        // hours = "0" + hours;
        // }

        /*
         * Although we are not using milliseconds on the timer in this example I
         * included the code in the event that you wanted to include it on your
         * own
         */
        // milliseconds = String.valueOf((long) time);
        // if (milliseconds.length() == 2) {
        // milliseconds = "0" + milliseconds;
        // }
        // if (milliseconds.length() <= 1) {
        // milliseconds = "00";
        // }
        // milliseconds = milliseconds.substring(milliseconds.length() - 3,
        // milliseconds.length() - 2);
        /* Setting the timer text to the elapsed time */
        // ((Timer) findViewById(R.id.hours)).setCurrentDigit(hours);
        // ((Timer) findViewById(R.id.minutes)).setCurrentDigit(minutes);
        // ((Timer) findViewById(R.id.seconds)).setCurrentDigit(seconds);
        // ((Timer)
        // findViewById(R.id.milliseconds)).setCurrentDigit(milliseconds);

        // ((Timer)
        // findViewById(R.id.minutes)).setCurrentDigit(String.valueOf(mins));
        // ((Timer)
        // findViewById(R.id.seconds)).setCurrentDigit(String.valueOf(secs));
        // ((Timer)
        // findViewById(R.id.milliseconds)).setCurrentDigit(String.valueOf(msecs));

        // if (DEBUG) {
        // Log.v(getResources().getString(R.string.app_name), "SECONDS = " +
        // SECONDS.format(timer.second));
        // Log.v(getResources().getString(R.string.app_name), "MSECONDS = " +
        // MSECONDS.format(timer.toMillis(false)));
        // }

    }

    protected void check4Countdown(long elapsedTime) {

        final long time = elapsedTime;

        // start a new worker thread for countdown if needed
        new Thread(new Runnable() {
            public void run() {
                if (time > 4900 && time < 5100)
                    speak(5);
                else if (time > 5900 && time < 6100)
                    speak(6);
                else if (time > 6900 && time < 7100)
                    speak(7);
                else if (time > 7900 && time < 8100)
                    speak(8);
            }
        }).start();

    }

    private void initTextToSpeech() {
        Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, TTS_DATA_CHECK);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TTS_DATA_CHECK) {
            if (resultCode == Engine.CHECK_VOICE_DATA_PASS) {
                tts = new TextToSpeech(this, new OnInitListener() {
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            ttsIsInit = true;

                        }
                    }
                });
            }
            else {
                Intent installVoice = new Intent(Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installVoice);
            }
        }
    }

    private void speak(int second) {

        if (tts != null && ttsIsInit) {
            tts.setLanguage(Locale.US);
            tts.speak("" + second,
                    TextToSpeech.QUEUE_ADD, null);
        }
    }
}
