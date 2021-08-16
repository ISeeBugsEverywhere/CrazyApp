package com.vuchfi.ekspecialcalculator.ui.solution;

import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.vuchfi.ekspecialcalculator.R;

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
    private TextView massTextView;
    private TextView molarMassView;
    private TextView concentrationView;
    private TextView solutionField;
    private TextView dimensionsSolutionEntry;
    private TextView pickedVolumeEntry;

    private int mode = 0; // default mode is to calculate a volume of a solution.

    double mmass = 0.0;
    double pmass = 0.0;
    double molc = 0.0;
    double mM = 1.0;//jei 1.0 - molarai, jei
    double mG = 1.0;//jei 1.0 - gramai, jei 1e-3 - miligramai
    double tBP = 0.485;
    double LiTFSI = 0.275;
    double FK209 = 0.12;

    String default_molar = "20.0";
    String default_mol_mass = "1245.0";
    String default_mass = "10.0";
    String default_volume = "10.0";
    String default_volume_dim = "μl";

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

    private View.OnClickListener clearListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pickedMassEntry.setText("");
            pickedMassEntry.requestFocus();
            Selection.setSelection((Spannable) pickedMassEntry.getText(), pickedMassEntry.getText().length());
            if (molarMassEntry.getText().toString().equals(""))
            {
                molarMassEntry.setText(default_mol_mass);
            }
            if (molarsEntry.getText().toString().equals(""))
            {
                molarsEntry.setText(default_molar);
            }
            if (pickedVolumeEntry.getText().toString().equals(""))
            {
                pickedVolumeEntry.setText(default_volume);
                dimensionsSolutionEntry.setText(default_volume_dim);
            }
            mode = 2;
        }
    };

    private View.OnClickListener solutionFieldListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String txt = dimensionsSolutionEntry.getText().toString();
            if (txt.equals("ml"))
            {
                dimensionsSolutionEntry.setText("μl");
            }
            else if (txt.equals("μl"))
            {
                dimensionsSolutionEntry.setText("ml");
            }
            else
            {
                dimensionsSolutionEntry.setText("μl");
            }
            pickedVolumeEntry.requestFocus();
            pickedVolumeEntry.setText("");
            Selection.setSelection((Spannable) pickedVolumeEntry.getText(), pickedVolumeEntry.getText().length());
            if (molarMassEntry.getText().toString().equals(""))
            {
                molarMassEntry.setText(default_mol_mass);
            }
            if (molarsEntry.getText().toString().equals(""))
            {
                molarsEntry.setText(default_molar);
            }
            if (pickedMassEntry.getText().toString().equals(""))
            {
                pickedMassEntry.setText(default_mass);
            }
            mode = 0;
        }
    };

    private View.OnClickListener concentrationViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            molarsEntry.setText("");
            molarsEntry.requestFocus();
            Selection.setSelection((Spannable) molarsEntry.getText(), molarsEntry.getText().length());
            if (molarMassEntry.getText().toString().equals(""))
            {
                molarMassEntry.setText(default_mol_mass);
            }
            if (pickedVolumeEntry.getText().toString().equals(""))
            {
                pickedVolumeEntry.setText(default_volume);
                dimensionsSolutionEntry.setText(default_volume_dim);
            }
            if (pickedMassEntry.getText().toString().equals(""))
            {
                pickedMassEntry.setText(default_mass);
            }
            mode = 1;
        }
    };

    private View.OnClickListener molarMassViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            molarMassEntry.setText("");
            molarMassEntry.requestFocus();
            Selection.setSelection((Spannable) molarMassEntry.getText(), molarMassEntry.getText().length());
            if (pickedVolumeEntry.getText().toString().equals(""))
            {
                pickedVolumeEntry.setText(default_volume);
                dimensionsSolutionEntry.setText(default_volume_dim);
            }
            if (molarsEntry.getText().toString().equals(""))
            {
                molarsEntry.setText(default_molar);
            }
            if (pickedMassEntry.getText().toString().equals(""))
            {
                pickedMassEntry.setText(default_mass);
            }
            mode = 3;
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
        massTextView = view.findViewById(R.id.massTextView);
        molarMassView = view.findViewById(R.id.molarMassView);
        concentrationView = view.findViewById(R.id.concentrationView);
        solutionField = view.findViewById(R.id.solutionField);
        dimensionsSolutionEntry = view.findViewById(R.id.dimensionsSolutionEntry);
        pickedVolumeEntry = view.findViewById(R.id.pickedVolumeEntry);
        //listent'eriai
        solutionButton.setOnClickListener(solutionButtonListener);
        additivesButton.setOnClickListener(additivesButtonListener);
        massTextView.setOnClickListener(clearListener);
        //
        molarMassView.setOnClickListener(molarMassViewListener);
        concentrationView.setOnClickListener(concentrationViewListener);
        solutionField.setOnClickListener(solutionFieldListener);

    }

    private void solutionFunction()
    {
        if (mode == 0)
        {
            solution();
        }
        else if (mode == 1)
        {
            solution_II();
        }
        else if (mode == 2)
        {
            solution_III();
        }
    }

    private void solution_II()
    {
//        molarsEntry skaičiuojamas
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
            double moles = (pmass*mG)/mmass; //an amount in moles
            double solutionVolume = Double.parseDouble(pickedVolumeEntry.getText().toString());
            if (dimensionsSolutionEntry.getText().toString().equals("μl"))
            {
                solutionVolume = solutionVolume * 1e-3; //in milliliters (μl -> ml)
            }
            double molars  = moles / solutionVolume; //in milliMolars!
            molarsEntry.setText(String.valueOf(molars));

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), "PROBLEMOS: II()", Toast.LENGTH_SHORT).show();
        }

    }

    private void solution_III()
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

        }
        catch (Exception ex)
        {

        }
    }

    private void solution()
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
            if (microliters < 1000.0) {
                pickedVolumeEntry.setText(String.valueOf(microliters));
                dimensionsSolutionEntry.setText("μl");
            }
            else
            {
                pickedVolumeEntry.setText(String.valueOf(milliliters));
                dimensionsSolutionEntry.setText("ml");
            }
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