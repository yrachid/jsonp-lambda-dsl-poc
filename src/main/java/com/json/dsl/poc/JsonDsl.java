package com.json.dsl.poc;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.util.function.Consumer;

public class JsonDsl {

    public static final class ObjectDsl {

        private JsonObjectBuilder _builder = Json.createObjectBuilder();

        public ObjectDsl string(String name, String value) {
            _builder.add(name, value);
            return this;
        }

        public ObjectDsl integer(String name, Integer value) {
            _builder.add(name, value);
            return this;
        }

        public ObjectDsl object(String name, Consumer<ObjectDsl> objectApplier) {
            ObjectDsl value = new ObjectDsl();
            objectApplier.accept(value);

            _builder.add(name, value.build());

            return this;
        }

        private JsonValue build() {
            return _builder.build();
        }
    }

    public static JsonValue jsonObject(Consumer<ObjectDsl> applier) {

        ObjectDsl rootReceiver = new ObjectDsl();

        applier.accept(rootReceiver);

        return rootReceiver.build();
    }
}
