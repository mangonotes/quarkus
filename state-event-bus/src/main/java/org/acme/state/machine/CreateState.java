package org.acme.state.machine;

public class CreateState implements StandaloneState {
    public CreateState() {
    }

    @Override
    public void next(Context context) {
        context.setState(new SaveDatabase());
    }

    @Override
    public void execute(Context context) {
        System.out.println("Create " + context.getData());

    }
}
