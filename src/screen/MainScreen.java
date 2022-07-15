package screen;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import model.Chromosome;
import model.GAForTSP;
import screen.dialog.MessageDialog;
import screen.dialog.QuitDialog;
import screen.pane.DrawPane;

public class MainScreen extends JFrame {
	private JTextField tfNum, tfLoop;
	private JLabel lblLoop, lblDistanceMin, lblDistance, lblRoute, lblRouteMin;
	private JButton btnLoad, btnRun, btnStop, btnReset;
	private JPanel right, bottom;
	private DrawPane center;
	private GAForTSP algo;
	private Timer t;
	
// VIEW
	public MainScreen() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		//		NORTH
		JMenuBar menubar = new JMenuBar();
		menubar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JMenuItem help = new JMenuItem("Help");
		help.addActionListener(new Help());
		menubar.add(help);
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new Quit());
		menubar.add(quit);
		
		setJMenuBar(menubar);

		//		CENTER
		center = new DrawPane() ;
		center.setSize(new Dimension(800, 600));
		
		cp.add(center, BorderLayout.CENTER);
		
		//		EAST
		right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		JPanel input = new JPanel();
		input.setLayout(new GridLayout(3, 2, 10, 10));
		input.add(new JLabel("Number of cities:"));
		tfNum = new JTextField();
		input.add(tfNum);
		input.add(new JLabel("Number of interations:"));
		tfLoop = new JTextField();
		input.add(tfLoop);
		input.add(new JLabel("Loop:"));
		lblLoop = new JLabel();
		input.add(lblLoop);
		
		btnReset = new JButton("Clear");
		btnReset.addActionListener(new Reset());
		
		btnLoad = new JButton("Load");
		btnLoad.setVisible(false);
		btnLoad.addActionListener(new Load());
		
		btnRun = new JButton("Run");
		btnRun.setVisible(false);
		btnRun.addActionListener(new Run());
		
		btnStop = new JButton("Stop");
		btnStop.setVisible(false);
		btnStop.addActionListener(new Stop());
		
		t = new Timer(100, new Draw());
		
		right.add(input);
		right.add(btnReset);
		right.add(Box.createVerticalGlue());
		right.add(btnLoad);
		right.add(btnRun);
		right.add(btnStop);
		
		cp.add(right, BorderLayout.EAST);
		
		//		SOUTH
		bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		
		JPanel minroute = new JPanel();
		minroute.setLayout(new FlowLayout(FlowLayout.LEFT));
		minroute.add(new JLabel("Min route:"));
		lblRouteMin = new JLabel();
		JScrollPane scrollMin = new JScrollPane(lblRouteMin);
		scrollMin.setPreferredSize(new Dimension(center.getWidth()-200, 40));
		minroute.add(scrollMin);
		minroute.add(new JLabel("Min distance:"));
		lblDistanceMin = new JLabel();
		minroute.add(lblDistanceMin);
		
		JPanel route = new JPanel();
		route.setLayout(new FlowLayout(FlowLayout.LEFT));
		route.add(new JLabel("Current route:"));
		lblRoute = new JLabel();
		JScrollPane scroll = new JScrollPane(lblRoute);
		scroll.setPreferredSize(new Dimension(center.getWidth()-200, 40));
		route.add(scroll);
		route.add(new JLabel("Current distance: "));
		lblDistance = new JLabel();
		route.add(lblDistance);
		bottom.add(minroute);
		bottom.add(route);
		
		cp.add(bottom, BorderLayout.SOUTH);
		
		//		SET UP
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
//	CONTROLLER
	// Controller of help button
	private class Help implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new HelpScreen();
		}
		
	}
	
	// Controller of quit button
	private class Quit implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new QuitDialog();
		}
	}
	
	// Controller of reset button
	private class Reset implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			tfNum.setText("");
			tfNum.setEditable(true);
			tfLoop.setText("");
			tfLoop.setEditable(true);
			lblLoop.setText("");
			lblDistanceMin.setText("");
			lblRoute.setText("");
			lblRouteMin.setText("");
			lblDistance.setText("");
			algo = null;
			center.setPoints(null);
			center.setLines(null);
			center.setBestLines(null);
			center.repaint();
			if (btnRun.getLabel().equals("Continue")) {
				btnRun.setLabel("Run");
			}
			btnLoad.setVisible(true);
			btnRun.setVisible(false);
			btnStop.setVisible(false);
		}
		
	}
	
	// Controller of load button
	private class Load implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int num = Integer.parseInt(tfNum.getText());
				if (num < 0) {
					throw new NegativeInput();
				} else if (num <6) {
					throw new TooSmallInput();
				} else if (num > 500) {
					throw new InputExceedLimit();
				}
				algo = new GAForTSP(num);
				center.setPoints(algo.getPoints());
				center.repaint();
				btnLoad.setVisible(false);
				btnRun.setVisible(true);
				tfNum.setEditable(false);
			} catch (NumberFormatException e1) {
				new MessageDialog("ERROR: invalid data type entered in 'Number of cities'", "Please enter a number");
			} catch (NegativeInput e2) {
				new MessageDialog("ERROR: non-possitive number entered in 'Number of cities'", "Please enter a possitive number");
			} catch (TooSmallInput e3) {
				new MessageDialog("ERROR: number entered in 'Number of cities' is not enough for generate routes'", "Please enter a number greater than 5");
			} catch (InputExceedLimit e4) {
				new MessageDialog("ERROR: number entered in 'Number of cities'", "Please enter a possitive number less than or equals 500");
			} catch (Exception ex) {
				new MessageDialog(ex.getMessage());
			}
		}
	}
	
	// Controller of run button
	private class Run implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {		
			try {
				int interation = Integer.parseInt(tfLoop.getText());
				if (interation < 0) {
					throw new NegativeInput();
				}
				t.start();
				btnRun.setLabel("Run");
				btnStop.setVisible(true);
				tfLoop.setEditable(false);
			} catch (NumberFormatException e1) {
				new MessageDialog("ERROR: invalid data type entered in 'Number of interations'", "Please enter a number");
			} catch (NegativeInput e2) {
				new MessageDialog("ERROR: non-possitive number entered in 'Number of interations'", "Please enter a possitive number");
			} catch (Exception ex) {
				new MessageDialog(ex.getMessage());
			}
		}
	}
	
	private class Draw implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int interation = Integer.parseInt(tfLoop.getText());
			ArrayList<Chromosome> routes = algo.solveNext();
			for (Chromosome route : routes) {
				lblRoute.setText(route.toString());
				lblDistance.setText("" + algo.objectiveFunction(route));
				if (center.getLines() != null) {
					if (algo.objectiveFunction(route) < algo.objectiveFunction(center.getLines())){
						center.setBestLines(route);
					}
				}
				center.setLines(route);
				lblRouteMin.setText(center.getBestLines().toString());
				lblDistanceMin.setText("" + algo.objectiveFunction(center.getBestLines()));
				repaint();
			}
			lblLoop.setText(""+algo.getStep());
			if (algo.getStep() == interation) {
				((Timer)e.getSource()).stop();
				btnRun.setVisible(false);
				btnStop.setVisible(false);
			}
		}
	}
	
	// Controller of stop button
	private class Stop implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			t.stop();
			tfLoop.setEditable(true);
			btnRun.setLabel("Continue");
			btnStop.setVisible(false);
		}
	}
	
//	Exceptions
	private class NegativeInput extends Exception {
	}
	
	private class InputExceedLimit extends Exception {
	}
	private class TooSmallInput extends Exception{
		
	}
	
	public static void main(String args[]) {
		new MainScreen();
	}
}
