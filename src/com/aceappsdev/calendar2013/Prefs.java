package com.aceappsdev.calendar2013;


import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class Prefs extends PreferenceActivity implements OnPreferenceChangeListener {
	private static int prefs=R.xml.prefs;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT>11){
            try{getClass().getMethod("getFragmentManager");}
            catch(Exception e){e.printStackTrace();}
            AddResourceApi11AndGreater();
        }else{
            AddResourceApiLessThan11();
        }
    }

    @SuppressWarnings("deprecation")
    protected void AddResourceApiLessThan11()
    {
    	addPreferencesFromResource(prefs);
    }

    //@TargetApi(11)
    protected void AddResourceApi11AndGreater()
    {
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PF()).commit();
    }

    //@TargetApi(11)
    public static class PF extends PreferenceFragment
    {       
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(Prefs.prefs); //outer class private members seem to be visible for inner class, and making it static made things so much easier
        }
    }

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		
		if(preference.getKey().equals("showInt")){
			Preference pr = findPreference("intCntry");
			pr.setEnabled((Boolean) newValue);
		}
		return false;
	}

}
