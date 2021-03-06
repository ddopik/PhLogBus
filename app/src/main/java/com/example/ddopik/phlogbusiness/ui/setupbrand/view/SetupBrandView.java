package com.example.ddopik.phlogbusiness.ui.setupbrand.view;

public interface SetupBrandView {
    void setLoading(boolean b);

    void setVerificationRequestSuccess(boolean success);

    interface Communicator {

        void handle(Type type, Object... objects);

        enum Type {
            PROGRESS, DONE, ERROR, ALL_UPLOADING_DONE
        }
    }
}
