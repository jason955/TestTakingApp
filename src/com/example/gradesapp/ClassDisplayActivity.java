package com.example.gradesapp;

import android.view.View;
import java.util.Observable;
import java.util.Observer;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 *
 * @author Zakk
 * @version 2014.11.29
 */
public class ClassDisplayActivity extends ActionBarActivity
implements Observer {
	private Class thisClass;
	private Classes clss;

	/**
	 * Gets the current class from getExtras() through the intent
	 * identifies as an observer of the model
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_display);

		Intent inte = getIntent();
		Bundle b = inte.getExtras();
		if (b != null)
		{
			clss = (Classes) b.getParcelable("Classes");
			thisClass = clss.getCurClass();

			updateDisplay();
		}

		setList();
	}

	/**
	 * Updates the display with the current class
	 */
	public void updateDisplay()
	{
		TextView name = (TextView) findViewById(R.id.clsName);
		name.setText(thisClass.getName());

		TextView grade = (TextView) findViewById(R.id.curGrade);
		grade.setText(String.valueOf(thisClass.getGrade()));

		TextView hours = (TextView) findViewById(R.id.hours);
		hours.setText("Credit Hours: " + thisClass.getNumCrHrs());
	}

    /**
     * Opens the addClass Activity
     * @param view the button
     */
    public void addGrades(View view)
    {
        Intent intent = new Intent(this, AddGradesActivity.class);
        intent.putExtra("Classes", clss);
        startActivity(intent);
    }

    public void setList()
    {
    	int totNumAsgn = 0;
    	for (Category cat: thisClass.getCats())
    	{
    		totNumAsgn += cat.getAssmts().size();
    	}

    	int index = 0;

    	String[] list = new String[totNumAsgn];
    	for (int i = 0; i < thisClass.getCats().size(); i++)
    	{
    		Category curCat = thisClass.getCats().get(i);
    		for (int j = index; j < curCat.getAssmts().size(); j++)
    		{
    			Assignment curAsgn = curCat.getAssmts().get(j);
    			list[j] = curCat.getName() + " - " + curAsgn.getName() + ": " +
    			String.valueOf(curAsgn.getPtsRecieved())
    					+ " / " + String.valueOf(curAsgn.getTotPts());
    			index++;
    		}
    	}

    	ListView lsView = (ListView) findViewById(R.id.listView1);
    	ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
    	    android.R.layout.simple_list_item_1, list);
    	lsView.setAdapter(adapt);
    }

    // ----------------------------------------------------------
    /**
     * takes you home
     * @param view The view
     */
    public void home(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present
		getMenuInflater().inflate(R.menu.class_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
