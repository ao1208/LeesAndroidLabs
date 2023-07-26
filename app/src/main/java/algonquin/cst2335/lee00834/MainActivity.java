package algonquin.cst2335.lee00834;

import static java.nio.charset.StandardCharsets.UTF_8;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

import algonquin.cst2335.lee00834.databinding.ActivityMainBinding;

/**
 * This page represents the first page loaded
 * @author Wan-Hsuan Lee
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    // for sending network requests:
    protected RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//calling onCreate from parent class
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //loads an XML file on the page
        setContentView(R.layout.activity_main);

        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this);

        binding.getForecast.setOnClickListener(click -> {

            String cityName = binding.theEditText.getText().toString();

            //server name and parameters:                           name=value&name2=value2&name3=value3
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" +
                    URLEncoder.encode(cityName) //replace spaces, &. = with other characters
                    + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";

            Log.e("URL", url);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (successfulResponse) -> {
                        try {
                            String name = successfulResponse.getString( "name" );
                            JSONObject coord = successfulResponse.getJSONObject( "coord" );
                            int vis = successfulResponse.getInt("visibility");

                            JSONArray weatherArray = successfulResponse.getJSONArray( "weather");
                            JSONObject pos0 = weatherArray.getJSONObject( 0 );
                            String description = pos0.getString("description");
                            String iconName = pos0.getString("icon");
                            String imageURL = "http://openweathermap.org/img/w/" + iconName + ".png";

                            JSONObject main = successfulResponse.getJSONObject( "main"  );
                            double temp = main.getDouble("temp");
                            double max = main.getDouble("temp_max");
                            double min = main.getDouble("temp_min");
                            int humidity = main.getInt("humidity");

                            runOnUiThread(() -> {
                                binding.temp.setText("The temperature is " + temp + " degrees");
                                binding.temp.setVisibility(View.VISIBLE);
                                binding.maxTemp.setText("The temperature is " + max + " degrees");
                                binding.maxTemp.setVisibility(View.VISIBLE);
                                binding.minTemp.setText("The temperature is " + min + " degrees");
                                binding.minTemp.setVisibility(View.VISIBLE);
                                binding.humidity.setText("The temperature is " + humidity + " degrees");
                                binding.humidity.setVisibility(View.VISIBLE);
                                binding.description.setText("The temperature is " + description + " degrees");
                                binding.description.setVisibility(View.VISIBLE);
                            });

                            String imageFilePath = getFilesDir().getPath() + "/" + iconName + ".png";
                            File imageFile = new File(imageFilePath);
                            if (imageFile.exists()) {
                                Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
                                runOnUiThread(() -> binding.icon.setImageBitmap(bitmap));
                            } else {
                                ImageRequest imgReq = new ImageRequest(imageURL, new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        try {
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName, MODE_PRIVATE));
                                        } catch (FileNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                        (error) -> {
                                            int i = 0;
                                        });
                                queue.add(imgReq);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },//gets called if it is successful
                    (vError) -> {
                        int i = 0;
                    });//gets called if there is an error
            queue.add(request);//run the web query
        });
    }
}