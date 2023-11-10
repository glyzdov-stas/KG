import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageEdit
{
    // Режим рисования 
    int             rezhim    = 0;
    int             xPad;
    int             xf;
    int             yf;
    int             yPad;
    int             gridWidth = 30;
    boolean         pressed   = false;
    long startTime, endTime;
    // текущий цвет
    Color           maincolor;
    MyFrame         f;
    MyPanel         japan;
    BufferedImage   imag;
    ArrayList<Pair> beginLSbS = new ArrayList<Pair>();
    ArrayList<Pair> endLSbS   = new ArrayList<Pair>();
    ArrayList<Pair> beginLDDA = new ArrayList<Pair>();
    ArrayList<Pair> endLDDA   = new ArrayList<Pair>();
    ArrayList<Pair> beginLBr  = new ArrayList<Pair>();
    ArrayList<Pair> endLBr    = new ArrayList<Pair>();
    ArrayList<Pair> beginE    = new ArrayList<Pair>();
    ArrayList<Pair> endE      = new ArrayList<Pair>();
    int             width, height;

    public ImageEdit()
    {
        f = new MyFrame("Графический редактор");
        f.setSize(350, 350);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maincolor = Color.black;

        JMenuBar menuBar = new JMenuBar();
        f.setJMenuBar(menuBar);
        menuBar.setBounds(0, 0, 350, 30);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        Action saveAction = new AbstractAction("Сохранить")
        {
            public void actionPerformed(ActionEvent event)
            {
                try
                {
                    ImageIO.write(imag, "png", new File("rez.png"));
                } catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(f, "Ошибка ввода-вывода");
                }
            }
        };

        JMenuItem saveMenu = new JMenuItem(saveAction);
        fileMenu.add(saveMenu);

        JMenu gridMenu = new JMenu("Сетка");
        menuBar.add(gridMenu);

        Action gridAction = new AbstractAction("Ширина")
        {
            public void actionPerformed(ActionEvent event)
            {
                try
                {
                    gridWidth = Integer.parseInt(JOptionPane.showInputDialog("Введите значение:"));
                    Graphics2D g2 = (Graphics2D) imag.getGraphics();
                    g2.setColor(Color.white);
                    g2.fillRect(0, 0, japan.getWidth(), japan.getHeight());
                    drawGrid(g2);
                    japan.repaint();
                } catch (Exception ignored)
                {
                }
            }
        };

        JMenuItem widthMenu = new JMenuItem(gridAction);
        gridMenu.add(widthMenu);

        japan = new MyPanel();
        japan.setBounds(30, 30, 260, 260);
        japan.setBackground(Color.white);
        japan.setOpaque(true);
        f.add(japan);

        JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);

        JButton penbutton = new JButton("p");
        penbutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                rezhim = 0;
            }
        });
        toolbar.add(penbutton);

        JButton lasticbutton = new JButton("l");
        lasticbutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                rezhim = 1;
            }
        });
        toolbar.add(lasticbutton);

        JButton SbSbutton = new JButton("S");
        SbSbutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                rezhim = 2;
            }
        });
        toolbar.add(SbSbutton);

        JButton textbutton = new JButton("D");
        textbutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                rezhim = 3;
            }
        });
        toolbar.add(textbutton);

        JButton linebutton = new JButton("B");
        linebutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                rezhim = 4;
            }
        });
        toolbar.add(linebutton);

        JButton elipsbutton = new JButton("C");
        elipsbutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                rezhim = 5;
            }
        });
        toolbar.add(elipsbutton);

        toolbar.setBounds(0, 0, 30, 300);
        f.add(toolbar);

        japan.addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                if (pressed)
                {
                    Graphics   g  = imag.getGraphics();
                    Graphics2D g2 = (Graphics2D) g;
                    // установка цвета
                    g2.setColor(maincolor);
                    switch (rezhim)
                    {
                        // карандаш
                        case 0:
                            drawLineBr(g2, xPad, yPad, e.getX(), e.getY(), false);
                            break;
                        // ластик
                        case 1:
                            clearArrays();
                            g2.setStroke(new BasicStroke(1000000.0f));
                            drawLineBr(g2, xPad, yPad, e.getX(), e.getY(), false);
                            drawGrid(g2);
                            break;
                    }
                    xPad = e.getX();
                    yPad = e.getY();
                }
                japan.repaint();
            }
        });
        japan.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {

                Graphics   g  = imag.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                // установка цвета
                g2.setColor(maincolor);
                switch (rezhim)
                {
                    // карандаш
                    case 0:
                        drawLineBr(g2, xPad, yPad, xPad, yPad, false);
                        break;
                    // ластик
                    case 1:
                        clearArrays();
                        g2.setStroke(new BasicStroke(1000000.0f));
                        drawLineBr(g2, xPad, yPad, xPad, yPad, false);
                        drawGrid(g2);
                        break;
                }
                xPad = e.getX();
                yPad = e.getY();

                pressed = true;
                japan.repaint();
            }

            public void mousePressed(MouseEvent e)
            {
                xPad    = e.getX();
                yPad    = e.getY();
                xf      = e.getX();
                yf      = e.getY();
                pressed = true;
            }

            public void mouseReleased(MouseEvent e)
            {

                Graphics   g  = imag.getGraphics();
                Graphics2D g2 = (Graphics2D) g;
                // установка цвета
                g2.setColor(maincolor);
                switch (rezhim)
                {
                    // ластик
                    case 1:
                        clearArrays();
                        g2.setStroke(new BasicStroke(1000000.0f));
                        drawLineBr(g2, xPad, yPad, xPad, yPad, false);
                        drawGrid(g2);
                        break;
                    // линия Step by Step
                    case 2:
                        beginLSbS.add(new Pair((double) xf / width, (double) yf / height));
                        endLSbS.add(new Pair((double) e.getX() / width, (double) e.getY() / height));

                        drawLineSbS(g2, xf / gridWidth, yf / gridWidth,
                                    e.getX() / gridWidth, e.getY() / gridWidth, true);
                        startTime = System.nanoTime();
                        drawLineSbS(g2, xf, yf, e.getX(), e.getY(), false);
                        endTime = System.nanoTime();
                        System.out.println("Step by step time " + (endTime - startTime) + "ns");
                        break;
                    // линия DDA
                    case 3:
                        beginLDDA.add(new Pair((double) xf / width, (double) yf / height));
                        endLDDA.add(new Pair((double) e.getX() / width, (double) e.getY() / height));
                        drawLineDDA(g2, xf / gridWidth, yf / gridWidth,
                                    e.getX() / gridWidth, e.getY() / gridWidth, true);

                        startTime = System.nanoTime();
                        drawLineDDA(g2, xf, yf, e.getX(), e.getY(), false);
                        endTime = System.nanoTime();
                        System.out.println("DDA time " + (endTime - startTime) + "ns");
                        break;
                    // линия Brezenhem
                    case 4:
                        beginLBr.add(new Pair((double) xf / width, (double) yf / height));
                        endLBr.add(new Pair((double) e.getX() / width, (double) e.getY() / height));
                        drawLineBr(g2, xf / gridWidth, yf / gridWidth,
                                   e.getX() / gridWidth, e.getY() / gridWidth, true);

                        startTime = System.nanoTime();
                        drawLineBr(g2, xf, yf, e.getX(), e.getY(), false);
                        endTime = System.nanoTime();
                        System.out.println("Brezenhem time " + (endTime - startTime) + "ns");
                        break;
                    // круг
                    case 5:
                        beginE.add(new Pair((double) xf / width, (double) yf / height));
                        endE.add(new Pair((double) e.getX() / width, (double) e.getY() / height));
                        drawCircleBr(g2, xf / gridWidth, yf / gridWidth,
                                     e.getX() / gridWidth, e.getY() / gridWidth, true);

                        startTime = System.nanoTime();
                        drawCircleBr(g2, xf, yf, e.getX(), e.getY(), false);
                        endTime = System.nanoTime();
                        System.out.println("Brezenhem circle time " + (endTime - startTime) + "ns");
                        break;
                }
                xf      = 0;
                yf      = 0;
                pressed = false;
                japan.repaint();
            }
        });

        f.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                japan.setSize(f.getWidth() - 40, f.getHeight() - 80);
                BufferedImage tempImage = new BufferedImage(japan.getWidth(), japan.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D    d2        = (Graphics2D) tempImage.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, japan.getWidth(), japan.getHeight());
                width  = japan.getWidth();
                height = japan.getHeight();
                d2.setColor(Color.black);
                d2.setStroke(new BasicStroke(1.0f));
                redrawAll(d2);
                drawGrid(d2);
                imag = tempImage;
                japan.repaint();
            }
        });
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void main(String[] args)
    {

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new ImageEdit();
            }
        });
    }

    public void drawLineSbS(Graphics2D g2, int x1, int y1, int x2, int y2, boolean bigD)
    {
        int dx = x2 - x1;
        int dy = y2 - y1;

        if (Math.abs(dx) >= Math.abs(dy))
        {
            double k = (double) dy / dx;
            double b = y1 - k * x1;

            int x  = Math.min(x1, x2);
            int mx = Math.max(x1, x2);
            int y;

            while (x <= mx)
            {
                y = (int) Math.round(k * x + b);
                pixel(g2, x, y, bigD);
                x++;
            }
        } else
        {
            double k = (double) dx / dy;
            double b = x1 - k * y1;

            int y  = Math.min(y1, y2);
            int my = Math.max(y1, y2);
            int x;

            while (y <= my)
            {
                x = (int) Math.round(k * y + b);
                pixel(g2, x, y, bigD);
                y++;
            }
        }
    }

    public void drawLineDDA(Graphics2D g2, int x1, int y1, int x2, int y2, boolean bigD)
    {
        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++)
        {
            pixel(g2, Math.round(x), Math.round(y), bigD);

            x += xIncrement;
            y += yIncrement;
        }
    }

    public void drawLineBr(Graphics2D g2, int x1, int y1, int x2, int y2, boolean bigD)
    {
        int dx  = Math.abs(x2 - x1);
        int dy  = Math.abs(y2 - y1);
        int sx  = x1 < x2 ? 1 : -1;
        int sy  = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (x1 != x2 || y1 != y2)
        {
            pixel(g2, x1, y1, bigD);

            int err2 = 2 * err;

            if (err2 > -dy)
            {
                err -= dy;
                x1 += sx;
            }

            if (err2 < dx)
            {
                err += dx;
                y1 += sy;
            }
        }

        pixel(g2, x2, y2, bigD);
    }

    private void drawCircleBr(Graphics2D g2d, int centerX, int centerY, int pointX, int pointY, boolean bigD)
    {
        int radius = (int) Math.sqrt(Math.pow(pointX - centerX, 2) + Math.pow(pointY - centerY, 2));
        int x      = 0;
        int y      = radius;
        int d      = 1 - radius;

        drawSymmetricPoints(g2d, centerX, centerY, x, y, bigD);

        while (y > x)
        {
            if (d < 0)
            {
                d += 2 * x + 3;
            } else
            {
                d += 2 * (x - y) + 5;
                y--;
            }
            x++;

            drawSymmetricPoints(g2d, centerX, centerY, x, y, bigD);
        }
    }

    private void drawSymmetricPoints(Graphics2D g2, int centerX, int centerY, int x, int y, boolean bigD)
    {
        pixel(g2, centerX + x, centerY + y, bigD);
        pixel(g2, centerX - x, centerY + y, bigD);
        pixel(g2, centerX + x, centerY - y, bigD);
        pixel(g2, centerX - x, centerY - y, bigD);
        pixel(g2, centerX + y, centerY + x, bigD);
        pixel(g2, centerX - y, centerY + x, bigD);
        pixel(g2, centerX + y, centerY - x, bigD);
        pixel(g2, centerX - y, centerY - x, bigD);
    }

    public void pixel(Graphics2D g2, int x, int y, boolean bigD)
    {
        if (!bigD)
        {
            if (rezhim != 1) g2.setColor(Color.red);
            else g2.setColor(Color.white);
            g2.drawLine(x, y, x, y);
            g2.setColor(Color.black);
        } else
        {
            g2.setColor(Color.black);
            g2.fillRect(x * gridWidth, y * gridWidth, gridWidth, gridWidth);
        }
    }

    public void redrawAll(Graphics2D g2)
    {
        for (int i = 0; i < beginLSbS.size(); ++i)
        {
            drawLineSbS(g2, (int) (beginLSbS.get(i).first * width / gridWidth), (int) (beginLSbS.get(i).second * height / gridWidth),
                        (int) (endLSbS.get(i).first * width / gridWidth), (int) (endLSbS.get(i).second * height / gridWidth), true);
            drawLineSbS(g2, (int) (beginLSbS.get(i).first * width), (int) (beginLSbS.get(i).second * height),
                        (int) (endLSbS.get(i).first * width), (int) (endLSbS.get(i).second * height), false);
        }

        for (int i = 0; i < beginLDDA.size(); ++i)
        {
            drawLineDDA(g2, (int) (beginLDDA.get(i).first * width / gridWidth), (int) (beginLDDA.get(i).second * height / gridWidth),
                        (int) (endLDDA.get(i).first * width / gridWidth), (int) (endLDDA.get(i).second * height / gridWidth), true);
            drawLineDDA(g2, (int) (beginLDDA.get(i).first * width), (int) (beginLDDA.get(i).second * height),
                        (int) (endLDDA.get(i).first * width), (int) (endLDDA.get(i).second * height), false);
        }

        for (int i = 0; i < beginLBr.size(); ++i)
        {
            drawLineBr(g2, (int) (beginLBr.get(i).first * width / gridWidth), (int) (beginLBr.get(i).second * height / gridWidth),
                       (int) (endLBr.get(i).first * width / gridWidth), (int) (endLBr.get(i).second * height / gridWidth), true);
            drawLineBr(g2, (int) (beginLBr.get(i).first * width), (int) (beginLBr.get(i).second * height),
                       (int) (endLBr.get(i).first * width), (int) (endLBr.get(i).second * height), false);
        }

        for (int i = 0; i < beginE.size(); ++i)
        {
            drawCircleBr(g2, (int) (beginE.get(i).first * width / gridWidth), (int) (beginE.get(i).second * height / gridWidth),
                         (int) (endE.get(i).first * width / gridWidth), (int) (endE.get(i).second * height / gridWidth), true);
            drawCircleBr(g2, (int) (beginE.get(i).first * width), (int) (beginE.get(i).second * height),
                         (int) (endE.get(i).first * width), (int) (endE.get(i).second * height), false);
        }
    }

    public void drawGrid(Graphics2D g2)
    {
        if (gridWidth <= 0) return;
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(1.0f));
        for (int x = 0; x <= width; x += gridWidth)
        {
            g2.drawLine(x, 0, x, height);
        }

        for (int y = 0; y <= height; y += gridWidth)
        {
            g2.drawLine(0, y, width, y);
        }
    }

    public void clearArrays()
    {
        beginLSbS.clear();
        endLSbS.clear();
        beginLDDA.clear();
        endLDDA.clear();
        beginLBr.clear();
        endLBr.clear();
        beginE.clear();
        endE.clear();
    }

    class MyFrame extends JFrame
    {
        public MyFrame(String title)
        {
            super(title);
        }

        public void paint(Graphics g)
        {
            super.paint(g);
        }
    }

    class MyPanel extends JPanel
    {
        public MyPanel()
        {}

        public void paintComponent(Graphics g)
        {
            if (imag == null)
            {
                imag = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = (Graphics2D) imag.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, this.getWidth(), this.getHeight());
                drawGrid(d2);
            }
            super.paintComponent(g);
            g.drawImage(imag, 0, 0, this);
        }
    }

    class Pair
    {
        double first;
        double second;


        public Pair(double first, double second)
        {
            this.first  = first;
            this.second = second;
        }
    }
}
