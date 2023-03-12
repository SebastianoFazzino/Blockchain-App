package com.blockchain.app.services;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UtilsService {

    protected final String UTC_DATE_FORMAT = "yyyy/MM/dd";
    protected final String UTC_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss z(Z)";
    protected final String ITALIAN_DATE_FORMAT = "dd/MM/yyyy";
    protected final String ITALIAN_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss z(Z)";


    protected String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_FORMAT);
        return sdf.format(date);
    }

    protected String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_TIME_FORMAT);
        return sdf.format(date);
    }

    protected String formatItalianDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(ITALIAN_DATE_FORMAT);
        return sdf.format(date);
    }

    protected String formatItalianDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(ITALIAN_DATE_TIME_FORMAT);
        return sdf.format(date);
    }

}
