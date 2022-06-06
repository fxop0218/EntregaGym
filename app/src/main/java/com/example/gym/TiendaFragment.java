package com.example.gym;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class TiendaFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        requireActivity().setContentView(R.layout.fragment_tienda);

        super.onCreate(savedInstanceState);

        ViewPager viewPager = requireActivity().findViewById(R.id.viewPager);
        TabLayout tabLayout = requireActivity().findViewById(R.id.tabLayout);

        TabItem tabMaterial = (TabItem) requireActivity().findViewById(R.id.tab1);
        TabItem tabSuplementos = (TabItem) requireActivity().findViewById(R.id.tab2);
        TabItem tabVestuario = (TabItem) requireActivity().findViewById(R.id.tab3);
    }
}