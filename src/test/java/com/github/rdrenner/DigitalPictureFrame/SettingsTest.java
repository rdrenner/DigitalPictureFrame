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
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)
public class SettingsTest {
   private Settings settings;

   @BeforeAll 
   public void setUp() throws Exception {
      settings = new Settings();
   }

   @Test
   public void TestDuration() {
      assertEquals(20, settings.getDuration(), "default duration is 20");
   }

   @Test
   public void TestCaptionFont() {
      assertEquals("Calibri", settings.getCaptionFont(), "default captionFOnt is Calibri");
   }

   @Test
   public void TestCaptionFontSize() {
      assertEquals(12, settings.getCaptionFontSize(), "default captionFontSize is 12");
   }

   @Test
   public void TestCaptionLocation() {
      assertEquals("Bottom", settings.getCaptionLocation(), "default captionLocation is Bottom");
   }

   @Test
   public void TestImagePath() {
      assertEquals("/Images", settings.getImagePath(), "default imagePath is /Images");
   }

   @Test
   public void TestSampleImagePath() {
      assertEquals("/SampleImages", settings.getSampleImagePath(), "default sampleImagePath is /SampleImages");
   }

   @Test
   public void TestUseSampleImages() {
      assertEquals(true, settings.getUseSampleImages(), "default useSampleImages is true");
   }

   @AfterAll
   public void tearDown() throws Exception {

   }
}