package com.example.ddopik.phlogbusiness.utiltes;

import com.example.ddopik.phlogbusiness.R;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.CampaignStatus.*;

public interface CampaignStatusStringProvider {

    default int getStatus(int status) {
        switch (status) {
            case CAMPAIGN_STATUS_CANCELLED:
                return R.string.campaign_status_cancelled;
            case CAMPAIGN_STATUS_REQUEST:
                return R.string.campaign_status_requested;
            case CAMPAIGN_STATUS_PENDING:
                return R.string.campaign_status_pending;
            case CAMPAIGN_STATUS_APPROVED:
                return R.string.campaign_status_approved;
            case CAMPAIGN_STATUS_RUNNING:
                return R.string.campaign_status_running;
            case CAMPAIGN_STATUS_PRIZE_PROCESSING:
                return R.string.campaign_status_processing;
            case CAMPAIGN_STATUS_COMPLETED:
                return R.string.campaign_status_completed;
            default:
                return R.string.campaign_status_unknown;
        }
    }
}
