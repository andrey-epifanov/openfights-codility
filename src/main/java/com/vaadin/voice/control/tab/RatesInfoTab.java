package com.vaadin.voice.control.tab;

import com.google.speech.VoiceManager;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.voice.control.backend.Contact;
import com.vaadin.voice.control.backend.Rate;
import com.vaadin.voice.control.bank.RatesHelper;
import com.vaadin.ui.*;
import com.vaadin.v7.data.util.BeanItemContainer;
import com.vaadin.v7.ui.Grid;
import com.vaadin.v7.ui.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vt.audiorecord.AudioRecorder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 16.09.2017.
 */
public class RatesInfoTab {
    private static final Logger logger = LoggerFactory.getLogger(RatesInfoTab.class);

    private AudioRecorder recorder;
    private VoiceManager voiceManager;
    private List<List<String>> translated = new ArrayList<>(); // get only first

    Image image = new Image();
    private TextField filter;

    Grid ratesList = new Grid();
    //Table grid = new Table();

    public RatesInfoTab(AudioRecorder recorder, VoiceManager voiceManager) {
        this.recorder = recorder;
        this.voiceManager = voiceManager;
        //configureRatesList();
    }

    private void configureRatesList() {
        ratesList
                .setContainerDataSource(new BeanItemContainer<>(Rate.class));
//        ratesList.setColumnOrder("firstName", "lastName", "email");
//        ratesList.removeColumn("id");
//        ratesList.removeColumn("birthDate");
//        ratesList.removeColumn("phone");
        ratesList.setSelectionMode(com.vaadin.v7.ui.Grid.SelectionMode.SINGLE);
//        ratesList.addSelectionListener(
//                e -> contactForm.edit((Contact) ratesList.getSelectedRow()));
        refreshRatesFromNet();
    }

    /** Курсы Валют
     *
     * @return
     */
    public VerticalLayout generateRatesInfo() {
        configureRatesList();

        image = new Image("Image from file",
            new FileResource(new File("./src/main/webapp/WEB-INF/images/saveMoney_v01.jpg")));
        image.setVisible(false);
        Button btnUseSkin = new Button("Skin!");
        btnUseSkin.addClickListener(
                e -> {
//                    image = new Image("Image from file",
//                        new FileResource(new File("./src/main/webapp/WEB-INF/images/saveMoney_v01.jpg")));
                    image.setVisible(true);
                    //image.attach();
                }
        );
        HorizontalLayout skin = new HorizontalLayout(image, btnUseSkin);

        filter = new TextField();
        filter.setInputPrompt("Filter rates...");
        filter.addTextChangeListener(e -> filterRatesBy(e.getText())); // refresh rates

        Button btnNewContact = new Button("Обновить");
        btnNewContact.addClickListener(e -> refreshRatesFromNet());

        HorizontalLayout actions = new HorizontalLayout(
                filter,
                btnNewContact
        );

        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);

        VerticalLayout left = new VerticalLayout(skin, actions, ratesList);
        left.setSizeFull();
        ratesList.setSizeFull();
        left.setExpandRatio(ratesList, 1);

        VerticalLayout tab = new VerticalLayout();
        tab.addComponent(new Image(null,
                new ThemeResource("img/planets/Mercury.jpg")));

        tab.addComponent(left);

        return tab;
    }

    private void refreshRatesFromNet() {
        List<Rate> rates = RatesHelper.getRatesFromBank();

        BeanItemContainer<Rate> container =
                new BeanItemContainer<>(Rate.class, rates);

        ratesList.setContainerDataSource(container);
//        ratesList.setVisible(true);
    }

    private void filterRatesBy(String text) {

    }
}
