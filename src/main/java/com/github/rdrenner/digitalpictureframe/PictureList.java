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

import java.util.ArrayList;
import java.nio.file.SimpleFileVisitor;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Ray Renner
 *
 */
public class PictureList implements Serializable {
   private ArrayList<Picture> pictures;
   private Settings settings;

   static final long serialVersionUID = 1L;
   private static final Logger logger = LogManager.getLogger(PictureList.class);

   PictureList(Settings settings) {
      this.pictures = new ArrayList<>(100);
      this.settings = settings;
   }

   public void addPicture(Picture pict) {
      pictures.add(pict);
   }

   public int getNumberPictures() {
      return pictures.size();
   }

   public Picture getPicture(int index) {
      return pictures.get(index);
   }

   public void loadCatalog() {
      try {
         if (settings.getUseSampleImages()) {
            String sampleImagePath = settings.getSampleImagePath();

            if (sampleImagePath == null) {
               logger.error("SampleImages resource not found: {}", sampleImagePath);
            } else {
               Files.walkFileTree(Paths.get(sampleImagePath), new CatalogTraverse());
            }


         }

         String imagePath = settings.getImagePath();
         if (imagePath == null) {
            logger.error("Images resource not found: {}", imagePath);
         } else {
            Files.walkFileTree(Paths.get(imagePath), new CatalogTraverse());
         }

         logger.info("{} images loaded into list for display.", pictures.size());

      } catch (Exception ex) {
         logger.error("Error loading images: ", ex);
      }
   }

   private class CatalogTraverse extends SimpleFileVisitor<Path> {

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
         addPicture(new Picture(file.toString()));
         return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {
         return FileVisitResult.CONTINUE;
      }

   }
}
