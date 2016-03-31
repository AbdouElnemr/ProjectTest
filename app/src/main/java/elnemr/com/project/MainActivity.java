package elnemr.com.project;

import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import elnemr.com.project.Adapters.MovieAdapter;
import elnemr.com.project.Adapters.Pojo;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    JSONClass jsonClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    gridView = (GridView) findViewById(R.id.gridview);
        jsonClass = new JSONClass();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            jsonClass.execute("http://api.themoviedb.org/3/movie/popular?api_key=013fdbe9d59157bfc099e169c0ab7a83");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class JSONClass extends AsyncTask<String, Void, List<Pojo>> {
        List<Pojo>pojoList;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<Pojo> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();

                String Line = "";
                while ((Line = bufferedReader.readLine()) != null) {
                    buffer.append(Line);
                }
                String finalJSOn = buffer.toString();


                JSONObject parentObject = new JSONObject(finalJSOn);
                JSONArray parentArray = parentObject.getJSONArray("results");



                pojoList = new ArrayList<>();
                for(int i =0; i<parentArray.length();i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    Pojo pojo = new Pojo();

                    pojo.setImageurl(finalObject.getString("poster_path"));

                    pojoList.add(pojo);

                    System.out.println(" ------------------------------------");
                    System.out.println(pojoList.toString());
                    System.out.println(" ------------------------------------");

                }

                return  pojoList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Pojo> aVoid) {
            MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), R.layout.image, pojoList );
            gridView.setAdapter(movieAdapter);

        }
    }
}
