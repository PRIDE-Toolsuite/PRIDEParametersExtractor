
package com.compomics.pride_asa_pipeline.gui.view;

import java.awt.Color;
import javax.swing.*;

/**
 * The modification configuration dialog.
 * 
 * @author Harald Barsnes
 */
public class ModificationConfigDialog extends javax.swing.JDialog {

    private JFileChooser fileChooser;
    
    /**
     * Creates a new ModificationConfigDialog.
     * 
     * @param parent 
     * @param modal 
     */
    public ModificationConfigDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        fileChooser = new JFileChooser();        
        modificationsTableScrollPane.getViewport().setOpaque(false);
        modifcationsTable.getTableHeader().setReorderingAllowed(false);
        modifcationsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        setLocationRelativeTo(parent);
    }

    public JTable getModifcationsTable() {
        return modifcationsTable;
    }

    public JTextField getModAccessionTextField() {
        return modAccessionTextField;
    }

    public JComboBox getModLocationComboBox() {
        return modLocationComboBox;
    }

    public JTextField getModAverageMassShiftTextField() {
        return modAverageMassShiftTextField;
    }

    public JTextField getModMonoIsotopicMassShiftTextField() {
        return modMonoIsotopicMassShiftTextField;
    }

    public JTextField getModNameTextField() {
        return modNameTextField;
    }

    public JList getAffectedAminoAcidsList() {
        return affectedAminoAcidsList;
    }

    public JButton getAddAminoAcidButton() {
        return addAminoAcidButton;
    }

    public JList getAminoAcidsList() {
        return aminoAcidsList;
    }

    public JButton getRemoveAminoAcidButton() {
        return removeAminoAcidButton;
    }

    public JButton getAddModificationButton() {
        return addModificationButton;
    }

    public JButton getRemoveModificationButton() {
        return removeModificationButton;
    }

    public JLabel getBindingLoggingLabel() {
        return bindingLoggingLabel;
    }

    public JTextField getModAccessionValueTextField() {
        return modAccessionValueTextField;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modificationsTablePanel = new javax.swing.JPanel();
        modificationsTableScrollPane = new javax.swing.JScrollPane();
        modifcationsTable = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();
        importButton = new javax.swing.JButton();
        addModificationButton = new javax.swing.JButton();
        removeModificationButton = new javax.swing.JButton();
        editModificationPanel = new javax.swing.JPanel();
        modNameLabel = new javax.swing.JLabel();
        modNameTextField = new javax.swing.JTextField();
        modAccessionLabel = new javax.swing.JLabel();
        modAccessionTextField = new javax.swing.JTextField();
        modMonoIsotopicMassShiftLabel = new javax.swing.JLabel();
        modMonoIsotopicMassShiftTextField = new javax.swing.JTextField();
        modLocationLabel = new javax.swing.JLabel();
        modLocationComboBox = new javax.swing.JComboBox();
        modAverageMassShiftLabel = new javax.swing.JLabel();
        modAverageMassShiftTextField = new javax.swing.JTextField();
        affectedAminoAcidsLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        aminoAcidsList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        affectedAminoAcidsList = new javax.swing.JList();
        addAminoAcidButton = new javax.swing.JButton();
        removeAminoAcidButton = new javax.swing.JButton();
        modAccessionValueLabel = new javax.swing.JLabel();
        modAccessionValueTextField = new javax.swing.JTextField();
        bindingLoggingLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modification Configuration");
        setMinimumSize(new java.awt.Dimension(500, 700));

        modificationsTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Modifications"));
        modificationsTablePanel.setOpaque(false);

        modificationsTableScrollPane.setOpaque(false);
        modificationsTableScrollPane.setPreferredSize(new java.awt.Dimension(25, 25));

        modifcationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        modifcationsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        modifcationsTable.setOpaque(false);
        modificationsTableScrollPane.setViewportView(modifcationsTable);

        saveButton.setText("Save");
        saveButton.setToolTipText("Click to save the modifications to the modifications file.");
        saveButton.setMaximumSize(new java.awt.Dimension(80, 25));
        saveButton.setMinimumSize(new java.awt.Dimension(80, 25));
        saveButton.setPreferredSize(new java.awt.Dimension(80, 25));

        importButton.setText("Import");
        importButton.setToolTipText("Click to import a modification file.");
        importButton.setMaximumSize(new java.awt.Dimension(80, 25));
        importButton.setMinimumSize(new java.awt.Dimension(80, 25));
        importButton.setPreferredSize(new java.awt.Dimension(80, 25));

        addModificationButton.setText("Add");
        addModificationButton.setToolTipText("Click to add a modification.");
        addModificationButton.setMaximumSize(new java.awt.Dimension(80, 25));
        addModificationButton.setMinimumSize(new java.awt.Dimension(80, 25));
        addModificationButton.setPreferredSize(new java.awt.Dimension(80, 25));

        removeModificationButton.setText("Remove");
        removeModificationButton.setToolTipText("Click to delete the selected modification");
        removeModificationButton.setMaximumSize(new java.awt.Dimension(80, 25));
        removeModificationButton.setMinimumSize(new java.awt.Dimension(80, 25));
        removeModificationButton.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout modificationsTablePanelLayout = new javax.swing.GroupLayout(modificationsTablePanel);
        modificationsTablePanel.setLayout(modificationsTablePanelLayout);
        modificationsTablePanelLayout.setHorizontalGroup(
            modificationsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modificationsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modificationsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(modificationsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(modificationsTablePanelLayout.createSequentialGroup()
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(importButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addModificationButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeModificationButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        modificationsTablePanelLayout.setVerticalGroup(
            modificationsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modificationsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modificationsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(modificationsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addModificationButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeModificationButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(importButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        editModificationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Modification Details"));
        editModificationPanel.setMinimumSize(new java.awt.Dimension(100, 100));
        editModificationPanel.setOpaque(false);
        editModificationPanel.setPreferredSize(new java.awt.Dimension(300, 300));

        modNameLabel.setText("Name");

        modNameTextField.setMinimumSize(new java.awt.Dimension(80, 20));
        modNameTextField.setPreferredSize(new java.awt.Dimension(300, 30));

        modAccessionLabel.setText("Accession");

        modAccessionTextField.setMinimumSize(new java.awt.Dimension(80, 20));
        modAccessionTextField.setPreferredSize(new java.awt.Dimension(300, 30));

        modMonoIsotopicMassShiftLabel.setText("Monoisotopic Mass Shift");

        modMonoIsotopicMassShiftTextField.setMinimumSize(new java.awt.Dimension(80, 20));
        modMonoIsotopicMassShiftTextField.setPreferredSize(new java.awt.Dimension(300, 30));

        modLocationLabel.setText("Location");

        modLocationComboBox.setMinimumSize(new java.awt.Dimension(80, 20));
        modLocationComboBox.setPreferredSize(new java.awt.Dimension(300, 30));

        modAverageMassShiftLabel.setText("Average Mass Shift");

        modAverageMassShiftTextField.setMinimumSize(new java.awt.Dimension(80, 20));
        modAverageMassShiftTextField.setPreferredSize(new java.awt.Dimension(300, 30));

        affectedAminoAcidsLabel.setText("Affected Amino Acids");

        jScrollPane3.setMaximumSize(new java.awt.Dimension(0, 0));
        jScrollPane3.setMinimumSize(new java.awt.Dimension(20, 20));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(20, 20));

        jScrollPane3.setViewportView(aminoAcidsList);

        jScrollPane2.setMaximumSize(new java.awt.Dimension(0, 0));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(20, 20));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(20, 20));

        jScrollPane2.setViewportView(affectedAminoAcidsList);

        addAminoAcidButton.setText(">>");
        addAminoAcidButton.setMaximumSize(new java.awt.Dimension(80, 25));
        addAminoAcidButton.setMinimumSize(new java.awt.Dimension(80, 25));
        addAminoAcidButton.setPreferredSize(new java.awt.Dimension(80, 25));

        removeAminoAcidButton.setText("<<");
        removeAminoAcidButton.setMaximumSize(new java.awt.Dimension(80, 25));
        removeAminoAcidButton.setMinimumSize(new java.awt.Dimension(80, 25));
        removeAminoAcidButton.setPreferredSize(new java.awt.Dimension(80, 25));

        modAccessionValueLabel.setText("Accession Value");

        modAccessionValueTextField.setMinimumSize(new java.awt.Dimension(80, 20));
        modAccessionValueTextField.setPreferredSize(new java.awt.Dimension(300, 30));

        bindingLoggingLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        bindingLoggingLabel.setForeground(new java.awt.Color(255, 0, 0));
        bindingLoggingLabel.setMinimumSize(new java.awt.Dimension(80, 25));
        bindingLoggingLabel.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout editModificationPanelLayout = new javax.swing.GroupLayout(editModificationPanel);
        editModificationPanel.setLayout(editModificationPanelLayout);
        editModificationPanelLayout.setHorizontalGroup(
            editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editModificationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modAccessionLabel)
                    .addComponent(modAccessionValueLabel)
                    .addComponent(modAverageMassShiftLabel)
                    .addComponent(modLocationLabel)
                    .addComponent(affectedAminoAcidsLabel)
                    .addComponent(modNameLabel)
                    .addComponent(modMonoIsotopicMassShiftLabel))
                .addGap(26, 26, 26)
                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editModificationPanelLayout.createSequentialGroup()
                        .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editModificationPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addAminoAcidButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(removeAminoAcidButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editModificationPanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(modAccessionValueTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                                    .addComponent(modMonoIsotopicMassShiftTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                                    .addComponent(modAverageMassShiftTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                                    .addComponent(modLocationComboBox, 0, 581, Short.MAX_VALUE)))
                            .addGroup(editModificationPanelLayout.createSequentialGroup()
                                .addComponent(modAccessionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addContainerGap())
                    .addGroup(editModificationPanelLayout.createSequentialGroup()
                        .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(modNameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bindingLoggingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        editModificationPanelLayout.setVerticalGroup(
            editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editModificationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bindingLoggingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modAccessionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modAccessionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modAccessionValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modAccessionValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modMonoIsotopicMassShiftTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modMonoIsotopicMassShiftLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modAverageMassShiftTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modAverageMassShiftLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modLocationLabel)
                    .addComponent(modLocationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editModificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editModificationPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(addAminoAcidButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeAminoAcidButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(editModificationPanelLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(affectedAminoAcidsLabel))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modificationsTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editModificationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modificationsTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editModificationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAminoAcidButton;
    private javax.swing.JButton addModificationButton;
    private javax.swing.JLabel affectedAminoAcidsLabel;
    private javax.swing.JList affectedAminoAcidsList;
    private javax.swing.JList aminoAcidsList;
    private javax.swing.JLabel bindingLoggingLabel;
    private javax.swing.JPanel editModificationPanel;
    private javax.swing.JButton importButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel modAccessionLabel;
    private javax.swing.JTextField modAccessionTextField;
    private javax.swing.JLabel modAccessionValueLabel;
    private javax.swing.JTextField modAccessionValueTextField;
    private javax.swing.JLabel modAverageMassShiftLabel;
    private javax.swing.JTextField modAverageMassShiftTextField;
    private javax.swing.JComboBox modLocationComboBox;
    private javax.swing.JLabel modLocationLabel;
    private javax.swing.JLabel modMonoIsotopicMassShiftLabel;
    private javax.swing.JTextField modMonoIsotopicMassShiftTextField;
    private javax.swing.JLabel modNameLabel;
    private javax.swing.JTextField modNameTextField;
    private javax.swing.JTable modifcationsTable;
    private javax.swing.JPanel modificationsTablePanel;
    private javax.swing.JScrollPane modificationsTableScrollPane;
    private javax.swing.JButton removeAminoAcidButton;
    private javax.swing.JButton removeModificationButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}