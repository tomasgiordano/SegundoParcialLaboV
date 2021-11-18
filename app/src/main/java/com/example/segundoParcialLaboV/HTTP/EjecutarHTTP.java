package com.example.segundoParcialLaboV.HTTP;

import android.os.Handler;
import android.os.Message;

import com.example.segundoParcialLaboV.HTTP.ConexionHTTP;

public class EjecutarHTTP extends Thread {
    private Handler handler;

    public EjecutarHTTP(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        ConexionHTTP conexion = new ConexionHTTP();
        //IP DEL HOST
        byte[] respuestaJson = conexion.obtenerRespuesta("http://192.168.0.203:3001/usuarios");

        String respuestaString = new String(respuestaJson);
        Message message = new Message();
        message.obj = respuestaString;

        this.handler.sendMessage(message);
    }
}
