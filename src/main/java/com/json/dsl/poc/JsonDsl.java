package com.json.dsl.poc;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.util.function.Consumer;

public class JsonDsl {

    // TODO: Can it be a (package) private class?
    // Should type uniformity be a concern or a possibility granted by the API? If so, perhaps a partial builder?
    public static final class ArrayDsl {
        private final JsonArrayBuilder _builder = Json.createArrayBuilder();

        public ArrayDsl string(String value) {
            _builder.add(value);
            return this;
        }

        public ArrayDsl integer(Integer value) {
            _builder.add(value);
            return this;
        }

        public ArrayDsl object(Consumer<ObjectDsl> applier) {
            ObjectDsl receiver = new ObjectDsl();

            applier.accept(receiver);

            _builder.add(receiver.build());
            return this;
        }

        private JsonValue build() {
            return _builder.build();
        }
    }

    // Can this interface be exposed beyond the package level without
    // exposing the existence of the ObjectDsl class?
    interface ObjectDslApplier extends Consumer<ObjectDsl> {
    }

    // TODO: Can it be a (package) private class?
    // Maybe expose just ObjectDslApplier?
    public static final class ObjectDsl {

        private final JsonObjectBuilder _builder = Json.createObjectBuilder();

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

    public static JsonValue jsonArray(Consumer<ArrayDsl> applier) {
        ArrayDsl rootReceiver = new ArrayDsl();

        applier.accept(rootReceiver);

        return rootReceiver.build();
    }
}
