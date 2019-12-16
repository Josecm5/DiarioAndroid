package com.example.jose.diario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> tareas;
    ArrayAdapter<String> adapter;
    ListView listado;
    int posicion;
    SharedPreferences sharedPreferences;//
    EditText tarea;
    String texto;

    private MainActivity text;

    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("text", MODE_PRIVATE);
        listado =(ListView) findViewById(R.id.listado);
        tareas = cargarLista();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , tareas);//Esto se encarga de recuperar datos del conjuto de Array
        listado.setAdapter(adapter);
        text =this;
        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int posicionn, long id) {
                posicion = posicionn;
                Intent intent = new Intent(text, Activity2.class);
                String  adquirimosTexto = tareas.get(posicionn);
                intent.putExtra("aparecer", adquirimosTexto);
                startActivityForResult(intent, 2);//
                adapter.notifyDataSetChanged();

            }

        });
        //Elimina el texto del Array manteniendo click
        listado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tareas.remove(position);
                adapter.notifyDataSetChanged();//Esto lo utilizas cada vez que la lista es actualizada
                guardarLista(tareas);
                return true;
            }
        });

    }


    //Boton de añadir Tarea que te manda a la segunda Actividad
    public void añadirLista(View view){
        Button boton =(Button) findViewById(R.id.button1);
        if(boton.isClickable()){
            Intent intent = new Intent(this, Activity2.class);
            startActivityForResult(intent,1);
        }
    }
    //Para que cuando escribes en la otra pantalla y no escribes nada no te deja guardar
    private void arrayAnadir(ArrayList<String> diario, String texto){
        if(texto.isEmpty()){
            return;
        }
        diario.add(texto);
        adapter.notifyDataSetChanged();//Te indica que el ListView ha cambiado
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Muestra las entradas que acabas de añadir
        if (requestCode == 1) { // Asegura de que la solicitud es correcta
            if(resultCode == MainActivity.RESULT_OK){//Te indica que resultados se escogieron finalmente
                texto = data.getStringExtra("resultado");//Recupera los datos del Intent
                arrayAnadir(tareas, texto);
            }
        }
        //Editar entradas
        if(requestCode ==2){
            if(resultCode ==MainActivity.RESULT_OK){
                String editarEntrada = data.getStringExtra("resultado");
                tareas.set(posicion, editarEntrada);
                adapter.notifyDataSetChanged();
            }
        }
        guardarLista(tareas);
    }


    //Lo que pones en el Array se guarda en la aplicación
    private void guardarLista(ArrayList<String> textos) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < textos.size(); i++) {
            editor.putString("text" + i, textos.get(i));
        }
        editor.putInt("longitud", textos.size()); // Guardar el tamaño de la lista
        editor.commit();
    }
    //Lo que tienes guardado en el Array al reiniciar la aplicación se mantiene
    private ArrayList<String> cargarLista() {
        ArrayList<String> textos = new ArrayList<>();
        int longitud = sharedPreferences.getInt("longitud", 0);
        for (int i = 0; i < longitud; i++) {
            String texto = sharedPreferences.getString("text" + i, "");
            textos.add(texto);
        }
        return textos;
    }

    //Botones
    //Button button1 = findViewById(R.id.button1);
    //button1.setOnClickListener(new View.OnClickListener() {
    //@Override
    //public void onClick(View v) {
    //openActivity2();
    //}
    //});

    //public void insertar(View view) {
        //String t = tarea.getText().toString();
        //tareas.add(t);
        //adapter.notifyDataSetChanged(); //Refrescar el listado
    //}

    //public void openActivity2(){
        //Intent intent = new Intent(this, Activity2.class);
        //startActivity(intent);
}

