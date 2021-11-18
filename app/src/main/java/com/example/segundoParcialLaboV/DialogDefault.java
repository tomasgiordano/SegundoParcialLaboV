package com.example.segundoParcialLaboV;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogDefault extends DialogFragment {
    private String title;
    private String neutral;
    private String positivo;
    private String negativo;
    private View view;
    private DialogInterface.OnClickListener listener;
    private String mensaje;



    public DialogDefault(View view) {
        this.view = view;
    }

    public DialogDefault(String title, String mensaje, String positivo, String negativo, String neutral, View view, DialogInterface.OnClickListener listener) {
        this.title = title;
        this.negativo = negativo;
        this.neutral = neutral;
        this.view = view;
        this.listener = listener;
        this.mensaje = mensaje;
        this.positivo = positivo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(super.getActivity());

        if (!("".equals(this.negativo)) && this.negativo != null) builder.setNegativeButton(this.negativo, this.listener);
        if (!("".equals(this.neutral)) && this.neutral != null) builder.setNeutralButton(this.neutral, this.listener);
        if (!("".equals(this.title)) && this.title != null) builder.setTitle(this.title);
        if (!("".equals(this.positivo)) && this.positivo != null) builder.setPositiveButton(this.positivo, this.listener);
        if (!("".equals(this.mensaje)) && this.mensaje != null) builder.setMessage(this.mensaje);

        if (this.view != null) builder.setView(this.view);

        return builder.create();
    }
}
