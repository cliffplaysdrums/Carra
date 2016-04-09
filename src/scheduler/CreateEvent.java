/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ayomitunde
 */
public class CreateEvent extends javax.swing.JFrame {

    public String[] months = new String[]{"null", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    static final String[] users = {"Users"};
    static DefaultTableModel _userModel = new DefaultTableModel(users, 100);
    Event newEvent;
    int _rowCounter = 0;

    /**
     * Creates new form CreateEvent
     */
    public CreateEvent() {
        initComponents();
        showUsers();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtEventName = new javax.swing.JTextField();
        jdpDateSelector = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Date date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        jspTimeSelector = new javax.swing.JSpinner(sm);
        btnCreateEvent = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmbPriority = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUserTable = new javax.swing.JTable();
        rbnDept = new javax.swing.JRadioButton();

        jLabel1.setText("Event Title");

        jLabel2.setText("Event Date");

        jLabel3.setText("Event Time");

        JSpinner.DateEditor de = new JSpinner.DateEditor(jspTimeSelector, "HH:mm:ss");
        jspTimeSelector.setEditor(de);

        btnCreateEvent.setText("Create Event");
        btnCreateEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateEventActionPerformed(evt);
            }
        });

        jLabel4.setText("Priority");

        cmbPriority.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Medium", "High"}));

        jLabel5.setText("Participants");

        tblUserTable.setModel(_userModel);
        jScrollPane1.setViewportView(tblUserTable);

        rbnDept.setText("Add Department");
        rbnDept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnDeptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jdpDateSelector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEventName, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCreateEvent)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(cmbPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jspTimeSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addComponent(rbnDept)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtEventName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jdpDateSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jspTimeSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbnDept))
                .addGap(18, 18, 18)
                .addComponent(btnCreateEvent)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateEventActionPerformed
        // TODO add your handling code here:
        String eventName = txtEventName.getText();
        Date eventDate = jdpDateSelector.getDate();
        String time = jspTimeSelector.getValue().toString();
        User creator = GUI._currentUser;
        //time matching for easy access
        String timePattern = "\\d{2}:\\d{2}:\\d{2}";
        Pattern timeP = Pattern.compile(timePattern);
        Matcher m = timeP.matcher(time);
        if (m.find()) {
            time = m.group();
        }
        String date = eventDate.toString();
        String ndate = date.substring(date.indexOf(" ") + 1);
        String Mon = ndate.substring(0, ndate.indexOf(" "));

        String datePattern = "\\w{3} \\d{2}";
        Pattern dateP = Pattern.compile(datePattern);
        m = dateP.matcher(ndate);
        if (m.find()) {
            ndate = m.group();
        }

        for (int i = 0; i < months.length; i++) {
            if (months[i] == null ? Mon == null : months[i].equals(Mon)) {
                if (!ndate.contains(Mon)) {
                } else {
                    ndate = ndate.replace(Mon, String.valueOf(i));
                }
            }
        }
        ndate += date.substring(date.lastIndexOf(" "));
        ndate = ndate.replaceAll(" ", "/");
        // create new event here
        newEvent = new Event(eventName, ndate, time, creator);
        if (GUI._upcomingEventsModel.getRowCount() < 6) {
            GUI._upcomingEventsModel.setValueAt(newEvent.getEventName() + " at " + newEvent.getEventTime(), GUI._upcomingEventsModel.getRowCount(), 0);
        }
        getSelectedUsers();
        ArrayList<Event> userEvents = GUI._userInfo.get(creator);
        userEvents.add(newEvent);
        GUI._userInfo.put(creator, userEvents);
        GUI._allEvents.add(newEvent);
        Serialize.saveUserFiles(Serialize._fileLocation);
        Serialize.saveServerFile(Serialize._serverFile);
        String currentDate = GUI._df.format(GUI._currentDate);
        GUI.updateUpcoming(currentDate);
        GUI.refreshCalendar(GUI._realMonth, GUI._realYear);
        this.setVisible(false);
    }//GEN-LAST:event_btnCreateEventActionPerformed

    public boolean deptState() {
        return rbnDept.isSelected();
    }
    private void rbnDeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnDeptActionPerformed
        // TODO add your handling code here:
        showUsers();
    }//GEN-LAST:event_rbnDeptActionPerformed

    private void getSelectedUsers() {
        int[] selectedRows = tblUserTable.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            Object user = tblUserTable.getValueAt(selectedRows[i], 0);
            String objectSelected = String.valueOf(user);
            if (Arrays.asList(GUI._dept).contains(objectSelected)) {
                ArrayList<User> departments = GUI._allDepts.get(objectSelected);
                if (departments != null) {
                    for (int j = 0; j < departments.size(); j++) {
                        newEvent.addAttendee(departments.get(i).getUsername());
                    }
                }
            } else {
                newEvent.addAttendee(String.valueOf(user));
            }
        }
    }

    private void showDepts() {
        for (String s : GUI._dept) {
            _userModel.setValueAt(s, _rowCounter, 0);
            _rowCounter++;
        }
    }

    private void showUsers() {
        _rowCounter = 0;
        if (rbnDept.isSelected()) {
            showDepts();
        } else {
            //reset
            for (int i = 0; i < 50; i++) {
                _userModel.setValueAt(null, i, 0);
            }
        }
        for (Iterator<User> u = GUI._userInfo.keySet().iterator(); u.hasNext();) {
            User user = u.next();
            if (!GUI._currentUser.getUsername().equals(user.getUsername())) {
                _userModel.setValueAt(user.getUsername(), _rowCounter, 0);
                _rowCounter++;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void run() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CreateEvent().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateEvent;
    private static javax.swing.JComboBox<String> cmbPriority;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXDatePicker jdpDateSelector;
    private static javax.swing.JSpinner jspTimeSelector;
    private javax.swing.JRadioButton rbnDept;
    private static javax.swing.JTable tblUserTable;
    private javax.swing.JTextField txtEventName;
    // End of variables declaration//GEN-END:variables
}
