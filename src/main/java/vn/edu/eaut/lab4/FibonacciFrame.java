package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FibonacciFrame extends JFrame {
    private JTextField txtN;
    private JButton btnCalculate;
    private JLabel lblResult;
    private JProgressBar progressBar;

    public FibonacciFrame() {
        // Cấu hình cửa sổ giao diện chính
        setTitle("Tìm số Fibonacci thứ N (Memoization)");
        setSize(500, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel nhập liệu phía trên
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(new JLabel("Nhập N:"));
        txtN = new JTextField(10);
        topPanel.add(txtN);

        btnCalculate = new JButton("Tìm");
        topPanel.add(btnCalculate);
        add(topPanel, BorderLayout.NORTH);

        // 2. Panel hiển thị thanh tiến trình và kết quả ở trung tâm
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        centerPanel.add(progressBar);

        lblResult = new JLabel("Kết quả: ", SwingConstants.CENTER);
        lblResult.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(lblResult);

        add(centerPanel, BorderLayout.CENTER);

        // Đăng ký sự kiện click cho nút "Tìm"
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCalculation();
            }
        });
    }

    private void startCalculation() {
        String input = txtN.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá trị N!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int n = Integer.parseInt(input);
            if (n < 0) {
                JOptionPane.showMessageDialog(this, "N phải là số nguyên không âm (N >= 0)!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Vô hiệu hóa nút và đặt lại giao diện trước khi bắt đầu
            btnCalculate.setEnabled(false);
            progressBar.setValue(0);
            lblResult.setText("Đang tính toán...");

            // Khởi chạy SwingWorker
            FibonacciWorker task = new FibonacciWorker(n);
            task.execute();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "N phải là một số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * SwingWorker<BigInteger, Integer>
     * - BigInteger: Kết quả của số Fibonacci thứ N
     * - Integer: Đẩy phần trăm tiến trình (%) ra giao diện
     */
    private class FibonacciWorker extends SwingWorker<BigInteger, Integer> {
        private final int n;
        private final Map<Integer, BigInteger> memo = new HashMap<>();

        public FibonacciWorker(int n) {
            this.n = n;
        }

        @Override
        protected BigInteger doInBackground() throws Exception {
            // Trường hợp cơ sở: F(0) = 0, F(1) = 1
            if (n == 0) return BigInteger.ZERO;
            if (n == 1) return BigInteger.ONE;

            memo.put(0, BigInteger.ZERO);
            memo.put(1, BigInteger.ONE);

            // Tính tuần tự từ 2 tới N kết hợp Memoization
            for (int i = 2; i <= n; i++) {
                BigInteger fibI = memo.get(i - 1).add(memo.get(i - 2));
                memo.put(i, fibI);

                // Cập nhật thanh tiến trình theo từng khoảng hoặc khi kết thúc
                if (i % 100 == 0 || i == n) {
                    int progress = (int) (((double) i / n) * 100);
                    publish(progress);
                }
            }

            return memo.get(n);
        }

        @Override
        protected void process(List<Integer> chunks) {
            // Lấy tiến trình mới nhất và cập nhật JProgressBar
            int latestProgress = chunks.get(chunks.size() - 1);
            progressBar.setValue(latestProgress);
        }

        @Override
        protected void done() {
            try {
                BigInteger result = get();
                // Hiển thị kết quả (rút gọn nếu quá dài để tránh vỡ khung UI)
                String resultStr = result.toString();
                if (resultStr.length() > 30) {
                    lblResult.setText("F(" + n + ") có " + resultStr.length() + " chữ số. " +
                            "Bắt đầu: " + resultStr.substring(0, 15) + "...");
                    lblResult.setToolTipText("Full result: " + resultStr);
                } else {
                    lblResult.setText("F(" + n + ") = " + resultStr);
                }
                progressBar.setValue(100);
            } catch (Exception e) {
                lblResult.setText("Có lỗi xảy ra trong quá trình tính toán!");
                e.printStackTrace();
            } finally {
                btnCalculate.setEnabled(true); // Bật lại nút tính
            }
        }
    }

    public static void main(String[] args) {
        // Khởi chạy ứng dụng trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FibonacciFrame().setVisible(true);
            }
        });
    }
}