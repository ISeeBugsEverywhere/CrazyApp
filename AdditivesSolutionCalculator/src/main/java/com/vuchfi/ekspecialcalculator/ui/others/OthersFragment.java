package com.vuchfi.ekspecialcalculator.ui.others;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vuchfi.ekspecialcalculator.R;

public class OthersFragment extends Fragment {

    private OthersViewModel othersViewModel;
    //private members:
    private TextView picked_mass;
    private TextView molar_mass;
    private TextView walls_volume;
    private Button calculate_button;
    private TextView answer_label;
    private TextView area;
    //


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        othersViewModel = new ViewModelProvider(this).get(OthersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_others, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        picked_mass = view.findViewById(R.id.picked_mass);
        molar_mass = view.findViewById(R.id.molar_mass);
        walls_volume = view.findViewById(R.id.waals_volume);
        calculate_button = view.findViewById(R.id.calculate_button);
        answer_label = view.findViewById(R.id.d_answer_text);
        area = view.findViewById(R.id.area_box);
//        listener'iai:
        calculate_button.setOnClickListener(calculateListener);

    }

    private View.OnClickListener calculateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calclulate_fn();
        };
    };

    private void calclulate_fn()
    {
        try {
//            double m_p = 0.0;
//            double m_molar = 0.0;
//            double vol_walls = 0.0;
//            double S = 0.0;
            double m_p = Double.parseDouble(picked_mass.getText().toString());
            double m_molar = Double.parseDouble(molar_mass.getText().toString());
            double vol_walls = Double.parseDouble(walls_volume.getText().toString()) * 1e-30;//m^3
            double S = Double.parseDouble(area.getText().toString()) * 1e-4;//m^2 <-- cm^2 į metrus
            if (S >= 1e-8 && m_p >= 1e-4 && m_molar >= 1e-3 && vol_walls >= 1e-30) {
                double Na = 6.03e23;
                double N_moles = m_p / m_molar * 1e-3; //moles
                double N_molecules = N_moles * Na;
                double volume = N_molecules * vol_walls; //volume in m^3;
                double d = volume / S; //metrais
                double dum = Math.round((d / 1e-6) * 1e4) / 1e4; //metrai į  μmetrus
                answer_label.setText("d = " + String.valueOf(dum) + " μm");
            }
            else
            {
                Toast.makeText(getActivity(), "ZERO in input?", Toast.LENGTH_SHORT).show();
                answer_label.setText("d = x? μm");
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), "ZERO in input?", Toast.LENGTH_SHORT).show();
            answer_label.setText("d = x? μm");
        }
    }
}