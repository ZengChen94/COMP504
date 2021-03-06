package client.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The view class for the Client
 *
 * @author Zhenwei Feng
 * @author Chen Zeng
 * @version 1.0
 * @param <TDropListItem> Type of JComboBox 
 *
 */

public class ClientView<TDropListItem> extends JFrame {

	/**
	 * The serial version ID
	 */
	private static final long serialVersionUID = 8672420403620843195L;

	/**
	 * Components
	 */
	private JPanel contentPane;
	private JTextField txtRemotehost;
	private JTextField txtTaskInput;
	private JTextField txtMsg;
	private JTextField txtParameter;
	private JComboBox<TDropListItem> comboBox_1 = new JComboBox<TDropListItem>();
	private JComboBox<TDropListItem> comboBox_2 = new JComboBox<TDropListItem>();

	JTextArea textArea = new JTextArea();

	/**
	 * Adapter
	 */
	private IClientModelAdapter<TDropListItem> _modelAdapt;

	//	/**
	//	 * Launch the application.
	//	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					ClientView frame = new ClientView();
	//					frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Start the app
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * Constructor of ClientView
	 * @param modelAdpt The ModelAdapter of client
	 */
	public ClientView(IClientModelAdapter<TDropListItem> modelAdpt) {
		super("Client GUI");
		this._modelAdapt = modelAdpt;
		initGUI();
	}

	/**
	 * Create the frame.
	 */
	public void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel_top = new JPanel();
		contentPane.add(panel_top, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		panel_top.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnQuit = new JButton("Quit");
		btnQuit.setToolTipText("Shutdown the RMI system and quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("quitBtn.actionPerformed, event=" + e);
				_modelAdapt.quit();
			}
		});
		panel_1.add(btnQuit);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Remote Host", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_top.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		txtRemotehost = new JTextField();
		txtRemotehost.setText("127.0.0.1");
		panel_2.add(txtRemotehost);
		txtRemotehost.setColumns(10);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				append("Connecting...\n");
				append(_modelAdapt.connectTo(txtRemotehost.getText()) + "\n");
			}
		});
		panel_2.add(btnConnect);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Send msg to remote host's view", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_top.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));

		txtMsg = new JTextField();
		txtMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_modelAdapt.sendMsg(txtMsg.getText());
			}
		});
		panel_3.add(txtMsg);
		txtMsg.setText("Hit Enter to send msg...");
		txtMsg.setToolTipText("Hit Enter to send the string to the remote Compute Engine to be displayed on its view");
		txtMsg.setColumns(20);

		JPanel panel_4 = new JPanel();
		panel_top.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));

		txtTaskInput = new JTextField();
		panel_4.add(txtTaskInput);
		txtTaskInput.setColumns(10);

		JButton btnAddToLists = new JButton("Add to lists");
		btnAddToLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//				System.out.println("btnAddToLists.actionPerformed");
				String task = txtTaskInput.getText();
				TDropListItem o = _modelAdapt.addTask(task);
				if (null == o)
					return; // just in case
				comboBox_1.insertItemAt(o, 0);
				comboBox_2.insertItemAt(o, 0);
			}
		});
		panel_4.add(btnAddToLists);

		TDropListItem o = _modelAdapt.addTask("Pi2");
		comboBox_1.insertItemAt(o, 0);
		comboBox_2.insertItemAt(o, 0);
		
		o = _modelAdapt.addTask("GetSquare");
		comboBox_1.insertItemAt(o, 0);
		comboBox_2.insertItemAt(o, 0);

		JPanel panel_5 = new JPanel();
		panel_top.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnRunTask = new JButton("Run Task");
		btnRunTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int item_pos = comboBox_1.getSelectedIndex();
				_modelAdapt.runTask(comboBox_1.getItemAt(item_pos), txtParameter.getText());
			}
		});
		panel_5.add(btnRunTask);

		panel_5.add(comboBox_1);

		panel_5.add(comboBox_2);

		JButton btnCombineTasks = new JButton("Combine Tasks");
		btnCombineTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int item_pos1 = comboBox_1.getSelectedIndex();
				int item_pos2 = comboBox_2.getSelectedIndex();
				TDropListItem o = _modelAdapt.combineTask(comboBox_1.getItemAt(item_pos1), comboBox_2.getItemAt(item_pos2));
				if (null == o)
					return; // just in case
				comboBox_1.insertItemAt(o, 0);
				comboBox_2.insertItemAt(o, 0);
			}
		});
		panel_5.add(btnCombineTasks);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "Run Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_top.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));

		txtParameter = new JTextField();
		panel_6.add(txtParameter);
		txtParameter.setText("5");
		txtParameter.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		scrollPane.setViewportView(textArea);
	}

	/**
	 * Append message to textArea
	 * @param s Message to be appended
	 */
	public void append(String s) {
		textArea.append(s);
		textArea.setCaretPosition(textArea.getText().length());
	}

	/**
	 * Set the remote host address if it's detectable
	 * @param hostAddress Remote host address
	 */
	public void setRemoteHost(String hostAddress) {
		// TODO Auto-generated method stub
		txtRemotehost.setText(hostAddress);
	}

}
