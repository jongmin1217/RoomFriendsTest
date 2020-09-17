package com.example.roomfriendstest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
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
import com.example.roomfriendstest.databinding.ActivityMainBinding;
import com.example.roomfriendstest.recyclerview.UserAdapter;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int page;
    private Timer timer;
    private UserAdapter adapter;
    private InputMethodManager imm;

    ActivityMainBinding binding;
    private UserInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());



        binding.editSearch.addTextChangedListener(new TextWatcher() {
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
//                    binding.userRecyclerview.setVisibility(View.INVISIBLE);
//                    binding.textSearch.setVisibility(View.VISIBLE);
//                    binding.textSearch.setText(R.string.search);

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            page = 1;
                            viewModel.getUserInfo(editable.toString(), page);

                        }
                    }, 1000);
                    binding.btnRemove.setVisibility(View.VISIBLE);
                } else {
                    if (binding.textSearch.getVisibility() == View.VISIBLE) {
                        binding.textSearch.setVisibility(View.INVISIBLE);
                    }
                    //presenter.removeItem();
                    binding.btnRemove.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    public void init(){

        viewModel = new UserInfoViewModel();
        viewModel.onCreate();

        binding.setUserInfo(viewModel);
        binding.setMain(this);
        binding.setLifecycleOwner(this);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        binding.imgSearch.setColorFilter(Color.parseColor("#919191"));
        binding.btnRemove.setColorFilter(Color.parseColor("#919191"));
    }
//    private void init() {
//        binding.userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new UserAdapter(this, this);
//        binding.userRecyclerview.setAdapter(adapter);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            binding.userRecyclerview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                    int previousScroll = 0;
//                    if (previousScroll != i3) { removeKeybord(); }
//                    int lastVisibleItemPosition = ((LinearLayoutManager) Objects.requireNonNull(binding.userRecyclerview.getLayoutManager())).findLastCompletelyVisibleItemPosition();
//                    int itemTotalCount = Objects.requireNonNull(binding.userRecyclerview.getAdapter()).getItemCount() - 1;
//                    if (lastVisibleItemPosition == itemTotalCount && binding.userRecyclerview.getAdapter().getItemCount() > 29) {
//                        presenter.userInfo(binding.editSearch.getText().toString(), ++page);
//                    }
//
//                }
//            });
//        }
//    }


    public void onBtnRemove(View view){
        binding.editSearch.setText("");
    }

    public void onBtnCancel(View view){
        removeKeybord();
    }

    private void removeKeybord() {
        assert imm != null;
        imm.hideSoftInputFromWindow(binding.editSearch.getWindowToken(), 0);
    }

}
