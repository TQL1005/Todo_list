package com.fatscompany.bookseftonline.FragmentCode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fatscompany.bookseftonline.R;
import com.fatscompany.bookseftonline.UserSessionManager;
import com.fatscompany.bookseftonline.Utils.Login;
import com.fatscompany.bookseftonline.databinding.FragmentMainMoreBinding;


public class FragmentMainMore extends Fragment {
    private FragmentMainMoreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_more, container, false);

        Button btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserSessionManager sessionManager = new UserSessionManager(getActivity());
                sessionManager.clearUserDetails();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });
        return view;
    }
}