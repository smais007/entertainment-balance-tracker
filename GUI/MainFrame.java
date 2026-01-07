package GUI;

import Entry.DailyEntry;
import File.BalanceCalculator;
import File.FileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MainFrame extends JFrame {
    
    private ArrayList<DailyEntry> entries;
    
    private JTextField dateField;
    private JTextField gamingField;
    private JTextField movieField;
    private JTextField studyField;
    private JTextField notesField;
    
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    
    private JLabel statusLabel;
    private JProgressBar progressBar;
    
    private JTable table;
    private DefaultTableModel tableModel;
    
    private int selectedRow = -1;

    public MainFrame() {

        setTitle("Entertainment Balance Tracker");
        
        setSize(700, 600);
        
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        entries = FileHandler.loadEntries();
        
        initializeComponents();
        
        setupLayout();
        
        setupListeners();
        
        refreshTable();
    }
    

    private void initializeComponents() {
        dateField = new JTextField(15);
        gamingField = new JTextField(10);
        movieField = new JTextField(10);
        studyField = new JTextField(10);
        notesField = new JTextField(20);
        
        addButton = createStyledButton("Add Entry", new Color(76, 175, 80));
        updateButton = createStyledButton("Update", new Color(33, 150, 243));
        deleteButton = createStyledButton("Delete", new Color(244, 67, 54));
        clearButton = createStyledButton("Clear", new Color(158, 158, 158));

        statusLabel = new JLabel("BALANCED");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.GREEN.darker());
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);  
        progressBar.setValue(0);
        
        // Create table with column names
        String[] columnNames = {"Date", "Gaming", "Movie", "Study", "Total", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Entertainment Balance Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        inputPanel.add(new JLabel("Date (DD/MM/YYYY):"));
        inputPanel.add(dateField);
        
        inputPanel.add(new JLabel("Gaming (minutes):"));
        inputPanel.add(gamingField);
        
        inputPanel.add(new JLabel("Movie/TV (minutes):"));
        inputPanel.add(movieField);
        
        inputPanel.add(new JLabel("Study (minutes):"));
        inputPanel.add(studyField);
        
        inputPanel.add(new JLabel("Notes (optional):"));
        inputPanel.add(notesField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        statusPanel.add(new JLabel("Balance Status: "));
        statusPanel.add(statusLabel);
        
        JPanel progressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        progressPanel.add(new JLabel("Entertainment %: "));
        progressBar.setPreferredSize(new Dimension(200, 25));
        progressPanel.add(progressBar);
        
        centerPanel.add(inputPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(statusPanel);
        centerPanel.add(progressPanel);
        
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(650, 200));
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Daily Entries"));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }
    
  
    private void setupListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEntry();
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEntry();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEntry();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table.getSelectedRow();
                if (selectedRow >= 0 && selectedRow < entries.size()) {
                    DailyEntry entry = entries.get(selectedRow);
                    dateField.setText(entry.getDate());
                    gamingField.setText(String.valueOf(entry.getGamingMinutes()));
                    movieField.setText(String.valueOf(entry.getMovieMinutes()));
                    studyField.setText(String.valueOf(entry.getStudyMinutes()));
                    notesField.setText(entry.getNotes());
                    
                    updateStatusDisplay(entry);
                }
            }
        });
    }

    private void addEntry() {
        try {
            String date = getValidatedDate();
            int gaming = getValidatedNumber(gamingField.getText(), "Gaming");
            int movie = getValidatedNumber(movieField.getText(), "Movie");
            int study = getValidatedNumber(studyField.getText(), "Study");
            String notes = notesField.getText().trim();
            
            DailyEntry newEntry = new DailyEntry(date, gaming, movie, study, notes);
            
            entries.add(newEntry);
            
            FileHandler.saveEntries(entries);
            
            refreshTable();
            
            updateStatusDisplay(newEntry);
            clearFields();
            
            JOptionPane.showMessageDialog(this, "Entry added successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void updateEntry() {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an entry to update!", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String date = getValidatedDate();
            int gaming = getValidatedNumber(gamingField.getText(), "Gaming");
            int movie = getValidatedNumber(movieField.getText(), "Movie");
            int study = getValidatedNumber(studyField.getText(), "Study");
            String notes = notesField.getText().trim();
            
            DailyEntry entry = entries.get(selectedRow);
            entry.setDate(date);
            entry.setGamingMinutes(gaming);
            entry.setMovieMinutes(movie);
            entry.setStudyMinutes(study);
            entry.setNotes(notes);
            
            FileHandler.saveEntries(entries);
            
            refreshTable();
            
            updateStatusDisplay(entry);
            
            JOptionPane.showMessageDialog(this, "Entry updated successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void deleteEntry() {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an entry to delete!", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this entry?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            entries.remove(selectedRow);
            
            FileHandler.saveEntries(entries);
            
                refreshTable();
            
            clearFields();
            
            selectedRow = -1;
            
            JOptionPane.showMessageDialog(this, "Entry deleted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

    private void clearFields() {
        dateField.setText("");
        gamingField.setText("");
        movieField.setText("");
        studyField.setText("");
        notesField.setText("");
        table.clearSelection();
        selectedRow = -1;
        
        statusLabel.setText("BALANCED");
        statusLabel.setForeground(Color.GREEN.darker());
        progressBar.setValue(0);
    }
    

    private void refreshTable() {
        tableModel.setRowCount(0);
        
        for (DailyEntry entry : entries) {
            String status = BalanceCalculator.calculateStatus(entry);
            
            Object[] row = {
                entry.getDate(),
                entry.getGamingMinutes(),
                entry.getMovieMinutes(),
                entry.getStudyMinutes(),
                entry.getTotalTime(),
                status
            };
            
            tableModel.addRow(row);
        }
    }
    

    private void updateStatusDisplay(DailyEntry entry) {
        String status = BalanceCalculator.calculateStatus(entry);
        statusLabel.setText(status);
        
        if (status.equals(BalanceCalculator.BALANCED)) {
            statusLabel.setForeground(Color.GREEN.darker());
        } else if (status.equals(BalanceCalculator.ENTERTAINMENT_HEAVY)) {
            statusLabel.setForeground(Color.ORANGE);
        } else if (status.equals(BalanceCalculator.HIGH_RISK)) {
            statusLabel.setForeground(Color.RED);
        } else if (status.equals(BalanceCalculator.STUDY_FOCUSED)) {
            statusLabel.setForeground(Color.BLUE);
        }
        
        double percentage = BalanceCalculator.calculateEntertainmentPercentage(entry);
        progressBar.setValue((int) percentage);
    }
    

    private String getValidatedDate() throws Exception {
        String date = dateField.getText().trim();
        
        if (date.isEmpty()) {
            throw new Exception("Date cannot be empty!");
        }
        
        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new Exception("Invalid date format! Please use DD/MM/YYYY");
        }
        
        return date;
    }
    

    private int getValidatedNumber(String text, String fieldName) throws Exception {
        if (text.trim().isEmpty()) {
            throw new Exception(fieldName + " cannot be empty!");
        }
        
        try {
            int number = Integer.parseInt(text.trim());
            
            if (number < 0) {
                throw new Exception(fieldName + " cannot be negative!");
            }
            
            return number;
            
        } catch (NumberFormatException e) {
            throw new Exception(fieldName + " must be a valid number!");
        }
    }
    

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        
        button.setBackground(bgColor);
        
        button.setForeground(Color.WHITE);
        
        button.setFont(new Font("Arial", Font.BOLD, 12));
        
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        
        button.setOpaque(true);
        
        button.setPreferredSize(new Dimension(100, 30));
        
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
}
