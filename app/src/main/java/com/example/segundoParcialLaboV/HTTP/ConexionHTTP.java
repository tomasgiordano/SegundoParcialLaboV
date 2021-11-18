package com.example.segundoParcialLaboV.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexionHTTP {

    public byte[] obtenerRespuesta(String urlString) {
        try {
            URL url = new URL(urlString);

            HttpURLConnection URLConnection = (HttpURLConnection) url.openConnection();

            URLConnection.setRequestMethod("GET");
            URLConnection.connect();

            int respuesta = URLConnection.getResponseCode();

            if (respuesta == 200) {
                InputStream inputStream = URLConnection.getInputStream();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int cant_bytes_leidos = 0;

                while ((cant_bytes_leidos = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, cant_bytes_leidos);
                }

                inputStream.close();

                return byteArrayOutputStream.toByteArray();
            }
            else {
                throw new RuntimeException("Error en la conexión con el servidor: " + respuesta);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
