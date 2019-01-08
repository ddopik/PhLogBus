package com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;

import java.util.List;

public interface AddToLightBoxDialogFragmentView {

    void viewLightBoxes(List<LightBox> lightBoxList);

    void viewLightBoxProgress(Boolean state);
}
