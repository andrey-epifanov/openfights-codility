package com.google.speech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import vt.audiorecord.AudioRecorder;

import java.util.List;

/**
 * Created by Андрей on 16.09.2017.
 */
public class VoiceManagerTest {
    private static Logger logger = LoggerFactory.getLogger(VoiceManagerTest.class);

    @Test
    public static void testTranslate() throws Exception {
        VoiceManager voiceManager = new VoiceManager();
        List<String> result = voiceManager.translate(AudioRecorder.FILE_PATH);

        String all = "";
        for(String word : result) {
            all = all + " " + word;
        }
        logger.info("Results : " + all);
    }
}
