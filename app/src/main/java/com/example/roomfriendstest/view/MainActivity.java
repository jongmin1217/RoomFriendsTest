package com.example.roomfriendstest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.roomfriendstest.R;
import com.example.roomfriendstest.data.UserData.UserList;
import com.example.roomfriendstest.databinding.ActivityMainBinding;
import com.example.roomfriendstest.databinding.RecyclerviewUserinfoBinding;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Listener{

    private InputMethodManager imm;
    private ActivityMainBinding binding;
    private UserAdapter adapter;
    private OrganizationsAdapter orgAdapter;
    private UserInfoViewModel viewModel;
    private Timer timer;
    private int page;
    private boolean lastPage = false;
    public String notifyText = "Searching...";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        adapter = new UserAdapter(this);

        initObserver();
        initBinding();
        initTextWatcher();
        initPaging();

        binding.imgSearch.setColorFilter(Color.parseColor("#919191"));
        binding.btnClear.setColorFilter(Color.parseColor("#919191"));
    }

    private void initObserver() {
        viewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        viewModel.getUsers().observe(this, users -> {
            adapter.setItem(users, page);
            if (users.size() == 0) {
                if (page == 1) {
                    showNotiSceen(true, false);
                    setNotifyText("No Result");
                } else lastPage = true;
            } else showNotiSceen(false, true);
            hideView(binding.textLoding);
        });
        viewModel.getOrganizationsUsers().observe(this, organizationsUsers -> {
            Log.d("qweqwe","size "+organizationsUsers.size());
            orgAdapter.setItem(organizationsUsers);
        });
        viewModel.showErrorToast.observe(this, showErrorToast -> {
            Toast.makeText(this, "너무 많이 요청하였습니다. 잠시후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            hideView(binding.textLoding);
        });
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);
        binding.setLifecycleOwner(this);
    }

    private void initTextWatcher() {
        binding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setNotifyText("Searching...");
                showNotiSceen(true, false);
                if (!editable.toString().equals("")) {
                    showView(binding.btnClear);
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            page = 1;
                            lastPage = false;
                            viewModel.getUserInfo(editable.toString(), page);
                        }
                    }, 1000);
                } else {
                    hideView(binding.btnClear);
                    showNotiSceen(false, false);
                }
            }
        });
    }

    private void initPaging() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.userRecyclerview.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
                int previousScroll = 0;
                if (previousScroll != i3) {
                    keybordHide();
                }
                int lastVisibleItemPosition = ((LinearLayoutManager) Objects.requireNonNull(binding.userRecyclerview.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = Objects.requireNonNull(binding.userRecyclerview.getAdapter()).getItemCount() - 1;
                if (lastVisibleItemPosition == itemTotalCount && binding.userRecyclerview.getAdapter().getItemCount() % 30 == 0 && !lastPage) {
                    showView(binding.textLoding);
                    Thread thread = new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                            viewModel.getUserInfo(binding.editSearch.getText().toString(), ++page);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    thread.start();
                }

            });
        }
    }

    public UserAdapter getAdapter() {
        return adapter;
    }


    public void hideClick(View view) {
        keybordHide();
    }

    public void clearClick(View view) {
        binding.editSearch.setText("");
    }

    private void keybordHide() {
        assert imm != null;
        imm.hideSoftInputFromWindow(binding.editSearch.getWindowToken(), 0);
    }

    private void showNotiSceen(boolean searchview, boolean recyclerview) {
        if (searchview) showView(binding.textSearch);
        else hideView(binding.textSearch);
        if (recyclerview) showView(binding.userRecyclerview);
        else hideView(binding.userRecyclerview);
    }

    private void setNotifyText(String notifyText) {
        if (!this.notifyText.equals(notifyText)) {
            this.notifyText = notifyText;
            binding.invalidateAll();
        }
    }

    private void showView(View view) {
        if (view.getVisibility() == View.INVISIBLE) view.setVisibility(View.VISIBLE);
    }

    private void hideView(View view) {
        if (view.getVisibility() == View.VISIBLE) view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(String login,OrganizationsAdapter orgAdapter) {
        this.orgAdapter = orgAdapter;
        viewModel.getOrganizations(login);
    }
}
