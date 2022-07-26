package daniel.darkarmored.danielalejandro.appagro;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Ensenanza extends AppCompatActivity {

    String[] spinnerTierra= {"Arcilloso","Arenoso","Franco","Franco arenoso","Franco arcilloso","Franco limoso","Franco arcilloso-arenoso"};
    String [] spinnerClima= {"Frio","Moderadamente frio","Templado","Moderadamente calido","Calido","Caliente"};
    String[] spinnerMes= {"enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
    String [] spinnerDepto= {"Boyaca","Cundinamarca","Nariño"};

    ArrayList<String> Hortalizas;
    ArrayAdapter adaptador;
    ListView Listahortalizas;

    Spinner spinnertierra;
    Spinner spinnerclima;
    Spinner spinnermes;
    Spinner spinnerdepto;
    EditText Temperatura;

    String SeleccionTierra;
    String SeleccionClima;
    String SeleccionMes;
    String SeleccionDepto;
    String TemperaturaH;
    String ObtenerHortaliza;

    TextView hortaliza;

    Button Listo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensenanza);

        hortaliza = (TextView)findViewById(R.id.textviewhortaliza);
        Listo= (Button)findViewById(R.id.BotonListo);
        Temperatura=(EditText)findViewById(R.id.temp);

        spinnertierra=(Spinner)findViewById(R.id.spinnerTierra);
        spinnerclima=(Spinner)findViewById(R.id.spinnerClima);
        spinnermes=(Spinner)findViewById(R.id.spinnerMes);
        spinnerdepto=(Spinner)findViewById(R.id.spinnerDepto);
        cargaSpinner();



        spinnertierra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SeleccionTierra= parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), SeleccionTierra+ " tierra es seleccionado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerclima.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SeleccionClima= parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), SeleccionClima+ " clima es seleccionado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnermes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SeleccionMes= parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), SeleccionMes+ " mes es seleccionado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerdepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SeleccionDepto= parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), SeleccionDepto+ " depto es seleccionado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Listahortalizas= (ListView)findViewById(R.id.ListaHortalizas);


        BaseDeConocimiento bd = new BaseDeConocimiento(getApplicationContext(),null,null,1);
        Hortalizas = bd.llenar_lv("select distinct Nombre_Verdura from Hortaliza_Verdura");
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,Hortalizas);

        Listahortalizas.setAdapter(adaptador);

        Listahortalizas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String opcion = adaptador.getItem(position).toString();
                hortaliza.setText(opcion);
                ObtenerHortaliza=hortaliza.getText().toString();

            }
        });

         Enseñar();

    }

    public  void cargaSpinner(){
        ArrayAdapter<String> adaptadorTierra = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerTierra);
        ArrayAdapter<String> adaptadorClima = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerClima);
        ArrayAdapter<String> adaptadorMes = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerMes);
        ArrayAdapter<String> adaptadorDepto = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerDepto);
        spinnertierra.setAdapter(adaptadorTierra);
        spinnerclima.setAdapter(adaptadorClima);
        spinnermes.setAdapter(adaptadorMes);
        spinnerdepto.setAdapter(adaptadorDepto);
    }

    public void Enseñar (){

        Listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemperaturaH = Temperatura.getText().toString();
                String consultasql = "INSERT INTO Hortaliza_Verdura (Nombre_Verdura, Temperatura, Departamento, Tipo_Suelo, Mes_Optimo, Clima_Optimo, Conclusion) VALUES ('"+ObtenerHortaliza+"', ' "+TemperaturaH+" ', '"+SeleccionDepto+"', '"+SeleccionTierra+"', '"+SeleccionMes+"', '"+SeleccionClima+"', 'Ademas me enseñaste que para sembrar "+ObtenerHortaliza+" la temperatura de su siembra es "+TemperaturaH+", que puedes sembrarlo en el departamento de "+SeleccionDepto+" en el mes de "+SeleccionMes+" a un clima "+SeleccionClima+"') ";

                BaseDeConocimiento bd = new BaseDeConocimiento(getApplicationContext(),null,null,1);
                SQLiteDatabase db =  bd.getWritableDatabase();
                db.execSQL(consultasql);

                AlertDialog.Builder myBuild = new AlertDialog.Builder(Ensenanza.this);
                myBuild.setMessage("Gracias por enseñarme, eres el mejor agricultor!");
                myBuild.setTitle("Muchas gracias");
                myBuild.setNegativeButton("Continua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        onBackPressed();
                    }
                });
                AlertDialog dialog = myBuild.create();
                dialog.show();




            }

        });

    }



}
