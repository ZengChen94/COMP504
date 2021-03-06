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
import java.awt.SystemColor;

import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import java.awt.Color;
import java.awt.FlowLayout;

/**
 * The view class for the ball GUI
 *
 * @author Yue Pan
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
	 * left split panel for Adding Strategy
	 */
	private final JSplitPane splitPaneLeft = new JSplitPane();

	/**
	 * mid split panel for Droplists
	 */
	private final JSplitPane splitPanemid = new JSplitPane();

	/**
	 * right Split panel for btn to make swicher
	 */
	private final JSplitPane splitPaneRight = new JSplitPane();

	/**
	 * btn Split panel for btn 
	 */
	private final JSplitPane splitPanebtn = new JSplitPane();

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
	private JTextField paintTxt = new JTextField("Square");

	/**
	 * paint droplist .
	 */
	private final JComboBox<TPaintDropList> PaintDroplist = new JComboBox<TPaintDropList>();

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
		pnlCntl.setBackground(Color.GREEN);

		pnlCntl.setToolTipText("Control Panel"); //Control panel.
		contentPane.add(pnlCntl, BorderLayout.NORTH);

		splitPaneLeft.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneLeft.setDividerLocation(1.0 / 5.0);
		splitPaneLeft.setToolTipText("Left split panel for adding strategy");
		pnlCntl.add(splitPaneLeft);
		splitPaneLeft.setLeftComponent(ballText); //Add strategy text field.
		ballText.setToolTipText("Enter the type of ball you want.");
		ballText.setColumns(10);
		ballText.setColumns(10);
		splitPaneLeft.setRightComponent(_addbtmlist);

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

		btnAddBall.setToolTipText("Button to add a new ball in selected strategy."); //Button to add a ball.

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
		btnAddBall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_IModelControlAdapter.makeBall(_list1DL.getItemAt(_list1DL.getSelectedIndex()),
						PaintDroplist.getItemAt(PaintDroplist.getSelectedIndex()));
			}
		});
		splitPanebtn.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPanebtn.setDividerLocation(1.0 / 5.0);
		splitPanebtn.setToolTipText("pane for btn");
		splitPanebtn.setLeftComponent(btnCombine);
		splitPanebtn.setRightComponent(btnAddBall);
		pnlCntl.add(splitPanebtn);

		splitPanemid.setToolTipText("mid split panel for droplist");
		splitPanemid.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPanemid.setDividerLocation(1.0 / 5.0);
		pnlCntl.add(splitPanemid);

		_list1DL.setToolTipText("strategy list 1");
		_list2DL.setToolTipText("strategy list 2");
		_list1DL.setPreferredSize(new Dimension(100, 30));
		_list2DL.setPreferredSize(new Dimension(100, 30));
		splitPanemid.setLeftComponent(_list1DL);
		splitPanemid.setRightComponent(_list2DL);

		splitPaneRight.setToolTipText("Swticher Part Split Panel.");
		splitPaneRight.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneRight.setDividerLocation(1.0 / 5.0);
		pnlCntl.add(splitPaneRight);
		splitPaneRight.setLeftComponent(btnSwitchmaker);
		btnSwitchmaker.setToolTipText("Button to make a switcher ball");
		btnSwitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_IModelControlAdapter.switchStrategy(_list1DL.getItemAt(_list1DL.getSelectedIndex()));
			}
		});
		splitPaneRight.setRightComponent(btnSwitch);
		btnSwitch.setToolTipText("Button to swtich swithers.");
		btnSwitchmaker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_IModelControlAdapter.makeSwitcher(PaintDroplist.getItemAt(PaintDroplist.getSelectedIndex()));
			}
		});
		PaintDroplist.setPreferredSize(new Dimension(70, 30));
		btnCBall.setToolTipText("clear all the balls");
		pnlCntl.add(btnCBall);
				pnlCntl.add(PaintDroplist);
				PaintDroplist.setToolTipText("the droplist for adding paint strategy");
				pnlCntl.add(btnAddPaint);
				btnAddPaint.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TPaintDropList o = _IModelControlAdapter.addPaint(paintTxt.getText());
						if (null == o)
							return;
						PaintDroplist.insertItemAt(o, 0);
					}
				});
				btnAddPaint.setToolTipText("add the paint strategy");
				pnlCntl.add(paintTxt);
		
				paintTxt.setColumns(10);
				paintTxt.setColumns(10);
				paintTxt.setToolTipText("the text field for adding strategy");
		btnCBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				_IModelControlAdapter.clear();
			}
		});

		/*
		 * The panel for paint
		 */
		pnlCanvas.setBackground(SystemColor.inactiveCaptionBorder);
		pnlCanvas.setToolTipText("panel for painting");
		contentPane.add(pnlCanvas, BorderLayout.CENTER);

	}

}
