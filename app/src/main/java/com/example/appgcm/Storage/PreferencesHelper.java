
package com.example.appgcm.Storage;
import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesHelper {

    private static final String GCMPREFERENCES = "GCMPREFERENCES";
    private static final String PREFERENCES_USERNAME = GCMPREFERENCES + ".username";
    private static final String PREFERENCES_PASSWORD = GCMPREFERENCES + ".password";
    private static final String PREFERENCES_NAME = GCMPREFERENCES + ".name";
    private static final String PREFERENCES_LASTNAME = GCMPREFERENCES + ".password";
    private static final String PREFERENCES_REGISTRATIONDATE = GCMPREFERENCES + ".regisDate";

    private PreferencesHelper() {
        //no instance
    }

    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(PREFERENCES_USERNAME);
        editor.remove(PREFERENCES_PASSWORD);
        editor.remove(PREFERENCES_NAME);
        editor.remove(PREFERENCES_PASSWORD);
        editor.remove(PREFERENCES_REGISTRATIONDATE);
        editor.apply();
    }

    public static void saveSession(Context context, String username, String password, String name, String lastName, String date)
    {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCES_USERNAME, username);
        editor.putString(PREFERENCES_PASSWORD, password);
        editor.putString(PREFERENCES_NAME, name);
        editor.putString(PREFERENCES_LASTNAME, lastName);
        editor.putString(PREFERENCES_REGISTRATIONDATE, date);
        editor.apply();
    }

    public static String getUserSession(Context context)
    {
        SharedPreferences sharedPreferences= getSharedPreferences(context);
        String username= sharedPreferences.getString(PREFERENCES_USERNAME,null);

        return username;
    }
    public static String getNameUserSession(Context context)
    {
        SharedPreferences sharedPreferences= getSharedPreferences(context);
        String name= sharedPreferences.getString(PREFERENCES_NAME,null)+" "+sharedPreferences.getString(PREFERENCES_LASTNAME,null);

        return name;
    }

    public static boolean isSignedIn(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(PREFERENCES_USERNAME) &&
                preferences.contains(PREFERENCES_PASSWORD);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(GCMPREFERENCES, Context.MODE_PRIVATE);
    }
}
