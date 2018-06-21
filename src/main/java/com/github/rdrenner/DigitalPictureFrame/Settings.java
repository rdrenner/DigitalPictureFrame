/**
 * Digital Picture Frame
 * 
 * Copyright (c) 2016 - Ray Renner
 * 
 * MIT License
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * You can find this project at https://github.com/rdrenner/DigitalPictureFrame
 * 
 */
package com.github.rdrenner.DigitalPictureFrame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.nio.file.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Ray Renner
 *
 */
public class Settings {
   // Static property keys
   private static final String PROP_PICTURE_DURATION = "duration";
   private static final String PROP_CAPTION_FONT = "captionFont";
   private static final String PROP_CAPTION_FONT_SIZE = "captionFontSize";
   private static final String PROP_CAPTION_LOCATION = "captionLocation";
   private static final String PROP_IMAGE_PATH = "imagePath";

   private static final String PROPERTY_FILE = "DigitalPictureFrame.properties";

   // Digitial Picture Frame Properties
   private static final Logger logger = LogManager.getLogger(Settings.class);
   // Picture settings
   private int duration;

   // caption settings
   private String captionFont;
   private int captionFontSize;
   private String captionLocation;

   //catalog seetings
   private String imagePath;

   private Properties defaultProps;
   private Properties appProps;

   Settings() {
      try {
         // Load default properties
         ClassLoader classLoader = getClass().getClassLoader();
         InputStream defaultFile = classLoader.getResourceAsStream(PROPERTY_FILE);
         defaultProps = new Properties();
         defaultProps.load(defaultFile);
         defaultFile.close();
         logger.debug("Default Properties" + defaultProps.toString());
         // defaultProps.list(System.out);

         // Load application properties
         appProps = new Properties(defaultProps);
         Path appPropPath = Paths.get(PROPERTY_FILE);
         if (Files.exists(appPropPath)) {
            FileInputStream appFile = new FileInputStream(PROPERTY_FILE);
            if (appFile != null) {
               appProps.load(appFile);
            }
            appFile.close();
         }

         logger.debug("Application Properties" + appProps.toString());
         // appProps.list(System.out);

         // Load properties variables
         duration = Integer.parseInt(getPropValue(PROP_PICTURE_DURATION));
         captionFont = getPropValue(PROP_CAPTION_FONT);
         captionFontSize = Integer.parseInt(getPropValue(PROP_CAPTION_FONT_SIZE));
         captionLocation = getPropValue(PROP_CAPTION_LOCATION);
         imagePath = getPropValue(PROP_IMAGE_PATH);

      } catch (IOException e) {
         logger.error("IOException ", e);
      }
   }

   public int getDuration() {
      return duration;
   }

   public String getCaptionFont() {
      return captionFont;
   }

   public int getCaptionFontSize() {
      return captionFontSize;
   }

   public String getCaptionLocation() {
      return captionLocation;
   }

   public String getImagePath()
   {
      return imagePath;
   }

   private String getPropValue(String key) {
      if (appProps.contains(key) || defaultProps.containsKey(key)) {
         return appProps.getProperty(key);
      } else {
         logger.error("Application Property no found: " + key);
         throw new RuntimeException("Application Property no found: " + key);
      }
   }
}