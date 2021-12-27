package org.acme.state.machine;

public class SaveDatabase implements StandaloneState {

    public SaveDatabase() {

    }

    @Override
    public void next(Context context) {
        context.setState(new SendMail());
    }

    @Override
    public void execute(Context context) {
        System.out.println("Save Database" + context.getData());

    }


}
