package com.vaadin.voice.control.backend;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.beanutils.BeanUtils;
import org.joda.time.DateTime;

/**
 * Created by Андрей on 17.09.2017.
 */
@Getter
@Setter
@ToString
public class Rate {
    private DateTime startDate;
    private String operationType;
    private String rateValue;
    private String curCharCode;
    private String curUnitValue;
    private String minLimit;
    private String department;

    @Override
    public Rate clone() throws CloneNotSupportedException {
        try {
            return (Rate) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }
}
