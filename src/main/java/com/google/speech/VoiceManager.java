/*
  Copyright 2017, Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.google.speech;

// [START speech_quickstart]
// Imports the Google Cloud client library

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.cloud.speech.v1.*;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.protobuf.ByteString;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vt.audiorecord.AudioRecorder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VoiceManager {
    private final Logger logger = LoggerFactory.getLogger(AudioRecorder.class);

    private SpeechClient speech;

    public VoiceManager() {
        // not work
        File key = new File("./src/main/resources/googleApiKey/Voice-Control-ownerAndIAP.json");
        if (!key.exists())
            new NullPointerException("key is not exists!");
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS" , "./src/main/resources/googleApiKey/Voice-Control-ownerAndIAP.json");

//        GoogleCredential credential = GoogleCredential.getApplicationDefault();
        GoogleCredential credential = new GoogleCredential.Builder().build();
        credential.getServiceAccountUser();

        //TODO: need rework
//        Compute compute = new Compute.Builder
//                (transport, jsonFactory, credential).build();
//        Collection COMPUTE_SCOPES =
//                Collections.singletonList(ComputeScopes.COMPUTE);
//        if (credential.createScopedRequired()) {
//            credential = credential.createScoped(COMPUTE_SCOPES);
//        }

        speech = init();
    }

    private SpeechClient init() {
        try {
            return SpeechClient.create();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            logger.error("SpeechClient is not created!");
        }
        return null;
    }

    public List<String> translate(String pathToAudioFile) throws Exception {
        // The path to the audio file to transcribe
        String fileName = pathToAudioFile; //"./resources/audio.raw"

        // Reads the audio file into memory
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        ByteString audioBytes = ByteString.copyFrom(data);

        // Builds the sync recognize request
        RecognitionConfig config = RecognitionConfig.newBuilder()
            .setEncoding(AudioEncoding.LINEAR16)
            .setSampleRateHertz(16000)
            //.setLanguageCode("en-US")
            .setLanguageCode("ru-RU")
            .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder()
            .setContent(audioBytes)
            .build();

        // Performs speech recognition on the audio file
        RecognizeResponse response = speech.recognize(config, audio);
        List<SpeechRecognitionResult> results = response.getResultsList();
        List<String> out = new ArrayList<>();

        for (SpeechRecognitionResult result: results) {
            // There can be several alternative transcripts for a given chunk of speech. Just use the
            // first (most likely) one here.
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            System.out.printf("Transcription: %s%n", alternative.getTranscript());
            out.add(alternative.getTranscript());
        }
        return out;
    }

    public void close() throws Exception {
        speech.close();
    }
}
