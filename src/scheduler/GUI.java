/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
import javax.swing.Timer;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ayomitunde
 * @author Cliff
 */
public class GUI extends javax.swing.JFrame {

    /* Variables for the dateGUI
     * These only affect the appearance of the "hidden" table that is 
     *   displayed when you click on a specific date
     */
    private java.awt.Color BACKGROUND_COLOR = new java.awt.Color(204, 204, 255);
    private java.awt.Color FOREGROUND_COLOR = new java.awt.Color(51, 51, 51);
    private java.awt.Color BTN_BACKGROUND_COLOR = new java.awt.Color(102, 204, 255);
    private java.awt.Font BTN_FONT = new java.awt.Font("Tahoma", 1, 11);
    private java.awt.Color BTN_FOREGROUND_COLOR = new java.awt.Color(255, 255, 255);

    /**
     * Creates new form GUI
     */
    // initializatin should happen in the constructor... this should be moved.
    static final String[] _days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday"};
    static final String[] _dept = {"Staff", "IT", "Services", "Marketing", "Human Resources", "Financial", "Purchasing", "Sales", "Inventory", "Licenses", "Operational", "Admin"};
    static final String[] _upEvents = {"Upcoming Events"};
    static final String[] _invitesModel = {"Event Invitations"};
    static int _realYear, _realMonth, _realDay, _currentYear, _currentMonth;
    static int modelrowCount = 0;
    GregorianCalendar _calendar;
    static DefaultTableModel _CalendarTableModel = new DefaultTableModel(_days, 6);
    static DefaultTableModel _upcomingEventsModel = new DefaultTableModel(0,0);
    static DefaultTableModel _invitationEventModel = new DefaultTableModel(_invitesModel, 100);
    final int _CALENDAR_HEIGHT = 100;
    static ArrayList<Event> _allEvents = new ArrayList<>();
    static HashMap<User, ArrayList> _userInfo = new HashMap<>();
    static HashMap<String, ArrayList> _allDepts = new HashMap<>();
    static String _eventday;
    static User _currentUser;
    static DateFormat _df = new SimpleDateFormat("M/dd/yyyy");
    static Date _currentDate = new Date();
    private final ActionListener updateCalendar;
    private final ActionListener showEventsUp;
    private final ActionListener checkForNotification;
    static boolean dateClicked = false;
    boolean showOneEvent = false;
    static ArrayList<Event> _upcomingEvents = new ArrayList<>();
    static ArrayList<Event> _notifiedAlready = new ArrayList<>();
    public static HashMap<String, Double> _dueEvent = new HashMap<>();

    static int counter = 0;

    public GUI() throws IOException {
        initComponents();
        this._calendar = new GregorianCalendar();
        _realDay = _calendar.get(GregorianCalendar.DAY_OF_MONTH);
        _realMonth = _calendar.get(GregorianCalendar.MONTH);
        _realYear = _calendar.get(GregorianCalendar.YEAR);
        _currentMonth = _realMonth;
        _currentYear = _realYear;
        _currentUser = null;
        _upcomingEventsModel.setColumnIdentifiers(_upEvents);
        tblUpcomingEvents.setModel(_upcomingEventsModel);
        set();
        updateCalendar = (ActionEvent e) -> {
            if (_currentUser != null) { // this should be removed too
                updateCalendar();
            }
        };
        checkForNotification = (ActionEvent e) -> {
            checkNotify();
        };
        updateCalendar(); /* This ensures that there is no delay between when
                        the calendar is loaded and when it is populated.
                        However, if no users exist, this code will cause using
                        the home button to login to crash the application */
        checkNotify(); //call once when gui starts
        Timer checkUpdt = new Timer(5000, updateCalendar);
        Timer checkNotifyTimer = new Timer(15000, checkForNotification);
        checkNotifyTimer.start();
        checkUpdt.start();
        showEventsUp = (ActionEvent e) -> {
            if (_currentUser != null) {
                if (showEvent()) {
                    EventNotification.run();
                }
            }
        };
        Timer checkEvUp = new Timer(15000, showEventsUp);
        checkEvUp.start();
    }
    
    public static void snooze(String eventName, String eventDescr, String eventTime) {
        ActionListener notifyEvent = (ActionEvent e) -> {
            new Notification(eventName, eventDescr, eventTime);
        };
        Timer snoozeTimer = new Timer(1*60*1000, notifyEvent);
        snoozeTimer.start();
        snoozeTimer.setRepeats(false);
    }
    
    private void checkNotify() {
        java.util.Calendar now = java.util.Calendar.getInstance();
        int currentHour = now.get(java.util.Calendar.HOUR);
        if (now.get(java.util.Calendar.AM_PM) == java.util.Calendar.PM &&
                currentHour != 12) {
            currentHour += 12;
        }
        int currentMinutes = now.get(java.util.Calendar.MINUTE);
        int currentTotalMinutes = currentHour * 60 + currentMinutes;
        Iterator<Event> it = _userInfo.get(_currentUser).iterator();
        String currentDate = (_realMonth +1) + "/" + _realDay + "/" + _realYear;

        while (it.hasNext()) {
            Event e = it.next();
            Iterator<Event> itQ = _notifiedAlready.iterator();
            if (e.getEventDate().equals(currentDate)) {
                boolean notified = false;
                while(itQ.hasNext()) {
                    Event evt = itQ.next();
                    if (e.getEventName().equals(evt.getEventName())) {
                        notified = true;
                    }
                }
                if (notified == false) { 
                    String[] eventTimeS = e.getEventTime().split(":");
                    int eventHours = Integer.parseInt(eventTimeS[0]);
                    int eventMinutes = Integer.parseInt(eventTimeS[1]);
                    int totalEventMinutes = eventHours * 60 + eventMinutes;
                    int minutesUntil = totalEventMinutes - currentTotalMinutes;
                    if (minutesUntil < 15 && minutesUntil > 0) {
                        _notifiedAlready.add(e);
                        new Notification(e.getEventName(), e.getEventDescription(), 
                        e.getEventTime());
                    }
                }
            }
        }
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
        btnUCreateEvent = new javax.swing.JButton();
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
        lblUserName = new javax.swing.JLabel();
        btnListEvents = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUpcomingEvents = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            };
        };
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        mnuExit = new javax.swing.JMenuItem();
        mnuMainEdit = new javax.swing.JMenu();
        mnuUser = new javax.swing.JMenu();
        mnuAddUser = new javax.swing.JMenuItem();
        mnuListEditUser = new javax.swing.JMenuItem();
        mnuEditPassword = new javax.swing.JMenuItem();
        mnuCustomizeCalendar = new javax.swing.JMenuItem();

        jMenuItem4.setText("jMenuItem4");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pnlBackground.setBackground(new java.awt.Color(204, 255, 204));
        pnlBackground.setForeground(new java.awt.Color(51, 51, 51));

        btnUCreateEvent.setBackground(new java.awt.Color(102, 204, 255));
        btnUCreateEvent.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnUCreateEvent.setForeground(new java.awt.Color(255, 255, 255));
        btnUCreateEvent.setText("Create Event");
        btnUCreateEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUCreateEventActionPerformed(evt);
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

        lblUserName.setText("jLabel1");

        btnListEvents.setBackground(new java.awt.Color(102, 204, 255));
        btnListEvents.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnListEvents.setForeground(new java.awt.Color(255, 255, 255));
        btnListEvents.setText("List Events");
        btnListEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListEventsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                                .addComponent(btnPrev)
                                .addGap(361, 361, 361)
                                .addComponent(lblMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(353, 353, 353))
                            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                                .addComponent(btnUCreateEvent)
                                .addGap(27, 27, 27)
                                .addComponent(btnListEvents)
                                .addGap(464, 464, 464)
                                .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNext)
                            .addComponent(btnLogout)))
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 942, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUCreateEvent)
                        .addComponent(btnListEvents))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLogout)
                        .addComponent(lblUserName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrev)
                    .addComponent(lblMonth)
                    .addComponent(btnNext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jScrollPane3.setViewportView(tblUpcomingEvents);

        jTable1.setModel(_invitationEventModel);
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(pnlBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

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

        mnuAddUser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
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

        jMenuBar1.add(mnuMainEdit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        Serialize.saveUserFiles(Serialize._fileLocation); // change this to your desktop just for testing
        System.exit(0);
    }//GEN-LAST:event_mnuExitActionPerformed


    private void mnuAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddUserActionPerformed
    AddUsers.run();
    }//GEN-LAST:event_mnuAddUserActionPerformed

    private void set() {
        // set table properties here

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

        // fill in the combobox for year. Year range is from currentYear - 100 to currentYear + 100
        for (int i = _realYear - 100; i <= _realYear + 100; i++) {
            cmbYear.addItem(String.valueOf(i));
        }

        // check it a user is logged (logged is set to true from login). If logged is true, then there is at least one user logged on.
        // check if that user is an admin, if yes then show things admin can see but if not hide them.
        if (_currentUser == null) {
            for (Iterator<User> u = _userInfo.keySet().iterator(); u.hasNext();) {
                _currentUser = u.next();
                if (_currentUser.getUsername().equals(Logon._loginUsername)) {
                    lblUserName.setText("User " + _currentUser.getUsername());
                    if (_currentUser.isAdmin() == false) {
                        hideNonAdmin();
                        if (_currentUser.getCustomColor() != null) {
                            pnlBackground.setBackground(_currentUser.getCustomColor());
                        }
                    }
                    break;
                } else {
                    lblUserName.setText("Unknown User");
                }
            }
        }

        // get the current date and set upcoming events. Update the table
        String currentDate = _df.format(_currentDate);
        updateUpcoming(currentDate);

        // this is just for testing. This should be removed
        if (_currentUser != null) {
            ArrayList<Event> currentuserEvents = _userInfo.get(_currentUser);
            if (currentuserEvents != null) {
                for (int i = 0; i < currentuserEvents.size(); i++) {
                    Event e = currentuserEvents.get(i);
                }
            }
        }
        refreshCalendar(_realMonth, _realYear);
    }

    //Go through all events for current user, if date is today, update table to show them
    public static void updateUpcoming(String currentDate) {
        _upcomingEventsModel.setRowCount(0);
        if (_currentUser != null) { // null check should be removed later
            ArrayList<Event> currentuserEvents = _userInfo.get(_currentUser);
            if (currentuserEvents != null) { // should never be null
                for (Iterator<Event> it = currentuserEvents.iterator(); it.hasNext();) {
                    Event e = it.next();
                    if (e.getEventDate().equals(currentDate)) {
                        _upcomingEventsModel.addRow(new Object[]{e.getEventName()+" at "+e.getEventTime()});
                    }
                }
            }
        }
    }

    // hide admin functionalities from normal users
    private void hideNonAdmin() {
        mnuAddUser.setVisible(false);
        mnuListEditUser.setVisible(false);
    }

    // logout redirects user to login page
    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        _currentUser.setLogged(false);
        _currentUser = null;
        this.dispose();
        try {
            new Logon().setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void mnuListEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuListEditUserActionPerformed
        // TODO add your handling code here:
        EditUser.run();
    }//GEN-LAST:event_mnuListEditUserActionPerformed

    private void mnuUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuUserActionPerformed

    // refreshes the calendar whenever the year is changed
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
            int mon = month + 1;
            String curDay = String.valueOf(i);
            if (i < 10) {
                curDay = "0" + String.valueOf(i);
            }
            String currentDate = mon + "/" + curDay + "/" + _currentYear;
            String valueatI = "<html><b>" + String.valueOf(i) + "</b></html>";
            boolean eventExist = false;
            //_CalendarTableModel.setValueAt(i, row, column);
            if (_currentUser != null) {
                ArrayList<Event> currentuserEvents = _userInfo.get(_currentUser);
                if (currentuserEvents != null) {
                    for (Iterator<Event> it = currentuserEvents.iterator(); it.hasNext();) {
                        Event e = it.next();
                        if (e.getEventDate().equals(currentDate)) {
                            eventExist = true;
                            valueatI += "<br><em>" + e.getEventName() + " at " + e.getEventTime() + "</em>";
                        }
                    }
                }
            }
            valueatI += "</html>";
            if (eventExist == true) {
                valueatI = valueatI.replaceFirst("</html>", "");
            }
            _CalendarTableModel.setValueAt(valueatI, row, column);
        }
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
        counter++;
    }

    private void getModelrowCount() {
        for (; modelrowCount < _invitationEventModel.getRowCount(); modelrowCount++) {
            Object value = _invitationEventModel.getValueAt(modelrowCount, 0);
            if (value != null) {
                modelrowCount++;
            }
        }
    }

    // this does not work now. similar to updateCalendar, purpose is to replace that since it forces 
    // events to be added to calendar without any check whatsoever or user accepting.
    private void showInvites() {
        getModelrowCount();
        File _testLog = new File(Serialize._serverFile);
        try {
            Serialize.OpenServerFiles(_testLog);
            ArrayList<Event> _currUserEvents = _userInfo.get(_currentUser);
            if (_currUserEvents != null) {
                for (Iterator<Event> it = _allEvents.iterator(); it.hasNext();) {
                    Event event;
                    event = it.next();
                    boolean eventExist = false; // assumption is that the event is not here already
                    if (event.getAttendees().contains(_currentUser.getUsername()) || event.getEventCreator().equals(_currentUser)) {
                        for (Event e : _currUserEvents) {
                            if (e.getEventName().equals(event.getEventName())) {
                                eventExist = true;
                            }
                        }
                        if (eventExist == false) {
                            _invitationEventModel.setValueAt(event.getEventName(), modelrowCount, 0);
                            modelrowCount++;
                        } // only add if this is false
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // update Calendar so when new events are added, the calendar gets updated with those events
    private void updateCalendar() {
        if (_currentUser != null) {
            try {
                int userId = dbModel.findId(_currentUser.getUsername(), "User");
                dbModel.updateUserEvents(userId);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            refreshCalendar(_currentMonth, _currentYear);
            String currentDate = _df.format(_currentDate);
            updateUpcoming(currentDate);
        }
    }

    private boolean showEvent() {
        boolean eventExist = false;
        for (Iterator<Event> it = _userInfo.get(_currentUser).iterator(); it.hasNext();) {
            Event e = it.next();
            String currDate = _df.format(_currentDate);
            if (currDate.equals(e.getEventDate())) {
                String time = LocalTime.now().toString();
                time = time.substring(0, time.lastIndexOf("."));
                int timeDue = calcTime(e.getEventTime()) - calcTime(time);
                //System.out.println("curr time " + time + " even time " + e.getEventTime() + " time diff " + timeDue);
                String tD = timeDue > 1 ? String.valueOf(timeDue) + " minutes" : String.valueOf(timeDue) + " minute";
                String tme = tD.split(" ")[0];
                if (timeDue >= 0 && timeDue <= 15) {
                    if (!_dueEvent.containsKey(e.getEventName()) || _dueEvent.get(e.getEventName()) <= 0) {
                        _dueEvent.put(e.getEventName(), Double.parseDouble(tme)); // show again in 5 minutes
                        EventNotification.run();
                        eventExist = true;
                        //EventNotification._upcomingEventsModel.addRow(new Object[]{e.getEventDescription(), tD});
                    } else// reduce time until it shows again
                    {
                        if (timeDue == 0) {
                            _dueEvent.remove(e.getEventName());
                        } else{
                            _dueEvent.put(e.getEventName(), (_dueEvent.get(e.getEventName()) - 0.5));
                        }
                    }
                }
            }
        }
        return eventExist;
    }

    private int calcTime(String time) {
        int currTime = 0;
        String [] tokens = time.split(":");
        currTime += Integer.parseInt(tokens[0]) * 3600;
        currTime += Integer.parseInt(tokens[1]) * 60;
        currTime += Integer.parseInt(tokens[2]);
        return currTime/60;
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
                val = val.substring(val.indexOf("<b>") + 3, val.indexOf("</b>"));
                //val = val.contains(" ") ? val.substring(0, val.indexOf(" ")) : val;
                if (Integer.parseInt(val) == _realDay && _currentMonth == _realMonth && _currentYear == _realYear) {
                    //current Day
                    setBackground(new Color(220, 220, 255));
                }
            }

            if (selected) {
                setBackground(new Color(128, 128, 128));
                Object dateChosen = _CalendarTableModel.getValueAt(tblCalendar.getSelectedRow(),
                        tblCalendar.getSelectedColumn());
                //_eventday = String.valueOf(dateChosen);
                //printing for debug purposes
                //System.out.println("date is " + _currentMonth + "-" + _eventday + "-" + _currentYear);
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
        //respond only to double click
        if (dateClicked) {
            dateClicked = false;
            buildDateGUI();
            jScrollPane1.setViewportView(dateGUI);
        } else {
            dateClicked = true;
            java.util.Timer t = new java.util.Timer();
            t.schedule(new java.util.TimerTask() {

                @Override
                public void run() {
                    dateClicked = false;
                }
            }, 700);
        }
    }

//GEN-LAST:event_tblCalendarMouseClicked

    /* builds the GUI to be displayed when a particular date in the calendar is clicked
     * consists of an upper and lower Jpanel inside the dateGUI JPanel
     */
    private void buildDateGUI() {
        GregorianCalendar testCalendar = new GregorianCalendar(_currentYear, _currentMonth, 1);
        int offset = testCalendar.get(GregorianCalendar.DAY_OF_WEEK) - 1;

        int row = tblCalendar.getSelectedRow();
        int col = tblCalendar.getSelectedColumn();
        int daySelected = row * 7 + col + 1 - offset;
        String dayS = daySelected < 10 ? "0" + String.valueOf(daySelected) : String.valueOf(daySelected);
        int cMonth = _currentMonth+1;
        String dateSelected_noZero = cMonth + "/" + daySelected + "/" + _currentYear;
        String currMonth = cMonth < 10 ? "0"+String.valueOf(cMonth) : String.valueOf(cMonth);
        String dateSelected = currMonth + "/" + dayS + "/" + _currentYear;

        //add some layout components
        dateGUI = new javax.swing.JPanel(new GridBagLayout());
        java.awt.Dimension d = new java.awt.Dimension(jScrollPane1.getPreferredSize());
        GridBagConstraints c = new GridBagConstraints();
        javax.swing.JPanel upperPanel = new javax.swing.JPanel(new GridBagLayout());
        javax.swing.JPanel lowerPanel = new javax.swing.JPanel(new GridBagLayout());
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(lowerPanel);
        //int always = javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
        //int never = javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER;
        //jScrollPane1.setVerticalScrollBarPolicy(never);
        //scroll.setVerticalScrollBarPolicy(always);
        scroll.setPreferredSize(new java.awt.Dimension(d.width, d.height));
        lowerPanel.setPreferredSize(new java.awt.Dimension(980, 1100));

        javax.swing.JButton btnBack = new javax.swing.JButton("Back");
        javax.swing.JButton btnCreateEvent = new javax.swing.JButton("Create Event");

        //coloring
        dateGUI.setBackground(tblCalendar.getBackground());
        dateGUI.setForeground(tblCalendar.getForeground());
        upperPanel.setBackground(tblCalendar.getBackground());
        upperPanel.setForeground(tblCalendar.getForeground());
        lowerPanel.setBackground(tblCalendar.getBackground());
        lowerPanel.setForeground(tblCalendar.getForeground());
        btnBack.setBackground(btnUCreateEvent.getBackground());
        btnBack.setForeground(btnUCreateEvent.getForeground());
        btnBack.setFont(btnUCreateEvent.getFont());
        btnCreateEvent.setBackground(btnUCreateEvent.getBackground());
        btnCreateEvent.setFont(btnUCreateEvent.getFont());
        btnCreateEvent.setForeground(btnUCreateEvent.getForeground());

        //layout
        //BACK
        c.insets = new java.awt.Insets(2, 2, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        upperPanel.add(btnBack, c);

        //seperator
        c.gridx = 1;
        c.fill = GridBagConstraints.BOTH;
        upperPanel.add(new javax.swing.JSeparator(
                javax.swing.SwingConstants.VERTICAL), c);

        //CREATE EVENT
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        upperPanel.add(btnCreateEvent, c);

        //TIME
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        lowerPanel.add(new javax.swing.JLabel("Time"), c);

        //seperator
        c.gridx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        lowerPanel.add(new javax.swing.JSeparator(
                javax.swing.SwingConstants.VERTICAL), c);

        //EVENTS
        c.gridx = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        lowerPanel.add(new javax.swing.JLabel("Events"), c);

        //fill in times and event descriptions
        c.anchor = GridBagConstraints.WEST;
        for (int i = 1; i <= 24; i++) {
            //time on the hour (1, 2, 3, etc)
            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;
            c.gridy = i * 2;
            c.gridwidth = 1;
            c.weightx = 0;
            lowerPanel.add(new javax.swing.JLabel(Integer.toString(i - 1)), c);

            c.gridx = 1;
            c.fill = GridBagConstraints.BOTH;
            lowerPanel.add(new javax.swing.JSeparator(
                    javax.swing.SwingConstants.VERTICAL), c);

            //time every 1/2 hour (1:30, 2:30, etc)
            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;
            c.gridy++;
            c.weightx = 0;
            lowerPanel.add(new javax.swing.JLabel(Integer.toString(i - 1) + ":30"), c);

            //event
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.fill = GridBagConstraints.BOTH;
            lowerPanel.add(new javax.swing.JSeparator(
                    javax.swing.SwingConstants.VERTICAL), c);
        }

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 3;
        c.weightx = 1;
        //add event descriptions and delete buttons
        ArrayList<Event> currentUserEvents = _userInfo.get(_currentUser);
        for (Iterator<Event> it = currentUserEvents.iterator(); it.hasNext();) {
            Event e = it.next();
            if (e.getEventDate().equals(dateSelected_noZero)) {
                String[] time = e.getEventTime().split(":");
                int hour = Integer.parseInt(time[0]);
                int min = Integer.parseInt(time[1]);

                c.gridy = (hour + 1) * 2;
                if (min >= 30) {
                    c.gridy++;
                }
                c.gridx = GridBagConstraints.RELATIVE;
                lowerPanel.add(new javax.swing.JLabel(e.getEventDescription()), c);
            }
        }

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        dateGUI.add(upperPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        dateGUI.add(scroll, c);
        
        scroll.getVerticalScrollBar().setUnitIncrement(12);

        btnBack.addActionListener((java.awt.event.ActionEvent e) -> {
            jScrollPane1.setViewportView(tblCalendar);
        });

        btnCreateEvent.addActionListener((java.awt.event.ActionEvent e) -> {
            try {
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date date = (Date) df.parse(dateSelected);
                CreateEvent.run();
                //CreateEvent.jdpDateSelector.setDate(date); think about this later.
            } catch (ParseException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void mnuEditPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditPasswordActionPerformed
        // TODO add your handling code here:
        String newPassword = JOptionPane.showInputDialog("Enter new password here");
        Encryption encrypt = new Encryption();
        if ((!"".equals(newPassword) || newPassword != null)) {
            try {
                _currentUser.setPassword(encrypt.getEncryptedPassword(newPassword,
                        _currentUser.getSalt()));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            _userInfo.put(_currentUser, _userInfo.get(_currentUser));
            Serialize.saveUserFiles(Serialize._fileLocation);
        } else {
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
            Serialize.saveUserFiles(Serialize._fileLocation);
        }
    }//GEN-LAST:event_mnuCustomizeCalendarActionPerformed

    private void btnUCreateEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUCreateEventActionPerformed
        // TODO add your handling code here:
        //this.setVisible(false);
        CreateEvent.run();
    }//GEN-LAST:event_btnUCreateEventActionPerformed

    private void btnListEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListEventsActionPerformed
        // TODO add your handling code here:
        // first set count dynamically.. sorta
        ListEvents.run();

    }//GEN-LAST:event_btnListEventsActionPerformed

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
    private javax.swing.JButton btnListEvents;
    private javax.swing.JButton btnLogout;
    private static javax.swing.JButton btnNext;
    private static javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUCreateEvent;
    private static javax.swing.JComboBox<String> cmbYear;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTable jTable1;
    private static javax.swing.JLabel lblMonth;
    private static javax.swing.JLabel lblUserName;
    private javax.swing.JMenuItem mnuAddUser;
    private javax.swing.JMenuItem mnuCustomizeCalendar;
    private javax.swing.JMenuItem mnuEditPassword;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenuItem mnuListEditUser;
    private javax.swing.JMenu mnuMainEdit;
    private javax.swing.JMenu mnuUser;
    private javax.swing.JPanel pnlBackground;
    private static javax.swing.JTable tblCalendar;
    public static javax.swing.JTable tblUpcomingEvents;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JPanel dateGUI;
}
