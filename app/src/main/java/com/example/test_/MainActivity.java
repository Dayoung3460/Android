package com.example.test_;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    CheckBox chBoxExtraRice, chBoxSpecial;
    EditText edtTxt;
    Button btnOrder;
    Intent intent;

    String[] menuList;
    int[] listPrice = {4000, 5000, 4500};
    ArrayAdapter<String> adapter;
    ListView listView;

    int aprice = 0, bprice = 0, cprice = 0, tprice = 0;
    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Order Food");

        chBoxExtraRice = (CheckBox) findViewById(R.id.checkBox1);
        chBoxSpecial = (CheckBox) findViewById(R.id.checkBox2);
        edtTxt = (EditText) findViewById(R.id.editTxt);
        btnOrder = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listViewMenu);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_Amount);

        menuList = getResources().getStringArray(R.array.menu);
        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_single_choice,
                menuList
        );


        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        radioGroup.check(R.id.radioButton_nor);

        listView.setOnItemClickListener(new MenuItemClickListener());
        radioGroup.setOnCheckedChangeListener(new RadioCheckListener());
        chBoxSpecial.setOnCheckedChangeListener(new AdderCheckListener());
        chBoxExtraRice.setOnCheckedChangeListener(new AdderCheckListener());
        btnOrder.setOnClickListener(new ButtonClickListener());
    }

    public class MenuItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            aprice = listPrice[i];
        }
    }

    public class RadioCheckListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(radioGroup.getCheckedRadioButtonId() == R.id.radioButton_extra){
                bprice = 2000;
            } else{
                bprice = 0;
            }

        }
    }

    public class AdderCheckListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (compoundButton.getId() == R.id.checkBox1) {
                if (isChecked) {
                    cprice += 1000;
                } else{
                    cprice -= 1000;
                }
                }

            if (compoundButton.getId() == R.id.checkBox2) {
                cprice += 2000;
                } else{
                    cprice -= 2000;
                }
            }
        }



    public class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button:
                    computeOrder();
                    break;
            }
        }
    }


    public void computeOrder() {
        num = Integer.parseInt(edtTxt.getText().toString());
        tprice = (aprice + bprice + cprice) * num;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Total Price");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Total price you pay is " + tprice + ".");
        AlertDialog dialog = builder.create();

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent = new Intent(MainActivity.this, ThankYouActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

}


