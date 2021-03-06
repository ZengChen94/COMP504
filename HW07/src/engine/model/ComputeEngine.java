package engine.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;

import provided.compute.ICompute;
import provided.compute.ILocalTaskViewAdapter;
import provided.compute.IRemoteTaskViewAdapter;
import provided.compute.ITask;

/**
 * The ComputeEngine which implements ICompute
 *
 * @author Zhenwei Feng
 * @author Chen Zeng
 * @version 1.0
 *
 */

public class ComputeEngine implements ICompute {
	private Consumer<String> _viewCmd;
	/**
	 * Remote task adapter stub
	 */
	private IRemoteTaskViewAdapter _clientTaskViewAdpt = null;
	/**
	 * Local task adapter stub
	 */
	private ILocalTaskViewAdapter localTaskViewAdpt = new ILocalTaskViewAdapter() {
		public void append(String s) {
			_viewCmd.accept(s);
		}
	};

	/**
	 * Constructor of ComputeEngine
	 */
	public ComputeEngine(Consumer<String> cmd) {
		this._viewCmd = cmd;
	}

	/**
	 * This method executes tasks
	 * @param t Type of task to be executed
	 * @return result The result of task executing
	 * @throws RemoteException if a network error occurs. 
	 */
	@Override
	public <T> T executeTask(ITask<T> t) throws RemoteException {
		try {
			t.setTaskViewAdapter(localTaskViewAdpt);
			_viewCmd.accept("Executing task: " + t + "\n");
			_clientTaskViewAdpt.append("Executing task: " + t + "\n");
			T result = t.execute();
			_viewCmd.accept("Task result: " + result + "\n");
			return result;
		} 
		catch (Exception e) {
			_viewCmd.accept("Error: " + e + "\n");
			return null;
		}
	}

	/**
	 * Sets this object's IRemoteTaskViewAdapter stub so that it can print strings out on the client's user interface.
	 * @param clientTVA  the task view adapter STUB to for the server to use.
	 * @return An adapter STUB to the ICompute server's view.
	 * @throws RemoteException if a network error occurs. 
	 */
	@Override
	public IRemoteTaskViewAdapter setTextAdapter(IRemoteTaskViewAdapter clientTVAStub) throws RemoteException {
		_clientTaskViewAdpt = clientTVAStub;
		_viewCmd.accept("Got IRemoteTaskViewAdapter stub from client: " + clientTVAStub + "\n");
		IRemoteTaskViewAdapter receiveTextAdapter = new IRemoteTaskViewAdapter() {
			public void append(String s) {
				_viewCmd.accept("[Chen@Client] " + s + "\n");
			}
		};
		try {
			IRemoteTaskViewAdapter remoteTVAStub = (IRemoteTaskViewAdapter) UnicastRemoteObject
					.exportObject(receiveTextAdapter, IRemoteTaskViewAdapter.BOUND_PORT_SERVER);
			return remoteTVAStub;
		} 
		catch (Exception e) {
			_viewCmd.accept("Error in ComputeEngine.setTextAdapter: " + e + "\n");
			System.err.println("ComputeEngine.setTextAdapter: " + e + "\n");
			return null;
		}
	}

	/**
	 * Sending text to the client
	 * @param text The contents of text
	 * @throws RemoteException if a network error occurs. 
	 */
	public void sendMsgToClient(String text) {
		if (_clientTaskViewAdpt != null) {
			_viewCmd.accept("Sending msg to connected remote client: " + text + "\n");
			try {
				_clientTaskViewAdpt.append(text+"\n");
			} 
			catch (RemoteException e) {
				_viewCmd.accept("Error: " + e + "\n");
				e.printStackTrace();
			}
		} 
		else {
			_viewCmd.accept("Error: _clientTaskViewAdpt is null \n");
		}
	}
}
