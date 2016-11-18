package com.deitel.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // Форматировщики денежных сумм и процентов
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // Сумма счета, введенная пользователем
    private double percent = 0.15; // Исходный процент чаевых
    private TextView amountTV; // Для отформатированной суммы счета
    private TextView percentTV; // Для вывода процента чаевых
    private TextView tipTV; // Для вывода вычисленных чаевых
    private TextView totalTV; // Для вычисленной общей суммы

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountTV = (TextView) findViewById(R.id.amountTV);
        percentTV = (TextView) findViewById(R.id.percentTV);
        tipTV = (TextView) findViewById(R.id.tipTV);
        totalTV = (TextView) findViewById(R.id.totalTV);
        tipTV.setText(currencyFormat.format(0));
        totalTV.setText(currencyFormat.format(0));

        // Назначение слушателя TextWatcher для amountEditText
        EditText amountET = (EditText) findViewById(R.id.amountET);
        amountET.addTextChangedListener(amountETW);

        // Назначение слушателя OnSeekBarChangeListener для percentSeekBar
        SeekBar percentSB = (SeekBar) findViewById(R.id.percentSB);
        percentSB.setOnSeekBarChangeListener(seekBarListener);
    }

    // Вычисление и вывод чаевых и общей суммы
    private void calculate(){
        // Форматирование процентов и вывод в percentTextView
        percentTV.setText(percentFormat.format(percent));

        // Вычисление чаевых и общей суммы
        double tip = billAmount * percent;
        double total = billAmount + tip;

        // Вывод чаевых и общей суммы в формате денежной величины
        tipTV.setText(currencyFormat.format(tip));
        totalTV.setText(currencyFormat.format(total));
    }

    // Объект слушателя для событий изменения состояния SeekBar
    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        // Обновление процента чаевых и вызов calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            percent = progress / 100.0; // Назначение процента чаевых
            calculate(); // Вычисление и вывод чаевых и суммы
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    // Объект слушателя для событий изменения текста в EditText
    public final TextWatcher amountETW = new TextWatcher() {
        // Вызывается при изменении пользователем величины счета
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try { // Получить счет и вывести в формате денежной суммы
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTV.setText(currencyFormat.format(billAmount));
            } catch(NumberFormatException e) { // Если s пусто или не число
                amountTV.setText("");
                billAmount = 0.0;
            }

            calculate(); // Обновление полей с чаевыми и общей суммой
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
