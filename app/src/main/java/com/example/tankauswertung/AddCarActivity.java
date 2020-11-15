package com.example.tankauswertung;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddCarActivity extends AppCompatActivity {


    /**
     * ausgeführt, sobald die Aktivität gestartet wird
     *
     * @param savedInstanceState —
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // zeigt den Zurück-Button an
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * erstellt das Menü (den Fertig-Button) in der ActionBar
     * @param menu Menü
     * @return -
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    /**
     * lässt den Fertig-Button funktionieren
     * @param item geklicktes MenuItem
     * @return -
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_car_done) {
            Toast toast = Toast.makeText(getApplicationContext(), "Add car finished!", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        } else {
            return false;
        }
    }

    /**
     * lässt den Zurück-Button funktionieren
     * @return -
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
