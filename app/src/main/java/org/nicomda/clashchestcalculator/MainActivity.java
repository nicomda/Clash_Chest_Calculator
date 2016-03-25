package org.nicomda.clashchestcalculator;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ChestFragment.OnFragmentInteractionListener, GoldFragment.OnFragmentInteractionListener, GiantFragment.OnFragmentInteractionListener,MagicFragment.OnFragmentInteractionListener {

    private EditText edit_total_wins;
    private TextView text_total_wins;
    private TextView text_next_chest;
    private ArrayList<String> chestcycle;
    private ImageView next_chest;
    private android.support.v4.app.Fragment fragment_chest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Typeface clashfont=Typeface.createFromAsset(getAssets(),"fonts/supercell.ttf");
        text_total_wins=(TextView)findViewById(R.id.textView2);
        edit_total_wins=(EditText)findViewById(R.id.editText);
        text_next_chest=(TextView)findViewById(R.id.text_nextChest);
        next_chest=(ImageView)findViewById(R.id.nextChest);
        text_total_wins.setTypeface(clashfont);
        edit_total_wins.setTypeface(clashfont);
        text_next_chest.setTypeface(clashfont);
        chestcycle=new ArrayList<>();
        try {
            getChestCycle(chestcycle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        edit_total_wins.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String chesttype="";
                if(!edit_total_wins.getText().toString().isEmpty()){
                    chesttype=calculateChest(Integer.parseInt(edit_total_wins.getText().toString()),chestcycle);
                    if(chesttype.contentEquals("Silver")){
                        next_chest.setImageResource(R.drawable.silver_chest);
                    }
                    else if(chesttype.contentEquals("Gold")){
                       next_chest.setImageResource(R.drawable.gold_chest);
                    }
                    else if(chesttype.contentEquals("Giant")){
                        next_chest.setImageResource(R.drawable.giant_chest);
                    }
                    else {
                    next_chest.setImageResource(R.drawable.magical_chest);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void getChestCycle(ArrayList<String> cycle) throws IOException{
        String str="";
        StringBuffer buf=new StringBuffer();
        InputStream is = this.getResources().openRawResource(R.raw.chest_cycle);
        BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        if (is!=null) {
            while ((str = reader.readLine()) != null) {
                cycle.add(str);
            }
        }
    }
    public String calculateChest(int numberofwins, ArrayList<String> cycle){
        String chesttype="";
        chesttype=cycle.get(numberofwins%240);
        return chesttype;
    }


}
