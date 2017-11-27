package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Buttons {

    PointActions field = new PointActions();
    Integer count;
    String functionSelector;

    public PointActions getField(){
        return field;
    }

    // Объявление элементов управления

    JButton add;
    JButton choose;
    JList<String> functionsList;
    private ArrayList<JButton> buttons = new ArrayList<>();

    // 用於插值的源函數字符串數組

    String functions[] = {
      "-e^(0.005x)", "-17*sqrt(x)", "-x/2"
    };

    Buttons() {

        // 创建和初始化按钮

        add = new JButton("编辑坐标");
        add.setActionCommand("add");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ае){
            field.createPoints(count, functionSelector);
            }
        });

        buttons.add(add);

        choose = new JButton("清除面板");
        choose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ае){
              //  label.setText("Очистка");
                field.clearField();
            }
        });

        buttons.add(choose);

        // Инициализация листа

        functionsList = new JList<>(functions);
        functionsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                functionSelector = functionsList.getSelectedValue();
                System.out.println(functionSelector);
            }
        });

    }

    public JList getList(){
        return functionsList;
    }

    public ArrayList<JButton> getListOfButtons(){
        return buttons;
    }

}
