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

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

public class Util {
    public static float getImageFactor(Resources r){
        DisplayMetrics metrics = r.getDisplayMetrics();
        float multiplier=metrics.density;
        return multiplier;
    }
    public static Bitmap getScaledImage(Bitmap image, int w, int h, float multiplier){
        int newW = (int)(w*multiplier);
        int newH = (int)(h*multiplier);
        if (image.getWidth() > newW || image.getHeight() > newH) {
            return Bitmap.createScaledBitmap(image, newW, newH, false);
        }
        return image;
    }
}
