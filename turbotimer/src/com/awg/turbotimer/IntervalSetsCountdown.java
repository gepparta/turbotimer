
package com.awg.turbotimer;

import android.speech.tts.TextToSpeech;

public class IntervalSetsCountdown implements Command {
    MyCountDownTimer timer;
     static long startTime = 180000; // -> 3 min
//    long startTime = 10000; // -> 10 sec -> fürs debugging
    long countDownInterval = 1000;
    IntervalSetsCountdown instance;
    TextToSpeech tts;

    public IntervalSetsCountdown start(TimerActivity timerActivity, TextToSpeech tts) {

        this.tts = tts;
        // use elapsedTime to sync timer with countdown
        startTime = startTime - timerActivity.getElapsedTime();
        return instance;
    }

    @Override
    public void execute() {
        timer = new MyCountDownTimer(tts, startTime, countDownInterval);
        timer.start();
    }

}
