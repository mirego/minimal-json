package com.eclipsesource.json;

import java.io.IOException;

@SuppressWarnings("serial")
class OptimizedJsonNumber extends JsonValue {

    private final boolean isPositive;
    private final long integerPart;
    private final long fractionPart;
    private final byte fractionScale;
    private final short exponentPart;

    OptimizedJsonNumber(long integerPart, long fractionPart, byte fractionScale, short exponentPart, boolean isPositive) {
        this.isPositive = isPositive;
        this.integerPart = isPositive ? integerPart : integerPart * -1;
        this.fractionPart = fractionPart;
        this.fractionScale = fractionScale;
        this.exponentPart = exponentPart;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!isPositive) {
            sb.append('-');
        }

        sb.append(String.valueOf(Math.abs(integerPart)));

        if (fractionScale > 0) {
            sb.append('.');
            String fractionStr = String.valueOf(fractionPart);
            for (int toPrepend = fractionScale - fractionStr.length(); toPrepend > 0; toPrepend--) {
                sb.append('0');
            }
            sb.append(fractionStr);
        }
        if (exponentPart != 0) {
            sb.append('e');
            sb.append(String.valueOf(exponentPart));
        }
        return sb.toString();
    }

    @Override
    protected void write(JsonWriter writer) throws IOException {
        writer.write(toString());
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public int asInt() {
        return (int) asLong();
    }

    @Override
    public long asLong() {
        if (exponentPart == 0) {
            return integerPart;
        }
        if (fractionScale > 0) {
            return (long) fullDouble();
        }
        return (long) (integerPart * Math.pow(10, exponentPart));
    }

    @Override
    public float asFloat() {
        return (float) asDouble();
    }

    @Override
    public double asDouble() {
        if (exponentPart == 0) {
            if (fractionScale > 0) {
                if (isPositive) {
                    return integerPart + fractionPart * Math.pow(10, -fractionScale);
                } else {
                    return -1 * (Math.abs(integerPart) + fractionPart * Math.pow(10, -fractionScale));
                }
            } else {
                return integerPart;
            }
        }

        if (fractionScale > 0) {
            return fullDouble();
        }

        return integerPart * Math.pow(10, exponentPart);
    }

    private double fullDouble() {
        return integerPart * Math.pow(10, exponentPart) + fractionPart * Math.pow(10, exponentPart - fractionScale);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (integerPart ^ (integerPart >>> 32));
        result = 31 * result + (int) (fractionPart ^ (fractionPart >>> 32));
        result = 31 * result + fractionScale;
        result = 31 * result + exponentPart;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OptimizedJsonNumber that = (OptimizedJsonNumber) o;

        if (exponentPart != that.exponentPart) return false;
        if (fractionPart != that.fractionPart) return false;
        if (fractionScale != that.fractionScale) return false;
        if (integerPart != that.integerPart) return false;

        return true;
    }

}
