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

package ru.org.sevn.android;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
    public static void toastLong(final Context ctx, final String s) {
        toast(ctx, s, Toast.LENGTH_LONG);
    }
    public static void toastShort(final Context ctx, final String s) {
        toast(ctx, s, Toast.LENGTH_SHORT);
    }
    public static void toastLongBottom(final Context ctx, final String s) {
        toast(ctx, s, Toast.LENGTH_LONG, Gravity.BOTTOM|Gravity.LEFT);
    }
    public static void toastShortBottom(final Context ctx, final String s) {
        toast(ctx, s, Toast.LENGTH_SHORT, Gravity.BOTTOM|Gravity.LEFT);
    }
    public static void toast(final Context ctx, final String s, final int length) {
        toast(ctx, s, length, Gravity.TOP|Gravity.LEFT);
    }
    public static void toast(final Context ctx, final String s, final int length, int gravity) {
        Toast t = Toast.makeText(ctx, s, length);
        t.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
        t.show();
    }
}
