package dk.sdu.se_f22.brandmodule.infrastructure;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class TokenizationParametersAdapter extends TypeAdapter<TokenizationParameters> {
    @Override
    public TokenizationParameters read(JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldName = null;
        String delimiter = null;
        String ignore = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldName = reader.nextName();
            }

            if ("delimiterRegex".equals(fieldName)) {
                //move to next token
                token = reader.peek();
                delimiter = reader.nextString();
            }

            if ("ignoreRegex".equals(fieldName)) {
                //move to next token
                token = reader.peek();
                ignore = reader.nextString();
            }
        }
        reader.endObject();

        return new TokenizationParameters(delimiter, ignore);
    }

    @Override
    public void write(JsonWriter writer, TokenizationParameters student) throws IOException {
        writer.beginObject();
        writer.name("delimiterRegex");
        writer.value(student.delimiterRegex);
        writer.name("ignoreRegex");
        writer.value(student.ignoreRegex);
        writer.endObject();
    }
}
