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

import java.io.File;
import java.io.IOException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.imageio.ImageIO;

/**
 * @author Ray Renner
 *
 */
public class Picture {
   static final long serialVersionUID = 1L;
   private static final Logger logger = LogManager.getLogger(Picture.class);

   private String filename;
   private String caption;

   Picture() {

   }

   Picture(String filename, String caption) {
      this.filename = filename;
      this.caption = caption;
   }

   Picture(String filename) {
      this.filename = filename;
      this.caption = null;
   }

   public String getFilename() {
      return filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }

   public String getCaption() {
      return caption;
   }

   public void setCaption(String caption) {
      this.caption = caption;
   }

   public Metadata getMetadata() {
      Metadata metadata = null;

      try {
         File file = new File(getFilename());
         metadata = ImageMetadataReader.readMetadata(file);

         logger.debug("Image Metadata: {}", metadata);

      } catch (ImageProcessingException e) {
         logger.error("Error reading image metadata.", e);
      } catch (IOException e) {
         logger.error("Error reading image metadata.", e);
      }

      return metadata;

   }

   public String getMetadataString() {
      StringBuilder metadataStr = new StringBuilder();
      Metadata metadata = getMetadata();

      for (Directory dir : metadata.getDirectories()) {

         metadataStr.append(dir).append("\n");

         for (Tag tag : dir.getTags()) {
            metadataStr.append(tag).append("\n");
         }

      }

      logger.debug("Metadata string: {}", metadataStr);

      return metadataStr.toString();

   }

   public BufferedImage getImage() {
      BufferedImage image = null;
      BufferedImage transformedImage = null;
      int orientation;

      try {
         image = ImageIO.read(new File(this.getFilename()));
         orientation = this.getOrientation();

         if (orientation != 1) {
            transformedImage = this.correctOrientation(image, orientation);
            image = transformedImage;
         }

      } catch (IOException e) {
         logger.error("Error reading image file: ", e);
      }

      return image;
   }

   /**
    *
    * @param newWidth  The required width
    * @param newHeight The required width
    *
    * @return The scaled image
    */
   public BufferedImage getScaledImage(int newWidth, int newHeight) {
      try {
         BufferedImage image = this.getImage();
         int imageWidth = image.getWidth(null);
         int imageHeight = image.getHeight(null);
         // Make sure the aspect ratio is maintained, so the image is not distorted
         logger.info("Scaling the image to {} x {} from {} x {}", newWidth, newHeight, imageWidth, imageHeight);
         double thumbRatio = (double) newWidth / (double) newHeight;
         double aspectRatio = (double) imageWidth / (double) imageHeight;

         if (thumbRatio < aspectRatio) {
            logger.debug("thumbRatio {} < aspectRatio {}", thumbRatio, aspectRatio);
            newHeight = (int) (newWidth / aspectRatio);
         } else {
            logger.debug("thumbRatio {} >= aspectRatio {}", thumbRatio, aspectRatio);
            newWidth = (int) (newHeight * aspectRatio);
         }

         logger.debug("New scaled image will be {} x {}",  newWidth, newHeight);

         // Draw the scaled image
         BufferedImage newImage =
               new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
         Graphics2D graphics2D = newImage.createGraphics();
         graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
               RenderingHints.VALUE_INTERPOLATION_BILINEAR);
         graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);

         return newImage;
      } catch (Exception e) {
         logger.error("Error scaling image.", e);
         throw e;
      }
   }

   public int getOrientation() {

      Metadata meta = getMetadata();
      int orientation = 1;

      try {

         if (meta != null) {
            if (meta.containsDirectoryOfType(ExifIFD0Directory.class)) {
               // Get the current orientation of the image
               Directory directory = meta.getFirstDirectoryOfType(ExifIFD0Directory.class);
               orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            }
         }
      } catch (MetadataException e) {
         logger.error("Metadata error reading orientation", e);
      }

      return orientation;
   }

   private BufferedImage correctOrientation(BufferedImage in, int orientation) {
      BufferedImage output = null;
      int width = in.getWidth();
      int height = in.getHeight();

      AffineTransform t = new AffineTransform();

      logger.debug("Correcting the orientation of the image. Orientation is {}", orientation);

      switch (orientation) {
         case 2: // Flip X
            t.scale(-1.0, 1.0);
            t.translate(-width, 0);
            output = transform(in, width, height, t);
            break;
         case 3: // PI rotation
            t.translate(width, height);
            t.rotate(Math.PI);
            output = transform(in, width, height, t);
            break;
         case 4: // Flip Y
            t.scale(1.0, -1.0);
            t.translate(0, -height);
            output = transform(in, width, height, t);
            break;
         case 5: // - PI/2 and Flip X
            t.rotate(-Math.PI / 2);
            t.scale(-1.0, 1.0);
            output = transform(in, height, width, t);
            break;
         case 6: // -PI/2 and -width
            t.translate(height, 0);
            t.rotate(Math.PI / 2);
            output = transform(in, height, width, t);
            break;
         case 7: // PI/2 and Flip
            t.scale(-1.0, 1.0);
            t.translate(height, 0);
            t.translate(0, width);
            t.rotate(3 * Math.PI / 2);
            output = transform(in, height, width, t);
            break;
         case 8: // PI / 2
            t.translate(0, width);
            t.rotate(3 * Math.PI / 2);
            output = transform(in, height, width, t);
            break;
         default:
            output = in;
            break;
      }

      return output;
   }

   private static BufferedImage resize(BufferedImage img, int height, int width) {
      Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = resized.createGraphics();
      g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
      g2d.drawImage(tmp, 0, 0, null);
      g2d.dispose();
      return resized;
   }

   private static BufferedImage transform(BufferedImage input, int outputWidth, int outputHeight, AffineTransform transform) {

      BufferedImage output;

      // Create an transformation operation
      AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
      logger.debug("Transforming the image, {} x {}.", outputWidth, outputHeight);

      // Create an instance of the resulting image, with the same width, height and image type than
      // the referenced one
      output = new BufferedImage(outputWidth, outputHeight, input.getType());
      op.filter(input, output);

      return output;
   }

}
