package com.example.jose.diario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Set;

public class Activity2 extends AppCompatActivity {
    EditText texto1, texto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Intent intent =new Intent();

        texto1 = findViewById(R.id.EditText); //Con este metodo buscamos nuestro TextView por el ID
        texto1.setText(intent.getStringExtra("aparecer"));
        Log.d( "****", "onCreate");//Sirve para escribir mensajes de registro en el código que  te ayudan a comprender el flujo de ejecución recopilando los resultados de la depuración del sistema mientras interactúas con tu app.
    }

    public void GuardarTexto(View view){
        Button boton = (Button) findViewById(R.id.button1);

        String texto = texto1.getText().toString();

        if(texto.isEmpty()){//Comprueba si el texto esta vacio
            return;
        }
        if(boton.isClickable()) { //Con esto creamos un post en el Array al dar al boton guardar
            Intent intentResultado = new Intent();
            intentResultado.putExtra("resultado", texto);
            setResult(RESULT_OK, intentResultado);
            finish();
        }
    }

    public void Cancelar(View view){
        Button boton =(Button) findViewById(R.id.button2);
        if(boton.isClickable()){
            finish();
        }
    }

    //Botones
    //Button button2 = findViewById(R.id.button2);
    //button2.setOnClickListener(new View.OnClickListener() {
    //@Override
    //public void onClick(View v) {
    //openMainActivity();
    //}
    //});


    //public void openMainActivity(){
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    //}
}
