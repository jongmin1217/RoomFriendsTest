package com.example.roomfriendstest.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomfriendstest.R;
import com.example.roomfriendstest.recyclerview.UserAdapter;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.btnRemove)
    ImageButton btnRemove;
    @BindView(R.id.editSearch)
    EditText editSearch;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.textSearch)
    TextView textSearch;
    @BindView(R.id.userRecyclerview)
    RecyclerView userRecyclerview;

    private MainPresenter presenter;
    private int page;
    private Timer timer;
    private UserAdapter adapter;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        init();
        presenter = new MainPresenter(this, adapter);

        imgSearch.setColorFilter(Color.parseColor("#919191"));
        btnRemove.setColorFilter(Color.parseColor("#919191"));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeKeybord();
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSearch.setText("");
            }
        });
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (!editable.toString().equals("")) {
                    userRecyclerview.setVisibility(View.INVISIBLE);
                    textSearch.setVisibility(View.VISIBLE);
                    textSearch.setText(R.string.search);

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            page = 1;
                            presenter.userInfo(editable.toString(), page);

                        }
                    }, 1000);
                    btnRemove.setVisibility(View.VISIBLE);
                } else {
                    if (textSearch.getVisibility() == View.VISIBLE) {
                        textSearch.setVisibility(View.INVISIBLE);
                    }
                    presenter.removeItem();
                    btnRemove.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    private void init() {
        userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, this);
        userRecyclerview.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            userRecyclerview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    int previousScroll = 0;
                    if (previousScroll != i3) { removeKeybord(); }
                    int lastVisibleItemPosition = ((LinearLayoutManager) Objects.requireNonNull(userRecyclerview.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                    int itemTotalCount = Objects.requireNonNull(userRecyclerview.getAdapter()).getItemCount() - 1;
                    if (lastVisibleItemPosition == itemTotalCount && userRecyclerview.getAdapter().getItemCount() > 29) {
                        presenter.userInfo(editSearch.getText().toString(), ++page);
                    }

                }
            });
        }
    }

    private void removeKeybord() {
        assert imm != null;
        imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
    }

    @Override
    public void screenVisible() {
        textSearch.setVisibility(View.INVISIBLE);
        userRecyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void noResult() {
        textSearch.setText(R.string.noResult);
    }

    @Override
    public void responseError() {
        Toast.makeText(this, "너무 많이 요청하였습니다. 잠시 후 시도해 주세요", Toast.LENGTH_SHORT).show();
    }
}
