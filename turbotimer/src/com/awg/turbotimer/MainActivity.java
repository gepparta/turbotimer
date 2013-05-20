
package com.awg.turbotimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
    public final static String CHECKED_RADIO_BUTTON_ID = "com.awg.turbotimer.CHECKED_RADIO_BUTTON_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /** Called when the user clicks the Send button */
    public void startTimer(View view) {
        Intent intent = new Intent(this, TimerActivity.class);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);

        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
//        View radioButton = radioGroup.findViewById(checkedRadioButtonId);
        intent.putExtra(CHECKED_RADIO_BUTTON_ID, checkedRadioButtonId);
        startActivity(intent);
    }
}
