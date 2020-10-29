package com.vuchfi.crazyapp.ui.dcalc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vuchfi.crazyapp.R;

public class DCalcFragment extends Fragment {

    private DCalcViewModel DCalcViewModel; //unused; i'll learn about it later. A lot later...
    private Button calcButton;
    private Button quitButton;
    private TextView xField;
    private TextView ugenField;
    private TextView deltaTField;
    private TextView upeakField;
    private TextView rField;
    private RadioButton ohmButton;
    private RadioButton kOhmButton;
    private TextView eField;
    private CheckBox sButton;
    private TextView sField;
    private CheckBox dButton;
    private TextView dField;
    private double e0 = 8.85e-12; //[F/m]
    private double e = 3.5; //default value;
    private double Rapkr; //Ω
    private double factorR;
    private double Ugen; //V
    private double Upeak; //mV
    private double S; //mm^2
    private double S_;
    private double d; //mm
    private double deltaT; //μs
    private double dmkm = -1;
    private double dmkm_ = 0;

    //listener'iai:

    private final View.OnClickListener calcButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //code:
            calcButtonFunction();
        }
    };

    private final View.OnClickListener quitButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            quitButtonFunction();
        }
    };

    private final View.OnClickListener dButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (dButton.isChecked())
            {
                sButton.setChecked(false);
            }
            else
            {
                dButton.setChecked(true);
            }
        }
    };

    private final View.OnClickListener sButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (sButton.isChecked())
            {
                dButton.setChecked(false);
            }
            else
            {
                sButton.setChecked(true);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DCalcViewModel = new ViewModelProvider(this).get(DCalcViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dcalc, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //get all controls:
        calcButton = view.findViewById(R.id.calc_button);
        quitButton = view.findViewById(R.id.quit_button);
        xField = view.findViewById(R.id.x_field);
        ugenField = view.findViewById(R.id.ugen_field);
        deltaTField = view.findViewById(R.id.delta_t_field);
        upeakField = view.findViewById(R.id.upeak_field);
        rField = view.findViewById(R.id.rapkr_field);
        ohmButton = view.findViewById(R.id.ohms_button);
        kOhmButton = view.findViewById(R.id.kohms_button);
        eField = view.findViewById(R.id.epsilon_field);
        sButton = view.findViewById(R.id.s_button);
        sField = view.findViewById(R.id.s_field);
        dButton = view.findViewById(R.id.d_button);
        dField = view.findViewById(R.id.d_field);
        //listenerių priskyrimas:
        quitButton.setOnClickListener(quitButtonClickListener);
        calcButton.setOnClickListener(calcButtonClickListener);
        sButton.setOnClickListener(sButtonClickListener);
        dButton.setOnClickListener(dButtonClickListener);
    }


    //naudingos funkcijos:
    private void calcButtonFunction()
    {
        try
        {


            xField.setText("Atsakymas :) μm");
            Ugen = Double.parseDouble(ugenField.getText().toString());
            Upeak = Double.parseDouble(upeakField.getText().toString());
            Rapkr = Double.parseDouble(rField.getText().toString());
            e = Double.parseDouble(eField.getText().toString());
            d = Double.parseDouble(dField.getText().toString());
            S = Double.parseDouble(sField.getText().toString());
            deltaT = Double.parseDouble(deltaTField.getText().toString());
            get_dmkm();
            String ss = "Atsakymas x μm";
            xField.setText(ss.replace("x", String.valueOf(dmkm_)));
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), "Įvesta ne skaičiai?", Toast.LENGTH_SHORT).show();
        }

    }

    private void quitButtonFunction()
    {
        Toast.makeText(getActivity(), "Išjungiama", Toast.LENGTH_SHORT).show();
        getActivity().finishAndRemoveTask();
    }

    private void get_dmkm ()
    {
        if (ohmButton.isChecked())
        {
            factorR = 1;
        }
        else if (kOhmButton.isChecked())
        {
            factorR = 1000;
        }
        else
        {
            factorR = 1;
        }
        if (dButton.isChecked())
        {
            S_ = 1e-6*Math.PI * d*d/4;
        }
        else if (sButton.isChecked())
        {
            S_ = 1e-6*S;
        }
        dmkm = e*e0*Ugen/((deltaT*1e-6)*((Upeak*1e-3)/((Rapkr*factorR)*S_)));
        dmkm_ = Math.round((dmkm / 1e-6)*1000d)/1000d; //μm...
    }
}