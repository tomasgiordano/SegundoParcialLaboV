package com.example.segundoParcialLaboV;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.segundoParcialLaboV.Entities.User;

import java.util.List;

public class ListenerAgregarUsuario implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private DialogFragment dialog;
    private View viewDialog;
    private Activity activity;
    private List<User> users;
    private User user;

    public ListenerAgregarUsuario(Activity activity, View viewDialog, List<User> users) {
        this.activity = activity;
        this.viewDialog = viewDialog;
        this.users = users;
        this.user = new User();
        this.user.setAdmin(false);
        this.user.setRol("Supervisor");
    }

    @Override
    public void onClick(View viewClicked) {
        if (viewClicked.getId() == R.id.btnCancelar) {
            this.dialog.dismiss();
        }
        else if (viewClicked.getId() == R.id.btnGuardar) {
            if (this.validarCampos()) {
                String username = ((EditText) this.viewDialog.findViewById(R.id.inputUsername)).getText().toString();
                this.user.setId(this.generarId());
                this.user.setUsername(username);
                this.users.add(this.user);

                ((MainActivity) this.activity).actualizarSharedPreferences();
                ((MainActivity) this.activity).actualizarTextView();
                this.dialog.dismiss();
            }
            else {
                Toast.makeText(viewDialog.getContext(), "Los campos no pueden estar vacíos", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.user.setAdmin(Boolean.valueOf(isChecked));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
            this.user.setRol("Supervisor");
        else if (position == 1)
            this.user.setRol("Construction Manager");
        else if (position == 2)
            this.user.setRol("Project Manager");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setDialog(DialogFragment dialog) {
        this.dialog = dialog;
    }

    private boolean validarCampos() {
        EditText inputUsername = this.viewDialog.findViewById(R.id.inputUsername);

        if (inputUsername.getText().toString().isEmpty()) {
            return false;
        }

        return true;
    }

    private int generarId() {
        int id_anterior = 0;

        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId() == null)
                continue;

            int id = this.users.get(i).getId();

            if (id > id_anterior)
                id_anterior = id;
        }

        return id_anterior + 1;
    }


}
