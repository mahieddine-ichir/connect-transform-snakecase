package net.thinatech.connect.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class SnakeTransformerTest {

    SnakeTransformer snakeTransformer = new SnakeTransformer();

    @Test
    public void convert_on_json_asMap() throws IOException {

        byte[] jsonBytes = Files.readAllBytes(Paths.get(this.getClass().getResource("/data.json").getPath()));
        Map input = new ObjectMapper().readValue(jsonBytes, Map.class);
        Map output = (Map) snakeTransformer.convert(input);

        //byte[] transformed = (byte[]) new SnakeTransformer().convert(jsonBytes);
        //Map output = new ObjectMapper().readValue(transformed, Map.class);

        Assert.assertEquals("ichir", output.get("id"));
        Assert.assertEquals("mahieddine.ichir@gmail.com", output.get("user_email"));

        Map civility = (Map) output.get("civility_information");
        Assert.assertEquals("mahieddine", civility.get("first_name"));
        Assert.assertEquals("ichir", civility.get("last_name"));

        Map address = (Map) civility.get("address");
        Assert.assertEquals("10 Rue du vélo", address.get("line1"));
        Assert.assertEquals("FR", address.get("country_reference"));

        List tags = (List) output.get("tags");
        Map tag1 = (Map) tags.get(1);

        Assert.assertEquals("tag2", tag1.get("name"));
        Assert.assertEquals("tagCat2", tag1.get("tag_category"));
    }
}
