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

/**
 * @author Ray Renner
 *
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PicturePanel extends JPanel {

   static final long serialVersionUID = 1L;
   private static final Logger logger = LogManager.getLogger(PicturePanel.class);

   private BufferedImage image;
   private Dimension screenSize;

   private static BufferedImage resize(BufferedImage img, int height, int width) {
      Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = resized.createGraphics();
      g2d.drawImage(tmp, 0, 0, null);
      g2d.dispose();
      return resized;
   }

   /**
    *
    * @param image     The image to be scaled
    * @param imageType Target image type, e.g. TYPE_INT_RGB
    * @param newWidth  The required width
    * @param newHeight The required width
    *
    * @return The scaled image
    */
   public static BufferedImage scaleImage(BufferedImage image, int imageType, int newWidth, int newHeight) {
      try {
         // Make sure the aspect ratio is maintained, so the image is not distorted
         logger.info("Scaling the image to {} x {}", newWidth, newHeight);
         double thumbRatio = (double) newWidth / (double) newHeight;
         int imageWidth = image.getWidth(null);
         int imageHeight = image.getHeight(null);
         double aspectRatio = (double) imageWidth / (double) imageHeight;

         if (thumbRatio < aspectRatio) {
            newHeight = (int) (newWidth / aspectRatio);
         } else {
            newWidth = (int) (newHeight * aspectRatio);
         }

         // Draw the scaled image
         BufferedImage newImage = new BufferedImage(newWidth, newHeight, imageType);
         Graphics2D graphics2D = newImage.createGraphics();
         graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
         graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);

         return newImage;
      } catch (Exception e) {
         logger.error("Error resizing image.", e);
         throw e;
      }
   }

   public PicturePanel() {
      try {
         logger.info("Creating PicturePanel.");
         screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         image = scaleImage(ImageIO.read(new File("e:\\Dev\\SampleImages\\Owl1.jpg")), BufferedImage.TYPE_INT_RGB,
               screenSize.width, screenSize.height);
      } catch (IOException ex) {
         logger.error("Error reading image file: ", ex);

      }
   }

   @Override
   protected void paintComponent(Graphics g) {
      int x = 0;
      int y = 0;

      if (screenSize.height != image.getHeight())
      {
         //Let's center it
         x = (screenSize.height - image.getHeight())/2;
      }

      if (screenSize.width != image.getWidth())
      {
         //Let's center it
         x = (screenSize.width - image.getWidth())/2;
      }

      logger.info("Drawing Picture Image.");
      super.paintComponent(g);
      g.drawImage(image, x, y, this); // see javadoc for more info on the parameters
   }

}
