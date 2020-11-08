package com.vuchfi.ekspecialcalculator.ui.others;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vuchfi.ekspecialcalculator.R;

public class OthersFragment extends Fragment {

    private OthersViewModel othersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        othersViewModel = new ViewModelProvider(this).get(OthersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_others, container, false);
        return root;
    }
}