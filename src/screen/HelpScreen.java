package screen;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HelpScreen extends JFrame {
	// Create help screen to show our aims and how to use
	public HelpScreen() {
		setLocationRelativeTo(null);
		setSize(1024, 768);
		setVisible(true);
		JPanel imgPanel = new JPanel();
		imgPanel.setLayout(new GridLayout(8, 1, 0, 0));
		ImageIcon Img = new ImageIcon("src/css/help.png");
		imgPanel.add(new JLabel(Img));
		JScrollPane scrollPane = new JScrollPane(imgPanel);
		add(scrollPane);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
}
