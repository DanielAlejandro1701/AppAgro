package daniel.darkarmored.danielalejandro.appagro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class EleccionSe extends AppCompatActivity {

    String[] TipoTierra= {"Arcilloso","Arenoso","Franco","Franco arenoso","Franco arcilloso","Franco limoso","Franco arcilloso-arenoso"};
    String [] TipoClima= {"Frio","Moderadamente frio","Templado","Moderadamente calido","Calido","Caliente"};
    Spinner SpinnerTipotierra;
    Spinner SpinnerClima;
    TextView Temperatura;
    String Ciudad;
    TextView Fecha ;
    String Celsius,Mes;
    double tempCelsius;
    String SeleccionClima;
    String SeleccionTierra;
    CheckBox Cundinamarca;
    CheckBox Boyaca;
    CheckBox Nariño;
    public String SentenciaSQL = "hola",frase;

    Button boton;

    public EleccionSe () {
        SentenciaSQL = "";
        frase="";

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleccion_se);

        Cundinamarca = (CheckBox) findViewById(R.id.Cundinamarca);
        Boyaca = (CheckBox) findViewById(R.id.Boyaca);
        Nariño = (CheckBox) findViewById(R.id.Nariño);
        Temperatura = (TextView) findViewById(R.id.Temp);
        boton = (Button)findViewById(R.id.BotonSE);

        SpinnerTipotierra=(Spinner)findViewById(R.id.SpinnerTierra);
        SpinnerClima=(Spinner)findViewById(R.id.SpinnerClima);
        CargaSpinner();


        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        EleccionSe.Localizacion Local = new EleccionSe.Localizacion();
        Local.setUbicacion(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        TextView Temperatura = (TextView) findViewById(R.id.Temp);
        //final String temp = (String) Temperatura.getText();
        //final Double tempe = Double.parseDouble(temp);

        // Fecha Mes
        final String[] monthname = {(String) android.text.format.DateFormat.format("MMMM", new Date())};
        Fecha = (TextView) findViewById(R.id.Fecha);
        Fecha.setText(monthname[0]);
        Mes = Fecha.getText().toString();
        //final int Mesfinal = Integer.parseInt(Mes);
        //cierre mes
        SpinnerClima.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+ "es seleccionado", Toast.LENGTH_LONG).show();
                SeleccionClima= parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), SeleccionClima+ " clima es seleccionado", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        SpinnerTipotierra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+ "es seleccionado", Toast.LENGTH_LONG).show();
                SeleccionTierra = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), SeleccionTierra+ " tierra es seleccionado", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //tempCelsius = Double.parseDouble(Celsius);
       // Toast.makeText(getBaseContext(), tempCelsius + " ", Toast.LENGTH_LONG).show();

        RealizarConsulta();



    }

    public void CargaSpinner(){

        ArrayAdapter<String> adaptadorTierra = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,TipoTierra);
        ArrayAdapter<String> adaptadorClima = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,TipoClima);
        SpinnerTipotierra.setAdapter(adaptadorTierra);
        SpinnerClima.setAdapter(adaptadorClima);

    }

    //en la siguiente funcion se obtiene la ubicacion por GPS, esta es una funcion de Android.

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    /*mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0) + "\n"

                            + DirCalle.getAdminArea()

                    );*/
                }
                Address DirCalle=list.get(0);
                Ciudad=DirCalle.getAdminArea().toString();
                new EleccionSe.OpenWeatherMapTask(Ciudad,Temperatura).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Localizacion implements LocationListener {
        EleccionSe ubicacion;

        public EleccionSe getUbicacion() {
            return ubicacion;
        }

        public void setUbicacion(EleccionSe ubicacion) {
            this.ubicacion = ubicacion;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();
            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            //mensaje1.setText(Text);
            this.ubicacion.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            //mensaje1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            //mensaje1.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Este metodo se ejecuta cada vez que se detecta un cambio en el
            // status del proveedor de localizacion (GPS)
            // Los diferentes Status son:
            // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
            // TEMPORARILY_UNAVAILABLE -> Temporalmente no disponible pero se
            // espera que este disponible en breve
            // AVAILABLE -> Disponible
        }

    }

    // en la siguiente funcion se obtiene la temperatura del lugar donde nos encontramos, esta funcion es una API propiedad de Open Weather Map.

    private class OpenWeatherMapTask extends AsyncTask<Void, Void, String> {

        String cityName;
        TextView tvResult;

        String dummyAppid = "8890d1e80ac68d64b838b6b6427f8f4a";
        String queryWeather = "http://api.openweathermap.org/data/2.5/weather?q=";
        String queryDummyKey = "&appid=" + dummyAppid;

        OpenWeatherMapTask(String cityName, TextView tvResult){
            this.cityName = cityName;
            this.tvResult = tvResult;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            String queryReturn;

            String query = null;
            try {
                query = queryWeather + URLEncoder.encode(cityName, "UTF-8")+"&units=metric" + queryDummyKey;
                queryReturn = sendQuery(query);
                result += ParseJSON(queryReturn);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            }


            final String finalQueryReturn = query + "\n\n" + queryReturn;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //textViewInfo.setText(finalQueryReturn);
                }
            });


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            tvResult.setText(s);
            Celsius = s;

        }

        private String sendQuery(String query) throws IOException {
            String result = "";

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection)searchURL.openConnection();
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader,
                        8192);

                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
            }

            return result;
        }

        private String ParseJSON(String json){
            String jsonResult = "";

            try {
                JSONObject JsonObject = new JSONObject(json);
                String cod = jsonHelperGetString(JsonObject, "cod");

                if(cod != null){
                    if(cod.equals("200")){

                        //jsonResult += jsonHelperGetString(JsonObject, "name") + "\n";
                       // JSONObject sys = jsonHelperGetJSONObject(JsonObject, "sys");
                        //if(sys != null){
                           // jsonResult += jsonHelperGetString(sys, "country") + "\n";
                       // }
                        //jsonResult += "\n";

                        /*JSONObject coord = jsonHelperGetJSONObject(JsonObject, "coord");
                        if(coord != null){
                            String lon = jsonHelperGetString(coord, "lon");
                            String lat = jsonHelperGetString(coord, "lat");
                            jsonResult += "lon: " + lon + "\n";
                            jsonResult += "lat: " + lat + "\n";
                        }
                        jsonResult += "\n";*/

                        /*JSONArray weather = jsonHelperGetJSONArray(JsonObject, "weather");
                        if(weather != null){
                            for(int i=0; i<weather.length(); i++){
                                JSONObject thisWeather = weather.getJSONObject(i);
                                jsonResult += "weather " + i + ":\n";
                                jsonResult += "id: " + jsonHelperGetString(thisWeather, "id") + "\n";
                                jsonResult += jsonHelperGetString(thisWeather, "main") + "\n";
                                jsonResult += jsonHelperGetString(thisWeather, "description") + "\n";
                                jsonResult += "\n";
                            }
                        }*/

                        JSONObject main = jsonHelperGetJSONObject(JsonObject, "main");
                        if(main != null){
                            jsonResult += " " + jsonHelperGetString(main, "temp");
                            //jsonResult += "pressure: " + jsonHelperGetString(main, "pressure") + "\n";
                            //jsonResult += "humidity: " + jsonHelperGetString(main, "humidity") + "\n";
                            //jsonResult += "temp_min: " + jsonHelperGetString(main, "temp_min") + "\n";
                            //jsonResult += "temp_max: " + jsonHelperGetString(main, "temp_max") + "\n";
                            //jsonResult += "sea_level: " + jsonHelperGetString(main, "sea_level") + "\n";
                            //jsonResult += "grnd_level: " + jsonHelperGetString(main, "grnd_level") + "\n";
                            //jsonResult += "\n";
                        }

                       /* jsonResult += "visibility: " + jsonHelperGetString(JsonObject, "visibility") + "\n";
                        jsonResult += "\n";

                        JSONObject wind = jsonHelperGetJSONObject(JsonObject, "wind");
                        if(wind != null){
                            jsonResult += "wind:\n";
                            jsonResult += "speed: " + jsonHelperGetString(wind, "speed") + "\n";
                            jsonResult += "deg: " + jsonHelperGetString(wind, "deg") + "\n";
                            jsonResult += "\n";
                        }*/

                        //...incompleted

                    }else if(cod.equals("404")){
                        String message = jsonHelperGetString(JsonObject, "message");
                        jsonResult += "cod 404: " + message;
                    }
                }else{
                    jsonResult += "cod == null\n";
                }

            } catch (JSONException e) {
                e.printStackTrace();
                jsonResult += e.getMessage();
            }

            return jsonResult;
        }

        private String jsonHelperGetString(JSONObject obj, String k){
            String v = null;
            try {
                v = obj.getString(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return v;
        }

        private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k){
            JSONObject o = null;

            try {
                o = obj.getJSONObject(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return o;
        }

        private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k){
            JSONArray a = null;

            try {
                a = obj.getJSONArray(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return a;
        }
    }




    public void RealizarConsulta(){

         boton = (Button)findViewById(R.id.BotonSE);
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
             public void onClick(View v) {

                if (Boyaca.isChecked()) {
                    String consulta = "Select distinct Nombre_Verdura from Hortaliza_Verdura where Departamento = 'Boyaca' AND Clima_Optimo = '"+SeleccionClima+"' AND Tipo_Suelo = '"+SeleccionTierra+"' AND Mes_Optimo= '"+Mes+"'";
                    Intent i = new Intent(EleccionSe.this, Lista.class);
                    i.putExtra("consulta", consulta);
                    startActivity(i);
                } else {
                    if (Cundinamarca.isChecked()) {
                        String consulta = "Select distinct Nombre_Verdura from Hortaliza_Verdura where Departamento = 'Cundinamarca' AND Clima_Optimo = '"+SeleccionClima+"' AND Tipo_Suelo = '"+SeleccionTierra+"' AND Mes_Optimo= '"+Mes+"'";
                        Intent i = new Intent(EleccionSe.this, Lista.class);
                        i.putExtra("consulta", consulta);
                        startActivity(i);
                    } else {
                        if (Nariño.isChecked()) {
                            String consulta = "Select distinct Nombre_Verdura from Hortaliza_Verdura where Departamento = 'Nariño' AND Clima_Optimo = '"+SeleccionClima+"' AND Tipo_Suelo = '"+SeleccionTierra+"' AND Mes_Optimo= '"+Mes+"'";
                            Intent i = new Intent(EleccionSe.this, Lista.class);
                            i.putExtra("consulta", consulta);
                            startActivity(i);
                        }
                        else {
                            final AlertDialog.Builder myBuild = new AlertDialog.Builder(EleccionSe.this);
                            myBuild.setMessage("Selecciona alguna opcion");
                            myBuild.setTitle("Lo sentimos");
                            myBuild.setNegativeButton("Continuar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                                                    }
                            });
                            AlertDialog dialog = myBuild.create();
                            dialog.show();

                            }
                        }
                    }
                }


       });


    }




}