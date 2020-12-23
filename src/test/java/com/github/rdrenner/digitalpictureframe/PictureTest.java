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

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(Lifecycle.PER_CLASS)
public class PictureTest {
   private Picture pict1;
   private Picture pict2;
   private Picture pict3;
   private Picture pict4;
   private Picture pict5;
   private Picture pict6;

   @BeforeAll 
   public void setUp() throws Exception {
      pict1 = new Picture("picture1.jpg", "Test Picture");
      pict2 = new Picture();
      pict3 = new Picture("./SampleImages/Owl1.jpg");
      pict4 = new Picture("./Images/DSC_0005.jpg");
      pict5 = new Picture("./SampleImages/Amaryllis1.jpg");
      pict6 = new Picture("./SampleImages/Amaryllis2.jpg");
   }

   @Test
   public void TestSetandGetFilename() {
      assertEquals("picture1.jpg", pict1.getFilename(), "pict1.filename is picture1.jpg");
      assertNull(pict2.getFilename(), "pict2.filename is null");
      pict1.setFilename("picture1a.jpg");
      assertEquals("picture1a.jpg", pict1.getFilename(), "pict1.filename is now picture1a.jpg");
   }

   @Test
   public void TestSetAndGetCaption() {
      assertEquals("Test Picture", pict1.getCaption(), "pict1.caption is Test Picture");
      assertNull(pict2.getCaption(), "pict2.caption is null");
      pict1.setCaption("Test Picture 1a");
      assertEquals("Test Picture 1a", pict1.getCaption(), "pict1.caption is Test Picture 1a");
   }

   @Test
   public void TestGetMetadata() {
      assertNotNull(pict3.getMetadata(), "Pict3 metadata should not be null.");
      assertNotNull(pict4.getMetadata(), "Pict4 metadata should not be null.");
      assertNotNull(pict5.getMetadataString(), "Pict5 metadata should not be null.");
      assertNotNull(pict6.getMetadataString(), "Pict6 metadata should not be null.");
   }

   @Test
   public void TestGetOrientation() {

      assertEquals(1, pict3.getOrientation(), "Orientation should be 1.");
      assertEquals(1, pict4.getOrientation(), "Orientation should be 1.");
      assertEquals(3, pict5.getOrientation(), "Orientation should be 3.");
      assertEquals(6, pict6.getOrientation(), "Orientation should be 6.");

   }

   @Test
   public void TestGetImage() {
      assertNotNull(pict3.getImage(), "Pict3 image should not be null.");
   }

   @Test
   public void TestGetScaledImage() {
      assertNotNull(pict3.getScaledImage(1920, 780), "Pict3 scaled image should not be null.");
      assertNotNull(pict4.getScaledImage(1920, 780), "Pict4 scaled image should not be null.");
      assertNotNull(pict5.getScaledImage(1920, 780), "Pict5 scaled image should not be null.");
      assertNotNull(pict6.getScaledImage(1920, 780), "Pict6 scaled image should not be null.");
   }

   @AfterAll
   public void tearDown() throws Exception {

   }
}