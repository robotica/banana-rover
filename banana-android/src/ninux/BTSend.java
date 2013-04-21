package ninux;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ninux.robotic.MainActivity;
import ninux.robotic.MainActivity.CONN_TYPE;
import lejos.pc.comm.NXTConnector;
import android.util.Log;

public class BTSend extends Thread {
	static final String TAG = "BTSend";
	private NXTConnector conn;
	DataOutputStream dos;
	DataInputStream dis;
	public BTSend() {
		super();
	}

	public void closeConnection() {
		try {
			Log.d(TAG, "BTSend run loop finished and closing");

			dis.close();
			dos.close();
			conn.getNXTComm().close();
		} catch (Exception e) {
		} finally {
			dis = null;
			dos = null;
			conn = null;
		}
	}

	@Override
	public void run() {
		Log.d(TAG, "BTSend run");
		
		conn = MainActivity.connect(CONN_TYPE.LEJOS_PACKET);

		dos = new DataOutputStream(conn.getOutputStream());
		dis = new DataInputStream(conn.getInputStream());
		int x;
		for (int i = 0; i < 100; i++) {

			try {
				dos.writeInt((i * 30000));
				dos.flush();
				yield();
				x = dis.readInt();
				Log.d(TAG, "spedito:" + i * 30000 + " ricevuto:" + x);
				MainActivity.sendMessageToUIThread("spedito:" + i * 30000 + " ricevuto:" + x);
				yield();
			} catch (IOException e) {
				Log.e(TAG, "Error ... ", e);

			}

		}

		closeConnection();
		MainActivity.sendMessageToUIThread("");//clear 
	}

}
