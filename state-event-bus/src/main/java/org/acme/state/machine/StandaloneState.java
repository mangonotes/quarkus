package org.acme.state.machine;

public interface StandaloneState {
    void next(Context context);
    void execute(Context context);
}
