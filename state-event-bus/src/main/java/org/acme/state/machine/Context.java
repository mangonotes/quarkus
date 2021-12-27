package org.acme.state.machine;

import io.vertx.core.json.JsonObject;

public class Context {
    private StandaloneState state;
    private JsonObject data;

    public void next(){
        this.state.next(this);
    }
    public void execute(){
        this.state.execute(this);
    }

    public StandaloneState getState() {
        return state;
    }

    public void setState(StandaloneState state) {
        this.state = state;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public Context(StandaloneState state, JsonObject data){
        this.state= state;
        this.data=data;

    }
}
