/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.awt.Color;
import java.awt.Component;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ayomitunde
 */
public class GUI extends javax.swing.JFrame {
    
    private final java.awt.Color BACKGROUND_COLOR = new java.awt.Color(204, 255, 204);
    private final java.awt.Color FOREGROUND_COLOR = new java.awt.Color(51, 51, 51);
    private final java.awt.Color BTN_BACKGROUND_COLOR = new java.awt.Color(102, 204, 255);
    private final java.awt.Font BTN_FONT = new java.awt.Font("Tahoma", 1, 11);
    private final java.awt.Color BTN_FOREGROUND_COLOR = new java.awt.Color(255, 255, 255);

    /**
     * Creates new form GUI
     */
    static final String[] _days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday"};
    static final String[] _dept = {"Staff", "IT", "Services", "Marketing", "Human Resources", "Financial", "Purchasing", "Sales", "Inventory", "Licenses", "Operational", "Admin"};
    static final String[] _upEvents = {"Upcoming Events"};
    static int _realYear, _realMonth, _realDay, _currentYear, _currentMonth;
    GregorianCalendar _calendar;
    static DefaultTableModel _CalendarTableModel = new DefaultTableModel(_days, 6);
    static DefaultTableModel _upcomingEventsModel = new DefaultTableModel(_upEvents, 6);
    final int _CALENDAR_HEIGHT = 100;
    static ArrayList<Event> _allEvents = new ArrayList<>();
    static HashMap<User, ArrayList> _userInfo = new HashMap<>();
    static String _eventday;
    static User _currentUser;
    DateFormat _df = new SimpleDateFormat("M/dd/yyyy");
    Date _currentDate = new Date();
    static boolean _logged = false;

    public GUI() throws IOException {
        this._calendar = new GregorianCalendar();
        _realDay = _calendar.get(GregorianCalendar.DAY_OF_MONTH);
        _realMonth = _calendar.get(GregorianCalendar.MONTH);
        _realYear = _calendar.get(GregorianCalendar.YEAR);
        _currentMonth = _realMonth;
        _currentYear = _realYear;
        initComponents();
        set();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem4 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        pnlBackground = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCalendar = new javax.swing.JTable(){
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column){
                return false;
            };
            public boolean getScrollableTracksViewportWidth(){
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        cmbYear = new javax.swing.JComboBox<>();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        lblMonth = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUpcomingEvents = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            };
        };
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        mnuExit = new javax.swing.JMenuItem();
        mnuMainEdit = new javax.swing.JMenu();
        mnuUser = new javax.swing.JMenu();
        mnuAddUser = new javax.swing.JMenuItem();
        mnuListEditUser = new javax.swing.JMenuItem();
        mnuRemoveUser = new javax.swing.JMenuItem();
        mnuEditPassword = new javax.swing.JMenuItem();
        mnuCustomizeCalendar = new javax.swing.JMenuItem();
        mnuServerLocation = new javax.swing.JMenuItem();

        jMenuItem4.setText("jMenuItem4");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlBackground.setBackground(new java.awt.Color(204, 255, 204));
        pnlBackground.setForeground(new java.awt.Color(51, 51, 51));

        jButton1.setBackground(new java.awt.Color(102, 204, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Create Event");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnLogout.setText("Log Out");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        tblCalendar.setBackground(new java.awt.Color(204, 204, 255));
        tblCalendar.setModel(_CalendarTableModel);
        tblCalendar.setRowHeight(_CALENDAR_HEIGHT);
        tblCalendar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCalendarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCalendar);

        cmbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
        cmbYear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbYearMouseClicked(evt);
            }
        });
        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNextMouseClicked(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrevMouseClicked(evt);
            }
        });
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        lblMonth.setText("jLabel1");

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(btnPrev)
                        .addGap(417, 417, 417)
                        .addComponent(lblMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                        .addGap(403, 403, 403)
                        .addComponent(btnNext))
                    .addComponent(jScrollPane1)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogout)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPrev)
                        .addComponent(lblMonth))
                    .addComponent(btnNext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        jPanel1.add(pnlBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 0, 1050, 600));

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        tblUpcomingEvents.setModel(_upcomingEventsModel);
        jScrollPane3.setViewportView(tblUpcomingEvents);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 472, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 600));

        jMenu1.setText("File");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Print Calendar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        mnuExit.setText("Exit");
        mnuExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuExitMouseClicked(evt);
            }
        });
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        jMenu1.add(mnuExit);

        jMenuBar1.add(jMenu1);

        mnuMainEdit.setText("Edit");

        mnuUser.setText("User");
        mnuUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUserActionPerformed(evt);
            }
        });

        mnuAddUser.setText("Add User");
        mnuAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddUserActionPerformed(evt);
            }
        });
        mnuUser.add(mnuAddUser);

        mnuListEditUser.setText("List/Edit Users");
        mnuListEditUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuListEditUserActionPerformed(evt);
            }
        });
        mnuUser.add(mnuListEditUser);

        mnuRemoveUser.setText("Remove User");
        mnuRemoveUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRemoveUserActionPerformed(evt);
            }
        });
        mnuUser.add(mnuRemoveUser);

        mnuEditPassword.setText("Edit Password");
        mnuEditPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditPasswordActionPerformed(evt);
            }
        });
        mnuUser.add(mnuEditPassword);

        mnuCustomizeCalendar.setText("Customize Calendar");
        mnuCustomizeCalendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCustomizeCalendarActionPerformed(evt);
            }
        });
        mnuUser.add(mnuCustomizeCalendar);

        mnuMainEdit.add(mnuUser);

        mnuServerLocation.setText("Server Location");
        mnuServerLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuServerLocationActionPerformed(evt);
            }
        });
        mnuMainEdit.add(mnuServerLocation);

        jMenuBar1.add(mnuMainEdit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuExitMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuExitMouseClicked

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
        // TODO add your handling code here:
        Serialize.save(Serialize.fileLocation); // change this to your desktop just for testing
        System.exit(0);
    }//GEN-LAST:event_mnuExitActionPerformed


    private void mnuAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddUserActionPerformed
        this.setVisible(false);
        AddUsers.run();

    }//GEN-LAST:event_mnuAddUserActionPerformed

    private void set() {
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblUpcomingEvents.getTableHeader().setResizingAllowed(false);
        tblUpcomingEvents.getTableHeader().setReorderingAllowed(false);
        tblUpcomingEvents.setColumnSelectionAllowed(true);
        tblUpcomingEvents.setRowSelectionAllowed(true);
        tblUpcomingEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (int i = _realYear - 100; i <= _realYear + 100; i++) {
            cmbYear.addItem(String.valueOf(i));
        }
        refreshCalendar(_realMonth, _realYear);
        if (_logged == true) {
            for (Iterator<User> u = _userInfo.keySet().iterator(); u.hasNext();) {
                _currentUser = u.next();
                if (_currentUser.getLogged()) {
                    if (_currentUser.isAdmin() == false) {
                        hideNonAdmin();
                        if (_currentUser.getCustomColor() != null) {
                            pnlBackground.setBackground(_currentUser.getCustomColor());
                        }
                    } else {

                    }
                }
            }
        }

        String currentDate = _df.format(_currentDate);
        System.err.println(currentDate);
        int j = 0;
        if (_currentUser != null) { // null check should be removed later
            ArrayList<Event> currentuserEvents = _userInfo.get(_currentUser);
            if (currentuserEvents != null) {
                for (int i = 0; i < currentuserEvents.size(); i++) {
                    Event e = currentuserEvents.get(i);
                    if (e.getEventDate().equals(currentDate)) {
                        if (j >= 5) {
                            break;
                        }
                        _upcomingEventsModel.setValueAt(e.getEventName() + " at " + e.getEventTime(), j, 0);
                        j++;
                    }
                }
            }
        }

        refreshCalendar(_currentMonth, _currentYear);
        if (_currentUser != null) { // null check should be removed later
            ArrayList<Event> currentuserEvents = _userInfo.get(_currentUser);
            if (currentuserEvents != null) {
                for (int i = 0; i < currentuserEvents.size(); i++) {
                    Event e = currentuserEvents.get(i);
                    System.out.println("event date is " + e.getEventDate());
                }
            }
        }
    }

    private void hideNonAdmin() {
        mnuAddUser.setVisible(false);
        mnuListEditUser.setVisible(false);
        mnuRemoveUser.setVisible(false);
        mnuServerLocation.setVisible(false);
    }
    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        this.dispose();
        try {
            new Logon().setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnLogoutActionPerformed

    private void mnuRemoveUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRemoveUserActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        RemoveUser.run();
    }//GEN-LAST:event_mnuRemoveUserActionPerformed

    private void mnuListEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuListEditUserActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        EditUser.run();
    }//GEN-LAST:event_mnuListEditUserActionPerformed

    private void mnuUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuUserActionPerformed

    private void mnuServerLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuServerLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuServerLocationActionPerformed

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        // TODO add your handling code here:
        if (cmbYear.getSelectedItem() != null) {
            String cYear = cmbYear.getSelectedItem().toString();
            _currentYear = Integer.parseInt(cYear);
            refreshCalendar(_currentMonth, _currentYear);
        }

    }//GEN-LAST:event_cmbYearActionPerformed

    public static void refreshCalendar(int month, int year) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int numDays, startMonth;

        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);

        // to avoid going over years we allow
        if (month == 11 && year >= _realYear + 100) {
            btnNext.setEnabled(false);
        }
        if (month == 0 && year <= _realYear - 100) {
            btnPrev.setEnabled(false);
        }

        lblMonth.setText(months[month] + " " + _currentYear);
        cmbYear.setSelectedItem(String.valueOf(year));
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                _CalendarTableModel.setValueAt(null, i, j);
            }
        }

        GregorianCalendar calendar = new GregorianCalendar(year, month, 1);
        numDays = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startMonth = calendar.get(GregorianCalendar.DAY_OF_WEEK);

        for (int i = 1; i <= numDays; i++) {
            int row = (i + startMonth - 2) / 7;
            int column = (i + startMonth - 2) % 7;
            int mon = month+1;
            String currentDate = mon+"/"+i+"/"+_currentYear;
            _CalendarTableModel.setValueAt(i, row, column);
            if (_currentUser != null) {
                ArrayList<Event> currentuserEvents = _userInfo.get(_currentUser);
                if (currentuserEvents != null) {
                    String valueatI = String.valueOf(i);
                    for (int p = 0; p < currentuserEvents.size(); p++) {
                        Event e = currentuserEvents.get(p);
                        if(e.getEventDate().equals(currentDate)){
                            //valueatI = "<html>"+valueatI;
                            valueatI+=" "+e.getEventName()+" at "+e.getEventTime();
                            _CalendarTableModel.setValueAt(valueatI, row, column);
                        }
                        //System.out.println("event date " + e.getEventDate());
                    }
                }
            }
            //System.out.println("date is "+month+"/"+i+"/"+_currentYear);
        }
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
    }

    static class tblCalendarRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6) { // weekend
                setBackground(new Color(255, 220, 220));
            } else {
                setBackground(new Color(255, 255, 255));
            }
            if (value != null) {
                String val = value.toString();
                val = val.contains(" ") ? val.substring(0, val.indexOf(" ")) : val;
                if (Integer.parseInt(val) == _realDay && _currentMonth == _realMonth && _currentYear == _realYear) {
                    //current Day
                    setBackground(new Color(220, 220, 255));
                }
            }

            if (selected) {
                setBackground(new Color(128, 128, 128));
                Object dateChosen = _CalendarTableModel.getValueAt(tblCalendar.getSelectedRow(),
                        tblCalendar.getSelectedColumn());
                _eventday = String.valueOf(dateChosen);
                //printing for debug purposes
                System.out.println("date is " + _currentMonth + "-" + _eventday + "-" + _currentYear);
            }

            Color color = Color.black;
            MatteBorder border = new MatteBorder(1, 1, 0, 0, color);
            setBorder(border);
            setForeground(Color.black);
            setSize(table.getColumnModel().getColumn(column).getWidth(),
                    Short.MAX_VALUE);
            return this;
        }
    }
    private void btnPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseClicked
        // TODO add your handling code here:
        if (_currentMonth == 0) {
            _currentMonth = 11;
            _currentYear -= 1;
        } else {
            _currentMonth -= 1;
        }
        refreshCalendar(_currentMonth, _currentYear);
    }//GEN-LAST:event_btnPrevMouseClicked

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseClicked
        // TODO add your handling code here:
        if (_currentMonth == 11) {
            _currentMonth = 0;
            _currentYear += 1; // go to next year
        } else {
            _currentMonth += 1; // just increment month
        }
        refreshCalendar(_currentMonth, _currentYear);
    }//GEN-LAST:event_btnNextMouseClicked

    private void cmbYearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbYearMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbYearMouseClicked

    private void tblCalendarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCalendarMouseClicked
        buildDateGUI();
        jScrollPane1.setViewportView(dateGUI);
    }                                        
                                  
//GEN-LAST:event_tblCalendarMouseClicked

    /* builds the GUI to be displayed when a particular date in the calendar is clicked
     * consists of an upper and lower Jpanel inside the dateGUI JPanel
     */
    private void buildDateGUI() {
        dateGUI = new javax.swing.JPanel();
        javax.swing.JPanel upperPanel = new javax.swing.JPanel();
        javax.swing.JPanel lowerPanel = new javax.swing.JPanel();
        java.awt.GridLayout layout = new java.awt.GridLayout(0, 2);
        lowerPanel.setLayout(layout);
        
        javax.swing.JButton btnBack = new javax.swing.JButton("Back");
        javax.swing.JButton btnCreateEvent = new javax.swing.JButton("Create Event");
        
        dateGUI.setBackground(BACKGROUND_COLOR);
        dateGUI.setForeground(FOREGROUND_COLOR);
        btnBack.setBackground(BTN_BACKGROUND_COLOR);
        btnBack.setForeground(BTN_FOREGROUND_COLOR);
        btnBack.setFont(BTN_FONT);
        btnCreateEvent.setBackground(BTN_BACKGROUND_COLOR);
        btnCreateEvent.setFont(BTN_FONT);
        btnCreateEvent.setForeground(BTN_FOREGROUND_COLOR);
        
        upperPanel.add(btnBack);
        upperPanel.add(btnCreateEvent);
        lowerPanel.add(new javax.swing.JLabel("Time"));
        lowerPanel.add(new javax.swing.JLabel("Events"));
        dateGUI.add(upperPanel);
        dateGUI.add(lowerPanel);
        
        btnBack.addActionListener((java.awt.event.ActionEvent e) -> {
            jScrollPane1.setViewportView(tblCalendar);
        });
       
        btnCreateEvent.addActionListener(this::jButton1ActionPerformed);
    }

    private void mnuEditPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditPasswordActionPerformed
        // TODO add your handling code here:
        String newPassword = JOptionPane.showInputDialog("Enter new password here");
        if (_logged == true && !"".equals(newPassword)) {
            _currentUser.setPassword(newPassword);
            _userInfo.put(_currentUser, _userInfo.get(_currentUser));
            Serialize.save(Serialize.fileLocation);
        }
    }//GEN-LAST:event_mnuEditPasswordActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        print();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mnuCustomizeCalendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCustomizeCalendarActionPerformed
        // TODO add your handling code here:
        Color c = JColorChooser.showDialog(null, "Select Color", pnlBackground.getBackground());
        if (c != null) {
            pnlBackground.setBackground(c);
            _currentUser.setCustomecolor(c);
            Serialize.save(Serialize.fileLocation);
        }
    }//GEN-LAST:event_mnuCustomizeCalendarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        CreateEvent.run();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void print() {
        try {
            tblCalendar.print();
        } catch (PrinterException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new GUI().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private static javax.swing.JButton btnNext;
    private static javax.swing.JButton btnPrev;
    private static javax.swing.JComboBox<String> cmbYear;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JLabel lblMonth;
    private javax.swing.JMenuItem mnuAddUser;
    private javax.swing.JMenuItem mnuCustomizeCalendar;
    private javax.swing.JMenuItem mnuEditPassword;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenuItem mnuListEditUser;
    private javax.swing.JMenu mnuMainEdit;
    private javax.swing.JMenuItem mnuRemoveUser;
    private javax.swing.JMenuItem mnuServerLocation;
    private javax.swing.JMenu mnuUser;
    private javax.swing.JPanel pnlBackground;
    private static javax.swing.JTable tblCalendar;
    private static javax.swing.JTable tblUpcomingEvents;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JPanel dateGUI;
}
