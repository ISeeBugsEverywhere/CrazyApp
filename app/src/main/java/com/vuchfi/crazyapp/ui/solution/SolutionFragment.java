package com.vuchfi.crazyapp.ui.solution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vuchfi.crazyapp.R;

public class SolutionFragment extends Fragment {

    private SolutionViewModel solutionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        solutionViewModel = new ViewModelProvider(this).get(SolutionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_solution, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

    }
}