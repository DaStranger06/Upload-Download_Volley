package com.siamsoft.updown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;

    RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radiomain);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radioButton = findViewById(checkedId);
                if(radioButton.getText().equals("Upload Image"))
                {
                    FragmentManager fm= MainActivity.this.getSupportFragmentManager();
                    //for single image
                    FragmentTransaction ft;
                    //for multiple
                    Fragment fragment = new Upload_image();

                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragmentview, fragment);
                    ft.commit();

                }

                else if(radioButton.getText().equals("View Image"))
                {


                    FragmentManager fm= MainActivity.this.getSupportFragmentManager();
                    //for single image
                    FragmentTransaction ft;
                    //for multiple
                    Fragment fragment = new View_Image();

                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragmentview, fragment);
                    ft.commit();


                }


            }
        });

    }
}