# Gimbal Android Basic Sample With Permissions
Minimal Gimbal Integration Example on Android with marshmallow permissions check. After setting up your application, place(s) and communication(s) on Gimbal Manager the code below will yield **Place Events** and **Local Notifications**.

## Before you create your Android application
Using the **Gimbal Manager**:
[https://manager.gimbal.com/](https://manager.gimbal.com/)
- create your Gimbal account 
- create an **Application** using package name **sample.android.gimbal.com.pleasepermitgimbal** (generates you API KEY)
- create at least one **Place** (using a Beacon or Geofence)
- create at least one **Communicate** (used for the local notification)
- download the latest V2 SDK

## In the sample Android application
- to run this sample in Android Studio choose the 'Import Project' option
- fill your API KEY into the MainActivity
- add the jars from the SDK libs directory to the project libs directory

Full Gimbal Docs [http://docs.gimbal.com/](http://docs.gimbal.com/)
