package com.ugurrsnr.drinkwaterreminder

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random


class SendNotifications(val context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {

    val intent = Intent(context, MainActivity::class.java)

    @SuppressLint("UnspecifiedImmutableFlag")
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    private lateinit var builder: NotificationCompat.Builder
    private val CHANNEL_ID = "i.apps.notifications"

    override fun doWork(): Result {
        notificationMaker()
        return Result.success()
    }

    private fun notificationMaker(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("deneme")
                .setContentText("deneme")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        }else{
            builder = NotificationCompat.Builder(context)
                .setContentTitle("deneme")
                .setContentText("deneme")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        }

        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(), builder.build())
        }

    }


}




    /*
    private fun notificationMaker(){

        //notificationManager = getSystemService(context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                //.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                //.setContentIntent(pendingIntent)
                .setContentTitle("Drink Water")
                .setContentText("Don't forget to drink water")
                .setAutoCancel(true) //notification will be removed when the user tapped
        } else {

            builder = Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                //.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                //.setContentIntent(pendingIntent)
                .setContentTitle("Drink Water")
                .setContentText("Don't forget to drink water")
                .setAutoCancel(true)
        }
        with(NotificationManagerCompat.from(context)) {
            notify(1234, builder.build())

        // notificationManager.notify(1234, builder.build())


    }




        /*/////////////////////////////////////////////7
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(), builder.build())
        }

        return Result.success()


    }

         */


}

     */