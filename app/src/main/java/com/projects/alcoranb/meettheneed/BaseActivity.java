package com.projects.alcoranb.meettheneed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.projects.alcoranb.meettheneed.utils.Constants;
import com.projects.alcoranb.meettheneed.utils.themeSwitch;

/**
 * Created by alcoranb on 5/24/16.
 */
public abstract class BaseActivity extends AppCompatActivity
{

  protected SharedPreferences.OnSharedPreferenceChangeListener mPrefListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);

    if (PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(Constants.PREF_DARK_THEME, false))
    {
      setTheme(R.style.AppTheme_Dark);
    }
    else
    {
      setTheme(R.style.AppTheme);
    }


  }

  public void switchTheme()
  {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor spe = sp.edit();

    boolean bUsingDarkTheme = sp.getBoolean(Constants.PREF_DARK_THEME, false);

    Log.e("theme", "pressed theme");

    if (!bUsingDarkTheme)
    {
      spe.putBoolean(Constants.PREF_DARK_THEME, true).commit();
      themeSwitch.changeToTheme(this, themeSwitch.THEME_DARK);

      Log.e("theme", "dark theme");
    }
    else
    {
      spe.putBoolean(Constants.PREF_DARK_THEME, false).commit();
      themeSwitch.changeToTheme(this, themeSwitch.THEME_DEFAULT);
      Log.e("theme", "default theme");
    }
  }



}
