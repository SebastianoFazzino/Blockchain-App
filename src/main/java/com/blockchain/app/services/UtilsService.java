package com.blockchain.app.services;

import com.blockchain.app.models.enums.Currency;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.blockchain.app.models.enums.Currency.BTC;
import static com.blockchain.app.models.enums.Currency.ETH;
import static java.math.RoundingMode.HALF_EVEN;

@Service
public class UtilsService {

    static HashMap<Currency, BigDecimal> currenciesBaseUnitMap;

    static {
        currenciesBaseUnitMap = new HashMap<>();
        currenciesBaseUnitMap.put(BTC, new BigDecimal("100000000"));
        currenciesBaseUnitMap.put(ETH, new BigDecimal("1000000000000000000"));
    }

    protected final String UTC_DATE_FORMAT = "yyyy/MM/dd";
    protected final String UTC_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss z(Z)";
    protected final String ITALIAN_DATE_FORMAT = "dd/MM/yyyy";
    protected final String ITALIAN_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss z(Z)";


    @SneakyThrows
    protected Date parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATE_FORMAT);
        return sdf.parse(date);
    }

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

    public BigDecimal convertToBaseUnit(Currency currency, BigDecimal amount) {
        BigDecimal conversionFactor = currenciesBaseUnitMap.get(currency);
        return amount.multiply(conversionFactor);
    }

    public BigDecimal convertFromBaseUnit(Currency currency, BigDecimal amount) {
        BigDecimal conversionFactor = currenciesBaseUnitMap.get(currency);
        return amount.divide(conversionFactor, HALF_EVEN);
    }
}
