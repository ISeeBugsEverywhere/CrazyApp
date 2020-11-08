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

    double solutionVolume = -1.0;


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
            solutionVolume = milliliters;
            Snackbar.make(getView(), String.valueOf(milliliters)+" ml,\narba "+String.valueOf(microliters)+" μl", 3000).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), "Problemos", Toast.LENGTH_SHORT).show();
        }
    }

    private void additivesFunction()
    {

        if (solutionVolume <= 0.0)
        {
            solutionFunction();
            showAdditives();
        }
        else
        {
            showAdditives();
        }

    }

    private void showAdditives()
    {
        LiTFSI = Math.round((solutionVolume * 0.275)*10000.0)/10000.0;
        tBP = Math.round((solutionVolume * 0.485)*10000.0)/10000.0;
        FK209 = Math.round((solutionVolume * 0.12)*10000.0)/10000.0;
        String answer = "tBP - "+String.valueOf(tBP)+" ml\nLiTFSI - "+String.valueOf(LiTFSI)+" ml\nFK209 - "+String.valueOf(FK209)+" ml";
        Snackbar snackbar = Snackbar.make(getView(), answer, 5000);
        View sbView = snackbar.getView();
        TextView textView =  sbView.findViewById(R.id.snackbar_text);
        // For multi-line text, limit max line count.
        textView.setMaxLines(3);
        snackbar.show();

    }
}