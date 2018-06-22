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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PictureFrame extends JFrame {
   static final long serialVersionUID = 1L;

   private Timer timer;
   private Settings settings;
   private GraphicsDevice device;

   private JPanel picturePanel;
   private JPopupMenu popupMenu;
   private JMenuItem exit;

   private Dimension screenSize;

   public PictureFrame() {
      setTitle("Digital Picture Frame");
      settings = new Settings();
      device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
      timer = new Timer(settings.getDuration() * 1000, new TimerListener());

      picturePanel = new JPanel();

      popupMenu = new JPopupMenu("Options");
      exit = new JMenuItem("Exit");
      popupMenu.add(exit);
      exit.addActionListener(new OptionMenuListener());
      picturePanel.setComponentPopupMenu(popupMenu);

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setUndecorated(true);
      // device.setFullScreenWindow(this);
      screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      setSize(screenSize.width, screenSize.height);
      add(picturePanel);

      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            dispose();
            System.exit(0);
         }

         public void windowClosed(WindowEvent e) {
            dispose();
            System.exit(0);
         }
      });

      setVisible(true);
   }

   class OptionMenuListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         Object obj = event.getSource();

         if (obj == exit) {
            dispose();
            System.exit(0);
         }
      }
   }

   class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         Object obj = event.getSource();

         if (obj == timer) {
            // TODO - Load picture
         }
      }
   }

}
