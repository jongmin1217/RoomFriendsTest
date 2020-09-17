package com.example.roomfriendstest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roomfriendstest.R;

public class MainActivity extends AppCompatActivity implements MainView{

    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.btnRemove)
    ImageButton btnRemove;


    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        imgSearch.setColorFilter(Color.parseColor("#919191"));
        btnRemove.setColorFilter(Color.parseColor("#919191"));


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        presenter = new MainPresenter(this);

        //presenter.userInfo("a");
    }

    @Override
    public void result(String result) {
        //text.setText(result);
    }
}
