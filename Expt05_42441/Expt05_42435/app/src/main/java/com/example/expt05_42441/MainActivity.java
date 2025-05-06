package com.example.expt05_42441;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> items = Arrays.asList(
            "Item 1", "Item 2", "Item 3", "Item 4"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        listView = findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fab);

        // Setup ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items
        );
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        // Setup click listeners
        findViewById(R.id.btnSimpleDialog).setOnClickListener(v -> showSimpleDialog());
        findViewById(R.id.btnCustomDialog).setOnClickListener(v -> showCustomDialog());

        fab.setOnClickListener(v -> {
            Snackbar.make(v, "This is a Snackbar message", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
        });
    }

    private void showSimpleDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Simple Dialog")
                .setMessage("This is a simple dialog message")
                .setPositiveButton("OK", (dialog, which) -> {
                    Toast.makeText(this, "OK clicked", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showCustomDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom, null);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Custom Dialog")
                .setView(dialogView)
                .setPositiveButton("Submit", (dialog, which) -> {
                    Toast.makeText(this, "Form submitted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(this, "About selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_delete) {
            Toast.makeText(this, "Delete selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_share) {
            Toast.makeText(this, "Share selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onContextItemSelected(item);
    }
}