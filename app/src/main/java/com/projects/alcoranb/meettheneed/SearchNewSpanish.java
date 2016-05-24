package com.projects.alcoranb.meettheneed;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projects.alcoranb.meettheneed.adapters.ArrayWheelAdapter;
import com.projects.alcoranb.meettheneed.adapters.AutoCompleteCustomArrayAdapter;
import com.projects.alcoranb.meettheneed.utils.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alcoranb with modifications by Victor on 4/14/16.
 */
public class SearchNewSpanish extends AppCompatActivity
{
  SpanishDatabaseHandler dbContext;
  SQLiteDatabase database;

  CustomAutoCompleteView inputTextName;
  CustomAutoCompleteView inputTextCategory;
  CustomAutoCompleteView inputTextZip;
  CustomAutoCompleteView inputTextCity;
  CustomAutoCompleteView inputTextState;
  CustomAutoCompleteView inputTextCost;
  CustomAutoCompleteView inputTextPopulation;
  CustomAutoCompleteView inputTextLanguage;

  String OrganizationMenu[];
  String CategoryMenu[];
  String ZipMenu[];
  String CityMenu[];
  String StateMenu[];
  String CostMenu[];
  String LanguageMenu[];
  String AgeGroupMenu[];


  CustomAutoCompleteView myAutoComplete;

  ArrayAdapter<String> mArrayAdapter;
  AutoCompleteCustomArrayAdapter mNewAdapter;
  String currentMenu[] = new String[]{""};
  Constants.FORMS whatMenu;


  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_search_new_spanish);
    //trying the new layout! Auto complete?


    dbContext = new SpanishDatabaseHandler(this);

    database = dbContext.getWritableDatabase();

    initViews();
    initMenus();
    setOnClickListeners();
    initAdapters();
    setupToolbar();
    //dbContext.onCreate(database);
    Log.e("here","are we here?");
    //dbContext.onUpgrade(database, 2,1);


    final Button mbSearch = (Button) findViewById(R.id.Search);
    mbSearch.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        Bundle inputs = new Bundle();
        Log.e("data[0]", String.valueOf(inputTextName.getText().toString()) + ".");
        inputs.putStringArray("Querry Strings", new String[]{inputTextName.getText().toString(),
            inputTextCategory.getText().toString(), inputTextZip.getText().toString(),
            inputTextCity.getText().toString(), inputTextState.getText().toString(),
            inputTextCost.getText().toString(), inputTextPopulation.getText().toString(),
            inputTextLanguage.getText().toString()});
        Intent intentData = new Intent(SearchNewSpanish.this, resultsSpanish.class);
        intentData.putExtras(inputs);
        startActivity(intentData);
      }
    });
  }

  public void initViews()
  {
    inputTextName = (CustomAutoCompleteView) findViewById(R.id.OrgInName);
    inputTextName.setHint("Nombre de la organización");
    inputTextCategory = (CustomAutoCompleteView) findViewById(R.id.OrgInCategory);
    inputTextCategory.setHint("Categoría");
    inputTextZip = (CustomAutoCompleteView) findViewById(R.id.OrgInAddressZip);
    inputTextZip.setHint("El Código Postal");
    inputTextCity = (CustomAutoCompleteView) findViewById(R.id.OrgInAddressCity);
    inputTextCity.setHint("Ciudad");
    inputTextState = (CustomAutoCompleteView) findViewById(R.id.OrgInAddressState);
    inputTextState.setHint("Estado");
    inputTextCost = (CustomAutoCompleteView) findViewById(R.id.OrgInCost);
    inputTextCost.setHint("El Costo");
    inputTextPopulation = (CustomAutoCompleteView) findViewById(R.id.OrgInPop);
    inputTextPopulation.setHint("Grupo De Personas");
    inputTextLanguage = (CustomAutoCompleteView) findViewById(R.id.OrgInLang);
    inputTextLanguage.setHint("Idioma");
    initTextChange();
  }

  public void initMenus()
  {
    OrganizationMenu = dbContext.allOrganizations();
    CategoryMenu = dbContext.allCategories();
    ZipMenu = dbContext.allZips();
    CityMenu = dbContext.allCity();
    StateMenu = dbContext.allState();
    CostMenu = dbContext.allCost();
    LanguageMenu = dbContext.allLanguage();
    AgeGroupMenu = dbContext.allAges();
  }

  public void initTextChange()
  {
    inputTextName.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this, true));
    inputTextCategory.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this, true));
    inputTextZip.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this, true));
    inputTextCity.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this, true));
    inputTextState.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this, true));
    inputTextCost.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this, true));
    inputTextPopulation.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this, true));
    inputTextLanguage.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this, true));
  }

  public void initAdapters()
  {
    mArrayAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_dropdown_item_1line, currentMenu);
    inputTextName.setAdapter(mArrayAdapter);
    inputTextCategory.setAdapter(mArrayAdapter);
    inputTextZip.setAdapter(mArrayAdapter);
    inputTextCity.setAdapter(mArrayAdapter);
    inputTextState.setAdapter(mArrayAdapter);
    inputTextCost.setAdapter(mArrayAdapter);
    inputTextLanguage.setAdapter(mArrayAdapter);
    inputTextPopulation.setAdapter(mArrayAdapter);
  }


  public void setOnClickListeners()
  {
    inputTextName.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View v, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
          whatMenu = Constants.FORMS.NAME;
        }
        return false;
      }
    });
    inputTextCategory.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View v, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
          whatMenu = Constants.FORMS.CATEGORY;
        }
        return false;
      }
    });
    inputTextZip.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View v, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
          whatMenu = Constants.FORMS.ZIPCODE;
        }
        return false;
      }
    });
    inputTextCity.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View v, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
          whatMenu = Constants.FORMS.CITY;
        }
        return false;
      }
    });
    inputTextState.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View v, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
          whatMenu = Constants.FORMS.STATE;
        }
        return false;
      }
    });
    inputTextCost.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View v, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
          whatMenu = Constants.FORMS.COST;
        }
        return false;
      }
    });
    inputTextLanguage.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View v, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
          whatMenu = Constants.FORMS.LANGUAGE;
        }
        return false;
      }
    });
    inputTextPopulation.setOnTouchListener(new View.OnTouchListener()
    {
      @Override
      public boolean onTouch(View v, MotionEvent event)
      {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
          whatMenu = Constants.FORMS.AGE_GROUP;
        }
        return false;
      }
    });
  }

  public void onNetoButton(View view)
  {
    inputTextName.setText("");
    inputTextCategory.setText("");
    inputTextZip.setText("");
    inputTextCity.setText("");
    inputTextState.setText("");
    inputTextCost.setText("");
    inputTextLanguage.setText("");
    inputTextPopulation.setText("");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_search, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }


  public String[] getItems(String search)
  {
    List<String> test = new ArrayList<String>();

    switch (whatMenu)
    {
      case NAME:
        for (String str : OrganizationMenu)
        {
          if (str.toLowerCase().contains(search.toString().toLowerCase()))
          {
            test.add(str);
          }
        }
        break;
      case CATEGORY:
        for (String str : CategoryMenu)
        {
          if (str.toLowerCase().contains(search.toString().toLowerCase()))
          {
            test.add(str);
          }
        }
        break;
      case ZIPCODE:
        for (String str : ZipMenu)
        {
          if (str.toLowerCase().contains(search.toString().toLowerCase()))
          {
            test.add(str);
          }
        }
        break;
      case CITY:
        for (String str : CityMenu)
        {
          if (str.toLowerCase().contains(search.toString().toLowerCase()))
          {
            test.add(str);
          }
        }
        break;
      case STATE:
        for (String str : StateMenu)
        {
          if (str.toLowerCase().contains(search.toString().toLowerCase()))
          {
            test.add(str);
          }
        }
        break;
      case COST:
        for (String str : CostMenu)
        {
          if (str.toLowerCase().contains(search.toString().toLowerCase()))
          {
            test.add(str);
          }
        }
        break;
      case LANGUAGE:
        for (String str : LanguageMenu)
        {
          if (str.toLowerCase().contains(search.toString().toLowerCase()))
          {
            test.add(str);
          }
        }
        break;
      case AGE_GROUP:
        for (String str : AgeGroupMenu)
        {
          if (str.toLowerCase().contains(search.toString().toLowerCase()))
          {
            test.add(str);
          }
        }
        break;
    }

    currentMenu = new String[test.size()];
    currentMenu = test.toArray(currentMenu);

    return currentMenu;
  }

  public void setupToolbar()
  {
    Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
    setSupportActionBar(toolbar);
    setTitle("Buscar");
  }


}
