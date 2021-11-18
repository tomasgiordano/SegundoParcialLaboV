package com.example.segundoParcialLaboV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.segundoParcialLaboV.Entities.User;
import com.example.segundoParcialLaboV.HTTP.EjecutarHTTP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback {
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.userList = this.traerUsuarios();
        this.actualizarTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu_principal, menu);

        MenuItem menuItem = menu.findItem(R.id.buscar);
        ListenerSearchView listenerSearchView = new ListenerSearchView(this.userList, this);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(listenerSearchView);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.agregar_usuario) {
            View viewDialog = LayoutInflater.from(this).inflate(R.layout.agregar_usuario, null);
            ListenerAgregarUsuario listenerAgregarUsuario = new ListenerAgregarUsuario(this, viewDialog, this.userList);

            Button btnCancelar = viewDialog.findViewById(R.id.btnCancelar);
            Button btnGuardar = viewDialog.findViewById(R.id.btnGuardar);
            CompoundButton tglAdmin = viewDialog.findViewById(R.id.tglAdmin);
            Spinner spinnerRol = viewDialog.findViewById(R.id.slcRol);

            btnCancelar.setOnClickListener(listenerAgregarUsuario);
            btnGuardar.setOnClickListener(listenerAgregarUsuario);
            tglAdmin.setOnCheckedChangeListener(listenerAgregarUsuario);
            spinnerRol.setOnItemSelectedListener(listenerAgregarUsuario);

            ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.roles_array, android.R.layout.simple_spinner_item);
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRol.setAdapter(adapterSpinner);

            DialogDefault dialog = new DialogDefault("Crear Usuario", null, null, null, null, viewDialog, null);

            listenerAgregarUsuario.setDialog(dialog);
            dialog.show(this.getSupportFragmentManager(), "Dialog agregar contacto");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<User> traerUsuarios() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("misUsuarios", Context.MODE_PRIVATE);
        String usuariosCompleto = sharedPreferences.getString("usuarios", "sinUsuarios");

        if ("sinUsuarios".equals(usuariosCompleto)) {
            Handler handler = new Handler(this);
            EjecutarHTTP hiloHttp = new EjecutarHTTP(handler);
            hiloHttp.start();

            return new ArrayList<User>();
        }
        else {
            return this.parserUsuariosJSON(usuariosCompleto);
        }
    }

    private List<User> parserUsuariosJSON(String string) {
        List<User> users = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(string);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = Integer.valueOf(jsonObject.getString("id"));
                boolean admin = Boolean.valueOf(jsonObject.getString("admin"));
                String username = jsonObject.getString("username");
                String rol = jsonObject.getString("rol");

                User user = new User(id, username, rol, admin);
                users.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        this.userList = this.parserUsuariosJSON(msg.obj.toString());
        this.actualizarTextView();
        this.actualizarSharedPreferences();

        return false;
    }

    public void actualizarTextView() {
        TextView textView = this.findViewById(R.id.users);
        textView.setText(this.userList.toString());
    }

    public void actualizarSharedPreferences() {
        SharedPreferences prefs = this.getSharedPreferences("misUsuarios", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuarios", this.userList.toString());
        editor.commit();
    }
}