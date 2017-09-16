package com.vaadin.tutorial.addressbook.tab;

import com.google.speech.VoiceManager;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vt.audiorecord.AudioRecorder;

import java.util.ArrayList;
import java.util.List;

import static vt.audiorecord.AudioRecorder.FILE_PATH;

/**
 * Created by Андрей on 16.09.2017.
 */
public class VoiceControlTab {
    private static final Logger logger = LoggerFactory.getLogger(VoiceControlTab.class);

    private AudioRecorder recorder;
    private VoiceManager voiceManager;
    private List<List<String>> translated = new ArrayList<>(); // get only first

    private TextArea area;

    public VoiceControlTab(AudioRecorder recorder, VoiceManager voiceManager) {
        this.recorder = recorder;
        this.voiceManager = voiceManager;
    }

    public VerticalLayout generateTabVoiceControl() {
        Button btnAudioRecordClick = new Button("Audio Rec. Click Me!");
        btnAudioRecordClick.addClickListener(e -> recorder.execute());

        Button btnAuudioRecTranslate = new Button("Audio Rec. Translate!");
        btnAuudioRecTranslate.addClickListener(e -> {
            try {
                translated.add(voiceManager.translate(FILE_PATH));
                refreshResult(translated.get(translated.size()));
            } catch (Exception e1) {
                logger.error(ExceptionUtils.getStackTrace(e1));
            }
        });

        HorizontalLayout actions = new HorizontalLayout(
                btnAudioRecordClick,
                btnAuudioRecTranslate
        );

        actions.setWidth("100%");
        actions.setExpandRatio(btnAudioRecordClick, 1);
        actions.setExpandRatio(btnAuudioRecTranslate, 1);

        // Create the area
        area = new TextArea("Big Area");
        HorizontalLayout results = new HorizontalLayout(area);

        VerticalLayout left = new VerticalLayout(actions, results);
        left.setSizeFull();
        //contactList.setSizeFull();
        //left.setExpandRatio(btnAudioRecordClick, 1);

        VerticalLayout tab = new VerticalLayout();
        tab.addComponent(new Image(null,
                new ThemeResource("img/planets/Mercury.jpg")));

        tab.addComponent(left);
        return tab;
    }

    private void refreshResult(List<String> result) {
        String all = "";
        for(String word : result) {
            all = all + " " + word;
        }
        area.setValue(all);
    }

    public List<String> getTranslateCommand() {
        List<String> translatedOne = translated.get(translated.size());
        return translatedOne;
    }
}
