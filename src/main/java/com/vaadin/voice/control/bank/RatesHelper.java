package com.vaadin.voice.control.bank;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.vaadin.voice.control.backend.Contact;
import com.vaadin.voice.control.backend.Rate;
import com.vaadin.voice.control.tab.RatesInfoTab;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 17.09.2017.
 */
public class RatesHelper {
    private static final Logger logger = LoggerFactory.getLogger(RatesInfoTab.class);

    public static List<Rate> getRatesFromBank() {
        Response resp = RestAssured.get("https://api.open.ru/getrates/1.0.0/rates/cash");

        List<Rate> rates = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(new JSONObject(resp.asString()).get("rates").toString());
        for(Object object: jsonArray) {
            JSONObject jsonObject = (JSONObject)object;
            rates.add(convertToRate(jsonObject));
        }
        logger.info("Current Rate. First: " + rates.get(0));

        return  rates;
    }

    private static Rate convertToRate(JSONObject jsonObject) {
        Rate rate = new Rate();
        rate.setStartDate((String)jsonObject.get("startDate"));
        rate.setCurCharCode((String)jsonObject.get("curCharCode"));
        rate.setOperationType((String)jsonObject.get("operationType"));
        rate.setRateValue((String)jsonObject.get("rateValue"));
        rate.setCurUnitValue((String)jsonObject.get("curUnitValue"));
        rate.setMinLimit((String)jsonObject.get("minLimit"));
        rate.setDepartment((String)jsonObject.get("department"));
        return rate;
    }
}
