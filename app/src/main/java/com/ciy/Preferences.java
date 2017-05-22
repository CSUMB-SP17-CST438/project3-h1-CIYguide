package com.ciy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class Preferences extends AppCompatActivity implements View.OnClickListener{

    DBHandler db;

    Button save;

    CheckBox[] boxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        save = (Button) findViewById(R.id.saveThis);
        save.setOnClickListener(this);

        db = new DBHandler(this);

        initBoxes();
    }

    public void onClick(View v){
        if(v.getId() == R.id.saveThis){
            Intent i = new Intent(this, UserProfileActivity.class);
            startActivity(i);
        }
    }

    public void onCheckBoxClicked(View view){
        //check if it's checked!
        boolean checked = ((CheckBox)view).isChecked();

        switch(view.getId()){
            case R.id.high_protein:
                if(checked)
                    db.updatePrefs("high-protein","y");
                else
                    db.updatePrefs("high-protein","n");
                break;
            case R.id.gluten_free:
                if(checked)
                    db.updatePrefs("gluten-free","y");
                else
                    db.updatePrefs("gluten-free","n");
                break;
            case R.id.vegetarian:
                if(checked)
                    db.updatePrefs("vegetarian","y");
                else
                    db.updatePrefs("vegetarian","n");
                break;
            case R.id.wheat_free:
                if(checked)
                    db.updatePrefs("wheat-free","y");
                else
                    db.updatePrefs("wheat-free","n");
                break;
            case R.id.no_dairy:
                if(checked)
                    db.updatePrefs("dairy-free","y");
                else
                    db.updatePrefs("dairy-free","n");
                break;
            case R.id.balanced:
                if(checked)
                    db.updatePrefs("balanced","y");
                else
                    db.updatePrefs("balanced","n");
                break;
            case R.id.high_fiber:
                if(checked)
                    db.updatePrefs("high-fiber","y");
                else
                    db.updatePrefs("high-fiber","n");
                break;
            case R.id.vegan:
                if(checked)
                    db.updatePrefs("vegan","y");
                else
                    db.updatePrefs("vegan","n");
                break;
            case R.id.low_sodium:
                if(checked)
                    db.updatePrefs("low-sodium","y");
                else
                    db.updatePrefs("low-sodium","n");
                break;
            case R.id.low_carb:
                if(checked)
                    db.updatePrefs("low-carb","y");
                else
                    db.updatePrefs("low-carb","n");
                break;
            case R.id.low_fat:
                if(checked)
                    db.updatePrefs("low-fat","y");
                else
                    db.updatePrefs("low-fat","n");
                break;
            case R.id.paleo:
                if(checked)
                    db.updatePrefs("paleo", "y");
                else
                    db.updatePrefs("paleo","n");
                break;
            case R.id.sugar_conscious:
                if(checked)
                    db.updatePrefs("low-sugar","y");
                else
                    db.updatePrefs("low-sugar","n");
                break;
            case R.id.no_eggs:
                if(checked)
                    db.updatePrefs("egg-free","y");
                else
                    db.updatePrefs("egg-free","n");
                break;
            case R.id.no_peanuts:
                if(checked)
                    db.updatePrefs("peanut-free","y");
                else
                    db.updatePrefs("peanut-free","n");
                break;
            case R.id.no_tree_nuts:
                if(checked)
                    db.updatePrefs("tree-nut-free","y");
                else
                    db.updatePrefs("tree-nut-free","n");
                break;
            case R.id.no_soy:
                if(checked)
                    db.updatePrefs("soy-free","y");
                else
                    db.updatePrefs("soy-free","n");
                break;
            case R.id.no_fish:
                if(checked)
                    db.updatePrefs("fish-free","y");
                else
                    db.updatePrefs("fish-free","n");
                break;
            case R.id.no_shellfish:
                if(checked)
                    db.updatePrefs("shellfish-free","y");
                else
                    db.updatePrefs("shellfish-free","n");
                break;
            default:
                Toast.makeText(getApplicationContext(), "How tho", Toast.LENGTH_LONG).show();
                break;
        }
    }

    //retrieve status of checkbox from db and set in view
    private void initBoxes(){
        CheckBox[] give = new CheckBox[19];
        give[0] = (CheckBox) findViewById(R.id.high_protein);
        give[0].setChecked(db.setChecked("high-protein", this));
        give[1] = (CheckBox) findViewById(R.id.gluten_free);
        give[1].setChecked(db.setChecked("gluten-free", this));
        give[2] = (CheckBox) findViewById(R.id.vegetarian);
        give[2].setChecked(db.setChecked("vegetarian", this));
        give[3] = (CheckBox) findViewById(R.id.wheat_free);
        give[3].setChecked(db.setChecked("wheat-free", this));
        give[4] = (CheckBox) findViewById(R.id.no_dairy);
        give[4].setChecked(db.setChecked("dairy-free", this));
        give[5] = (CheckBox) findViewById(R.id.no_fish);
        give[5].setChecked(db.setChecked("fish-free", this));
        give[6] = (CheckBox) findViewById(R.id.no_tree_nuts);
        give[6].setChecked(db.setChecked("tree-nut-free", this));
        give[7] = (CheckBox) findViewById(R.id.low_sodium);
        give[7].setChecked(db.setChecked("low-sodium", this));
        give[8] = (CheckBox) findViewById(R.id.high_fiber);
        give[8].setChecked(db.setChecked("high-fiber", this));
        give[9] = (CheckBox) findViewById(R.id.low_fat);
        give[9].setChecked(db.setChecked("low-fat", this));
        give[10] = (CheckBox) findViewById(R.id.sugar_conscious);
        give[10].setChecked(db.setChecked("low-sugar", this));
        give[11] = (CheckBox) findViewById(R.id.vegan);
        give[11].setChecked(db.setChecked("vegan", this));
        give[12] = (CheckBox) findViewById(R.id.paleo);
        give[12].setChecked(db.setChecked("paleo", this));
        give[13] = (CheckBox) findViewById(R.id.no_eggs);
        give[13].setChecked(db.setChecked("egg-free", this));
        give[14] = (CheckBox) findViewById(R.id.no_soy);
        give[14].setChecked(db.setChecked("soy-free", this));
        give[15] = (CheckBox) findViewById(R.id.no_shellfish);
        give[15].setChecked(db.setChecked("shellfish-free", this));
        give[16] = (CheckBox) findViewById(R.id.no_peanuts);
        give[16].setChecked(db.setChecked("peanut-free", this));
        give[17] = (CheckBox) findViewById(R.id.balanced);
        give[17].setChecked(db.setChecked("balanced", this));
        give[18] = (CheckBox) findViewById(R.id.low_carb);
        give[18].setChecked(db.setChecked("low-carb", this));
    }
}
