/*
 * Copyright 2017 Veronica Anokhina.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.org.sevn.sbarcall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    static class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;
        final SharedPreferences sharedPref = getSharedPreferences(context);

        bindTextField(R.id.phone1, sharedPref, PREF_NAME_PHONE1);
        bindTextField(R.id.phone2, sharedPref, PREF_NAME_PHONE2);
        bindTextField(R.id.phone3, sharedPref, PREF_NAME_PHONE3);
        bindTextField(R.id.phone4, sharedPref, PREF_NAME_PHONE4);
        MainActivity.this.sendBroadcast(new Intent(MAIN_NOTE));

        Button b1 = (Button)findViewById(R.id.button_phone1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = sharedPref.getString(MainActivity.PREF_NAME_PHONE1, "");
                if (phoneNumber != null && phoneNumber.length() > 0) {
                    makeCall(phoneNumber);
                }
            }
        });
    }

    private void bindTextField(final int id, final SharedPreferences sharedPref, final String prefId) {
        EditText editTextPhone1 = (EditText) findViewById(id);
        editTextPhone1.setText(sharedPref.getString(prefId, ""));
        editTextPhone1.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(prefId, s.toString());
                editor.commit();
            }
        });
    }

    public static final String PREF_NAME = "ru.org.sevn.sbarcall.prefs";
    public static final String PREF_NAME_PHONE1 = "ru.org.sevn.sbarcall.prefs.phone1";
    public static final String PREF_NAME_PHONE2 = "ru.org.sevn.sbarcall.prefs.phone2";
    public static final String PREF_NAME_PHONE3 = "ru.org.sevn.sbarcall.prefs.phone3";
    public static final String PREF_NAME_PHONE4 = "ru.org.sevn.sbarcall.prefs.phone4";
    public static final String MAIN_NOTE = "ru.org.sevn.sbarcall.mainNote";

    static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref;
    }

    private void makeCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        //setSim(intent, 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
    public static void setSim(Intent phoneIntent, int simnum) {
        String[] dualSimTypes = {"subscription", "Subscription",
                "extra_asus_dial_use_dualsim",
                "com.android.phone.extra.slot",
                "phone", "com.android.phone.DialingMode",
                "simId", "simnum", "phone_type",
                "simSlot", "extra_asus_dial_use_dualsim",
                "slot_id",
                "slot", "simslot", "sim_slot",
                "com.android.phone.DialingMode", "simnum", "phone_type", "slotId", "slotIdx"};

        phoneIntent.putExtra("Cdma_Supp", true);
        phoneIntent.putExtra("com.android.phone.force.slot", true);
        phoneIntent.setFlags(0x10000000);

        for (int i = 0; i < dualSimTypes.length; i++) {
            phoneIntent.putExtra(dualSimTypes[i], simnum);
        }
    }
}
