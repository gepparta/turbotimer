
package com.awg.turbotimer;

import android.speech.tts.TextToSpeech;

public class LaddersCountdown implements Command {
    MyCountDownTimer timer;
     long startTime = 4230000; // -> 7,5 min
//    long startTime = 10000; // -> 10 sec -> fürs debugging
    long countDownInterval = 1000;
    LaddersCountdown instance;
    TextToSpeech tts;

    public LaddersCountdown start(TimerActivity timerActivity, TextToSpeech tts) {
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
