package com.xlend.guiutil;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public ImagePanel(Image img, LayoutManager lt) {
        this.img = img;
//        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
//        setPreferredSize(size);
//        setMinimumSize(size);
//        setMaximumSize(size);
//        setSize(size);
        setLayout(lt);
    }
    
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
