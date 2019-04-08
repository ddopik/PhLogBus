package com.example.ddopik.phlogbusiness.ui.accountdetails.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseDialogFragment;
import io.reactivex.functions.BiConsumer;

public class ChangePasswordDialogFragment extends BaseDialogFragment {

    private BiConsumer<String, String> passwordsConsumer;

    private Button save, cancel;
    private EditText oldET, newET;
    private TextInputLayout oldLayout, newLayout;

    public static ChangePasswordDialogFragment getInstance(BiConsumer<String, String> passwordsConsumer) {
        ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment();
        fragment.passwordsConsumer = passwordsConsumer;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.dialog_change_password, container, false);
        return view;
    }

    @Override
    protected void setViews(View view) {
        save = view.findViewById(R.id.save_button);
        cancel = view.findViewById(R.id.cancel_button);
        oldET = view.findViewById(R.id.old_password_et);
        newET = view.findViewById(R.id.new_password_et);
        oldLayout = view.findViewById(R.id.old_password_layout);
        newLayout = view.findViewById(R.id.new_password_layout);
    }

    @Override
    protected void setListeners() {
        save.setOnClickListener(v -> {
            if (oldET.getText().toString().isEmpty()) {
                oldLayout.setError(getString(R.string.old_password_required));
            } else if (newET.getText().toString().isEmpty()) {
                newLayout.setError(getString(R.string.new_password_required));
            } else {
                try {
                    passwordsConsumer.accept(oldET.getText().toString(), newET.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dismiss();
                }
            }
        });
        cancel.setOnClickListener(v -> {
            dismiss();
        });
    }
}
