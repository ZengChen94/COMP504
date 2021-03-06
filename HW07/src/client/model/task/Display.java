package client.model.task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;

import provided.client.model.taskUtils.ITaskFactory;
import provided.compute.ILocalTaskViewAdapter;
import provided.compute.ITask;
import provided.compute.ITaskResultFormatter;

/**
 * Task that display input
 * @author Zhenwei Feng
 * @author Chen Zeng
 * @version 1.0
 */
public class Display implements ITask<String> {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7232620032428893430L;

	private transient ILocalTaskViewAdapter taskView = ILocalTaskViewAdapter.DEFAULT_ADAPTER;
	/**
	 * Input
	 */
	private String input = "";

	/**
	 * Constructor
	 */
	public Display(String input) {
		this.input = input;
	}

	/**
	 * This method execute task
	 * @return result The result of the task
	 */
	@Override
	public String execute() throws RemoteException {
		taskView.append("Display task called with input = " + input + "\n");
		return input;
	}

	/**
	 * This method initialize adapter
	 * @param viewAdapter Adapter of client stub
	 */
	@Override
	public void setTaskViewAdapter(ILocalTaskViewAdapter viewAdapter) {
		this.taskView = viewAdapter;
		viewAdapter.append("Display installed!\n");
		System.out.println("Display.setTaskViewAdapter called.\nDisplay = " + input + "\n");
	}

	/**
	 * This method return result
	 * @return result The result of the task
	 */
	@Override
	public ITaskResultFormatter<String> getFormatter() {
		return new ITaskResultFormatter<String>() {
			public String format(String result) {
				return "Display: System properties = " + result + "  (input = " + input + ")";
			}
		};
	}

	/**
	 * An ITaskFactory for this task
	 */
	public static final ITaskFactory<String> FACTORY = new ITaskFactory<String>() {

		@Override
		public ITask<String> make(String param) {
			return new Display(param);
		}

		@Override
		public String toString() {
			return Display.class.getName();
		}
	};

	/**
	 * Reinitializes transient fields upon deserialization.  See the 
	 * a href="http://download.oracle.com/javase/6/docs/api/java/io/Serializable.html"
	 * Serializable marker interface docs.
	 * taskView is set to a default value for now (ILocalTaskViewAdapter.DEFAULT_ADAPTER).
	 * @param stream The object stream with the serialized data
	 * @throws IOException if the input stream cannot be read correctly
	 * @throws ClassNotFoundException if the class file associated with the input stream cannot be located.
	 */
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject(); // Deserialize the non-transient data
		taskView = ILocalTaskViewAdapter.DEFAULT_ADAPTER; // re-initialize the transient field
	}
}
