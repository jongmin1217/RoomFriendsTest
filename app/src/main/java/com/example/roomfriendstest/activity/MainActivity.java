package com.example.roomfriendstest.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.example.roomfriendstest.R;

public class MainActivity extends AppCompatActivity implements MainView{
    private MainPresenter presenter;

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        presenter = new MainPresenter(this);

        presenter.userInfo("a");
    }

    @Override
    public void result(String result) {
        text.setText(result);
    }
}
