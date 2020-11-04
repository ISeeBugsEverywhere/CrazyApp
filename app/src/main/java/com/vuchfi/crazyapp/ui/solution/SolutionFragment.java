package com.vuchfi.crazyapp.ui.solution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.vuchfi.crazyapp.R;

public class SolutionFragment extends Fragment {

    private SolutionViewModel solutionViewModel;
    private TextView molarMassEntry;
    private TextView molarsEntry;
    private TextView pickedMassEntry;
    private RadioButton mgButton; //milligrams
    private RadioButton gButton; //grams
    private RadioButton mMButton; //millimolars
    private RadioButton MButton; //molars
    private Button solutionButton;
    private Button additivesButton;

    double mmass = 0.0;
    double pmass = 0.0;
    double molc = 0.0;
    double mM = 1.0;//jei 1.0 - molarai, jei
    double mG = 1.0;//jei 1.0 - gramai, jei 1e-3 - miligramai
    double tBP = 0.485;
    double LiTFSI = 0.275;
    double FK209 = 0.12;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        solutionViewModel = new ViewModelProvider(this).get(SolutionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_solution, container, false);
        return root;
    }

    private View.OnClickListener solutionButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            solutionFunction();
        }
    };

    private View.OnClickListener additivesButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            additivesFunction();
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        molarMassEntry = view.findViewById(R.id.molarMassEntry);
        molarsEntry = view.findViewById(R.id.molarsEntry);
        pickedMassEntry = view.findViewById(R.id.pickedMassEntry);
        mgButton = view.findViewById(R.id.mgButton);
        gButton = view.findViewById(R.id.gButton);
        mMButton = view.findViewById(R.id.mMButton);
        MButton = view.findViewById(R.id.MButton);
        solutionButton = view.findViewById(R.id.solutionButton);
        additivesButton = view.findViewById(R.id.additivesButton);
        //listent'eriai
        solutionButton.setOnClickListener(solutionButtonListener);
        additivesButton.setOnClickListener(additivesButtonListener);
    }

    private void solutionFunction()
    {

        if (mMButton.isChecked()){
            mM = 1e-3;
        }
        else if (MButton.isChecked())
        {
            mM = 1.0;
        }
        if (mgButton.isChecked())
        {
            mG = 1e-3;
        }
        else if (gButton.isChecked())
        {
            mG = 1.0;
        }

        try {
            mmass = Double.parseDouble(molarMassEntry.getText().toString());
            pmass = Double.parseDouble(pickedMassEntry.getText().toString());
            molc = Double.parseDouble(molarsEntry.getText().toString());
            double mgml = mM*molc*mmass;
            double liters = (pmass*mG)/mgml;
            double milliliters = Math.round((liters/1e-3)*1e4)/1e4;
            double microliters = milliliters*1000.0;
            Snackbar.make(getView(), String.valueOf(milliliters)+" ml,\narba "+String.valueOf(microliters)+" μl", 3000).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), "Problemos", Toast.LENGTH_SHORT).show();
        }
    }

    private void additivesFunction()
    {

        if (mMButton.isChecked()){
            mM = 1e-3;
        }
        else if (MButton.isChecked())
        {
            mM = 1.0;
        }
        if (mgButton.isChecked())
        {
            mG = 1e-3;
        }
        else if (gButton.isChecked())
        {
            mG = 1.0;
        }
        try {
            mmass = Double.parseDouble(molarMassEntry.getText().toString());
            pmass = Double.parseDouble(pickedMassEntry.getText().toString());
            molc = Double.parseDouble(molarsEntry.getText().toString());
            double mgml = mM*molc*mmass;
            double liters = (pmass*mG)/mgml;
            double milliliters = Math.round((liters/1e-3)*1e4)/1e4;
            double div = 1/(milliliters);
            double vtBP = Math.round((tBP/div)*100000)/100000;
            double vLiTFSI = Math.round((LiTFSI/div)*100000)/100000;
            double vFK209 = Math.round((FK209/div)*100000)/100000;
            String TBP = "tBP : "+String.valueOf(vtBP)+" ml";
            String LITFSI = "LiTFSI (520 mg/ml) : "+String.valueOf(vLiTFSI)+" ml";
            String FK = "FK209 (320 mg/ml) : "+ String.valueOf(vFK209)+" ml";
            Snackbar.make(getView(), TBP+"\n"+LITFSI+"\n"+FK, 5000).show();
        }
        catch (Exception exception)
        {
            Toast.makeText(getActivity(), "Problemos", Toast.LENGTH_SHORT).show();
        }

    }
}