package com.example.ddopik.phlogbusiness.base.widgets.dialogs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadPhotosDialog1Fragment extends BaseDialogFragment {

    private TextView title, message, option0, option1;

    private int titleRes, messageRes, option0Res, option1Res;
    private Function1<UploadPhotosDialog1Fragment, Integer, Object> optionConstumer;

    @Override
    protected void setViews(View view) {
        title = view.findViewById(R.id.title);
        message = view.findViewById(R.id.message);
        option0 = view.findViewById(R.id.option_0);
        option1 = view.findViewById(R.id.option_1);

        if (titleRes != 0)
            title.setText(titleRes);
        if (messageRes != 0)
            message.setText(messageRes);
        if (option0Res != 0)
            option0.setText(option0Res);
        if (option1Res != 0)
            option1.setText(option1Res);
    }

    @Override
    protected void setListeners() {
        View.OnClickListener listener = v -> {
            if (optionConstumer != null) {
                switch (v.getId()) {
                    case R.id.option_0:
                        optionConstumer.accept(this, 0);
                        break;
                    case R.id.option_1:
                        optionConstumer.accept(this, 1);
                        break;
                }
            }
        };
        option0.setOnClickListener(listener);
        option1.setOnClickListener(listener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog1, container, false);
    }

    public static class Builder {
        private int titleRes, messageRes, option0Res, option1Res;
        private boolean cancelable;
        private Function1<UploadPhotosDialog1Fragment, Integer, Object> optionConstumer;
        private FragmentManager fragmentManager;

        public Builder(Fragment fragment) {
            fragmentManager = fragment.getChildFragmentManager();
        }

        public Builder(AppCompatActivity activity) {
            fragmentManager = activity.getSupportFragmentManager();
        }

        public Builder title(int titleRes) {
            this.titleRes = titleRes;
            return this;
        }

        public Builder message(int messageRes) {
            this.messageRes = messageRes;
            return this;
        }

        public Builder option0(int option0Res) {
            this.option0Res = option0Res;
            return this;
        }

        public Builder option1(int option1Res) {
            this.option1Res = option1Res;
            return this;
        }

        public Builder optionConsumer(Function1<UploadPhotosDialog1Fragment, Integer, Object> optionConstumer) {
            this.optionConstumer = optionConstumer;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public UploadPhotosDialog1Fragment create() {
            UploadPhotosDialog1Fragment fragment = new UploadPhotosDialog1Fragment();
            fragment.titleRes = titleRes;
            fragment.messageRes = messageRes;
            fragment.option0Res = option0Res;
            fragment.option1Res = option1Res;
            fragment.cancelable = cancelable;
            fragment.optionConstumer = optionConstumer;
            return fragment;
        }

        public void show() {
            UploadPhotosDialog1Fragment fragment = new UploadPhotosDialog1Fragment();
            fragment.titleRes = titleRes;
            fragment.messageRes = messageRes;
            fragment.option0Res = option0Res;
            fragment.option1Res = option1Res;
            fragment.cancelable = cancelable;
            fragment.optionConstumer = optionConstumer;
            fragment.show(getFramentTransaction(), UploadPhotosDialog1Fragment.class.getSimpleName());
        }

        private FragmentTransaction getFramentTransaction() {
            return fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down);
        }
    }
}
