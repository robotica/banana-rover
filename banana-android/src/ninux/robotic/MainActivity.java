package ninux.robotic;

import ninux.BTSend;
import ninux.ThreadAlex;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private final static String TAG = "Alex";
    public final static String EXTRA_MESSAGE = "com.example.miaprimaapp.MESSAGE";
	public static final String MESSAGE_CONTENT = "String_message";
	public static UIMessageHandler mUIMessageHandler;
    public ThreadAlex alex = null;
	private TextView textView;
	
	public static enum CONN_TYPE {
		LEJOS_PACKET, LEGO_LCP 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUIMessageHandler = new UIMessageHandler();
		setContentView(R.layout.activity_rob_alex);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_rob_alex, menu);
		return true;
	}
	public static NXTConnector connect(final CONN_TYPE connection_type) {
		Log.d(TAG, " about to add LEJOS listener ");

		NXTConnector conn = new NXTConnector();
		conn.setDebug(true);
		conn.addLogListener(new NXTCommLogListener() {

			public void logEvent(String arg0) {
				Log.e(TAG + " NXJ log:", arg0);
			}

			public void logEvent(Throwable arg0) {
				Log.e(TAG + " NXJ log:", arg0.getMessage(), arg0);
			}
		});

		switch (connection_type) {
			case LEGO_LCP:
				conn.connectTo("btspp://Alex", NXTComm.LCP);
				break;
			case LEJOS_PACKET:
				conn.connectTo("btspp://");
				break;
		}

		return conn;

	}

	public static void sendMessageToUIThread(String message) {
		Bundle b = new Bundle();
		b.putString(MESSAGE_CONTENT, message);
		Message message_holder = new Message();
		message_holder.setData(b);
		mUIMessageHandler.sendMessage(message_holder);
	}

	public void comandaAlex(View view) {
		textView = (TextView) findViewById(R.id.messaggio);
		try {
			textView.setText("avviato");
			ThreadAlex.avviato=true;
			if (alex==null) {
				Log.e(TAG, "Spedito 333333");
				alex = new ThreadAlex();
				alex.start();
			}
		} catch (Exception e) {
			Log.e(TAG, "Mancato avvio ComandaAlex:" + e.getMessage(), e);
		}
	}
	public void stopAlex(View view) {
		textView = (TextView) findViewById(R.id.messaggio);
		try {
			textView.setText("stop");
			if (alex!=null) {
				ThreadAlex.avviato=false;
				alex=null;
			}
		} catch (Exception e) {
			Log.e(TAG, "Mancato avvio ComandaAlex:" + e.getMessage(), e);
		}
	}
	public void sendAlex(View view) {
		textView = (TextView) findViewById(R.id.messaggio);
			(new BTSend()).start();
	}
	class UIMessageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Log.e(TAG, "Spedito 222222");
			textView.setText((String) msg.getData().get(MESSAGE_CONTENT));
		}

	}
}
