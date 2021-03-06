package client.model.task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;

import provided.client.model.taskUtils.ITaskFactory;
import provided.compute.ILocalTaskViewAdapter;
import provided.compute.ITask;
import provided.compute.ITaskResultFormatter;

/**
 * Task that calculates sqrt
 * @author Zhenwei Feng
 * @author Chen Zeng
 * @version 1.0
 */
public class GetSqrt implements ITask<Double> {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	private transient ILocalTaskViewAdapter taskView = ILocalTaskViewAdapter.DEFAULT_ADAPTER;
	/**
	 * Input
	 */
	private int input = 5;
	/**
	 * Output result
	 */
	private double result;

	/**
	 * Constructor
	 */
	public GetSqrt(String input) {
		this.input = Integer.valueOf(input);
		taskView.append("GetSqrt constructing...");
	}

	/**
	 * This method execute task
	 * @return result The result of the task
	 */
	@Override
	public Double execute() throws RemoteException {
		taskView.append("Executing GetSqrt task with " + input + "\n");
		System.out.println("GetSqrt is executing");
		this.result = Math.sqrt((double) this.input);
		return result;
	}

	/**
	 * This method initialize adapter
	 * @param viewAdapter Adapter of client stub
	 */
	@Override
	public void setTaskViewAdapter(ILocalTaskViewAdapter viewAdapter) {
		this.taskView = viewAdapter;
		viewAdapter.append("GetSqrt installed!\n");
		System.out.println("GetSqrt.setTaskViewAdapter called with input: " + this.input + ".\n");
	}

	/**
	 * This method return result
	 * @return result The result of the task
	 */
	@Override
	public ITaskResultFormatter<Double> getFormatter() {
		return new ITaskResultFormatter<Double>() {
			public String format(Double result) {
				return "GetSqrt: System properties = " + result + "  (input = " + input + ")";
			}
		};
	}

	/**
	 * An ITaskFactory for this task
	 */
	public static final ITaskFactory<Double> FACTORY = new ITaskFactory<Double>() {

		@Override
		public ITask<Double> make(String param) {
			return new GetSqrt(param);
		}

		@Override
		public String toString() {
			return GetSqrt.class.getName();
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
