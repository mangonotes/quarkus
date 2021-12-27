package org.acme.state.machine;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {
    @Test
    void testExecute(){
        JsonObject data = new JsonObject().put("name","emp1").put("dept", "IT");
        Context context = new Context(new CreateState(), data);
        context.execute();
        context.next();
        assertTrue(context.getState() instanceof SaveDatabase);
        context.execute();
        context.next();
        assertTrue(context.getState() instanceof SendMail);
        context.execute();
        context.next();
        assertTrue(context.getState() instanceof ExitState);
        context.execute();

    }

}