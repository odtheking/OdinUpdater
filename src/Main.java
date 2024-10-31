import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {
        File oldJar;
        try {
           oldJar = new File(args[0]);
        } catch (Exception e)
        {
            showError("Failed to receive program arguments");
            return;
        }

        boolean deleted = false;
        for (int i = 0; i < 10; i++)
        {
            if (oldJar.exists() && oldJar.delete())
            {
                deleted = true;
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }

        if (!deleted && oldJar.exists())
        {
            showError("Failed to delete old jar file");
            return;
        }

        File relaunchCommandFile = new File(args[1]);
        String relaunchCommand;
        try {
            relaunchCommand = String.join("", Files.readAllLines(relaunchCommandFile.toPath()));
        } catch (IOException e) {
            showError("Failed to read relaunch command file");
            return;
        }

        try {
            relaunchCommandFile.delete();
        } catch (Exception e) {
            showError("Failed to delete relaunch command file");
            return;
        }

        try {
            Runtime.getRuntime().exec(relaunchCommand);
        } catch (IOException e) {
            showError("Failed to relaunch minecraft");
        }
    }

    public static void showError(String message) {
        JFrame frame = new JFrame("OdinUpdater - Error");
        frame.setSize(350, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding around the panel
        frame.add(panel);

        JLabel errorLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(errorLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(66, 133, 244));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        closeButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        closeButton.addActionListener(e -> System.exit(0));

        closeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                closeButton.setBackground(new Color(48, 92, 173));
            }

            public void mouseExited(MouseEvent evt) {
                closeButton.setBackground(new Color(66, 133, 244));
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

}