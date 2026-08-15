package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PrimeSumFrame extends JFrame {
    private JTextField txtN;
    private JButton btnCalculate;
    private JLabel lblResult;
    private JProgressBar progressBar;

    public PrimeSumFrame() {
        // Thiết lập tiêu đề và kích thước Frame
        setTitle("Tính tổng các số nguyên tố nhỏ hơn N");
        setSize(450, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel nhập liệu (Phía trên)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(new JLabel("Nhập N:"));
        txtN = new JTextField(10);
        topPanel.add(txtN);

        btnCalculate = new JButton("Tính");
        topPanel.add(btnCalculate);
        add(topPanel, BorderLayout.NORTH);

        // 2. Panel trung tâm hiển thị tiến trình và kết quả
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        centerPanel.add(progressBar);

        lblResult = new JLabel("Kết quả: ", SwingConstants.CENTER);
        lblResult.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(lblResult);

        add(centerPanel, BorderLayout.CENTER);

        // Sự kiện khi nhấn nút "Tính"
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
            long n = Long.parseLong(input);
            if (n <= 2) {
                lblResult.setText("Kết quả: 0 (Không có số nguyên tố nhỏ hơn " + n + ")");
                progressBar.setValue(100);
                return;
            }

            // Vô hiệu hóa nút bấm và reset progress bar trước khi chạy
            btnCalculate.setEnabled(false);
            progressBar.setValue(0);
            lblResult.setText("Đang tính toán...");

            // Khởi chạy SwingWorker
            PrimeTask task = new PrimeTask(n);
            task.execute();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "N phải là một số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * SwingWorker<Long, Integer>
     * - Long: Kiểu dữ liệu trả về của kết quả cuối cùng (Tổng các số nguyên tố)
     * - Integer: Kiểu dữ liệu dùng để cập nhật tiến trình (%)
     */
    private class PrimeTask extends SwingWorker<Long, Integer> {
        private final long n;

        public PrimeTask(long n) {
            this.n = n;
        }

        // Tính toán ngầm dưới Background Thread
        @Override
        protected Long doInBackground() throws Exception {
            long sum = 0;
            long count = 0;
            long totalNumbers = n - 2; // Số lượng số cần kiểm tra từ 2 đến n - 1

            for (long i = 2; i < n; i++) {
                if (isPrime(i)) {
                    sum += i;
                }
                count++;

                // Cập nhật % tiến trình sau mỗi 1000 số hoặc khi sắp xong (để tránh quá tải UI thread)
                if (count % 1000 == 0 || count == totalNumbers) {
                    int progress = (int) ((count * 100) / totalNumbers);
                    publish(progress);
                }
            }
            return sum;
        }

        // Cập nhật thanh tiến trình JProgressBar trên UI Thread
        @Override
        protected void process(List<Integer> chunks) {
            int latestProgress = chunks.get(chunks.size() - 1);
            progressBar.setValue(latestProgress);
        }

        // Gọi sau khi doInBackground hoàn thành
        @Override
        protected void done() {
            try {
                long resultSum = get(); // Lấy kết quả từ doInBackground
                lblResult.setText("Tổng các số nguyên tố < " + n + " là: " + resultSum);
                progressBar.setValue(100);
            } catch (Exception e) {
                lblResult.setText("Có lỗi xảy ra trong quá trình tính toán!");
                e.printStackTrace();
            } finally {
                btnCalculate.setEnabled(true); // Bật lại nút "Tính"
            }
        }

        // Hàm kiểm tra số nguyên tố
        private boolean isPrime(long number) {
            if (number < 2) return false;
            if (number == 2 || number == 3) return true;
            if (number % 2 == 0 || number % 3 == 0) return false;
            for (long i = 5; i * i <= number; i += 6) {
                if (number % i == 0 || number % (i + 2) == 0) return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        // Chạy giao diện trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrimeSumFrame().setVisible(true);
            }
        });
    }
}