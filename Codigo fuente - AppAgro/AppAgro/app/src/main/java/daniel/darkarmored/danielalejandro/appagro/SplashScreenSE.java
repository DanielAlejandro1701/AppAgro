package daniel.darkarmored.danielalejandro.appagro;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreenSE extends AppCompatActivity {

    public static final int segundos = 1;
    public static final int milisegundos = segundos*1000;
    public static final int delay= 2;
    private ProgressBar pbprogreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_se);
        pbprogreso=(ProgressBar) findViewById(R.id.BarraProgreso);
        pbprogreso.setMax(maximo_progreso());
        empezaranimacion();
    }

    public void empezaranimacion (){
        new CountDownTimer(milisegundos,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                pbprogreso.setProgress(establecer_progreso(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent nuevoform= new Intent(SplashScreenSE.this,EleccionSe.class);
                startActivity(nuevoform);
                finish();
            }
        }.start();
    }

    public int establecer_progreso(long miliseconds){
        return (int)((milisegundos-miliseconds)/1000);
    }
    public int maximo_progreso(){
        return segundos-delay;
    }
}
