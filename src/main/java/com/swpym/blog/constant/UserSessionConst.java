package com.swpym.blog.constant;

import java.util.concurrent.TimeUnit;

/**
 * @author fangl
 **/
public class UserSessionConst {

    public static final String USER_CACHE_PREFIX = "user:";

    public static final String TOKEN_COOKIE = "token";

    public static final int EXPIRE_HOURS = 1;

    public static final long EXPIRE_MILLIS = TimeUnit.HOURS.toMillis(EXPIRE_HOURS);

    public static final int EXPIRE_SECONDS = Math.toIntExact(EXPIRE_MILLIS / 1000);

    public static final String TOKEN_SECRET = "123";

    public static final String USERNAME = "USERNAME";

    public static final String USER_ID = "USER_ID";
}
