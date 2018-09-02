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
package com.github.rdrenner.digitalpictureframe;

/**
 * @author Ray Renner
 *
 */

import javax.swing.SwingUtilities;
import java.awt.GraphicsEnvironment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DigitalPictureFrame
{
   private static DigitalPictureFrame instance;

   private static final Logger logger = LogManager.getLogger(DigitalPictureFrame.class);

   public static DigitalPictureFrame Instance()
   {
      return instance;
   }

   private void createAndShowPictureFrame()
   {
      logger.info("Picture Frame Loading");
      PictureFrame pictFrame = new PictureFrame();
   }
   /**
    * @param args
    */
   public static void main(String[] args)
   {
      instance = new DigitalPictureFrame();

      if (GraphicsEnvironment.isHeadless())
      {
         logger.error("No monitor detected.");
         System.exit(0);
      }
      else
      {
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               instance.createAndShowPictureFrame(); 
            }
         });
      }
   }

}