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

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        final Intent callingIntent = getIntent();

        final String actionToLaunch = callingIntent.getStringExtra("actionId");
        final String number = callingIntent.getStringExtra("phoneNumber");

        final Intent activity = new Intent();
        if (actionToLaunch.equals(Intent.ACTION_DIAL)) {
            activity.setAction(Intent.ACTION_DIAL);
            activity.setData(Uri.parse("tel:"+number));
        } else
        if (actionToLaunch.equals(Intent.ACTION_CALL)) {
            activity.setAction(Intent.ACTION_CALL);
            activity.setData(Uri.parse("tel:"+number));
        } else {
            throw new IllegalArgumentException("Unrecognized action: "
                    + actionToLaunch);
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(activity);
                finish();//it is important to finish, but after a small delay
            }
        }, 50L);

    }
}
