package org.acme.state.machine;

public class ExitState implements StandaloneState {
    public ExitState() {
    }

    @Override
    public void next(Context context) {
        System.out.println("EXIT State for more next allowed");

    }

    @Override
    public void execute(Context context) {
        System.out.println("EXIT state " + context.getData());

    }
}
