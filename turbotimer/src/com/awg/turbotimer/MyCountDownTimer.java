
package com.awg.turbotimer;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;

public class MyCountDownTimer extends CountDownTimer {

    TextToSpeech tts;
    int counter = 0;

    public MyCountDownTimer(TextToSpeech tts, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.tts = tts;
    }

    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    long secondsLeft;

    @Override
    public void onFinish() {
        tts.speak("Go", TextToSpeech.QUEUE_FLUSH, null);
        if (counter < 3)
        {
            // restart Activity max 4x
            counter++;
            start();
        } else {
            counter = 0;
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        secondsLeft = millisUntilFinished / 1000;

        if (secondsLeft <= 5)
        {
            tts.speak("" + secondsLeft, TextToSpeech.QUEUE_ADD, null);
        }

    }

}
