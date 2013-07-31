package com.awg.turbotimer;

import android.speech.tts.TextToSpeech;

public interface Command {
    
    public Command start(TimerActivity timerActivity, TextToSpeech tts);
    public void execute( );

}
