package ninux;

import java.io.IOException;

import ninux.robotic.MainActivity;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.remote.NXTCommand;
import lejos.pc.comm.NXTConnector;
import android.util.Log;

public class ThreadAlex extends Thread {
	protected static final String TAG = "ThreadAlex";
	NXTConnector conn;
	public static boolean avviato=true;
	public ThreadAlex() {

	}

	public void closeConnection() {
		try {
			Thread.yield();
			conn.getNXTComm().close();
	   } catch (Exception e) {
		} finally {

			conn = null;
		}

	}

	@Override
	public void run() {
		Motor.C.setRegulationMode(NXTCommand.REGULATION_MODE_MOTOR_SYNC);
		Motor.B.setRegulationMode(NXTCommand.REGULATION_MODE_MOTOR_SYNC);
		setName(TAG + " thread");
		conn = MainActivity.connect(MainActivity.CONN_TYPE.LEGO_LCP);
		Motor.B.forward();
		Motor.C.forward();
		while (avviato) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Log.e(TAG, "Thread.sleep error", e);
				avviato=false;
				closeConnection();
			}
		}
		Motor.B.stop();
		Motor.C.stop();
		Sound.buzz();

		if (conn != null) {
			try {
				conn.close();
			} catch (IOException e) {
				Log.e(TAG, "Error closing connection", e);
			}
		}
		closeConnection();
		Log.e(TAG, "Tacho Count finished it's run");
	}

}
