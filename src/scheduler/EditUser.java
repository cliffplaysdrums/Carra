package scheduler;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ayomitunde
 */
public class EditUser extends javax.swing.JFrame {

    /**
     * Creates new form EditUser
     */
    String[] _header = new String[]{"Username", "Department", "IsAdmin"};
    Object[][] _data;
    static DefaultTableModel _dtm = new DefaultTableModel(0, 0){
        @Override
        public boolean isCellEditable(int row, int column){
            return column == 2;
        }
        
        @Override
        public Class<?> getColumnClass(int colIndex){
            if(colIndex == 2){
                return Boolean.class;
            }
            return super.getColumnClass(colIndex);
        }
    };
    static User _edit;

    public EditUser() {
        initComponents();
        _dtm.setColumnIdentifiers(_header);
        tblEditUser.setModel(_dtm);
        SelectionListener listener = new SelectionListener(tblEditUser, this);
        tblEditUser.getSelectionModel().addListSelectionListener(listener);
        tblEditUser.getColumnModel().getSelectionModel().addListSelectionListener(listener);
        listUsers();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblRmvUser = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEditUser = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblRmvUser.setFont(new java.awt.Font("Trajan Pro", 1, 24)); // NOI18N
        lblRmvUser.setText("Users");

        jLabel1.setText("Search");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblEditUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "IsAdmin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblEditUser);

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblRmvUser, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(108, 108, 108)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSearch))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblRmvUser)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnBack)
                    .addComponent(btnDelete))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Iterate through list of users and display username and if they are an admin or not
    private void listUsers() {
        try {
            _dtm.setRowCount(0);
            dbModel.showList();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EditUser.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        _dtm.setRowCount(0);
        String username = txtSearch.getText();
        for (Iterator<User> u = GUI._userInfo.keySet().iterator(); u.hasNext();) {
            User user = u.next();
            if (user.getUsername().equals(username)
                    || user.getUsername().startsWith(String.valueOf(username.charAt(0)))) {
                _dtm.addRow(new Object[]{user.getUsername(), user.isAdmin()});
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    public void getUser() {
        int row = tblEditUser.getSelectedRow();
        int column = tblEditUser.getSelectedColumn(); // 0 -> username, 1 -> departmentName, 2 -> isAdmin
        if (row >= 0 && column >= 0) {
            Object value = _dtm.getValueAt(row, column);
            switch (column) {
                case 0:
                    String username = value.toString(); // should not be editable
                    break;
                case 1:
                    String departmentName = value.toString();
                    break;
                default:
                    String isAdmin = value.toString();
                    
                    break;
            }
            for (Iterator<User> u = GUI._userInfo.keySet().iterator(); u.hasNext();) {
                _edit = u.next();
                if (value.equals(_edit.getUsername())) {
                    // print for debuggin
                    System.out.println("user is " + _edit.getUsername());
                    break;
                }
            }
        }

    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int row = tblEditUser.getSelectedRow();
        Object newvalue = _dtm.getValueAt(row, 1);
        String username = _dtm.getValueAt(row, 0).toString();
        String isAdmin = String.valueOf(newvalue).toLowerCase();
        if ("true".equals(isAdmin) || "false".equals(isAdmin)) {
            if ("true".equals(isAdmin)) {
                _edit.makeAdmin(true);
                try {
                    dbModel.makeAdmin(username, true);
                } catch (SQLException ex) {
                    Logger.getLogger(EditUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    dbModel.makeAdmin(username, false);
                } catch (SQLException ex) {
                    Logger.getLogger(EditUser.class.getName()).log(Level.SEVERE, null, ex);
                }
                _edit.makeAdmin(false);
            }
        } else {
            _edit.setUsername(isAdmin);
        }
        Serialize.saveUserFiles(Serialize._fileLocation);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int row = tblEditUser.getSelectedRow();
        String username = _dtm.getValueAt(row, 0).toString();
        for(Iterator<User> u = GUI._userInfo.keySet().iterator(); u.hasNext();){
            User user = u.next();
            if(user.getUsername().equals(username)){
                int reply = JOptionPane.showConfirmDialog(null, "Are you Sure?", "Delete "+user.getUsername(), JOptionPane.YES_NO_CANCEL_OPTION);
                if(reply == JOptionPane.YES_OPTION){
                    try {
                        dbModel.removeUser(username); // not working
                        u.remove();
                        JOptionPane.showMessageDialog(null, user+" Deleted");
                        Serialize.saveUserFiles(Serialize._fileLocation);
                    } catch (SQLException ex) {
                        Logger.getLogger(EditUser.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }else{
                    JOptionPane.showMessageDialog(null, "Canceled");
                }
            }
        }
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    public static void run() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new EditUser().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRmvUser;
    private static javax.swing.JTable tblEditUser;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
