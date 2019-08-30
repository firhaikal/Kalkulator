package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity<decimalFormat> extends AppCompatActivity {
    private int[] btnNum = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
    private int[] btnOp = {R.id.btntmb, R.id.btnkr, R.id.btnkl, R.id.btnbg};
    EditText edtTxt;
    boolean lastNum,stateError,lastDot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.edtTxt = (EditText)findViewById(R.id.edtTxt);
        setNumericOnClickListener();
        setOperatorOnClickListener();
    }

    private void setNumericOnClickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (stateError){
                    edtTxt.setText(button.getText());
                    stateError = false;
                }else {
                    edtTxt.append(button.getText());
                } lastNum = true;
            }
        };
        for (int id : btnNum){
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorOnClickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNum && !stateError){
                    Button button = (Button) v;
                    edtTxt.append(button.getText());
                    lastNum = false;
                    lastDot = false;
                }

            }
        };
        for (int id : btnOp){
            findViewById(id).setOnClickListener(listener);
        }
        findViewById(R.id.btndot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNum && !stateError && !lastDot){
                    edtTxt.append(".");
                    lastNum = false;
                    lastDot = true;
                }
            }
        });
        findViewById(R.id.btnAc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTxt.setText("");
                lastNum = false;
                lastDot = false;
                stateError = false;
            }
        });
        findViewById(R.id.btnsm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    private void onEqual(){
        if (lastNum && !stateError){
            String txt = edtTxt.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                edtTxt.setText(Double.toString(result));
                lastDot = true;
            }catch (ArithmeticException ex){
                edtTxt.setText("Error");
                stateError = true;
                lastNum = false;
            }
        }
    }

}
