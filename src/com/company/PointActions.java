package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;


public class PointActions extends JPanel {

    public JLabel coordinates = new JLabel("(0;0)");

    int x = 20, y = 20;
    ArrayList<Points> listOfPoints;
    ArrayList<Points> interpolatedPoints;
    ArrayList<Points> functionsPoints;

    PointActions() {
        coordinates.setForeground(Color.decode("#ffffff"));

        listOfPoints = new ArrayList<>();
        functionsPoints = new ArrayList<>();
        interpolatedPoints = new ArrayList<>();
        this.setPreferredSize(new Dimension(1300, 656));
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                coordinates.setText("(" + (x) + ";" + (-y) + ")");
                for (Points point : listOfPoints) {
                    if (((x > point.getX()) && x < (point.getX() + 10)) && ((-y < point.getY()) && (-y > (point.getY() - 10)))) {
                        listOfPoints.remove(point);
                        interpolate();
                        repaint();
                        return;
                    }
                }

                listOfPoints.add(new Points(x, (-y)));
                interpolate();
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    // метод создает на экране точки после нажатия на кнопку Добавить

    public void createPoints(Integer count, String choice) {
        // Добавление точек

        int d = 1300 / count;
        for (int i = 0; i < count; i++) {
            if (choice.equals("-x/2")) {
                listOfPoints.add(new Points((i * d), -i * d / 2));
            } else if (choice.equals("-e^(0.005x)")) {
                listOfPoints.add(new Points(i * d, - (int)Math.pow(Math.E, (double)i*d*0.005)));
            } else {
                listOfPoints.add(new Points(i * d,  -(int) (Math.sqrt(i * d) * 17)));
            }
        }

        // Высчитывание координат точек для построения графика исходной функции

        for (int i = 0; i < 1300; i++){
            if (choice.equals("-x/2")){
                functionsPoints.add(new Points((i), - i/2));
            } else if (choice.equals("-e^(0.005x)")){
                functionsPoints.add(new Points(i,  - (int)Math.pow(Math.E, (double)i*0.005)));
            } else {
                functionsPoints.add(new Points(i, -(int) (Math.sqrt(i) * 17)));
            }
        }
        interpolate();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        BasicStroke pen1 = new BasicStroke(3);
        g2.setStroke(pen1);

        Iterator<Points> it = functionsPoints.iterator();

        Points p1 = new Points(0,0);
        Points p2;

        // График исходной функции
        g2.setColor(Color.decode("#16a085"));

        if(it.hasNext()){
            p1 = it.next();
        }
        while (it.hasNext()){
            p2 = it.next();
            g2.drawLine(p1.getX(),-p1.getY(),p2.getX(),-p2.getY());
            if (it.hasNext()){
                p1 = p2;
                p2 = it.next();
            }
        }

        // Результат интерполяции
        it = interpolatedPoints.iterator();
        g2.setColor(Color.decode("#3498db"));

        if(it.hasNext()){
            p1 = it.next();
        }
        while (it.hasNext()){
            p2 = it.next();
            g2.drawLine(p1.getX(),-p1.getY(),p2.getX(),-p2.getY());
            if (it.hasNext()){
                p1 = p2;
                p2 = it.next();
            }
        }

        // Исходные точки

        for (Points point : listOfPoints) {
            g.setColor(Color.decode("#c0392b"));
            g.fillOval(point.getX()-5, - point.getY()-5, 10, 10);
        }
    }


    public void clearField() {
        listOfPoints.removeAll(listOfPoints);
        functionsPoints.removeAll(functionsPoints);
        interpolatedPoints.removeAll(interpolatedPoints);
        repaint();
    }

    public void interpolate() {
        interpolatedPoints.removeAll(interpolatedPoints);
        calculateLagrange();
        repaint();
    }

    public void calculateLagrange() {
        for (double i = 0; i < 1300; i++) {
            double lagrangePol = 0.0;
            double basicsPol;

            for (int j = 0; j < listOfPoints.size(); j++) {
                basicsPol = 1.0;
                for (int k = 0; k < listOfPoints.size(); k++) {
                    if (k == j) continue;
                    basicsPol *= ((i - listOfPoints.get(k).getX()) / (listOfPoints.get(j).getX() - listOfPoints.get(k).getX()));
                }
                lagrangePol += (basicsPol * (listOfPoints.get(j).getY()));
            }
            interpolatedPoints.add(new Points((int)i, (int)lagrangePol));
        }
    }
}