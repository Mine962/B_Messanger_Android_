package com.dgsw.project.b_messanger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.dgsw.project.b_messanger.UserInfo.Info_Data;
import com.dgsw.project.b_messanger.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String key  = "SAVING_USER_INFO";
    private ActivityMainBinding binding;
    private ArrayList<Info_Data> user_info = new ArrayList<>();
    private String user_id, user_password;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Status bar remove */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* Status bar remove */
        /* use binding */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /* use binding */

        user_info = getArrayDataInfo_Pref();



        binding.loginNow.setOnClickListener(v-> {
            user_id = binding.editId.getText().toString();
            user_password = binding.editPassword.getText().toString();
            addUser(user_id, user_password);
            setArrayDataInfo_Pref(key, user_info);
        });
    }
    /* addUser */
    private void addUser(String user_id, String user_password){
        user_info.add(new Info_Data(user_id, user_password));
        setArrayDataInfo_Pref(key, user_info);
    }
    @Override
    public void onDestroy(){
        setArrayDataInfo_Pref(key, user_info);
        super.onDestroy();
    }
    private void setArrayDataInfo_Pref(String key, ArrayList<Info_Data> values) {
        mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<Info_Data>>() {}.getType();

        String json = gson.toJson(values, listType);
        prefsEditor.putString(key, json);
        prefsEditor.apply();

        user_info = getArrayDataInfo_Pref();

        for(int index = 0; index < user_info.size(); index++){
            Toast.makeText(getApplicationContext(),"회원가입 성공! id : " + user_info.get(index).getUser_id() +" password : "+user_info.get(index).getUser_password(),Toast.LENGTH_LONG).show();
        }

    }
    private ArrayList<Info_Data> getArrayDataInfo_Pref() {
        user_info = new ArrayList<Info_Data>();

        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(key, "");
        Type myType = new TypeToken<ArrayList<Info_Data>>() {
        }.getType();
        ArrayList<Info_Data> data = gson.fromJson(json, myType);


        if(data != null){
            return data;
        }else{
            return new ArrayList<>();
        }
    }

}