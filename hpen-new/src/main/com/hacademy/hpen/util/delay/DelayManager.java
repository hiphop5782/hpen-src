package com.hacademy.hpen.util.delay;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hacademy.hpen.util.loader.annotation.Component;

@Component
public class DelayManager {
	public void setTimeout(long ms, Runnable runnable) {
		Executors.newSingleThreadScheduledExecutor().schedule(()->{
			runnable.run();
		}, ms, TimeUnit.MILLISECONDS);
	}
}
