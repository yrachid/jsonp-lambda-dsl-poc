import org.junit.Test;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

import static com.json.dsl.poc.JsonDsl.jsonObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JsonDslTest {

    @Test
    public void allows_integers_and_nested_objects() {
        JsonValue value = jsonObject(object -> object
            .string("name", "Emicida")
            .object("albums", bla -> bla
                .integer("count", 8)
            )
        );

        JsonNumber albumCount = value.asJsonObject().getJsonObject("albums").getJsonNumber("count");
        JsonString artist = value.asJsonObject().getJsonString("name");

        assertThat(artist.getString(), is("Emicida"));
        assertThat(albumCount.intValue(), is(8));
    }
}
