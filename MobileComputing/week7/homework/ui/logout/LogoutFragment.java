package com.example.mc_week7_homework.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mc_week7_homework.MainActivity;
import com.example.mc_week7_homework.R;
import com.example.mc_week7_homework.databinding.FragmentLogoutBinding;
import com.google.android.material.navigation.NavigationView;

public class LogoutFragment extends Fragment {
    private FragmentLogoutBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogoutViewModel logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View navHeader = navigationView.getHeaderView(0);
        ImageView icon_img = (ImageView) navHeader.findViewById(R.id.icon_img);
        TextView name_text = (TextView) navHeader.findViewById(R.id.name_text);
        TextView email_text = (TextView) navHeader.findViewById(R.id.email_text);

        // 로그아웃 (초기상태로 되돌리기)
        icon_img.setImageResource(R.mipmap.ic_launcher_round);
        name_text.setText(R.string.nav_header_title);
        email_text.setText(R.string.nav_header_subtitle);

        final TextView textView = binding.textLogout;
        logoutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        CheckBox check_login = (CheckBox) ((MainActivity) getActivity()).findViewById(R.id.check_login);
        check_login.setChecked(false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
