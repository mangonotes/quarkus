package org.acme.event.state.machine;

import io.vertx.core.json.JsonObject;

public record State(JsonObject data, TxnEvent current, TxnEvent next) {
}
