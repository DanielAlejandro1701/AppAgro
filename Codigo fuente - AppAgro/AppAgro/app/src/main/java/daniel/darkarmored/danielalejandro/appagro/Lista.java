package daniel.darkarmored.danielalejandro.appagro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Lista extends AppCompatActivity {

    ListView lv;
    ListView result;
    ArrayList<String> Lista1;
    ArrayList<String> Lista2;
    ArrayAdapter adaptador;
    ArrayAdapter adaptador2;
    Button ensename;


    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lv = (ListView)findViewById(R.id.lista);
        result = (ListView)findViewById(R.id.listados);

        String consulta = "";
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            consulta = null;
        } else {
            consulta = extras.getString("consulta");
        }

        BaseDeConocimiento bd = new BaseDeConocimiento(getApplicationContext(),null,null,1);

        Lista1 = bd.llenar_lv(consulta);
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,Lista1);

        lv.setAdapter(adaptador);

        if (adaptador.isEmpty()){

            final AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
            myBuild.setMessage("No hay verduras que sembrar en esta epoca");
            myBuild.setTitle("Lo sentimos");
            myBuild.setNegativeButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onBackPressed();
                }
            });
            AlertDialog dialog = myBuild.create();
            dialog.show();

        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                BaseDeConocimiento bd = new BaseDeConocimiento(getApplicationContext(),null,null,1);

                String opcion = adaptador.getItem(position).toString();
                String resultado = "select distinct Conclusion from Hortaliza_Verdura where Nombre_Verdura = '"+opcion+"'";
                Lista2 = bd.llenar_lista(resultado);
                adaptador2 = new ArrayAdapter(Lista.this, android.R.layout.simple_list_item_1,Lista2);

                result.setAdapter(adaptador2);



                //Toast.makeText(getBaseContext(), opcion + " ", Toast.LENGTH_LONG).show();

            }
        }

        );

        ensename = (Button)findViewById(R.id.enseñame);
        ensename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente = new Intent(Lista.this, Ensenanza.class);
                startActivity(siguiente);
                finish();
            }
        });


    }


}



