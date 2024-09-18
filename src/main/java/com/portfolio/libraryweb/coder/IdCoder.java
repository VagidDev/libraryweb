package com.portfolio.libraryweb.coder;
//TODO: change time limit(or not), or change the logic of encoding
@Deprecated
public abstract class IdCoder {
    private static final long HOURS = 3_600_000L;
    public static long encode(long id) {
        return id + (System.currentTimeMillis() / HOURS);
    }

    public static long decode(long id) {
        return id - (System.currentTimeMillis() / HOURS);
    }
}
