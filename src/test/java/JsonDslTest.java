import org.junit.Test;

import javax.json.JsonNumber;
import javax.json.JsonValue;

import static com.json.dsl.poc.JsonDsl.jsonArray;
import static com.json.dsl.poc.JsonDsl.jsonObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JsonDslTest {

    @Test
    public void creates_integer_attributes() {
        JsonValue value = jsonObject(object -> object
            .integer("ten", 10)
        );

        assertThat(value.asJsonObject().getInt("ten"), is(10));
    }

    @Test
    public void creates_nested_objects() {
        JsonValue value = jsonObject(object -> object
            .object("albums", bla -> bla
                .integer("count", 8)
            )
        );

        JsonNumber albumCount = value.asJsonObject().getJsonObject("albums").getJsonNumber("count");

        assertThat(albumCount.intValue(), is(8));
    }

    @Test
    public void creates_complex_objects_that_mix_different_data_types() {
        JsonValue value = jsonObject(object -> object
            .string("artist", "Emicida")
            .object("albums", bla -> bla
                .integer("count", 8)
            )
        );

        JsonNumber albumCount = value.asJsonObject().getJsonObject("albums").getJsonNumber("count");
        String artist = value.asJsonObject().getString("artist");

        assertThat(artist, is("Emicida"));
        assertThat(albumCount.intValue(), is(8));
    }

    @Test
    public void creates_root_arrays() {
        JsonValue value = jsonArray(array -> array
            .string("a")
            .integer(10)
            .object(person -> person
                .string("name", "Doe")
            )
        );

        assertThat(value.asJsonArray().getString(0), is("a"));
        assertThat(value.asJsonArray().getInt(1), is(10));
        assertThat(value.asJsonArray().getJsonObject(2).getString("name"), is("Doe"));
    }
}
