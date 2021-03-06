package view;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
//import javax.swing.JLabel;

/**
 * View for the system.
 * 
 * @author Chen Zeng (cz39)
 * @author Rong Hu (rh41)
 * @version 20170908
 * @since 20170908
 * 
 */

public class BallWorldView extends JFrame {

	private static final long serialVersionUID = -6220961448303331834L;

	private JPanel contentPane = new JPanel();

	// Create a special panel with an overridden paintComponent method.
	private JPanel _pnlCanvas = new JPanel() {
		private static final long serialVersionUID = -6952656931251224807L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // clear the panel and redo the background
			_view2ModelPaintAdpt.paintBalls(g); // call back to the model to paint the sprites
		}
	};

	// The model adapter is initialized to a no-op to insure that system always has well-defined behavior
	private IView2ModelCtrlAdapter _view2ModelCtrlAdpt = IView2ModelCtrlAdapter.NULL_OBJECT;
	private IView2ModelPaintAdapter _view2ModelPaintAdpt = IView2ModelPaintAdapter.NULL_OBJECT;
	private JTextField text_select;

	/**
	 * Initial the GUI.
	 */
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		contentPane.add(_pnlCanvas, BorderLayout.CENTER);
		_pnlCanvas.setBackground(new Color(255, 255, 255));

		JPanel panel_top = new JPanel();
		panel_top.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel_top, BorderLayout.NORTH);

		JLabel label_select = new JLabel("Choice (hover on textfield to get choices):");
		panel_top.add(label_select);

		text_select = new JTextField();
		text_select.setText("balls.ColorChangingBall");
		panel_top.add(text_select);
		text_select.setColumns(20);
		String choices = "<html>Choices:<br>ballworld.StraightBall<br>ballworld.CurveBall<br>ballworld.ColorChangingBall<br>ballworld.BreathingBall<br>ballworld.WanderBall<br>ballworld.WanderAndColorChangingBall<br>ballworld.ColorChangingAndBreathingBall<br>ballworld.BreathingAndWanderBall</html>";
		text_select.setToolTipText(choices);

		JButton button_select = new JButton("Select Ball");
		button_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelCtrlAdpt.loadBall(text_select.getText());
			}
		});
		panel_top.add(button_select);

		JButton button_clear = new JButton("Clear All");
		button_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_view2ModelCtrlAdpt.clearBalls();
			}
		});
		panel_top.add(button_clear);
	}

	/**
	 * Get the canvas of GUI.
	 * @return _pnlCanvas Canvas of the GUI.
	 */
	public Component getCanvas() {
		return _pnlCanvas;
	}

	/**
	 * Update the canvas of GUI.
	 */
	public void update() {
		_pnlCanvas.repaint();
	}

	/**
	 * Get the canvas of GUI.
	 * @return _pnlCanvas Canvas of the GUI.
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public BallWorldView(IView2ModelCtrlAdapter _view2ModelCtrlAdpt, IView2ModelPaintAdapter _view2ModelPaintAdpt) {
		setTitle("Bouncing Balls");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		this._view2ModelCtrlAdpt = _view2ModelCtrlAdpt;
		this._view2ModelPaintAdpt = _view2ModelPaintAdpt;
		initGUI();
	}
}
