/**
 * Digital Picture Frame
 * 
 * Copyright (c) 2016 - Ray Renner
 * 
 * MIT License
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * You can find this project at https://github.com/rdrenner/DigitalPictureFrame
 * 
 */
package com.github.rdrenner.digitalpictureframe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import java.nio.file.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Ray Renner
 *
 */
public class Settings implements Serializable {
   // Static property keys
   private static final String PROP_PICTURE_DURATION = "duration";
   private static final String PROP_CAPTION_FONT = "captionFont";
   private static final String PROP_CAPTION_FONT_SIZE = "captionFontSize";
   private static final String PROP_CAPTION_LOCATION = "captionLocation";
   private static final String PROP_IMAGE_PATH = "imagePath";
   private static final String PROP_SAMPLE_IMAGE_PATH = "sampleImagePath";
   private static final String PROP_USE_SAMPLE_IMAGES = "useSampleImages";


   private static final String PROPERTY_FILE = "DigitalPictureFrame.properties";
   private static final String CONFIG_FOLDER = "./conf/";

   // Digital Picture Frame Properties
   static final long serialVersionUID = 1L;
   private static final Logger logger = LogManager.getLogger(Settings.class);
   // Picture settings
   private int duration;

   // caption settings
   private String captionFont;
   private int captionFontSize;
   private String captionLocation;

   // catalog settings
   private String imagePath;
   private String sampleImagePath;
   private boolean useSampleImages;

   private Properties defaultProps;
   private Properties appProps;

   Settings() {
      try {

         logger.info("Getting Settings");
         // Load default properties
         ClassLoader classLoader = getClass().getClassLoader();
         InputStream defaultFile = classLoader.getResourceAsStream(PROPERTY_FILE);
         defaultProps = new Properties();
         defaultProps.load(defaultFile);
         defaultFile.close();
         logger.debug("Default Properties: {}", defaultProps);

         // Load application properties
         appProps = new Properties(defaultProps);
         String appPropFile = CONFIG_FOLDER + PROPERTY_FILE;
         Path appPropPath = Paths.get(appPropFile);
         logger.debug("Application Property Path is {}", appPropPath);
         if (appPropPath.toFile().exists()) {
            try (FileInputStream appFile = new FileInputStream(appPropFile)) {
               appProps.load(appFile);
            } catch (IOException e) {
               logger.error("IOException ", e);
            }
         }
         else {
            logger.warn("Application Property file does not exist: {}", appPropPath);
         }

         logger.debug("Application Properties: {}", appProps);

         // Load properties variables
         duration = Integer.parseInt(getPropValue(PROP_PICTURE_DURATION));
         captionFont = getPropValue(PROP_CAPTION_FONT);
         captionFontSize = Integer.parseInt(getPropValue(PROP_CAPTION_FONT_SIZE));
         captionLocation = getPropValue(PROP_CAPTION_LOCATION);
         imagePath = getPropValue(PROP_IMAGE_PATH);
         sampleImagePath = getPropValue(PROP_SAMPLE_IMAGE_PATH);
         useSampleImages = Boolean.valueOf(getPropValue(PROP_USE_SAMPLE_IMAGES));


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

   public String getImagePath() {
      return imagePath;
   }

   public String getSampleImagePath() {
      return sampleImagePath;
   }

   public boolean getUseSampleImages() {
      return useSampleImages;
   }

   private String getPropValue(String key) {
      if (appProps.containsKey(key) || defaultProps.containsKey(key)) {
         return appProps.getProperty(key);
      } else {
         logger.error("Application Property no found: {}", key);
         throw new RuntimeException("Application Property no found: " + key);
      }
   }
}
