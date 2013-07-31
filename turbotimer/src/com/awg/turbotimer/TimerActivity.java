
package com.awg.turbotimer;

import android.annotation.SuppressLint;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimerActivity extends Activity implements OnInitListener {

    private static final boolean DEBUG = true;
    private static final String TAG = "AgendaListView";
    private static final String START_TIME_KEY = "startTime";
    TimerActivity self;
    private Command countdown;
    private Handler mHandler = new Handler();
    // final Time timer = new Time();

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm:ss");

    private final int REFRESH_RATE = 10;
    private long startTime;
    private long elapsedTime;

    private TextView textView;

    private static int TTS_DATA_CHECK = 1;
    private TextToSpeech tts = null;
    private boolean ttsIsInit = false;
    private int checkedRadioButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;

        Bundle extraBundle = getIntent().getExtras();
        checkedRadioButtonId = extraBundle.getInt(MainActivity.CHECKED_RADIO_BUTTON_ID);

        switch (checkedRadioButtonId) {
            case R.id.rbladders:
                countdown = new LaddersCountdown();
            case R.id.rbinterval_sets:
                countdown = new IntervalSetsCountdown();
            case R.id.rbsuper_sets:
                 countdown = new SuperSetsCountdown();
            case R.id.rbsteppers:
                 countdown = new SteppersCountdown();
            case R.id.rbtabatas:
                 countdown = new TabatasCountdown();

            default:
                break;
        }

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

        // start the Timer
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
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;

            updateTimer(elapsedTime);
            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };

    private void updateTimer(long time) {

        textView.setText(TIME.format(time));
    }

    private void initTextToSpeech() {
        Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, TTS_DATA_CHECK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TTS_DATA_CHECK) {
            if (resultCode == Engine.CHECK_VOICE_DATA_PASS) {
                tts = new TextToSpeech(this, this);
                startCoundown(tts);
            }
            else {
                Intent installVoice = new Intent(Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installVoice);
            }
        }
    }

    /**
     * @return the elapsedTime
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * @return the tts
     */
    public TextToSpeech getTts() {
        return tts;
    }

    @Override
    public void onInit(int status) {
        // if tts is initialised start logic
        if (status == TextToSpeech.SUCCESS) {
            ttsIsInit = true;
            // starte den countdown
            tts.setLanguage(Locale.US);

        }
    }

    public void startCoundown(TextToSpeech tt)
    {
        countdown.start(this, tt);
        countdown.execute();
    }
}
