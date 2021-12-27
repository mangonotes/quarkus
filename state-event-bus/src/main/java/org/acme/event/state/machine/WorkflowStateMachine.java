package org.acme.event.state.machine;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Random;

@ApplicationScoped
public class WorkflowStateMachine {
    @Inject
    ManagedExecutor executor;
    @Inject
    EventBus eventBus;
    private static Logger LOGGER = LoggerFactory.getLogger(WorkflowStateMachine.class);



    @ConsumeEvent("CREATE")
    public Uni<State> create(State state){
        LOGGER.info("Create Events {}", state);
        State next = new State(state.data(), state.next(), TxnEvent.SEND_EMAIL);
       return eventBus.<State>request(next.current().name(),next).onItem().transform(Message::body);
    }

    @ConsumeEvent("SAVE_DATABASE")
    public Uni<State> saveRecord(State state){
        Random random = new Random();
        LOGGER.info("Save Record into DB {}", state);
        JsonObject data = state.data().put("ID",  random.nextInt(100000));
        State next = new State(data, state.next(), TxnEvent.EXIT);
        return eventBus.<State>request(next.current().name(),next).onItem().transform(Message::body);
    }

    @ConsumeEvent("SEND_EMAIL")
    public Uni<State> sendMail(State state){
        LOGGER.info("Send mail {}", state);
        State next = new State(state.data(), state.next(), TxnEvent.EXIT);
        return eventBus.<State>request(next.current().name(),next).onItem().transform(Message::body);
    }

    @ConsumeEvent("EXIT")
    public State exit(State state){
        LOGGER.info("Exit event {}", state);
        return new State(state.data(), state.next(), TxnEvent.EXIT);
    }

}
