package com.iesmurgi.org.u7_a4_notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.app.NotificationCompat as MediaNotificationCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnImg = findViewById<Button>(R.id.btnNotificacionImagenGrande);
        var btnMultimedia = findViewById<Button>(R.id.btnNotificacionMultimedia)

        btnImg.setOnClickListener { generarNotificacionImagenGrande() }
        btnMultimedia.setOnClickListener { generarNotificacionMultimedia(this) }

        crearCanalNotificacion()
    }

    private fun generarNotificacionMultimedia(context: Context) {
        val notificacionId = 0
        val canalId = getString(R.string.id_canal)
        val albumCoverBitmap = BitmapFactory.decodeResource( // (1)
            context.resources,
            R.drawable.simbol
        )
        val mediaStyle = MediaNotificationCompat.MediaStyle() // (2)
            .setShowActionsInCompactView(1, 2, 3) // (3)

        val notificacion = NotificationCompat.Builder(context, canalId)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // (4)
            .setSmallIcon(R.drawable.corchea)
            .addAction(R.drawable.pulgar_abajo, "No me gusta", null) // (5)
            .addAction(R.drawable.previa, "Anterior", null)
            .addAction(R.drawable.pausa, "Pausa", null)
            .addAction(R.drawable.siguiente, "Siguiente", null)
            .addAction(R.drawable.pulgar_arriba, "Me gusta", null)
            .setStyle(mediaStyle) // (6)
            .setContentTitle("Music Festival")
            .setContentText("01. Peace melody")
            .setLargeIcon(albumCoverBitmap) // (7)
            .build()

        with(NotificationManagerCompat.from(this)){
            notify(notificacionId, notificacion)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun crearCanalNotificacion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val nombre = getString(R.string.nombre_canal)
            val canalId = getString(R.string.id_canal)
            val descripcion = getString(R.string.desctipcion_canal)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val canal = NotificationChannel(canalId, nombre, importance).apply {
                description=descripcion
            }

            val nm: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(canal)
        }
    }

    private fun generarNotificacionImagenGrande(){
        val notificacionId = 0
        val canalId = getString(R.string.id_canal)
        val largeIcon = BitmapFactory.decodeResource(
            resources,
            R.drawable.simbol
        )

        val espacioImagen = BitmapFactory.decodeResource(
            resources,
            R.drawable.espacio
        )

        val imagenGrande = NotificationCompat.BigPictureStyle()
            .bigPicture(espacioImagen)
            .bigLargeIcon(null)

        val notificacion = NotificationCompat.Builder(this, canalId)
            .setLargeIcon(largeIcon)
            .setSmallIcon(R.drawable.ic_circle_notifications)
            .setContentTitle("PMDM Notificaciones en Android con Kotlin")
            .setContentText("1. Crear notificaciones")
            .setSubText("iesmurgi.org")
            .setStyle(imagenGrande)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_bookmark, "Leer más tarde", null)
            .build()

        with(NotificationManagerCompat.from(this)){
            notify(notificacionId, notificacion)
        }
    }
}