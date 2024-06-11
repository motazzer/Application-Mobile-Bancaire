package com.application.banque.home_activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.banque.R;
import com.application.banque.models.ExchangeRate;
import com.application.banque.services.ExchangeRateAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CurrencyExchangeActivity extends AppCompatActivity {

    private RecyclerView exchangeRatesRecyclerView;
    private ExchangeRateAdapter exchangeRateAdapter;

    private HashMap<String, Double> exchangeRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_exchange);

        exchangeRatesRecyclerView = findViewById(R.id.exchangeRatesRecyclerView);
        exchangeRatesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initExchangeRates();
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        for (String currency : exchangeRates.keySet()) {
            exchangeRateList.add(new ExchangeRate(currency, exchangeRates.get(currency)));
        }

        exchangeRateAdapter = new ExchangeRateAdapter(exchangeRateList);
        exchangeRatesRecyclerView.setAdapter(exchangeRateAdapter);
    }

    private void initExchangeRates() {

        exchangeRates = new HashMap<>();
        exchangeRates.put("Euro", 0.008);
        exchangeRates.put("Dollar", 0.009);
        exchangeRates.put("Dirham", 0.032);
        exchangeRates.put("Lira", 0.073);
        exchangeRates.put("Pound", 0.25);
        exchangeRates.put("Yen", 0.01);
        exchangeRates.put("Rupee", 0.012);
        exchangeRates.put("Franc", 0.007);
        exchangeRates.put("Australian Dollar", 0.012);
        exchangeRates.put("Canadian Dollar", 0.010);
        exchangeRates.put("Swiss Franc", 0.0075);
        exchangeRates.put("Chinese Yuan", 0.058);
        exchangeRates.put("Swedish Krona", 0.083);
        exchangeRates.put("Norwegian Krone", 0.086);
        exchangeRates.put("Brazilian Real", 0.049);
        exchangeRates.put("Indian Rupee", 0.67);
        exchangeRates.put("South African Rand", 0.15);
        exchangeRates.put("Mexican Peso", 0.21);
        exchangeRates.put("Singapore Dollar", 0.012);
        exchangeRates.put("Hong Kong Dollar", 0.069);
        exchangeRates.put("New Zealand Dollar", 0.014);
        exchangeRates.put("Russian Ruble", 0.67);
        exchangeRates.put("Turkish Lira", 0.074);
        exchangeRates.put("South Korean Won", 10.25);
        exchangeRates.put("Indonesian Rupiah", 126.31);
        exchangeRates.put("Malaysian Ringgit", 0.037);
        exchangeRates.put("Thai Baht", 0.24);
        exchangeRates.put("Danish Krone", 0.055);
        exchangeRates.put("Polish Złoty", 0.041);
        exchangeRates.put("Hungarian Forint", 0.0028);
        exchangeRates.put("Czech Koruna", 0.00039);
        exchangeRates.put("Israeli Shekel", 0.027);
        exchangeRates.put("Saudi Riyal", 0.0024);
        exchangeRates.put("UAE Dirham", 0.0024);
        exchangeRates.put("Chilean Peso", 0.0011);
        exchangeRates.put("Argentine Peso", 0.00012);
        exchangeRates.put("South African Rand", 0.15);

    }
}
