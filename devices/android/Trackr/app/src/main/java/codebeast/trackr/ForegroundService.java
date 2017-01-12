package codebeast.trackr;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import codebeast.trackr.domain.DeviceMessage;

public class ForegroundService extends Service {

    private static final String LOG_TAG = "ForegroundService";
    private Timer timer;
    private int UPDATE_RATE = 60000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(updateLocationTask(), 2000, UPDATE_RATE);
        final String fileName = "GpsPositions";
    }

    private TimerTask updateLocationTask() {
        return new TimerTask() {
            @Override
            public void run() {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (ActivityCompat.checkSelfPermission(ForegroundService.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ForegroundService.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                                    @Override
                                    public void onStatusChanged(String provider, int status, Bundle extras) {
                                        System.out.println("LoginActivity.onStatusChanged");
                                    }

                                    @Override
                                    public void onProviderEnabled(String provider) {
                                    }

                                    @Override
                                    public void onProviderDisabled(String provider) {
                                    }

                                    @Override
                                    public void onLocationChanged(final Location location) {
                                        System.out.println("Network - LoginActivity.onLocationChanged");
                                        System.out.println("location = [" + location + "]");

                                    }
                                });
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                                    @Override
                                    public void onStatusChanged(String provider, int status, Bundle extras) {
                                        System.out.println("LoginActivity.onStatusChanged");
                                    }

                                    @Override
                                    public void onProviderEnabled(String provider) {
                                    }

                                    @Override
                                    public void onProviderDisabled(String provider) {
                                    }

                                    @Override
                                    public void onLocationChanged(final Location location) {
                                        System.out.println("GPS - LoginActivity.onLocationChanged");
                                        System.out.println("location = [" + location + "]");
                                    }
                                });


                        final Location gps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        final Location network = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        final Location lastKnownLocation = gps == null ? network : gps;
                        if (lastKnownLocation == null) {
                            Toast.makeText(getApplicationContext(), "No location found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        final String message = lastKnownLocation.getLatitude() + ", " + lastKnownLocation.getLongitude();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        try {
                            sendLocation(lastKnownLocation);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Start Foreground Intent ");
            Intent notificationIntent = new Intent(this, MyApplication.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Intent previousIntent = new Intent(this, ForegroundService.class);
            previousIntent.setAction(Constants.ACTION.PREV_ACTION);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);

            Intent playIntent = new Intent(this, ForegroundService.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent nextIntent = new Intent(this, ForegroundService.class);
            nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_audiotrack);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Trackr")
                    .setTicker("Trackr")
                    .setContentText("Asset Tracking")
                    .setSmallIcon(R.drawable.ic_media_route_connecting_00_light)
                    .setLargeIcon(
                            Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(android.R.drawable.ic_media_play, "Play", pplayIntent)
                    .addAction(android.R.drawable.ic_media_pause, "Stop", pnextIntent).build();
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.i(LOG_TAG, "Clicked Previous");
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.i(LOG_TAG, "Clicked Play");
            timer = new Timer();
            timer.schedule(updateLocationTask(), 2000, UPDATE_RATE);
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i(LOG_TAG, "Clicked Next");
            timer.cancel();
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");
    }


    private void sendLocation(final Location location) {

        if (location == null) {
            Log.i(LOG_TAG, "location should not be null");
        }

        final String androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final String imei = telephonyManager.getDeviceId();

        final DeviceMessage deviceMessage = new DeviceMessage()
                .setDeviceId(androidDeviceId)
                .setImei(imei)
                .setLat(location.getLatitude())
                .setLng(location.getLongitude())
                .setSpeed(location.getSpeed())
                .setAccuracy(location.getAccuracy())
                .setDeviceTimestamp(location.getTime())
                .setSystemTimestamp(new Date().getTime());
        Log.i(LOG_TAG, "deviceMessage: " + deviceMessage);
        new ForegroundService.UpdateLocation().execute(deviceMessage);

    }

    private class UpdateLocation extends AsyncTask<DeviceMessage, Void, DeviceMessage> {
        @Override
        protected DeviceMessage doInBackground(DeviceMessage... params) {
            Log.i(LOG_TAG, "try post: ");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            final String ip = "52.56.101.230:5555";

            restTemplate.postForObject("http://52.56.101.230:3000/devicemessage", params[0], String.class);
            //  Log.i(TAG, "aBoolean: " + aBoolean);
            return params[0];
        }

        @Override
        protected void onPostExecute(DeviceMessage result) {
            Log.i(LOG_TAG, "posted: " + result);
        }
    }


}