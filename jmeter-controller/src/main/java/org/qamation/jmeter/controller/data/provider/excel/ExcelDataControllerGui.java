package org.qamation.jmeter.controller.data.provider.excel;

import org.apache.jmeter.control.gui.AbstractControllerGui;
import org.apache.jmeter.testelement.TestElement;

import javax.swing.*;
import java.awt.*;

public class ExcelDataControllerGui {//extends AbstractControllerGui {
    private final String LABEL = "Excel Data Controller";

    JTextField excelFileNameField;
    JTextField excelDataProviderImplClassField;
    JTextField excelFileTabField;

    public ExcelDataControllerGui () {
        //itit();
    }



    //@Override
    public String getLabelResource() {
        return "Excel Data Provider Controller";
    }

   //@Override
    public TestElement createTestElement() {
        return new ExcelDataController();
    }

    //@Override
    public void modifyTestElement(TestElement testElement) {

    }
/*
    private void itit() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createExcelDataPanel(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
*/
    private JPanel createExcelDataPanel() {
        JPanel excelDataPanel = new JPanel(new BorderLayout(5, 0));

        // Condition LABEL
        JLabel excelDataLabel = new JLabel();
        excelDataPanel.add(excelDataLabel, BorderLayout.WEST);

        // TEXT FIELD
        // This means exit if last sample failed
        excelFileNameField = new JTextField("");  // $NON-NLS-1$
        excelFileNameField.setName("Excel File Path");
        excelDataLabel.setLabelFor(excelFileNameField);
        excelDataPanel.add(excelFileNameField, BorderLayout.CENTER);

        excelDataPanel.add(Box.createHorizontalStrut(excelDataLabel.getPreferredSize().width
                + excelFileNameField.getPreferredSize().width), BorderLayout.NORTH);

        return excelDataPanel;
    }
}
