package com.example.ddopik.phlogbusiness.ui.commentimage.view;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseDialogFragment;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ReportModel;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ReportReason;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportImageFragment extends BaseDialogFragment {

    private RecyclerView reasonsRV;
    private EditText extra;
    private Button submit;

    private ReasonsAdapter adapter;

    private ReportModel model = new ReportModel();

    private List<ReportReason> reasons;
    private OnSubmitClickListener onSubmitClickListener;

    public ReportImageFragment() {
        // Required empty public constructor
    }

    public static ReportImageFragment newInstance(List<ReportReason> reasons, OnSubmitClickListener onSubmitClickListener) {
        ReportImageFragment fragment = new ReportImageFragment();
        fragment.reasons = reasons;
        fragment.onSubmitClickListener = onSubmitClickListener;
        return fragment;
    }

    @Override
    protected void setViews(View view) {
        reasonsRV = view.findViewById(R.id.reason_rv);
        adapter = new ReasonsAdapter(reasons);
        reasonsRV.setAdapter(adapter);
        extra = view.findViewById(R.id.extra_et);
        submit = view.findViewById(R.id.submit_btn);
    }

    @Override
    protected void setListeners() {
        adapter.setOnReasonClickListener(reason -> {
            for (ReportReason r : reasons)
                r.selected = false;
            reason.selected = true;
            adapter.notifyDataSetChanged();
            model.selectedReason = reason;
        });
        submit.setOnClickListener(v -> {
            if (onSubmitClickListener != null) {
                if (model.selectedReason == null) {
                    Toast.makeText(getContext(), R.string.choose_reason, Toast.LENGTH_SHORT).show();
                }
                model.extra = extra.getText().toString();
                onSubmitClickListener.onSubmitClick(this, model);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_image, container, false);
    }

    public interface OnSubmitClickListener {
        void onSubmitClick(DialogFragment fragment, ReportModel model);
    }
}
