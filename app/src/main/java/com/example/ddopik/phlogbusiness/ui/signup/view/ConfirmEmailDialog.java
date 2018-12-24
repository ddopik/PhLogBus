package com.example.ddopik.phlogbusiness.ui.signup.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.login.view.LoginActivity;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class ConfirmEmailDialog extends Dialog  {

    private Context context;
    private Button confirmBtn;


    public ConfirmEmailDialog(Context context){
        super(context);
        this.context =context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm_email);

        confirmBtn=findViewById(R.id.confirm_mail_btn);
        confirmBtn.setOnClickListener(v->{
            Intent intent=new Intent(context,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        });
    }


}