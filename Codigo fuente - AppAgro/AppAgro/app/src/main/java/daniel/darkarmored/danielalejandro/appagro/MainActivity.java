package daniel.darkarmored.danielalejandro.appagro;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bienvenido;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseDeConocimiento dbHelper = new BaseDeConocimiento(getBaseContext(),null,null,1);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Toast.makeText(getBaseContext(), "Base de datos preparada", Toast.LENGTH_LONG).show();


        bienvenido=(Button)findViewById(R.id.botonBienvenida);

        bienvenido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent bienvenido = new Intent(MainActivity.this, Navegador.class);
                startActivity(bienvenido);


            }
        });

    }



}
