package daniel.darkarmored.danielalejandro.appagro;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class MenuAyuda extends AppCompatActivity {

    VideoView videov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ayuda);
        videov = (VideoView) findViewById(R.id.Video);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Uri path = Uri.parse("android.resource://daniel.darkarmored.danielalejandro.appagro/"+ R.raw.video);
        MediaController mc = new MediaController (this);
        videov.setMediaController(mc);
        videov.setVideoURI(path);
        videov.start();

    }


}
