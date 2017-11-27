package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow {

    // Инициализация графических компонентов
    public JFrame mainWindow;
    public ArrayList<JButton> listOfButtons;
    public JLabel chooseFunction;
    public JLabel pointsCountMsg;
    public JLabel pointsCount;
    public JPanel menu;
    public PointActions field;
    public JSlider slider;
    public JList<String> functionList;

    MainWindow() {
        buildGUI();
    }

    // метод, добавляющий все кнопки из коллекции в mainWindow
    public void addButton(JButton button){
        menu.add(button);
    }

    // установка параметров и расположение элементов в mainWindow
    private void buildGUI(){
        mainWindow = new JFrame("Lagrange Interpolation");
        mainWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        mainWindow.setSize(1300, 735);
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание меню
        menu = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        menu.setPreferredSize(new Dimension(1300, 60));
        menu.setBackground(Color.decode("#212121"));

        Buttons buttons = new Buttons();

        field = buttons.getField();
        field.setBackground(Color.decode("#383838"));

        // Инициализация label

        Font font = new Font("Century Gothic", Font.PLAIN, 15);

        chooseFunction = new JLabel("Выборка точек");
        chooseFunction.setForeground(Color.WHITE);
        chooseFunction.setFont(font);

        pointsCount = new JLabel("..");
        pointsCount.setForeground(Color.WHITE);
        pointsCount.setFont(font);

        pointsCountMsg = new JLabel("Количество точек:");
        pointsCountMsg.setForeground(Color.WHITE);
        pointsCountMsg.setFont(font);

        listOfButtons = buttons.getListOfButtons();

        // Listener для слайдера

        slider = new JSlider();
        slider.setBackground(Color.decode("#212121"));
        slider.setMaximum(15);
        slider.setMinimum(2);
        slider.setValue(2);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Integer value = slider.getValue();
                buttons.count = value;
                pointsCount.setText(value.toString());
            }
        });

        // Создание листа функций
        functionList = buttons.getList();

        // Добавление компонентов в панели

        menu.add(chooseFunction);
        menu.add(functionList);
        menu.add(pointsCountMsg);
        menu.add(slider);
        menu.add(pointsCount);

        field.add(field.coordinates);

        mainWindow.add(field);
        mainWindow.add(menu);

        listOfButtons.stream().map(button -> {
            button.setFont(font);
            button.setBackground(Color.decode("#ffffff"));
            return button;
        })
        .forEach(button -> addButton(button));

        mainWindow.setVisible(true);
    }
}
