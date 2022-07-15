package screen.dialog;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuitDialog extends JDialog{
	// Create confirmation dialog
	public QuitDialog() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JPanel labels = new JPanel();
		labels.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel q = new JLabel("Do you want to quit?");
		labels.add(q);
		p.add(labels);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		
		JButton yes = new JButton("Yes");
		yes.addActionListener(new Yes());
		buttons.add(yes);
		JButton no = new JButton("No");
		no.addActionListener(new No());
		buttons.add(no);
		
		p.add(buttons);
		add(p);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(200, 100);
		setVisible(true);
	}
	
	private class No implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();			
		}
	}
	private class Yes implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);			
		}
	}
}
