package com.vaadin.voice.control.bank;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.vaadin.voice.control.backend.Rate;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by Андрей on 17.09.2017.
 */
public class RatesHelper {
    public static List<Rate> getRatesFromBank() {
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
        return  null;
    }
}
