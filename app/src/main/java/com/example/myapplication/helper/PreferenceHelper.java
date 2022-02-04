package com.example.myapplication.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.myapplication.constant.ProjectSettings;
import com.example.myapplication.model.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PreferenceHelper {


        private static PreferenceHelper sInstance;
        private SharedPreferences mPreferences;
        private SecurityHelper mSecurityHelper;
        private Gson mGson;

        public static PreferenceHelper getInstance(Context context) {
            if (sInstance == null) {
                sInstance = new PreferenceHelper(context);
            }
            return sInstance;
        }

        public PreferenceHelper(Context context) {
            mGson = new GsonBuilder().disableHtmlEscaping().create();
            mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            mPreferences.edit().commit();

        }

        public void remove(String key) {
            mPreferences.edit().remove(key).commit();
        }

        private void setBoolean(String key, Boolean value) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }

        private int getInt(String key, int defValue) {
            return mPreferences.getInt(key, defValue);
        }

        private void setInt(String key, int value) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
        }

        private void setLong(String key, long value) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putLong(key, value);
            editor.commit();
        }

        private float getFloat(String key, float defValue) {
            return mPreferences.getFloat(key, defValue);
        }

        private long getLong(String key, long defValue) {
            return mPreferences.getLong(key, defValue);
        }

        private boolean getBoolean(String key, boolean defValue) {
            return mPreferences.getBoolean(key, defValue);
        }

        private void setString(String key, String value) {

            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }

        private String getString(String key, String defValue) {
            String value;
            if (mPreferences.contains(key)) {
                value = mPreferences.getString(key, defValue);
            } else {
                value = defValue;
            }
            return value;
        }

        public void setToken(String token) {
            setString(Keys.TOKEN, token);
        }

        public void setUser(UserEntity user) {
            String json = mGson.toJson(user);
            setString(Keys.USER, json);
        }

        public void setNotificationsEnabled(boolean isNotificationsEnabled){
            setBoolean(Keys.IS_NOTIFICATIONS_ENABLED, isNotificationsEnabled);
        }

        public boolean isNotificationsEnabled() {
            return getBoolean(Keys.IS_NOTIFICATIONS_ENABLED, false);
        }

        public String getToken() {
            return getString(Keys.TOKEN, "");
        }

        public UserEntity getUser() {
            String json = getString(Keys.USER, null);
            Type type = new TypeToken<UserEntity>() {}.getType();
            return mGson.fromJson(json, type);
        }

        public static class Keys {

            public static final String TOKEN = "TOKEN";
            public static final String USER = "USER";
            public static final String IS_NOTIFICATIONS_ENABLED = "IS_NOTIFICATIONS_ENABLED";
        }
    }


