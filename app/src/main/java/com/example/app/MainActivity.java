package com.example.app;

import android.Manifest;

import androidx.annotation.RequiresPermission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String CHANNEL_ID = "1";
    private static final int notificationId = 2;
    private static final int REQUEST_CODE_NOTIFICATION = 100;

    Button b1, b2, b3, b4, b5, b6;
//    EditText t1;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        // Request notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_CODE_NOTIFICATION
                );
            }
        }
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
//        b7 = findViewById(R.id.b7);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
//        b7.setOnClickListener(this);
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private void SendNotification() {
        long[] vibrate = {0, 100, 200, 300};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle("Ma notification")
                .setContentText("hello user")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("C'est une notification pour le test"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(alarmSound)
                .setVibrate(vibrate);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.b1) {
            Toast.makeText(getApplicationContext(), "Message durée longue", Toast.LENGTH_LONG).show();
        } else if (id == R.id.b2) {
            Toast.makeText(getApplicationContext(), "Message durée courte", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.b3) {
            SendNotification();
        } else if (id == R.id.b4) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Salut");
            adb.setMessage("Celle-ci est une boite de dialogue");
            adb.setPositiveButton("OK", (arg0, arg1) -> Toast.makeText(MainActivity.this, "Merci", Toast.LENGTH_LONG).show());
            AlertDialog alert = adb.create();
            alert.show();
        } else if (id == R.id.b5) {
            final String[] filieres = {"TDI", "TDM", "TRI"};
            final boolean[] checkedItems = {false, false, false};
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Choix des filieres");
            adb.setMultiChoiceItems(filieres, checkedItems, (dialog, which, isChecked) -> {
                switch (which) {
                    case 0:
                        checkedItems[0] = isChecked;
                        break;
                    case 1:
                        checkedItems[1] = isChecked;
                        break;
                    case 2:
                        checkedItems[2] = isChecked;
                        break;
                }
                msg = "";
                for (int i = 0; i < checkedItems.length; i++)
                    if (checkedItems[i])
                        msg += filieres[i] + " ";
            });
            adb.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(MainActivity.this, "Filieres choisies " + msg, Toast.LENGTH_LONG).show();
                }
            });
            adb.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(MainActivity.this, "clic sur Non", Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alert = adb.create();
            alert.show();
        } else if (id == R.id.b6) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage("Voulez-vous quitter?");
            adb.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            adb.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(MainActivity.this, "clic sur Non", Toast.LENGTH_LONG).show();
                }
            });
            adb.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(MainActivity.this, "clic sur cancel", Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alert = adb.create();
            alert.show();
        }}}