package com.example.math;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.math.databinding.FragmentLoseBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoseFragment extends Fragment {


    public LoseFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final CalculationModel model;
        model = ViewModelProviders.of(getActivity(), new SavedStateVMFactory(requireActivity())).get(CalculationModel.class);
        FragmentLoseBinding binding;
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lose,container,false);
        binding.setData(model);
        binding.setLifecycleOwner(requireActivity());
        binding.button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getCurrentScore().setValue(0);
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_loseFragment_to_welcomeFragment);
            }
        });
        return binding.getRoot();
    }

}
