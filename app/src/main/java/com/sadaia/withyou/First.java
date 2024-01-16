package com.sadaia.withyou;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class First extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private TextView txvResult;
    LinearLayout one, tow , three;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        one = findViewById(R.id.one);
        tow = findViewById(R.id.tow);
        three = findViewById(R.id.three);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int langResult = textToSpeech.setLanguage(new Locale("ar"));
                            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(First.this, "اللغة غير مدعومة", Toast.LENGTH_SHORT).show();
                            } else {
                                convertTextToSpeech("  خدمة خُطى ، معاك بكل خطاويك رجلنا على رجلك");

                            }
                        } else {
                            Toast.makeText(First.this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        tow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int langResult = textToSpeech.setLanguage(new Locale("ar"));
                            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(First.this, "اللغة غير مدعومة", Toast.LENGTH_SHORT).show();
                            } else {
                                convertTextToSpeech("خدمة دليلك  ، أنت اطلب واحنا ندلك لأفضل الأماكن");

                            }
                        } else {
                            Toast.makeText(First.this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int langResult = textToSpeech.setLanguage(new Locale("ar")); // لغة النص
                            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(First.this, "اللغة غير مدعومة", Toast.LENGTH_SHORT).show();
                            } else {
                                convertTextToSpeech("لقد اخترت الاتصال السريع بالطوارئ سيتم الاتصال على 911 ");

                            }
                        } else {
                            Toast.makeText(First.this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {
                    }

                    @Override
                    public void onDone(String utteranceId) {
                        String phoneNumber = "911";
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phoneNumber));
                        if (callIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(callIntent);
                        }
                    }

                    @Override
                    public void onError(String utteranceId) {
                    }
                });
            }

        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int langResult = textToSpeech.setLanguage(new Locale("ar")); // لغة النص
                    if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(First.this, "اللغة غير مدعومة", Toast.LENGTH_SHORT).show();
                    } else {
                        convertTextToSpeech("السلام عليكم! أنا روبوت معاك يُسعدني ان اُساعدك في رحلتك. ما هي الخدمة التي تودني ان اقدمها لك؟  ");

                    }
                } else {
                    Toast.makeText(First.this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void convertTextToSpeech(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "uniqueId");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
