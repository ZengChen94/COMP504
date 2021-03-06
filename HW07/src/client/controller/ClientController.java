package client.controller;

import java.awt.EventQueue;

import client.model.ClientModel;
import client.model.IClientViewAdapter;
import provided.client.model.taskUtils.ITaskFactory;
import provided.client.model.taskUtils.SingletonTaskFactoryLoader;
import client.view.ClientView;
import client.view.IClientModelAdapter;

/**
 * 
 * MVC Controller for the app
 * Contain the main() method. 
 * @author Zhenwei Feng
 * @author Chen Zeng
 * @version 1.0
 *
 */
public class ClientController {
	/**
	 * The View of this Controller
	 */
	private ClientView<ITaskFactory<?>> _view;
	/**
	 * The Model of this Controller
	 */
	private ClientModel _model;

	/**
	 * The Constructor of this Controller
	 */
	public ClientController() {

		_model = new ClientModel(new IClientViewAdapter() {
			@Override
			public void append(String s) {
				_view.append(s);
			}

			@Override
			public void setRemoteHost(String hostAddress) {
				_view.setRemoteHost(hostAddress);
			}
		});

		_view = new ClientView<ITaskFactory<?>>(new IClientModelAdapter<ITaskFactory<?>>() {
			@Override
			public String connectTo(String remoteHost) {
				return _model.connectTo(remoteHost);
			}

			@Override
			public void quit() {
				_model.stop();
			}

			@Override
			public void sendMsg(String text) {
				_model.sendMsgToComputeEngine(text);
			}

			@Override
			public ITaskFactory<?> addTask(String text) {
				text = "client.model.task." + text;
				System.out.println(text);
				System.out.println(SingletonTaskFactoryLoader.SINGLETON.load(text));
				return SingletonTaskFactoryLoader.SINGLETON.load(text);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public void runTask(ITaskFactory task, String parameter) {
				@SuppressWarnings("unchecked")
				String result = _model.runTask(task.make(parameter));
				System.out.print(result);
			}

			@Override
			public ITaskFactory<?> combineTask(ITaskFactory<?> task1, ITaskFactory<?> task2) {
				return _model.combineTask(task1, task2);
			}

		});

	}

	/**
	 * Start the app
	 */
	public void start() {
		_view.start();
		_model.start();
	}

	/**
	 * Controller Main function
	 * @param args Arguments by default
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientController clientController = new ClientController();
					clientController.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
