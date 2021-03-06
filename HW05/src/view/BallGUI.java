package view;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

/**
 * The view class for the ball GUI
 *
 * @author Ye Wang
 * @author Chen Zeng
 * @version 1.0
 * @param <TDropListItem> 
 * @param <TPaintDropList> 
 *
 */
public class BallGUI<TDropListItem, TPaintDropList> extends JFrame {

	/**
	 * The serial version ID
	 */
	private static final long serialVersionUID = -1404940077024627488L;

	/**
	 * The model adapter is initialized to a no-op to insure that system always has well-defined behavior
	 * This part is from https://canvas.rice.edu/courses/6768/external_tools/1599
	 */
	private IModelUpdateAdapter _iModelUpdateAdapter = IModelUpdateAdapter.NULL_OBJECT;

	/**
	 * The model adapter is initialized to a no-op to insure that system always has well-defined behavior
	 * This part is from https://canvas.rice.edu/courses/6768/external_tools/1599
	 */
	private IModelControlAdapter<TDropListItem, TPaintDropList> _IModelControlAdapter;

	/**
	 * The main Panel of this BallGUI
	 */
	private JPanel contentPane;

	/**
	 * The text field for ball type.
	 */
	private JTextField ballText = new JTextField("Straight");

	/**
	 * The control panel.
	 */
	private JPanel pnlCntl = new JPanel();

	/**
	 * button for adding balls
	 */
	private JButton btnAddBall = new JButton("Make Ball");

	/**
	 * Clear all the balls
	 */
	JButton btnCBall = new JButton("Clear");

	/**
	 * The top drop list, used to select what strategy to use in a new ball and
	 * to switch the switcher to.
	 */
	private JComboBox<TDropListItem> _list1DL = new JComboBox<TDropListItem>();

	/**
	 * Bottom drop list, used for combining with the top list selection.
	 */

	private JComboBox<TDropListItem> _list2DL = new JComboBox<TDropListItem>();

	/**
	 * The button add new strategy to the droplists.
	 */
	private final JButton _addbtmlist = new JButton("Add strategy");

	/**
	 * Combine the strategies
	 */
	private final JButton btnCombine = new JButton("Combine");

	/**
	 * switch the balls
	 */
	private final JButton btnSwitchmaker = new JButton("Make Switcher");

	/**
	 * The button for switching the strategy
	 */
	private final JButton btnSwitch = new JButton("Switch");

	/**
	 * Starts the already initialized frame, making it
	 * visible and ready to interact with the user.
	 */

	private JPanel pnlCanvas = new JPanel() {

		private static final long serialVersionUID = -6952656931251224807L;

		/**
		 * The serialVersionUID of this canvas panel.
		 */

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			_iModelUpdateAdapter.paint(g);
		}

	};

	// HW4 GUI

	/**
	 * btn for adding paint 
	 */
	private final JButton btnAddPaint = new JButton("Add Paint");

	/**
	 * The text field for paint type.
	 */
	private JTextField paintTxt = new JTextField("Ball");

	/**
	 * paint droplist .
	 */
	private final JComboBox<TPaintDropList> PaintDroplist = new JComboBox<TPaintDropList>();
	private final JPanel panel2 = new JPanel();
	private final JPanel panel1 = new JPanel();
	private final JPanel panel3 = new JPanel();
	private final JPanel panel4 = new JPanel();
	private final JPanel panel5 = new JPanel();

	/**
	 * to start
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * The method to repaint the canvas and update the view
	 */
	public void update() {
		pnlCanvas.repaint();
	}

	/**
	 * The method to get the Component of the canvas panel
	 * @return The canvas panel
	 */
	public Component getCanvas() {
		return pnlCanvas;
	}

	/**
	 * BallGUI Constructor
	 * @param _iModelControlAdapter From view to model control adapter
	 * @param _iModelUpdateAdapter From view to model paint adapter
	 */
	public BallGUI(IModelControlAdapter<TDropListItem, TPaintDropList> _iModelControlAdapter,
			IModelUpdateAdapter _iModelUpdateAdapter) {
		this._IModelControlAdapter = _iModelControlAdapter;
		this._iModelUpdateAdapter = _iModelUpdateAdapter;
		initGUI();
	}

	/**
	 * Initialize the GUI components but do not start the frame.
	 * This method could be public if desired.
	 */
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 950);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setToolTipText("content");
		setContentPane(contentPane);
		FlowLayout flowLayout = (FlowLayout) pnlCntl.getLayout();
		flowLayout.setVgap(30);
		pnlCntl.setBackground(new Color(0, 255, 127));

		pnlCntl.setToolTipText("Control Panel"); //Control panel.
		contentPane.add(pnlCntl, BorderLayout.NORTH);

		pnlCntl.add(panel1);
		panel1.setLayout(new GridLayout(0, 1, 0, 0));
		panel1.add(ballText);
		ballText.setToolTipText("Enter the type of ball you want.");
		ballText.setColumns(10);
		ballText.setColumns(10);
		panel1.add(_addbtmlist);

		_addbtmlist.setToolTipText("Add new strategy to the droplists"); //Button to add strategy.
		_addbtmlist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TDropListItem o = _IModelControlAdapter.addStrategy(ballText.getText());
				if (null == o)
					return;

				_list1DL.insertItemAt(o, 0);
				_list2DL.insertItemAt(o, 0);
			}
		});

		pnlCntl.add(panel2);
		panel2.setLayout(new GridLayout(0, 1, 0, 0));
		panel2.add(btnAddBall);

		btnAddBall.setToolTipText("Button to add a new ball in selected strategy."); //Button to add a ball.
		btnAddBall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_IModelControlAdapter.makeBall(_list1DL.getItemAt(_list1DL.getSelectedIndex()),
						PaintDroplist.getItemAt(PaintDroplist.getSelectedIndex()));
			}
		});
		panel2.add(_list1DL);

		_list1DL.setToolTipText("strategy list 1");
		_list1DL.setPreferredSize(new Dimension(100, 30));
		panel2.add(_list2DL);
		_list2DL.setToolTipText("strategy list 2");
		_list2DL.setPreferredSize(new Dimension(100, 30));
		panel2.add(btnCombine);

		btnCombine.setToolTipText("Button to combine two strategies in the lists");

		btnCombine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TDropListItem o = _IModelControlAdapter.combineStrategies(
						_list1DL.getItemAt(_list1DL.getSelectedIndex()),
						_list2DL.getItemAt(_list2DL.getSelectedIndex()));
				if (null == o)
					return;
				_list1DL.insertItemAt(o, 0);
				_list2DL.insertItemAt(o, 0);
			}
		});

		pnlCntl.add(panel3);
		panel3.setLayout(new GridLayout(0, 1, 0, 0));
		panel3.add(btnSwitchmaker);
		btnSwitchmaker.setToolTipText("Button to make a switcher ball");
		panel3.add(btnSwitch);
		btnSwitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_IModelControlAdapter.switchStrategy(_list1DL.getItemAt(_list1DL.getSelectedIndex()));
			}
		});
		btnSwitch.setToolTipText("Button to swtich swithers.");
		btnSwitchmaker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_IModelControlAdapter.makeSwitcher(PaintDroplist.getItemAt(PaintDroplist.getSelectedIndex()));
			}
		});

		pnlCntl.add(panel4);
		panel4.setLayout(new GridLayout(0, 1, 0, 0));
		panel4.add(btnCBall);
		btnCBall.setToolTipText("clear all the balls");
		btnCBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				_IModelControlAdapter.clear();
			}
		});

		pnlCntl.add(panel5);
		panel5.setLayout(new GridLayout(0, 1, 0, 0));
		panel5.add(paintTxt);

		paintTxt.setColumns(10);
		paintTxt.setColumns(10);
		paintTxt.setToolTipText("the text field for adding strategy");
		panel5.add(btnAddPaint);
		btnAddPaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TPaintDropList o = _IModelControlAdapter.addPaint(paintTxt.getText());
				if (null == o)
					return;
				PaintDroplist.insertItemAt(o, 0);
			}
		});
		btnAddPaint.setToolTipText("add the paint strategy");
		panel5.add(PaintDroplist);
		PaintDroplist.setPreferredSize(new Dimension(70, 30));
		PaintDroplist.setToolTipText("the droplist for adding paint strategy");

		/*
		 * The panel for paint
		 */
		pnlCanvas.setBackground(new Color(224, 255, 255));
		pnlCanvas.setToolTipText("panel for painting");
		contentPane.add(pnlCanvas, BorderLayout.CENTER);

	}

}
