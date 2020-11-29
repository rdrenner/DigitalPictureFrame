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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PictureFrame extends JFrame {
   static final long serialVersionUID = 1L;
   private static final Logger logger = LogManager.getLogger(PictureFrame.class);

   private Timer timer;
   private Settings settings;
//   private GraphicsDevice device;

//   private JPanel picturePanel;
   private PicturePanel picturePanel;
   private PictureList pictureList;
   private JPopupMenu popupMenu;
   private JMenuItem exit;

   private Dimension screenSize;

   public PictureFrame() {
      setTitle("Digital Picture Frame");
      settings = new Settings();
//      device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
      timer = new Timer(settings.getDuration() * 1000, new TimerListener());

      pictureList = new PictureList (settings);
      pictureList.loadCatalog();
      picturePanel = new PicturePanel(pictureList);

      popupMenu = new JPopupMenu("Options");
      exit = new JMenuItem("Exit");
      popupMenu.add(exit);
      exit.addActionListener(new OptionMenuListener());
      picturePanel.setComponentPopupMenu(popupMenu);

      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setUndecorated(true);
      // device.setFullScreenWindow(this);
      screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      logger.info("Screen Size is: {}", screenSize);
      setSize(screenSize);
      picturePanel.setPreferredSize(screenSize);
      add(picturePanel);

      picturePanel.setBackground(Color.BLACK);

      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            dispose();
            System.exit(0);
         }

         @Override
         public void windowClosed(WindowEvent e) {
            dispose();
            System.exit(0);
         }
      });

      setVisible(true);
      timer.start();
   }

   class OptionMenuListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         Object obj = event.getSource();

         if (obj == exit) {
            logger.info("Exit Menu Selected. Exiting.");
            dispose();
            System.exit(0);
         }
      }
   }

   class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         Object obj = event.getSource();

         if (obj == timer) {
            picturePanel.nextPicture();
         }
         repaint();
      }
   }

}
