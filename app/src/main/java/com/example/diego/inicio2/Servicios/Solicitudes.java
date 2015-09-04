package com.example.diego.inicio2.Servicios;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.diego.inicio2.MainActivity;
import com.example.diego.inicio2.Manejadores.ManejadorNotificaciones;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by diego on 03/09/2015.
 */
public class Solicitudes extends Service {

    private Timer temporizador = new Timer();
    private static final long INTERVALO_ACTUALIZACION = 20000; // En ms .. 1 s son 1000 ms
    private Handler handler;
    int idNoti = 1001;
    int cantidad=0;

    @Override
    public void onCreate() {
        super.onCreate();
        iniciarCronometro();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what >0){
                    //lanza noti
                    ManejadorNotificaciones.notify("Nuevos amigos","Una nueva persona quiere ser tu amigo.",idNoti++);
                    ManejadorNotificaciones.modificarNotificacionesVisto();
                    MainActivity.modificaAmigosSolicitud(cantidad);
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        Log.i("diegoooooooooooo","paro");
        temporizador.cancel();
        super.onDestroy();
    }

    private void iniciarCronometro() {
        temporizador.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Log.i("diegoooooooooooo","paaaaaaa");
                cantidad = ManejadorNotificaciones.cuentaSolicitudes();
                if(cantidad>0){
                    handler.sendEmptyMessage(cantidad);
                }
            }
        }, 0, INTERVALO_ACTUALIZACION);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
