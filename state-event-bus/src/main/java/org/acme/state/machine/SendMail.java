package org.acme.state.machine;

public class SendMail implements StandaloneState {

    public SendMail() {
    }

    @Override
    public void next(Context context) {
        context.setState(new ExitState());

    }

    @Override
    public void execute(Context context) {
        System.out.println("Send Email" + context.getData());

    }
}
