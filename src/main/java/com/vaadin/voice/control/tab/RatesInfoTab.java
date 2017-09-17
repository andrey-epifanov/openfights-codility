package com.vaadin.voice.control.tab;

import com.google.speech.VoiceManager;
import com.jayway.restassured.response.Response;
import com.vaadin.server.ThemeResource;
import com.vaadin.voice.control.backend.Rate;
import com.vaadin.ui.*;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.v7.data.util.BeanItemContainer;
import com.vaadin.v7.ui.TextField;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vt.audiorecord.AudioRecorder;

import java.util.ArrayList;
import java.util.List;

import com.jayway.restassured.RestAssured;

/**
 * Created by Андрей on 16.09.2017.
 */
public class RatesInfoTab {
    private static final Logger logger = LoggerFactory.getLogger(RatesInfoTab.class);

    private AudioRecorder recorder;
    private VoiceManager voiceManager;
    private List<List<String>> translated = new ArrayList<>(); // get only first

    private TextArea area;
    private TextField filter;

    com.vaadin.v7.ui.Grid ratesList = new com.vaadin.v7.ui.Grid();

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

        filter = new TextField();
        filter.setInputPrompt("Filter rates...");
        filter.addTextChangeListener(e -> refreshRates(e.getText())); // refresh rates

        Button btnNewContact = new Button("Обновить");
        btnNewContact.addClickListener(e -> refreshRatesFromNet()); // !!!!!!!!!!

        HorizontalLayout actions = new HorizontalLayout(
                filter,
                btnNewContact
        );

        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);

        VerticalLayout left = new VerticalLayout(actions, ratesList);
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
        Response resp = RestAssured.get("https://api.open.ru/getrates/1.0.0/rates/cash");

        JSONArray jsonArray = new JSONArray(resp.asString());
//        jsonArray.get
//
//        ArrayList<Rate> rates = new ArrayList<>();
//
//        for(Object json : jsonArray) {
//            json
//        }
//        rates
//
//        BeanItemContainer<Rate> container =
//                new BeanItemContainer<Rate>(Rate.class, rates);

//        ratesList.setData(jsonArray);

//        jsonResponse; = > to Grid rates
    }

    private void refreshRates(String text) {

    }
}
