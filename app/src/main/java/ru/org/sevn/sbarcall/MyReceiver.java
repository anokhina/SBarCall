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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public static final String TITLE_NOTIFY = "SB Call";

    @Override
    public void onReceive(final Context context, Intent intent) {
        //Log.d("*********","onReceive>"+intent.getAction());

        if("android.intent.action.PACKAGE_REPLACED".equals(intent.getAction())) {
            if (intent.getData().getSchemeSpecificPart().equals(context.getPackageName())) {
                showControl(context);
            }
        } else if("android.intent.action.MY_PACKAGE_REPLACED".equals(intent.getAction())) {
            showControl(context);
        } else if (intent.getAction().equals(MainActivity.MAIN_NOTE)) {
            showControl(context);
        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            showControl(context);
        }
    }

    private void showControl(final Context context) {
        SharedPreferences sharedPref = MainActivity.getSharedPreferences(context);
        String str = sharedPref.getString(MainActivity.PREF_NAME_PHONE1, "");
        //new AppNotifier().showNoteNotify(context, "Note:", str);
        context.startService(new Intent(context, AppService.class).setAction(AppService.ACTION_NONE));
    }
}
