package screen.dialog;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageDialog extends JDialog {
	// Create dialog to notify error
	public MessageDialog(String... messages) {
		int max = 0;
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		for (String m : messages) {
			p.add(new JLabel(m));
			if (m.length() > max) {
				max = m.length();
			}
		}
		
		add(p);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(max*8, 50*messages.length);
		setVisible(true);
	}
}
