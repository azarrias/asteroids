package com.wax_tadpole_games.android.asteroids;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);

        final EditTextPreference fragments = (EditTextPreference)findPreference("fragments");
        fragments.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int value;
                try {
                    value = Integer.parseInt((String)newValue);
                } catch(Exception e) {
                    Toast.makeText(getActivity(), "Error: must enter a numeric value", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (value >= 0 && value <= 9) {
                    fragments.setSummary("Number of pieces an asteroid is divided into (" + value + ")");
                    return true;
                } else {
                    Toast.makeText(getActivity(), "Number of pieces must be between 0 and 9", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
    }

}
