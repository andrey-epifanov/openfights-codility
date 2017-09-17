package bank;

import com.google.speech.VoiceManager;
import com.vaadin.voice.control.backend.Rate;
import com.vaadin.voice.control.bank.RatesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import vt.audiorecord.AudioRecorder;

import java.util.List;

/**
 * Created by Андрей on 16.09.2017.
 */
public class RateTest {
    private static Logger logger = LoggerFactory.getLogger(RateTest.class);

    @Test
    public static void testRateGetting() throws Exception {
        List<Rate> all = RatesHelper.getRatesFromBank();
        logger.info("Results (0): " + all.get(0));
        logger.info("Results : " + all);
    }
}
