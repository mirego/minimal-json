package com.eclipsesource.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OptimizedJsonNumberTest {

    @Test
    public void test_full() {
        // 123.4567e2
        OptimizedJsonNumber number = new OptimizedJsonNumber(123, 4567, (byte) 4, (short) 2, true);

        assertEquals("123.4567e2", number.toString());
        assertEquals(12345, number.asInt());
        assertEquals(12345l, number.asLong());
        assertEquals(12345.67f, number.asFloat(), 1e-3);
        assertEquals(12345.67, number.asDouble(), 1e-10);
    }

    @Test
    public void test_integer_only() {
        // 12345
        OptimizedJsonNumber number = new OptimizedJsonNumber(12345, -1, (byte) 0, (short) 0, true);

        assertEquals("12345", number.toString());
        assertEquals(12345, number.asInt());
        assertEquals(12345l, number.asLong());
        assertEquals(12345f, number.asFloat(), 1e-3);
        assertEquals(12345d, number.asDouble(), 1e-10);
    }

    @Test
    public void test_integer_neg_exp() {
        // 12345e-2
        OptimizedJsonNumber number = new OptimizedJsonNumber(12345, -1, (byte) 0, (short) -2, true);

        assertEquals("12345e-2", number.toString());
        assertEquals(123, number.asInt());
        assertEquals(123l, number.asLong());
        assertEquals(123.45f, number.asFloat(), 1e-3);
        assertEquals(123.45d, number.asDouble(), 1e-10);
    }

    @Test
    public void test_fractal() {
        // 12345.6789
        OptimizedJsonNumber number = new OptimizedJsonNumber(12345, 6789, (byte) 4, (short) 0, true);

        assertEquals("12345.6789", number.toString());
        assertEquals(12345, number.asInt());
        assertEquals(12345l, number.asLong());
        assertEquals(12345.6789f, number.asFloat(), 1e-3);
        assertEquals(12345.6789d, number.asDouble(), 1e-10);
    }

    @Test
    public void test_negative() {
        // -73.5716511
        OptimizedJsonNumber number = new OptimizedJsonNumber(73, 5716511, (byte) 7, (short) 0, false);

        assertEquals("-73.5716511", number.toString());
        assertEquals(-73, number.asInt());
        assertEquals(-73l, number.asLong());
        assertEquals(-73.5716f, number.asFloat(), 1e-3);
        assertEquals(-73.5716511d, number.asDouble(), 1e-10);
    }

    @Test
    public void test_high_precision_double() {
        // 45.5122363923859
        OptimizedJsonNumber number = new OptimizedJsonNumber(45, 5122363923859l, (byte) 13, (short) 0, true);

        assertEquals("45.5122363923859", number.toString());
        assertEquals(45, number.asInt());
        assertEquals(45l, number.asLong());
        assertEquals(45.5122f, number.asFloat(), 1e-3);
        assertEquals(45.5122363923859d, number.asDouble(), 1e-10);
    }

    @Test
    public void test_fraction() {
        // 12345.6789
        OptimizedJsonNumber number = new OptimizedJsonNumber(0, 6789, (byte) 4, (short) 0, true);

        assertEquals("0.6789", number.toString());
        assertEquals(0, number.asInt());
        assertEquals(0l, number.asLong());
        assertEquals(0.6789f, number.asFloat(), 1e-3);
        assertEquals(0.6789d, number.asDouble(), 1e-10);
    }

    @Test
    public void test_negative_fraction() {
        // 12345.6789
        OptimizedJsonNumber number = new OptimizedJsonNumber(0, 6789, (byte) 4, (short) 0, false);

        assertEquals("-0.6789", number.toString());
        assertEquals(0, number.asInt());
        assertEquals(0l, number.asLong());
        assertEquals(-0.6789f, number.asFloat(), 1e-3);
        assertEquals(-0.6789d, number.asDouble(), 1e-10);
    }
}
