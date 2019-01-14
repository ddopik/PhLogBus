package com.example.ddopik.phlogbusiness.ui.setupbrand.view;

public interface SetupBrandView {
    void setLoading(boolean b);

    interface Communicator {

        void handle(Type type, Object... objects);

        enum Type {
            PROGRESS, ALL_UPLOADING_DONE
        }
    }
}
