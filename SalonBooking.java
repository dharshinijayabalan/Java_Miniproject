import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SalonBooking extends JFrame implements ActionListener {

    JTextField nameField, dateField, timeField;
    JComboBox<String> serviceBox;
    JButton bookBtn, viewBtn, cancelBtn, clearBtn;

    // MySQL Connection
    Connection con;

    // Admin password
    String adminPassword = "admin123";

    public SalonBooking() {
        setTitle("Salon Appointment Booking System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Salon Appointment Booking", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);
        gbc.gridwidth = 1;

        // Name
        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 1; add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1; add(nameField, gbc);

        // Service
        JLabel serviceLabel = new JLabel("Select Service:");
        serviceLabel.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 2; add(serviceLabel, gbc);

        String[] services = {"Hair Cut", "Facial", "Hair Coloring", "Manicure", "Pedicure"};
        serviceBox = new JComboBox<>(services);
        gbc.gridx = 1; add(serviceBox, gbc);

        // Date
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 3; add(dateLabel, gbc);

        dateField = new JTextField(20);
        gbc.gridx = 1; add(dateField, gbc);

        // Time
        JLabel timeLabel = new JLabel("Time (HH:MM):");
        timeLabel.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 4; add(timeLabel, gbc);

        timeField = new JTextField(20);
        gbc.gridx = 1; add(timeField, gbc);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.LIGHT_GRAY);
        Color btnColor = new Color(0, 123, 255); // same color for all
        Color textColor = Color.WHITE;

        bookBtn = new JButton("Book Appointment");
        bookBtn.setBackground(btnColor); bookBtn.setForeground(textColor); bookBtn.addActionListener(this);

        viewBtn = new JButton("Admin View");
        viewBtn.setBackground(btnColor); viewBtn.setForeground(textColor); viewBtn.addActionListener(this);

        cancelBtn = new JButton("Cancel Appointment");
        cancelBtn.setBackground(btnColor); cancelBtn.setForeground(textColor); cancelBtn.addActionListener(this);

        clearBtn = new JButton("Clear");
        clearBtn.setBackground(btnColor); clearBtn.setForeground(textColor); clearBtn.addActionListener(this);

        btnPanel.add(bookBtn); btnPanel.add(viewBtn); btnPanel.add(cancelBtn); btnPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // Note
        JLabel note = new JLabel("Customers can cancel their exact appointment from a list.", SwingConstants.CENTER);
        note.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        add(note, gbc);

        // Connect to DB
        connectDatabase();

        setVisible(true);
    }

    void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/salon_booking_db4", "root", "Dharshini09"); // change username/password
            Statement stmt = con.createStatement();
            // Create table if not exists
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50)," +
                    "service VARCHAR(50)," +
                    "date DATE," +
                    "time TIME" +
                    ")");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Connection Error: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookBtn) {
            String name = nameField.getText();
            String service = (String) serviceBox.getSelectedItem();
            String date = dateField.getText();
            String time = timeField.getText();

            if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            try {
                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO appointments(name, service, date, time) VALUES (?, ?, ?, ?)");
                pst.setString(1, name);
                pst.setString(2, service);
                pst.setString(3, date);
                pst.setString(4, time);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Appointment booked successfully!");
                clearFields();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }

        } else if (e.getSource() == viewBtn) {
            String pass = JOptionPane.showInputDialog(this, "Enter Admin Password:");
            if (pass == null) return;
            if (!pass.equals(adminPassword)) {
                JOptionPane.showMessageDialog(this, "Incorrect Password!");
                return;
            }

            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    sb.append("ID: ").append(rs.getInt("id"))
                            .append(" | Name: ").append(rs.getString("name"))
                            .append(" | Service: ").append(rs.getString("service"))
                            .append(" | Date: ").append(rs.getDate("date"))
                            .append(" | Time: ").append(rs.getTime("time"))
                            .append("\n");
                }
                if (sb.length() == 0) sb.append("No appointments available!");
                JOptionPane.showMessageDialog(this, sb.toString(), "All Appointments", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }

        } else if (e.getSource() == cancelBtn) {
            String idStr = JOptionPane.showInputDialog(this, "Enter Appointment ID to cancel:");
            if (idStr == null || idStr.isEmpty()) return;

            try {
                int id = Integer.parseInt(idStr);
                PreparedStatement pst = con.prepareStatement("DELETE FROM appointments WHERE id=?");
                pst.setInt(1, id);
                int affected = pst.executeUpdate();
                if (affected > 0) {
                    JOptionPane.showMessageDialog(this, "Appointment cancelled successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No appointment found with this ID.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }

        } else if (e.getSource() == clearBtn) {
            clearFields();
        }
    }

    void clearFields() {
        nameField.setText("");
        dateField.setText("");
        timeField.setText("");
        serviceBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new SalonBooking();
    }
}

