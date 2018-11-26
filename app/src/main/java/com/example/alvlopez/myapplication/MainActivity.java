package com.example.alvlopez.myapplication;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {



    Button btnVoice;
    TextView txtSearch;
    Button buttontexto;
    EditText textoingresado;


    String toSpeak="";
    String strSpeech2Text="";

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    TextToSpeech t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Relacionamos el boton con el XML
        btnVoice = (Button) findViewById(R.id.button1);
        txtSearch = (TextView) findViewById(R.id.txtsearch);
        buttontexto = (Button) findViewById(R.id.buttontexto);
        textoingresado = (EditText) findViewById(R.id.textoingresado);





        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.forLanguageTag("es-Mx"));
                }
            }
        });

        buttontexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //String toSpeak = textoingresado.getText().toString();
               switch (strSpeech2Text)
               {
                   case "Hola Marce":
                       toSpeak= "hola como estas";
                       t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        break;
                   case "adiós":
                       toSpeak= "chao te cuidas";
                       t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        break;
                   case "bien y tú":
                       toSpeak= "muy bien gracias por preguntar";
                       t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                       break;

                   default:
                       toSpeak= "No se que decir";
                       t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

               }
                /*if (strSpeech2Text.equals("Hola"))
                {
                     toSpeak= "hola como estas";

                }
                else
                    {
                    Toast.makeText(MainActivity.this, strSpeech2Text, Toast.LENGTH_SHORT).show();
                    toSpeak = "no";
                    }*/
              //  Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                //t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });




    } //Fin Oncreate


    @Override
    //Recogemos los resultados del reconocimiento de voz
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:

                if (resultCode == RESULT_OK && null != data)
                {

                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    strSpeech2Text = speech.get(0);
                    textoingresado.setText(strSpeech2Text);
                    txtSearch.setText(strSpeech2Text);
                }

                break;

            default:
                break;


        }

    }


    public void presione(View view)
    {
        //se encarga de reconocer lo hablado para llevarlo a texto cuando se presiona el boton
        Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        //Configura el lenguaje a español mexico2
        intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"es-MX");
        intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,0.00001);
        intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,0.00001);


        try {
            startActivityForResult(intentActionRecognizeSpeech, RECOGNIZE_SPEECH_ACTIVITY);

        }catch (ActivityNotFoundException a)
        {
            Toast.makeText(getApplicationContext(), "no soporta el reconocimiento de voz", Toast.LENGTH_SHORT).show();
        }

    }

    public void leertexto(View view) {
    }
} //Fin clase
