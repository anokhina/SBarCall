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

import android.content.Context;

import ru.org.sevn.android.Notifier;

public class AppNotifier extends Notifier {
	public AppNotifier() {
		super(R.mipmap.ic_launcher);
	}

	public static final int BATTERY_POWER = 1;
	public static final int SHOW_NOTE = 2;
	public static final int ALERT = 3;

    public void batteryPowerNotify(Context ctx, String title, String text) {
    	notify(BATTERY_POWER, ctx, MainActivity.class, title, text);
    }
    public void showNoteNotify(Context ctx, String title, String text) {
    	notify(SHOW_NOTE, ctx, MainActivity.class, title, text);
    }

	public void notify(int mId, Context ctx, String title, String text) {
		notify(mId, ctx, MainActivity.class, title, text);
	}
    @Override
    protected boolean isAutoCancel(int mId) {
    	if (SHOW_NOTE == mId) {
    		return false;
    	}
    	return true;
    }
}
