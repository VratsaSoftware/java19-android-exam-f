package com.vsc.androidexam2020.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.vsc.androidexam2020.data.remote.AuthService;
import com.vsc.androidexam2020.R;
import com.vsc.androidexam2020.databinding.ActivityMainBinding;
import com.vsc.androidexam2020.ui.fragments.MyOrdersFragment;
import com.vsc.androidexam2020.ui.fragments.NewOrderFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupActionBar();
        setupNavigation();
        openFragment(NewOrderFragment.newInstance());
    }

    private void setupActionBar() {
        getSupportActionBar().setTitle(
                AuthService.getInstance().getLoggedUser().getName() + "'s Photo Station"
        );
    }

    private void setupNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.item_new_order) {
                openFragment(NewOrderFragment.newInstance());
            } else if (menuItem.getItemId() == R.id.item_all_orders) {
                openFragment(MyOrdersFragment.newInstance());
            }
            return true;
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.grpContainer, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                AuthService.getInstance().logout();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
