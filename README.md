# DigitalPictureFrame

[![SonarCloud Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=rdrenner_DigitalPictureFrame&metric=alert_status)](https://sonarcloud.io/dashboard?id=rdrenner_DigitalPictureFrame)

## Digital Picture Frame for Raspberry Pi (Under Construction)

## Setting Up Your Raspberry Pi

After installing Raspbian and logging into your Raspberry Pi:

1. Disable screen blanking under Raspberry Pi Configuration -> Display

2. Edit the [config/DigitalPictureFrame.properties](config/DigitalPictureFrame.properties) file and set the imagePath property to point to the directory where your pictures are stored on the Raspberry Pi.

3. To set the Digital Picture Frame to run on startup edit/create file */home/pi/.config/autostart/App.desktop* and add this entry making sure to set the correct install path.

```
[Desktop Entry] 
Type=Application
Exec=<installpath>/DigitalPictureFrame.sh
```
