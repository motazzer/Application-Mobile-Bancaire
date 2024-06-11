package com.application.banque.home_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.application.banque.R;

import java.util.HashMap;

public class ConverterActivity extends AppCompatActivity {

    private EditText dinarAmountInput;
    private Spinner currencySpinner;
    private Button convertButton;
    private TextView resultTextView;

    private HashMap<String, Double> conversionRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        dinarAmountInput = findViewById(R.id.dinarAmountInput);
        currencySpinner = findViewById(R.id.currencySpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.currency_options,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
        initConversionRates();
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });
    }

    private void initConversionRates() {
        conversionRates = new HashMap<>();
        conversionRates.put("Euro", 0.29);
        conversionRates.put("Dollar", 0.32);
        conversionRates.put("Dirham", 1.18);
        conversionRates.put("Lira", 9.27);
        conversionRates.put("Pound", 0.25);
        conversionRates.put("Yen", 36.45);
        conversionRates.put("Rupee", 23.15);
        conversionRates.put("Franc", 0.30);
        conversionRates.put("Australian Dollar", 0.44);
        conversionRates.put("Canadian Dollar", 0.40);
        conversionRates.put("Swiss Franc", 0.29);
        conversionRates.put("Chinese Yuan", 2.07);
        conversionRates.put("Swedish Krona", 2.83);
        conversionRates.put("Norwegian Krone", 3.03);
        conversionRates.put("Brazilian Real", 1.63);
        conversionRates.put("Indian Rupee", 44.82);
        conversionRates.put("South African Rand", 5.16);
        conversionRates.put("Mexican Peso", 6.43);
        conversionRates.put("Singapore Dollar", 0.45);
        conversionRates.put("Hong Kong Dollar", 2.52);
        conversionRates.put("New Zealand Dollar", 0.49);
        conversionRates.put("Russian Ruble", 23.10);
        conversionRates.put("Turkish Lira", 2.66);
        conversionRates.put("South Korean Won", 375.21);
        conversionRates.put("Indonesian Rupiah", 4582.50);
        conversionRates.put("Malaysian Ringgit", 1.36);
        conversionRates.put("Thai Baht", 10.25);
        conversionRates.put("Danish Krone", 2.14);
        conversionRates.put("Polish Złoty", 1.49);
        conversionRates.put("Hungarian Forint", 113.75);
        conversionRates.put("Czech Koruna", 7.96);
        conversionRates.put("Israeli Shekel", 1.13);
        conversionRates.put("Saudi Riyal", 1.20);
        conversionRates.put("UAE Dirham", 1.20);
        conversionRates.put("Chilean Peso", 258.70);
        conversionRates.put("Argentine Peso", 18.92);
        conversionRates.put("South African Rand", 5.16);
    }


    private void convertCurrency() {
        try {
            double dinarAmount = Double.parseDouble(dinarAmountInput.getText().toString());
            String selectedCurrency = currencySpinner.getSelectedItem().toString();
            double conversionRate = conversionRates.get(selectedCurrency);
            double convertedAmount = dinarAmount * conversionRate;
            resultTextView.setText(String.format("Converted Amount: %.2f %s", convertedAmount, selectedCurrency));
        } catch (NumberFormatException e) {
            resultTextView.setText("Invalid input. Please enter a valid number.");
        }
    }
}
