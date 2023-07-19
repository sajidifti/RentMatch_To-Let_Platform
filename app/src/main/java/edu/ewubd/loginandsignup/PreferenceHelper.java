package edu.ewubd.loginandsignup;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }
}
