package org.acme;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.acme.event.state.machine.State;
import org.acme.event.state.machine.TxnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1")
public class GreetingResource {
    private static Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);
    @Inject
    EventBus eventBus;


    @GET
    @Path("/produce")
 @Produces(MediaType.TEXT_PLAIN)
    public Uni<State> process(){
        JsonObject data = new JsonObject().put("name", "emp1").put("Dept", "IT").put("ID", "");
        LOGGER.info("data {}" ,data);
        State state = new State(data, TxnEvent.CREATE, TxnEvent.SAVE_DATABASE);
        LOGGER.info("state {}", state);
        return eventBus.<State>request(state.current().name(), state).onItem().transform(Message::body);
    }
}