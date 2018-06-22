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

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PictureListTest {
   private PictureList list;

   @BeforeAll 
   static public void setUp() throws Exception {
   }

   @Test
   public void TestAddPicture() {
      list = new PictureList();
      list.addPicture(new Picture("pict1.jpg", "Picture 1"));
      list.addPicture(new Picture("pict2.jpg", "Picture 2"));
      list.addPicture(new Picture("pict3.jpg", "Picture 3"));
      assertEquals(3, list.getNumberPictures(), "Size should be 3.");
      list.addPicture(new Picture("pict4.jpg", "Picture 4"));
      assertEquals(4, list.getNumberPictures(), "Size should be 4.");
   }

   @Test
   public void TestGetPicture()
   {
      list = new PictureList();
      list.addPicture(new Picture("pict1.jpg", "Picture 1"));
      list.addPicture(new Picture("pict2.jpg", "Picture 2"));
      list.addPicture(new Picture("pict3.jpg", "Picture 3"));
      assertEquals("pict1.jpg", list.getPicture(0).getFilename(), "First picture should be pict1.jpg");
      assertEquals("pict3.jpg", list.getPicture(2).getFilename(), "Third picture should be pict3.jpg");
      list.addPicture(new Picture("pict4.jpg", "Picture 4"));
      assertEquals("pict4.jpg", list.getPicture(list.getNumberPictures()-1).getFilename(), "The last picture should be pict4.jpg");
   }

   @AfterAll
   static public void tearDown() throws Exception {

   }

}