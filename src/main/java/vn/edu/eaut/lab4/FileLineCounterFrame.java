package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileLineCounterFrame extends JFrame {
    private JTextField txtFilePath;
    private JButton btnBrowse;
    private JButton btnCountLines;
    private JLabel lblResult;
    private JProgressBar progressBar;

    private File selectedFile;

    public FileLineCounterFrame() {
        // Cấu hình giao diện chính
        setTitle("Đọc file lớn và đếm số dòng");
        setSize(520, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel chọn file phía trên
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        topPanel.add(new JLabel("File:"));

        txtFilePath = new JTextField(25);
        txtFilePath.setEditable(false);
        topPanel.add(txtFilePath);

        btnBrowse = new JButton("Chọn File...");
        topPanel.add(btnBrowse);

        btnCountLines = new JButton("Đếm dòng");
        btnCountLines.setEnabled(false); // Chỉ bật khi đã chọn file
        topPanel.add(btnCountLines);

        add(topPanel, BorderLayout.NORTH);

        // 2. Panel ở giữa hiển thị JProgressBar và kết quả
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        centerPanel.add(progressBar);

        lblResult = new JLabel("Kết quả: Chưa xử lý", SwingConstants.CENTER);
        lblResult.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(lblResult);

        add(centerPanel, BorderLayout.CENTER);

        // Sự kiện khi nhấn nút "Chọn File..."
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });

        // Sự kiện khi nhấn nút "Đếm dòng"
        btnCountLines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLineCounting();
            }
        });
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file cần đếm số dòng");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            txtFilePath.setText(selectedFile.getAbsolutePath());
            btnCountLines.setEnabled(true);
            lblResult.setText("Kết quả: Sẵn sàng đếm dòng.");
            progressBar.setValue(0);
        }
    }

    private void startLineCounting() {
        if (selectedFile == null || !selectedFile.exists()) {
            JOptionPane.showMessageDialog(this, "File không tồn tại hoặc chưa được chọn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vô hiệu hóa các nút bấm trước khi xử lý
        btnBrowse.setEnabled(false);
        btnCountLines.setEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Đang đọc file và đếm dòng...");

        // Khởi chạy SwingWorker
        LineCounterWorker worker = new LineCounterWorker(selectedFile);
        worker.execute();
    }

    /**
     * SwingWorker<Long, Integer>
     * - Long: Tổng số dòng đếm được (tránh tràn số nếu file cực lớn)
     * - Integer: % Tiến trình cập nhật cho JProgressBar
     */
    private class LineCounterWorker extends SwingWorker<Long, Integer> {
        private final File file;

        public LineCounterWorker(File file) {
            this.file = file;
        }

        @Override
        protected Long doInBackground() throws Exception {
            long lineCount = 0;
            long totalBytes = file.length();
            long bytesReadSoFar = 0;

            // Sử dụng BufferedReader để đọc từng dòng hiệu quả
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lineCount++;

                    // Ước lượng số byte đã đọc (độ dài dòng + ký tự xuống dòng '\n' khoảng 1-2 bytes)
                    bytesReadSoFar += line.getBytes(StandardCharsets.UTF_8).length + 2;

                    // Cập nhật JProgressBar sau mỗi 5000 dòng hoặc khi đọc xong
                    if (lineCount % 5000 == 0 || bytesReadSoFar >= totalBytes) {
                        int progress = 0;
                        if (totalBytes > 0) {
                            progress = (int) Math.min(100, (bytesReadSoFar * 100) / totalBytes);
                        }
                        publish(progress);
                    }
                }
            }

            return lineCount;
        }

        @Override
        protected void process(List<Integer> chunks) {
            // Lấy % tiến trình mới nhất
            int latestProgress = chunks.get(chunks.size() - 1);
            progressBar.setValue(latestProgress);
        }

        @Override
        protected void done() {
            try {
                long totalLines = get(); // Lấy tổng số dòng từ doInBackground
                lblResult.setText("Tổng số dòng: " + String.format("%,d", totalLines) + " dòng");
                progressBar.setValue(100);
            } catch (Exception e) {
                lblResult.setText("Lỗi khi đọc file!");
                JOptionPane.showMessageDialog(FileLineCounterFrame.this,
                        "Không thể đọc file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                // Mở lại khả năng tương tác của nút bấm
                btnBrowse.setEnabled(true);
                btnCountLines.setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        // Chạy giao diện trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileLineCounterFrame().setVisible(true);
            }
        });
    }
}