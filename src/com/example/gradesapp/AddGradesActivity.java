package com.example.gradesapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import br.com.kots.mob.complex.preferences.ComplexPreferences;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

// -------------------------------------------------------------------------
/**
 *  Description of class.
 *
 *  @author Zakk Lefkowitz
 *  @author Jason Barrett
 *  @author Tanner Hudson (tannerh4)
 *  @version 2014.11.30
 */
public class AddGradesActivity
    extends ActionBarActivity implements Observer
{
    private Class thisClass;
    private Classes clss;
    private ArrayList<Category> categories;
    private Category currentCat;
    private Assignment assmt;
    private String gradeName;
    private int ptsRcvd;
    private int ptsTot;

    /**
     * Description of onCreate method.
     * @param savedInstanceState A saved state of the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent inte = getIntent();
        Bundle b = inte.getExtras();
		if (b != null)
		{
			clss = (Classes) b.getParcelable("Classes");
		}
		thisClass = clss.getCurClass();


		categories = thisClass.getCats();
        updateSpinner();

    }
    // ----------------------------------------------------------
    /**
     * Description of button2 method. CALC BUTTON
     * @param view The view
     */
    public void button2(View view)
    {
        Spinner spinner = (Spinner) findViewById(R.id.categories);
        String curCategory = (String) spinner.getSelectedItem();
        for (Category c : categories)
        {
            if ( c.getName().equals(curCategory))
            {
                currentCat = c;
            }
            else
            {
               //may need this if there are no c's
            }
        }
        String grade; //hold grade ex: 15/20

        EditText nameG = (EditText) findViewById(R.id.gradeName);
        EditText gradeNumber = (EditText) findViewById(R.id.grade);

        gradeName = nameG.getText().toString();
        grade = gradeNumber.getText().toString();
        ptsRcvd = Integer.parseInt(grade.substring(0, grade.indexOf("/")));
        ptsTot = Integer.parseInt(grade.substring(grade.indexOf("/")
            , grade.length()));
        assmt = new Assignment(gradeName, ptsTot, ptsRcvd);
        currentCat.addAssmt(assmt);
        Intent intent = new Intent(this, ClassDisplayActivity.class);
        startActivity(intent);
    }
    // ----------------------------------------------------------
    /**
     * Description of addAnother method.
     * @param view The view
     */
    public void addAnother(View view)
    {
        Intent intent = new Intent(this, AddGradesActivity.class);
        startActivity(intent);
    }
    // ----------------------------------------------------------
    /**
     * Description of addCat method.
     * @param view The view
     */
    public void addCat(View view)
    {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        intent.putExtra("Classes", clss);
        startActivity(intent);
    }

    public void delete(View view)
    {
    	categories.remove(thisClass);
    	updateSpinner();

    	ComplexPreferences complexPreferences = ComplexPreferences.
    	        getComplexPreferences(this, "Classes", MODE_PRIVATE);
    	    complexPreferences.putObject("Model", clss);
    	    complexPreferences.commit();
    }

    public void updateSpinner()
    {
    	String[] cats = new String[categories.size()];
		for (int i = 0; i < categories.size(); i++)
		{
			cats[i] = categories.get(i).getName();
		}

		//Sets the spinner to display the string array of the names of the classes
        Spinner spinner = (Spinner) findViewById(R.id.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cats);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Description of the onCreateOptionsMenu method.
     * @param menu The menu of the app
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    /**
     * Description of the onOptionsItemSelected method.
     * @param item The individual item in the menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void update(Observable arg0, Object arg1)
    {
        // TODO Auto-generated method stub

    }
}
