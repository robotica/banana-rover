package ninux;

import java.io.IOException;

import ninux.robotic.MainActivity;



import android.os.Looper;
import android.util.Log;

public class TachoCount extends Thread {
	protected static final String TAG = "TachoCount";

	public TachoCount() {

	}

	public void closeConnection() {
	}

	@Override
	public void run() {
		setName(TAG + " thread");
		Looper.prepare();
		Log.e(TAG, "bbbbbbbbbbbbbbbb");
		try {
			for (int i=0;i<10;i++) {
				Log.e(TAG, "i="+i);
				MainActivity.sendMessageToUIThread("i="+i);
			Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			Log.e(TAG, "Thread.sleep error", e);
		}
//		MainActivity.sendMessageToUIThread("");
		closeConnection();
		Looper.loop();
		Looper.myLooper().quit();
		Log.e(TAG, "Tacho Count finished it's run");

//		MainActivity.displayToastOnUIThread("Tacho Count finished it's run");
	}

}
