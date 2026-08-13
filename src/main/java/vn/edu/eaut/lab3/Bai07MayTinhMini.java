package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bai07MayTinhMini extends JFrame implements ActionListener {

    private JTextField txtDisplay;
    private DefaultListModel<String> historyModel;
    private JList<String> listHistory;

    private double num1 = 0;
    private String operator = "";
    private boolean startNewInput = true;

    public Bai07MayTinhMini() {
        setTitle("Máy tính Mini");
        setSize(550, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        txtDisplay = new JTextField("0");
        txtDisplay.setFont(new Font("Consolas", Font.BOLD, 28));
        txtDisplay.setHorizontalAlignment(JTextField.RIGHT);
        txtDisplay.setEditable(false);
        txtDisplay.setBackground(Color.WHITE);
        txtDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(txtDisplay, BorderLayout.NORTH);

        JPanel panelButtons = new JPanel(new GridLayout(5, 4, 8, 8));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 5));

        String[] buttons = {
                "C", "CE", "Xoa", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setFocusPainted(false);

            if (text.matches("[0-9|.]")) {
                btn.setBackground(new Color(245, 245, 245));
            } else if (text.equals("=")) {
                btn.setBackground(new Color(52, 152, 219));
                btn.setForeground(Color.WHITE);
            } else if (text.equals("C") || text.equals("CE")) {
                btn.setBackground(new Color(231, 76, 60));
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(new Color(220, 220, 220));
            }

            btn.addActionListener(this);
            panelButtons.add(btn);
        }
        add(panelButtons, BorderLayout.CENTER);

        JPanel panelHistory = new JPanel(new BorderLayout(5, 5));
        panelHistory.setPreferredSize(new Dimension(200, 0));
        panelHistory.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 10));

        JLabel lblHistoryTitle = new JLabel("Lịch sử tính toán", JLabel.CENTER);
        lblHistoryTitle.setFont(new Font("Arial", Font.BOLD, 14));

        historyModel = new DefaultListModel<>();
        listHistory = new JList<>(historyModel);
        listHistory.setFont(new Font("Consolas", Font.PLAIN, 13));

        JScrollPane scrollHistory = new JScrollPane(listHistory);

        JButton btnClearHistory = new JButton("Xóa lịch sử");
        btnClearHistory.setFont(new Font("Arial", Font.PLAIN, 12));
        btnClearHistory.addActionListener(e -> historyModel.clear());

        panelHistory.add(lblHistoryTitle, BorderLayout.NORTH);
        panelHistory.add(scrollHistory, BorderLayout.CENTER);
        panelHistory.add(btnClearHistory, BorderLayout.SOUTH);

        add(panelHistory, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.matches("[0-9]")) {
            if (startNewInput || txtDisplay.getText().equals("0") || isErrorState()) {
                txtDisplay.setText(cmd);
                startNewInput = false;
            } else {
                txtDisplay.setText(txtDisplay.getText() + cmd);
            }
        } else if (cmd.equals(".")) {
            if (startNewInput || isErrorState()) {
                txtDisplay.setText("0.");
                startNewInput = false;
            } else if (!txtDisplay.getText().contains(".")) {
                txtDisplay.setText(txtDisplay.getText() + ".");
            }
        } else if (cmd.equals("C")) {
            txtDisplay.setText("0");
            num1 = 0;
            operator = "";
            startNewInput = true;
        } else if (cmd.equals("CE")) {
            txtDisplay.setText("0");
            startNewInput = true;
        } else if (cmd.equals("⌫")) {
            if (isErrorState()) {
                txtDisplay.setText("0");
                startNewInput = true;
                return;
            }
            String current = txtDisplay.getText();
            if (current.length() > 1) {
                txtDisplay.setText(current.substring(0, current.length() - 1));
            } else {
                txtDisplay.setText("0");
                startNewInput = true;
            }
        } else if (cmd.equals("+/-")) {
            if (isErrorState()) return;
            try {
                double val = Double.parseDouble(txtDisplay.getText());
                txtDisplay.setText(formatResult(val * -1));
            } catch (NumberFormatException ignored) {}
        } else if (cmd.equals("+") || cmd.equals("-") || cmd.equals("*") || cmd.equals("/")) {
            if (isErrorState()) return;
            try {
                num1 = Double.parseDouble(txtDisplay.getText());
                operator = cmd;
                startNewInput = true;
            } catch (NumberFormatException ignored) {}
        } else if (cmd.equals("=")) {
            if (operator.isEmpty() || isErrorState()) return;

            try {
                double num2 = Double.parseDouble(txtDisplay.getText());
                double result = 0;
                boolean hasError = false;

                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 == 0) {
                            hasError = true;
                        } else {
                            result = num1 / num2;
                        }
                        break;
                }

                if (hasError) {
                    txtDisplay.setText("Lỗi chia cho 0!");
                    historyModel.addElement(formatResult(num1) + " " + operator + " 0 = Lỗi");
                    startNewInput = true;
                    operator = "";
                } else {
                    String strNum1 = formatResult(num1);
                    String strNum2 = formatResult(num2);
                    String strResult = formatResult(result);

                    txtDisplay.setText(strResult);

                    String record = strNum1 + " " + operator + " " + strNum2 + " = " + strResult;
                    historyModel.addElement(record);

                    listHistory.ensureIndexIsVisible(historyModel.getSize() - 1);

                    startNewInput = true;
                    operator = "";
                }
            } catch (NumberFormatException ignored) {}
        }
    }

    private boolean isErrorState() {
        return txtDisplay.getText().contains("Lỗi");
    }

    private String formatResult(double val) {
        if (val == (long) val) {
            return String.format("%d", (long) val);
        } else {
            return String.valueOf(val);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            new Bai07MayTinhMini().setVisible(true);
        });
    }
}