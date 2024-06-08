package rodriguez.miguel.menu_lista;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonSalir;
    private Button buttonAdd;
    private ListView listViewTasks;
    private ArrayAdapter<String> tasksAdapter;
    private ArrayList<String> tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSalir = findViewById(R.id.buttonSalir);
        editTextTask = findViewById(R.id.editar_texto);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewTasks = findViewById(R.id.listViewTasks);

        tasksList = new ArrayList<>();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);
        listViewTasks.setAdapter(tasksAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    tasksList.add(task);
                    tasksAdapter.notifyDataSetChanged();
                    editTextTask.setText("");
                }
            }
        });
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTaskMenu(view, position);
            }
        });
    }

    private void showTaskMenu(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.task_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                if (item.getItemId() == R.id.menu_editer) {
                    showEditTaskDialog(position);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    tasksList.remove(position);
                    tasksAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showEditTaskDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar tarea");

        final EditText input = new EditText(this);
        input.setText(tasksList.get(position));
        builder.setView(input);

        builder.setPositiveButton("Guardar", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                String editedTask = input.getText().toString().trim();
                if (!editedTask.isEmpty()) {
                    tasksList.set(position, editedTask);
                    tasksAdapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.show();
    }
}


