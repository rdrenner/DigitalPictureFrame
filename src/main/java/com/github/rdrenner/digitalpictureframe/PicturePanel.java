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

/**
 * @author Ray Renner
 *
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.Random;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PicturePanel extends JPanel {

   static final long serialVersionUID = 1L;
   private static final Logger logger = LogManager.getLogger(PicturePanel.class);

   private BufferedImage image;
   private Dimension screenSize;
   private Picture pict;
   private PictureList pictList;
   private Random rand;

   public PicturePanel(Picture pict) {
      super();
      this.pict = pict;
      this.pictList = null;
      rand = new Random();
      logger.info("Creating PicturePanel.");
      screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      image = pict.getScaledImage(screenSize.width, screenSize.height);
   }

   public PicturePanel(PictureList list) {
      super();
      this.pictList = list;
      rand = new Random();

      logger.info("Creating PicturePanel.");
      screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      nextPicture();
   }

   public void nextPicture() {

      int numPict = pictList.getNumberPictures();
      if (numPict > 0) {
         pict = pictList.getPicture(rand.nextInt(pictList.getNumberPictures()));
         logger.info("Getting next picture: {}", pict.getFilename());
         image = pict.getScaledImage(screenSize.width, screenSize.height);
      } else {
         logger.error("Picture list is empty.");
      }

   }

   @Override
   protected void paintComponent(Graphics g) {
      int x = 0;
      int y = 0;

      if (screenSize.height != image.getHeight()) {
         // Let's center it
         y = (screenSize.height - image.getHeight()) / 2;
      }

      if (screenSize.width != image.getWidth()) {
         // Let's center it
         x = (screenSize.width - image.getWidth()) / 2;
      }

      logger.debug("Drawing Picture Image at ({},{}).", x, y);
      super.paintComponent(g);
      g.drawImage(image, x, y, this); // see javadoc for more info on the parameters
   }

}
