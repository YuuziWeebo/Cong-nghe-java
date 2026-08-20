package vn.edu.eaut.lab5.util;

import javax.swing.*;
import java.awt.*;

public class MessageUtil {

    // Hiển thị thông báo thông tin (như Lưu thành công, Xóa thành công,...)
    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // Hiển thị thông báo lỗi
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    // Hiển thị cảnh báo (validate dữ liệu, chọn dòng trước khi thao tác,...)
    public static void showWarning(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }

    // Hiển thị hộp thoại xác nhận Yes/No (dùng cho hành động Xóa)
    public static boolean showConfirm(Component parent, String message) {
        int choice = JOptionPane.showConfirmDialog(parent, message, "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return choice == JOptionPane.YES_OPTION;
    }
}