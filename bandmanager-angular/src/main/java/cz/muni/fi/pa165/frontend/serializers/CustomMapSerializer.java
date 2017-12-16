package cz.muni.fi.pa165.frontend.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class CustomMapSerializer extends JsonSerializer<Map> {

    @Override
    public void serialize(Map obj, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        Map<?, ?> map = obj;
        generator.writeStartArray();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            generator.writeStartObject();
            generator.writeObjectField("key", entry.getKey());
            generator.writeObjectField("value", entry.getValue());
            generator.writeEndObject();
        }
        generator.writeEndArray();
    }
}
