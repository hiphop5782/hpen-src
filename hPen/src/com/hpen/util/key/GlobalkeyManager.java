package com.hpen.util.key;

import java.io.IOException;

import com.hakademy.utility.magnify.MagnificationManager;
import com.hpen.draw.ui.CaptureFrame;
import com.hpen.draw.ui.DrawingFrame;
import com.hpen.livezoom.ui.ZoomFrame;
import com.tulskiy.keymaster.common.Provider;

public class GlobalkeyManager {
	static Process magnify_proc;
//	static class MagnifyThread extends Thread{
//		public MagnifyThread() {
//			setDaemon(true);
//		}
//		public void run() {
//			MagnificationManager.getManager().start();
//		}
//	}
//	static Thread magnify_thread;
	static boolean magnify = false;
	static {
		Provider provider = Provider.getCurrentProvider(false);
		provider.register(KeyManager.alt1, e->{
			CaptureFrame.start();
		});
		provider.register(KeyManager.alt2, e->{
			DrawingFrame.start();
		});
		provider.register(KeyManager.alt3, e->{
			ZoomFrame.start();
		});
		provider.register(KeyManager.alt4, e->{
			if(!magnify) {
				MagnificationManager.getManager().start();
			}
			else {
				MagnificationManager.getManager().stop();
			}
		});
		provider.register(KeyManager.alt5, e->{
			if(magnify_proc == null) {
				String command = System.getenv("windir")+"\\system32\\magnify.exe";
				ProcessBuilder builder = new ProcessBuilder(new String[] {"cmd.exe", "/C", command});
				try {
					magnify_proc = builder.start();
				}catch(IOException err) {
					magnify_proc = null;
				}
			}else {
				ProcessBuilder builder = new ProcessBuilder(new String[] {"tskill", "/a", "magnify"});
				try {
					builder.start();
				}catch(Exception err) {}
				magnify_proc = null;
			}
		});
	}
}
