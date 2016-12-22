package com.eclipsesource.json;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JsonParserTest {

    @Test
    public void parsePositiveExponentNumber() throws Exception {
        Assert.assertEquals(25000, parseValue("2.5e4").asDouble(), 1e-10);
    }

    @Test
    public void parseNegativeExponentNumber() throws Exception {
        Assert.assertEquals(0.00025d, parseValue("2.5e-4").asDouble(), 1e-10);
    }

    @Test
    public void parseNegativeNumberWithPositiveExponent() throws Exception {
        Assert.assertEquals(-25000d, parseValue("-2.5e4").asDouble(), 1e-10);
    }

    @Test
    public void parseNegativeNumberWithNegativeExponent() throws Exception {
        Assert.assertEquals(-0.00025d, parseValue("-2.5e-4").asDouble(), 1e-10);
    }

    @Test
    public void parseInteger() throws Exception {
        Assert.assertEquals(10, parseValue("10").asInt(), 1e-10);
    }

    @Test
    public void parseNegativeInteger() throws Exception {
        Assert.assertEquals(-10, parseValue("-10").asInt(), 1e-10);
    }

    @Test
    public void parseIntegerWithFraction() throws Exception {
        Assert.assertEquals(10.05d, parseValue("10.05").asDouble(), 1e-10);
    }

    @Test
    public void parseNegativeIntegerWithFraction() throws Exception {
        Assert.assertEquals(-10.05d, parseValue("-10.05").asDouble(), 1e-10);
    }

    private JsonValue parseValue(String value) throws IOException {
        JsonParser jsonParser = new JsonParser("{\"value\": " + value + "}");
        return jsonParser.parse().asObject().get("value");
    }

}