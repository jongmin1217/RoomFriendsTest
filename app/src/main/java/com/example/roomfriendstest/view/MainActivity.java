package com.example.roomfriendstest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.example.roomfriendstest.R;
import com.example.roomfriendstest.databinding.ActivityMainBinding;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private int page;
    private Timer timer;
    private InputMethodManager imm;
    private ActivityMainBinding binding;
    private UserAdapter adapter;
    private UserInfoViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        adapter = new UserAdapter();

        viewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        viewModel.getUsers().observe(this,users-> adapter.setItem(users));
        bindingInit();
//        binding.editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (timer != null) {
//                    timer.cancel();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(final Editable editable) {
//                if (!editable.toString().equals("")) {
////                    binding.userRecyclerview.setVisibility(View.INVISIBLE);
////                    binding.textSearch.setVisibility(View.VISIBLE);
////                    binding.textSearch.setText(R.string.search);
//
//                    timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            page = 1;
//                            viewModel.getUserInfo(editable.toString(), page);
//
//                        }
//                    }, 1000);
//                    binding.btnRemove.setVisibility(View.VISIBLE);
//                } else {
//                    if (binding.textSearch.getVisibility() == View.VISIBLE) {
//                        binding.textSearch.setVisibility(View.INVISIBLE);
//                    }
//                    //presenter.removeItem();
//                    binding.btnRemove.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

    }

    private void bindingInit(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setUserInfo(viewModel);
        binding.setMain(this);
        binding.setLifecycleOwner(this);
    }

//    public void init(){
//
//        viewModel = new UserInfoViewModel();
//
//        binding.setUserInfo(viewModel);
//        binding.setMain(this);
//        binding.setLifecycleOwner(this);
//
//        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        binding.imgSearch.setColorFilter(Color.parseColor("#919191"));
//        binding.btnRemove.setColorFilter(Color.parseColor("#919191"));
//    }
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

    public UserAdapter getAdapter(){
        return adapter;
    }

    public void onClick(View view){
//        if (binding.btnRemove.equals(view)) {
//            binding.editSearch.setText("");
//        } else if (binding.btnCancel.equals(view)) {
//            //keybordHide();
//        }

    }
//
//    private void keybordHide() {
//        assert imm != null;
//        imm.hideSoftInputFromWindow(binding.editSearch.getWindowToken(), 0);
//    }

}
