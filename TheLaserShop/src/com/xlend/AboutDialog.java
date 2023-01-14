package com.xlend;

//import com.xlend.util.ImagePanel;
//import com.xlend.util.PopupDialog;
//import com.xlend.util.TexturedPanel;
//import com.xlend.util.Util;
import com.xlend.guiutil.GeneralUtils;
import com.xlend.guiutil.ImagePanel;
import com.xlend.guiutil.PopupDialog;
import com.xlend.guiutil.TexturedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class AboutDialog extends PopupDialog {

    private static final String BACKGROUNDIMAGE = "about.png";
    private AbstractAction closeAction;
    private JButton closeBtn;

    public AboutDialog() {
        super(null, "Учет инсультов", null);
        //AIBclient.setWindowIcon(this, "aib.png");
    }

    @Override
    protected Color getHeaderBackground() {
        return null;//new Color(102, 125, 158);
    }

    protected void fillContent() {
        Color fg = new Color(53,100,230);
        super.fillContent();
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        getContentPane().add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(GeneralUtils.loadImage(BACKGROUNDIMAGE));
        this.setMinimumSize(new Dimension(img.getWidth(), img.getHeight() + 25));
        closeBtn = new JButton(closeAction = new AbstractAction("Ok") {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JLabel version = new JLabel("Версия " + TheLaserShop.version);
        version.setBounds(170, 60, version.getPreferredSize().width, version.getPreferredSize().height);
        version.setForeground(fg);
        main.add(version);

        JLabel devBy = new JLabel("Николай Мухин (mukhin.nick@gmail.com) (c) 2017");
        devBy.setFont(devBy.getFont().deriveFont(Font.ITALIC,10));
        devBy.setBounds(41, 105, devBy.getPreferredSize().width, devBy.getPreferredSize().height);
        devBy.setForeground(fg);
        main.add(devBy);
        
//        JLabel str1 = new JLabel("A I B");
//        str1.setFont(str1.getFont().deriveFont(Font.BOLD,12));
//        str1.setBounds(200, 135, str1.getPreferredSize().width, str1.getPreferredSize().height);
//        str1.setForeground(fg);
//        main.add(str1);
//
//        JLabel str2 = new JLabel("The Association for International Broadcasting");
//        str2.setFont(str2.getFont().deriveFont(Font.ITALIC|Font.BOLD,10));
//        str2.setBounds(43, 154, str2.getPreferredSize().width, str2.getPreferredSize().height);
//        str2.setForeground(fg);
//        main.add(str2);
//        
//        JLabel str3 = new JLabel("PO Box 141 | Cranbrook");
//        str3.setFont(str3.getFont().deriveFont(Font.ITALIC,10));
//        str3.setBounds(44, 173, str3.getPreferredSize().width, str3.getPreferredSize().height);
//        str3.setForeground(fg);
//        main.add(str3);
//        
//        JLabel str4 = new JLabel("TN17 9AJ | United Kingdom");
//        str4.setFont(str4.getFont().deriveFont(Font.ITALIC,10));
//        str4.setBounds(44, 185, str4.getPreferredSize().width, str4.getPreferredSize().height);
//        str4.setForeground(fg);
//        main.add(str4);

    closeBtn.setBounds(350, 278,
                closeBtn.getPreferredSize().width,
                closeBtn.getPreferredSize().height);

        main.add(closeBtn);
        setResizable(false);
    }

    @Override
    public void freeResources() {
        closeBtn.removeActionListener(closeAction);
        closeAction = null;
    }
}
