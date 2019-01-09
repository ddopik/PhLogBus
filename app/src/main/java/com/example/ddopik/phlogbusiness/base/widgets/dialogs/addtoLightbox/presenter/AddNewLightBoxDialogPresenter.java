package com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view.AddToLightBoxDialogFragment;

import java.util.List;

public interface AddNewLightBoxDialogPresenter {

    void getLightBoxes();

    void addImagesToLightBox(List<LightBox> lightBoxId, int imageID, AddToLightBoxDialogFragment addToLightBoxDialogFragment);
}
