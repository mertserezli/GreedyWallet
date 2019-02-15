/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.halfbyte.greedywallet;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.halfbyte.greedywallet.models.Item;
import com.halfbyte.greedywallet.ui.camera.GraphicOverlay;

import java.util.ArrayList;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {
    ArrayList<String> scannedTexts=new ArrayList<>();
    static ArrayList<Item> items=new ArrayList<>();
    static ArrayList<String> itemsName=new ArrayList<>();
    private GraphicOverlay<OcrGraphic> graphicOverlay;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        graphicOverlay = ocrGraphicOverlay;
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        graphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {
                Log.d("OcrDetectorProcessor", "Text detected! " + item.getValue());
                OcrGraphic graphic = new OcrGraphic(graphicOverlay, item);
                //graphicOverlay.add(graphic);
                String value=item.getValue().toLowerCase();
                if(value.contains("\n")){
                    String[] values=value.split("\n");
                    for (String s:values){
                        if (!scannedTexts.contains(s) && itemsName.contains(s)) {
                            scannedTexts.add(s);
                            OcrCaptureActivity.tts.speak(s, TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                        }
                    }
                }
                else{
                    if (!scannedTexts.contains(value) && itemsName.contains(value)) {
                        scannedTexts.add(value);
                        OcrCaptureActivity.tts.speak(item.getValue(), TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                    }
                }

            }
        }

    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        graphicOverlay.clear();
    }
}
