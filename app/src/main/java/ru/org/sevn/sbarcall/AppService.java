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
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

public class AppService extends Service {
    public AppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private NotificationCompat.Builder makeNotify(Context ctx, String title, String text) {
        NotificationCompat.Builder mBuilder = makeNotifyBuilder(ctx, title, text);

        RemoteViews notificationView = new RemoteViews(getPackageName(), R.layout.notification);
//        notificationView.setTextViewText(R.id.tvTitle, title);
//        notificationView.setTextViewText(R.id.textView1, "" + makeNotifyNumber() + " " + text);
        mBuilder.setContent(notificationView);
        mBuilder.setContentIntent(makePendingNotificationIntent(makeNotificationIntent()));
        notificationView.setOnClickPendingIntent(R.id.btn_phone1, makeActionPendingIntent(ACTION_PHONE1));
        notificationView.setOnClickPendingIntent(R.id.btn_phone2, makeActionPendingIntent(ACTION_PHONE2));
        notificationView.setOnClickPendingIntent(R.id.btn_phone3, makeActionPendingIntent(ACTION_PHONE3));
        notificationView.setOnClickPendingIntent(R.id.btn_phone4, makeActionPendingIntent(ACTION_PHONE4));

        return mBuilder;
    }

    public static final String ACTION_NONE = "ru.org.sevn.sbarcall.AppService.ACTION_NONE";
    public static final String ACTION_PHONE1 = "ru.org.sevn.sbarcall.AppService.ACTION_PHONE1";
    public static final String ACTION_PHONE2 = "ru.org.sevn.sbarcall.AppService.ACTION_PHONE2";
    public static final String ACTION_PHONE3 = "ru.org.sevn.sbarcall.AppService.ACTION_PHONE3";
    public static final String ACTION_PHONE4 = "ru.org.sevn.sbarcall.AppService.ACTION_PHONE4";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private NotificationCompat.Builder makeNotifyBuilder(Context ctx, String title, String text) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //.setLights(Color.RED, 3000, 3000);
                //.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
//                    .setSound(Uri.parse(PreferenceManager.getDefaultSharedPreferences(ctx).getString(
//                            ConstUtil.get(ctx, R.string.const_pref_charge_ringtone), "default ringtone")))
                .setContentTitle(title)
                .setContentText(text);
        return mBuilder;
    }

    private Intent makeNotificationIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    private PendingIntent makePendingNotificationIntent(Intent intent) {
        int requestCode = (int) System.currentTimeMillis();
        requestCode = 3;
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private PendingIntent makeActionPendingIntent(String actionid) {
        Intent intent = new Intent(this, AppService.class);
        intent.setAction(actionid);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private void doAction(String actionName) {
        SharedPreferences sharedPref = MainActivity.getSharedPreferences(getApplicationContext());
        String phoneNumber = null;
        //Log.d("*********","doAction>"+actionName);

        switch (actionName) {
            case ACTION_PHONE1:
                phoneNumber = sharedPref.getString(MainActivity.PREF_NAME_PHONE1, "");
                break;
            case ACTION_PHONE2:
                phoneNumber = sharedPref.getString(MainActivity.PREF_NAME_PHONE2, "");
                break;
            case ACTION_PHONE3:
                phoneNumber = sharedPref.getString(MainActivity.PREF_NAME_PHONE3, "");
                break;
            case ACTION_PHONE4:
                phoneNumber = sharedPref.getString(MainActivity.PREF_NAME_PHONE4, "");
                break;
        }
        if (phoneNumber != null && phoneNumber.length() > 0) {
            makeCall(phoneNumber);
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            getApplicationContext().sendBroadcast(it);
        }
    }

    private void makeDial(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void makeProxyCall(String phoneNumber) {
        final Intent activity = new Intent(getApplicationContext(), Main2Activity.class);
        activity.putExtra("actionId", Intent.ACTION_CALL);
        activity.putExtra("phoneNumber", phoneNumber);
        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(activity);
    }

    private void makeCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        //MainActivity.setSim(intent, 0);
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        doAction(intent.getAction());

        startForeground(1,
                makeNotify(
                        getApplicationContext(),
                        "Some title",
                        "Some msg"
                ).build());
        return START_NOT_STICKY;
    }

}
