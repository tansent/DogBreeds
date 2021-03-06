package jtli.com.dogbreeds.utils;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import jtli.com.dogbreeds.app.App;
import jtli.com.dogbreeds.ui.activity.base.BaseActivity;

/*
 * to extract the function as much as possible
 * 
 * work with BaseActivity and manifest file
 */
public class UIUtil {

	/**
	 * get string array from string.xml file
	 * @param tabNames  id of the string array
	 */
	public static String[] getStringArray(int tabNames) {
		return getResources().getStringArray(tabNames);
	}

	/**
	 * get context resources list
	 */
	public static Resources getResources() {
		return App.getApplication().getResources();
	}
	
	/**
	 * get the application context
	 */
	public static Context getContext(){
		return App.getApplication();
	}
	
	/** dip -> px */
	public static int dip2px(int dip) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** px -> dip */

	public static int px2dip(int px) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * make sure the function runs in the main thread
	 * @param runnable
	 */
	public static void runOnUiThread(Runnable runnable) {
			//main thread
		if (android.os.Process.myTid() == App.getMainTid()) {
			runnable.run();
		}
		else{
			//if it is in branch thread, use handler to post code to the main thread to run
			App.getHandler().post(runnable);
		}
	}
	
	/**
	 * just to simplify the parameters
	 * @param id
	 * @return
	 */
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	/**
	 * transfer the source from id to drawable
	 * @param id
	 * @return
	 */
	public static Drawable getDrawalbe(int id) {
		return getResources().getDrawable(id);
	}

	public static int getDimens(int homePictureHeight) {
		return (int) getResources().getDimension(homePictureHeight);
	}
	
	/**
	 * delay to run task
	 * @param run   task need to be run
	 * @param time  time delay to execute the event
	 */
	public static void postDelayed(Runnable run, int time) {
		App.getHandler().postDelayed(run, time); // (run function will be invoke)
	}
	/**
	 * cancel task
	 */
	public static void cancel(Runnable autoRunTask) {
		App.getHandler().removeCallbacks(autoRunTask); //(run function will be canceled)
	}

	/**
	 * start activity no matter currently in Service, Broadcast or Activity
	 * @param intent
	 */
	public static void startActivity(Intent intent) {
		//if not in an activity to start but to start an activity, a stack and a tag is needed
		if (BaseActivity.activity == null) { //every activity is a baseActivity
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//in broadcast, starting activity needs a new task
			getContext().startActivity(intent); //getContext(). means no task stack
		}else {
			//Context does not have a task stack, we need an activity to start activity
			BaseActivity.activity.startActivity(intent);
		}
		
	}
	
}
