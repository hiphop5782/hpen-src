package com.hpen.util.key;

import java.io.IOException;

import com.hakademy.utility.magnify.MagnificationManager;
import com.hpen.draw.ui.CaptureFrame;
import com.hpen.draw.ui.DrawingFrame;
import com.hpen.livezoom.ui.ZoomFrame;
import com.hpen.util.MagnifyManager;
import com.tulskiy.keymaster.common.Provider;

public class GlobalkeyManager {
	static Process magnify_proc;
	static {
		Provider provider = Provider.getCurrentProvider(false);
		provider.register(KeyManager.alt1, e->{
			CaptureFrame.start();
		});
		provider.register(KeyManager.alt2, e->{
			DrawingFrame.start(DrawingFrame.TRANSPARENT);
		});
		provider.register(KeyManager.alt3, e->{
			DrawingFrame.start(DrawingFrame.WHITEBOARD);
		});
		provider.register(KeyManager.alt4, e->{
			if(DrawingFrame.isNowDisplaying()) return;
			if(CaptureFrame.isNowDisplaing()) return;
			MagnifyManager.work();
		});
		provider.register(KeyManager.alt5, e->{
			if(DrawingFrame.isNowDisplaying()) return;
			ZoomFrame.start();
		});
//		provider.register(KeyManager.alt6, e->{
//			if(magnify_proc == null) {
//				String command = System.getenv("windir")+"\\system32\\magnify.exe";
//				ProcessBuilder builder = new ProcessBuilder(new String[] {"cmd.exe", "/C", command});
//				try {
//					magnify_proc = builder.start();
//				}catch(IOException err) {
//					magnify_proc = null;
//				}
//			}else {
//				ProcessBuilder builder = new ProcessBuilder(new String[] {"tskill", "/a", "magnify"});
//				try {
//					builder.start();
//				}catch(Exception err) {}
//				magnify_proc = null;
//			}
//		});
	}
}
