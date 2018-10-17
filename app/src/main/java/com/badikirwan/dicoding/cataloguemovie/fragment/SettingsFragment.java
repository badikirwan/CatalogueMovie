package com.badikirwan.dicoding.cataloguemovie.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.badikirwan.dicoding.cataloguemovie.R;
import com.badikirwan.dicoding.cataloguemovie.service.AlarmReceiver;

import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener {

    @BindString(R.string.reminder_daily)
    String reminder_daily;

    @BindString(R.string.reminder_upcoming)
    String reminder_upcoming;

    @BindString(R.string.setting_locale)
    String seting_locale;

    private AlarmReceiver alarmReceiver = new AlarmReceiver();

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);

        ButterKnife.bind(this, getActivity());

        findPreference(reminder_daily).setOnPreferenceChangeListener(this);
        findPreference(reminder_upcoming).setOnPreferenceChangeListener(this);
        findPreference(seting_locale).setOnPreferenceClickListener(this);

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();

        if (key.equals(reminder_daily)) {
            if ((Boolean) newValue) {
                alarmReceiver.setRepeatingAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING,"07:00", getString(R.string.label_alarm));
            } else {
                alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING);
            }

            Toast.makeText(getActivity(), ((Boolean) newValue) ? getString(R.string.label_daily_reminder_on) : getString(R.string.label_daily_reminder_off), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (key.equals(reminder_upcoming)) {
            if ((Boolean) newValue) {
                alarmReceiver.setRepeatingAlarm(getActivity(), AlarmReceiver.TYPE_RELEASE, "08:00", getString(R.string.label_upcoming_reminder_on));
            } else {
                alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.TYPE_RELEASE);
            }

            Toast.makeText(getActivity(), ((Boolean) newValue) ? getString(R.string.label_upcoming_reminder_on) : getString(R.string.label_upcoming_reminder_off), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();

        if (key.equals(seting_locale)) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        }
        return false;
    }

}
