import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ColorConverterApp extends JFrame
{
    JPanel topPanel, colorPanel, RGBsliderPanel, CMYKsliderPanel, HLSsliderPanel;
    JPanel redPanel, greenPanel, bluePanel;
    JPanel cPanel, mPanel, yPanel, kPanel;
    JPanel hPanel, sPanel, lPanel;
    JSlider redSlider, greenSlider, blueSlider;
    JSlider cSlider, mSlider, ySlider, kSlider;
    JSlider hSlider, sSlider, lSlider;
    JSpinner redSpinner, greenSpinner, blueSpinner;
    JSpinner cSpinner, mSpinner, ySpinner, kSpinner;
    JSpinner hSpinner, sSpinner, lSpinner;
    boolean inChange = false;

    public ColorConverterApp()
    {
        setLayout(new GridLayout(2, 2, 5, 5));
        topPanel        = new JPanel();
        RGBsliderPanel  = new JPanel();
        CMYKsliderPanel = new JPanel();
        HLSsliderPanel  = new JPanel();
        add(topPanel, 0);
        add(RGBsliderPanel, 1);
        add(CMYKsliderPanel, 2);
        add(HLSsliderPanel, 3);

        JMenuBar  menuBar          = new JMenuBar();
        JMenu     colorMenu        = new JMenu("Color");
        JMenuItem colorChooserItem = new JMenuItem("Choose Color");
        Component component        = this;
        colorChooserItem.addActionListener(e ->
                                           {
                                               Color selectedColor = JColorChooser.showDialog(component, "Choose Color", colorPanel.getBackground());
                                               if (selectedColor != null)
                                               {
                                                   redSlider.setValue(selectedColor.getRed());
                                                   greenSlider.setValue(selectedColor.getGreen());
                                                   blueSlider.setValue(selectedColor.getBlue());
                                               }
                                           });
        colorMenu.add(colorChooserItem);
        menuBar.add(colorMenu);
        this.setJMenuBar(menuBar);

        //COLOR AREA
        topPanel.setLayout(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        colorPanel = new JPanel();
        colorPanel.setBackground(new Color(0, 0, 0));
        topPanel.add(colorPanel, "Center");

        //RGB AREA
        {
            RGBsliderPanel.setLayout(new GridLayout(3, 1, 5, 5));
            RGBsliderPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            redPanel   = new JPanel();
            greenPanel = new JPanel();
            bluePanel  = new JPanel();
            RGBsliderPanel.add(redPanel);
            RGBsliderPanel.add(greenPanel);
            RGBsliderPanel.add(bluePanel);
            redPanel.setLayout(new BorderLayout());
            redPanel.setBackground(Color.white);
            redPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            redSlider = new JSlider(0, 255, 0);
            redSlider.setMajorTickSpacing(50);
            redSlider.setMinorTickSpacing(10);
            redSlider.setPaintTicks(true);
            redSlider.addChangeListener(new RGBListener());
            redSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
            redSpinner.addChangeListener(new RGB1Listener());
            redPanel.add(new JLabel("RED "), "West");
            redPanel.add(redSlider, "Center");
            redPanel.add(redSpinner, "East");
            greenPanel.setLayout(new BorderLayout());
            greenPanel.setBackground(Color.white);
            greenPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            greenSlider = new JSlider(0, 255, 0);
            greenSlider.setMajorTickSpacing(50);
            greenSlider.setMinorTickSpacing(10);
            greenSlider.setPaintTicks(true);
            greenSlider.addChangeListener(new RGBListener());
            greenSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
            greenSpinner.addChangeListener(new RGB1Listener());
            greenPanel.add(new JLabel("GREEN"), "West");
            greenPanel.add(greenSlider, "Center");
            greenPanel.add(greenSpinner, "East");
            bluePanel.setLayout(new BorderLayout());
            bluePanel.setBackground(Color.white);
            bluePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            blueSlider = new JSlider(0, 255, 0);
            blueSlider.setMajorTickSpacing(50);
            blueSlider.setMinorTickSpacing(10);
            blueSlider.setPaintTicks(true);
            blueSlider.addChangeListener(new RGBListener());
            blueSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
            blueSpinner.addChangeListener(new RGB1Listener());
            bluePanel.add(new JLabel("BLUE "), "West");
            bluePanel.add(blueSlider, "Center");
            bluePanel.add(blueSpinner, "East");
        }

        //CMYK AREA
        {
            CMYKsliderPanel.setLayout(new GridLayout(4, 1, 5, 5));
            CMYKsliderPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            cPanel = new JPanel();
            mPanel = new JPanel();
            yPanel = new JPanel();
            kPanel = new JPanel();
            CMYKsliderPanel.add(cPanel);
            CMYKsliderPanel.add(mPanel);
            CMYKsliderPanel.add(yPanel);
            CMYKsliderPanel.add(kPanel);
            cPanel.setLayout(new BorderLayout());
            cPanel.setBackground(Color.white);
            cPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            cSlider = new JSlider(0, 100, 0);
            cSlider.setMajorTickSpacing(50);
            cSlider.setMinorTickSpacing(10);
            cSlider.setPaintTicks(true);
            cSlider.addChangeListener(new CMYKListener());
            cSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            cSpinner.addChangeListener(new CMYK1Listener());
            cPanel.add(new JLabel("CYAN "), "West");
            cPanel.add(cSlider, "Center");
            cPanel.add(cSpinner, "East");
            mPanel.setLayout(new BorderLayout());
            mPanel.setBackground(Color.white);
            mPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            mSlider = new JSlider(0, 100, 0);
            mSlider.setMajorTickSpacing(50);
            mSlider.setMinorTickSpacing(10);
            mSlider.setPaintTicks(true);
            mSlider.addChangeListener(new CMYKListener());
            mSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            mSpinner.addChangeListener(new CMYK1Listener());
            mPanel.add(new JLabel("MAGENTA"), "West");
            mPanel.add(mSlider, "Center");
            mPanel.add(mSpinner, "East");
            yPanel.setLayout(new BorderLayout());
            yPanel.setBackground(Color.white);
            yPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            ySlider = new JSlider(0, 100, 0);
            ySlider.setMajorTickSpacing(50);
            ySlider.setMinorTickSpacing(10);
            ySlider.setPaintTicks(true);
            ySlider.addChangeListener(new CMYKListener());
            ySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            ySpinner.addChangeListener(new CMYK1Listener());
            yPanel.add(new JLabel("YELLOW "), "West");
            yPanel.add(ySlider, "Center");
            yPanel.add(ySpinner, "East");
            kPanel.setLayout(new BorderLayout());
            kPanel.setBackground(Color.white);
            kPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            kSlider = new JSlider(0, 100, 100);
            kSlider.setMajorTickSpacing(50);
            kSlider.setMinorTickSpacing(10);
            kSlider.setPaintTicks(true);
            kSlider.addChangeListener(new CMYKListener());
            kSpinner = new JSpinner(new SpinnerNumberModel(100, 0, 100, 1));
            kSpinner.addChangeListener(new CMYK1Listener());
            kPanel.add(new JLabel("KEY "), "West");
            kPanel.add(kSlider, "Center");
            kPanel.add(kSpinner, "East");
        }

        //HLS AREA
        {
            HLSsliderPanel.setLayout(new GridLayout(3, 1, 5, 5));
            HLSsliderPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            hPanel = new JPanel();
            lPanel = new JPanel();
            sPanel = new JPanel();
            HLSsliderPanel.add(hPanel);
            HLSsliderPanel.add(sPanel);
            HLSsliderPanel.add(lPanel);
            hPanel.setLayout(new BorderLayout());
            hPanel.setBackground(Color.white);
            hPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            hSlider = new JSlider(0, 360, 0);
            hSlider.setMajorTickSpacing(50);
            hSlider.setMinorTickSpacing(10);
            hSlider.setPaintTicks(true);
            hSlider.addChangeListener(new HLSListener());
            hSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            hSpinner.addChangeListener(new HLS1Listener());
            hPanel.add(new JLabel("HUE "), "West");
            hPanel.add(hSlider, "Center");
            hPanel.add(hSpinner, "East");
            sPanel.setLayout(new BorderLayout());
            sPanel.setBackground(Color.white);
            sPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            sSlider = new JSlider(0, 100, 0);
            sSlider.setMajorTickSpacing(50);
            sSlider.setMinorTickSpacing(10);
            sSlider.setPaintTicks(true);
            sSlider.addChangeListener(new HLSListener());
            sSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            sSpinner.addChangeListener(new HLS1Listener());
            sPanel.add(new JLabel("SATURATION "), "West");
            sPanel.add(sSlider, "Center");
            sPanel.add(sSpinner, "East");
            lPanel.setLayout(new BorderLayout());
            lPanel.setBackground(Color.white);
            lPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            lSlider = new JSlider(0, 100, 0);
            lSlider.setMajorTickSpacing(50);
            lSlider.setMinorTickSpacing(10);
            lSlider.setPaintTicks(true);
            lSlider.addChangeListener(new HLSListener());
            lSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            lSpinner.addChangeListener(new HLS1Listener());
            lPanel.add(new JLabel("LIGHTNESS"), "West");
            lPanel.add(lSlider, "Center");
            lPanel.add(lSpinner, "East");
        }
    }

    public static void main(String[] args)
    {
        ColorConverterApp window = new ColorConverterApp();
        window.setTitle("RGB Colour Code");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000, 700);
        window.setVisible(true);
    }

    public static int[] rgbToCmyk(int red, int green, int blue)
    {
        double r = red / 255.0;
        double g = green / 255.0;
        double b = blue / 255.0;

        double k = 1 - Math.max(Math.max(r, g), b);

        if (k == 1)
        {
            return new int[] {0, 0, 0, 100};
        }

        double c = 100 * (1 - r - k) / (1 - k);
        double m = 100 * (1 - g - k) / (1 - k);
        double y = 100 * (1 - b - k) / (1 - k);
        k *= 100;

        return new int[] {(int) c, (int) m, (int) y, (int) k};
    }

    public static int[] cmykToRgb(int cy, int ma, int ye, int ke)
    {
        double c = cy / 100.0;
        double m = ma / 100.0;
        double y = ye / 100.0;
        double k = ke / 100.0;
        double r = 255 * (1 - c) * (1 - k);
        double g = 255 * (1 - m) * (1 - k);
        double b = 255 * (1 - y) * (1 - k);

        int red   = (int) Math.round(r);
        int green = (int) Math.round(g);
        int blue  = (int) Math.round(b);

        return new int[] {red, green, blue};
    }

    public static int[] rgbToHls(int red, int green, int blue)
    {
        double r = red / 255.0;
        double g = green / 255.0;
        double b = blue / 255.0;

        double max = Math.max(Math.max(r, g), b);
        double min = Math.min(Math.min(r, g), b);

        double h, l, s;

        if (max == min)
        {
            h = 0;
        } else if (max == r)
        {
            h = (60 * ((g - b) / (max - min)) + 360) % 360;
        } else if (max == g)
        {
            h = (60 * ((b - r) / (max - min)) + 120) % 360;
        } else
        {
            h = (60 * ((r - g) / (max - min)) + 240) % 360;
        }

        l = (max + min) / 2;

        if (max == min)
        {
            s = 0;
        } else if (l <= 0.5)
        {
            s = (max - min) / (2 * l);
        } else
        {
            s = (max - min) / (2 - 2 * l);
        }

        return new int[] {(int) h, (int) (l * 100), (int) (s * 100)};
    }

    public static int[] hlsToRgb(double h, double l, double s)
    {
        double lightness  = l / 100.0;
        double saturation = s / 100.0;
        double q, p, r, g, b;

        if (lightness < 0.5)
        {
            q = lightness * (1 + saturation);
        } else
        {
            q = lightness + saturation - (lightness * saturation);
        }

        p = 2 * lightness - q;

        double hk = h / 360.0;

        r = hk + 1.0 / 3.0;
        g = hk;
        b = hk - 1.0 / 3.0;

        r = adjustColorComponent(r, p, q);
        g = adjustColorComponent(g, p, q);
        b = adjustColorComponent(b, p, q);

        int red   = (int) (r * 255);
        int green = (int) (g * 255);
        int blue  = (int) (b * 255);

        return new int[] {red, green, blue};
    }

    private static double adjustColorComponent(double colorComponent, double p, double q)
    {
        if (colorComponent < 0)
        {
            colorComponent += 1;
        } else if (colorComponent > 1)
        {
            colorComponent -= 1;
        }

        if (colorComponent < 1.0 / 6.0)
        {
            return p + ((q - p) * 6 * colorComponent);
        } else if (colorComponent < 0.5)
        {
            return q;
        } else if (colorComponent < 2.0 / 3.0)
        {
            return p + ((q - p) * 6 * (2.0 / 3.0 - colorComponent));
        } else
        {
            return p;
        }
    }

    private class RGBListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            if (inChange) return;
            inChange = true;
            int r, g, b;
            int h, l, s;
            int c, m, y, k;

            r = redSlider.getValue();
            redSpinner.setValue(Math.min(r, 255));
            g = greenSlider.getValue();
            greenSpinner.setValue(Math.min(g, 255));
            b = blueSlider.getValue();
            blueSpinner.setValue(Math.min(b, 255));

            int[] cmyk = rgbToCmyk(r, g, b);

            c = cmyk[0];
            m = cmyk[1];
            y = cmyk[2];
            k = cmyk[3];

            cSlider.setValue(c);
            cSpinner.setValue(Math.min(c, 100));
            mSlider.setValue(m);
            mSpinner.setValue(Math.min(m, 100));
            ySlider.setValue(y);
            ySpinner.setValue(Math.min(y, 100));
            kSlider.setValue(k);
            kSpinner.setValue(Math.min(k, 100));

            int[] hls = rgbToHls(r, g, b);

            h = hls[0];
            l = hls[1];
            s = hls[2];

            hSlider.setValue(h);
            hSpinner.setValue(Math.min(h, 360));
            lSlider.setValue(l);
            lSpinner.setValue(Math.min(l, 100));
            sSlider.setValue(s);
            sSpinner.setValue(Math.min(s, 100));

            colorPanel.setBackground(new Color(r, g, b));
            inChange = false;
        }
    }

    private class RGB1Listener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            if (inChange) return;
            int r = (int) redSpinner.getValue();
            redSlider.setValue(Math.min(r, 255));
            int g = (int) redSpinner.getValue();
            greenSlider.setValue(Math.min(g, 255));
            int b = (int) redSpinner.getValue();
            blueSlider.setValue(Math.min(b, 255));
        }
    }

    private class CMYK1Listener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            if (inChange) return;
            int c = (int) cSpinner.getValue();
            cSlider.setValue(Math.min(c, 100));
            int m = (int) mSpinner.getValue();
            mSlider.setValue(Math.min(m, 100));
            int y = (int) ySpinner.getValue();
            ySlider.setValue(Math.min(y, 100));
            int k = (int) kSpinner.getValue();
            kSlider.setValue(Math.min(k, 100));
        }
    }

    private class HLS1Listener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            if (inChange) return;
            int h = (int) hSpinner.getValue();
            hSlider.setValue(Math.min(h, 100));
            int l = (int) lSpinner.getValue();
            lSlider.setValue(Math.min(l, 100));
            int s = (int) sSpinner.getValue();
            sSlider.setValue(Math.min(s, 100));
        }
    }

    private class CMYKListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            if (inChange) return;
            inChange = true;
            int r, g, b;
            int h, l, s;
            int c, m, y, k;

            c = cSlider.getValue();
            cSpinner.setValue(Math.min(c, 100));
            m = mSlider.getValue();
            mSpinner.setValue(Math.min(m, 100));
            y = ySlider.getValue();
            ySpinner.setValue(Math.min(y, 100));
            k = kSlider.getValue();
            kSpinner.setValue(Math.min(k, 100));

            int[] rgb = cmykToRgb(c, m, y, k);

            r = rgb[0];
            g = rgb[1];
            b = rgb[2];

            redSlider.setValue(r);
            redSpinner.setValue(Math.min(r, 255));
            greenSlider.setValue(g);
            greenSpinner.setValue(Math.min(g, 255));
            blueSlider.setValue(b);
            blueSpinner.setValue(Math.min(b, 255));

            int[] hls = rgbToHls(r, g, b);

            h = hls[0];
            l = hls[1];
            s = hls[2];

            hSlider.setValue(h);
            hSpinner.setValue(Math.min(h, 360));
            lSlider.setValue(l);
            lSpinner.setValue(Math.min(l, 100));
            sSlider.setValue(s);
            sSpinner.setValue(Math.min(s, 100));

            colorPanel.setBackground(new Color(r, g, b));
            inChange = false;
        }
    }

    private class HLSListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            if (inChange) return;
            inChange = true;
            int r, g, b;
            int h, l, s;
            int c, m, y, k;

            h = hSlider.getValue();
            hSpinner.setValue(Math.min(h, 360));
            l = lSlider.getValue();
            lSpinner.setValue(Math.min(l, 100));
            s = sSlider.getValue();
            sSpinner.setValue(Math.min(s, 100));

            int[] rgb = hlsToRgb(h, l, s);

            r = rgb[0];
            g = rgb[1];
            b = rgb[2];

            redSlider.setValue(r);
            redSpinner.setValue(Math.min(r, 255));
            greenSlider.setValue(g);
            greenSpinner.setValue(Math.min(g, 255));
            blueSlider.setValue(b);
            blueSpinner.setValue(Math.min(b, 255));

            int[] cmyk = rgbToCmyk(r, g, b);

            c = cmyk[0];
            m = cmyk[1];
            y = cmyk[2];
            k = cmyk[3];

            cSlider.setValue(c);
            cSpinner.setValue(Math.min(c, 100));
            mSlider.setValue(m);
            mSpinner.setValue(Math.min(m, 100));
            ySlider.setValue(y);
            ySpinner.setValue(Math.min(y, 100));
            kSlider.setValue(k);
            kSpinner.setValue(Math.min(k, 100));

            colorPanel.setBackground(new Color(r, g, b));
            inChange = false;
        }
    }
}