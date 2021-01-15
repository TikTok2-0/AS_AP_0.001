package com.example.recyclerview;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class ActivitySwitcher extends Fragment {

    public ActivitySwitcher() {
    }
    public void change(){
        NavHostFragment.findNavController(ActivitySwitcher.this).navigate(R.id.MainActivity_to_menu1);
    }
}
