package com.vsc.androidexam2020.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.vsc.androidexam2020.R;
import com.vsc.androidexam2020.data.local.Order;
import com.vsc.androidexam2020.data.remote.AuthService;
import com.vsc.androidexam2020.data.remote.api.ApiWrapper;
import com.vsc.androidexam2020.databinding.FragmentMyOrdersBinding;

import java.util.List;

public class MyOrdersFragment extends Fragment {

    private static final String TAG = "MyOrdersFragment";
    private static MyOrdersFragment instance;

    private FragmentMyOrdersBinding binding;

    public static MyOrdersFragment newInstance() {
        if(instance == null) instance = new MyOrdersFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_my_orders, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiWrapper.getInstance().getAllOrders(AuthService.getInstance().getLoggedUser().getId(), this::onOrdersReceived);
    }

    private void onOrdersReceived(List<Order> data) {
        // TODO Ex 10
    }
}
