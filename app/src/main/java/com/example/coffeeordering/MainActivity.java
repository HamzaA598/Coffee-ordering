package com.example.coffeeordering;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CheckBox mCreamCB, mChocolateCB;
    TextView mQuantityTV;
    Button mDecreaseBtn, mIncreaseBtn, mOrderBtn;
    EditText mNameET;

    int quantity = 1;

    public void Toast(String message)
    {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCreamCB = findViewById(R.id.creamCB);
        mChocolateCB = findViewById(R.id.chocolateCB);
        mOrderBtn = findViewById(R.id.orderBtn);
        mDecreaseBtn = findViewById(R.id.decreaseBtn);
        mIncreaseBtn = findViewById(R.id.increaseBtn);
        mQuantityTV = findViewById(R.id.quantityTV);
        mNameET = findViewById(R.id.nameET);


        mDecreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity != 1)
                {
                    quantity -= 1;
                    mQuantityTV.setText("" + quantity);
                }
                else if(quantity == 1)
                    Toast("Sorry, you can't order less coffee");
            }
        });
        mIncreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity != 10)
                {
                    quantity += 1;
                    mQuantityTV.setText("" + quantity);
                }
                else if(quantity == 10)
                    Toast("Sorry, you can't order more coffee");
            }
        });

        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 5;
                String name = mNameET.getText().toString();
                boolean creamState = mCreamCB.isChecked();
                boolean chocolateState = mChocolateCB.isChecked();
                String sCreamState = "no";
                String sChocolateState = "no";
                if(!name.equals(""))
                {
                    if(creamState)
                    {
                        price+=1;
                        sCreamState = "yes";
                    }

                    if(chocolateState)
                    {
                        price+=2;
                        sChocolateState = "yes";
                    }

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffee_order_for) + name);
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.order_summary)
                            + "\n" + getString(R.string.name_order_summary) + name
                            + "\n" + getString(R.string.quantity) + quantity
                            + "\n" + getString(R.string.add_whipped_cream) + sCreamState
                            + "\n" + getString(R.string.add_chocolate) + sChocolateState
                            + "\n" + getString(R.string.total) + (price * quantity)
                            + "\n" + getString(R.string.thank_you));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        Toast("Ordering...");
                    }
                }
                else if(name.equals(""))
                    Toast("Please enter your name first!");

            }
        });

    }
}
