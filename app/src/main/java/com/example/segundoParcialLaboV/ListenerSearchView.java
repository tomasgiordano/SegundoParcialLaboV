package com.example.segundoParcialLaboV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.segundoParcialLaboV.Entities.User;

import java.util.List;

public class ListenerSearchView implements SearchView.OnQueryTextListener {
    private List<User> users;
    private AppCompatActivity activity;

    public ListenerSearchView(List<User> users, AppCompatActivity activity) {
        this.users = users;
        this.activity = activity;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        for (int i = 0; i < this.users.size(); i++) {
            User user = this.users.get(i);

            if (query.equals(user.getUsername())) {
                String mensaje = "El rol del usuario es ".concat(user.getRol());

                DialogDefault dialog = new DialogDefault("Usuario encontrado", mensaje, "Cerrar", null, null, null, null);

                dialog.show(this.activity.getSupportFragmentManager(), "Dialog encontró usuario");
                return false;
            }
        }

        DialogDefault dialog = new DialogDefault("Usuario no encontrado", "El usuario ".concat(query).concat(" no esta dentro de la lista"), "Cerrar", null, null, null, null );
        dialog.show(this.activity.getSupportFragmentManager(), "Dialog NO encontró usuario");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
