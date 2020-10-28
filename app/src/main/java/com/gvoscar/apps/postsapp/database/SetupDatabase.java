package com.gvoscar.apps.postsapp.database;


public class SetupDatabase {

    private static final boolean isDebug = true;
    private static final String PRODUCTION = "pnd";
    private static final String QA = "qa";

    public static String getOrientation() {
        return isDebug ? QA : PRODUCTION;
    }
}
