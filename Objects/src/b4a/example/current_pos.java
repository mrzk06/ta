package b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class current_pos extends Activity implements B4AActivity{
	public static current_pos mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.current_pos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (current_pos).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.current_pos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.current_pos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (current_pos) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (current_pos) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return current_pos.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (current_pos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (current_pos) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.gps.GPS _gpsclient = null;
public static anywheresoftware.b4a.gps.LocationWrapper _userlocation = null;
public static double _latuser = 0;
public static double _lnguser = 0;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button5 = null;
public b4a.example.dateutils _dateutils = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.pencarian _pencarian = null;
public b4a.example.report _report = null;
public b4a.example.radius _radius = null;
public b4a.example.informasi _informasi = null;
public b4a.example.location_route _location_route = null;
public b4a.example.gallery _gallery = null;
public b4a.example.takecam _takecam = null;
public b4a.example.takevid _takevid = null;
public b4a.example.scrollview _scrollview = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 27;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="Activity.LoadLayout(\"current_pos\")";
mostCurrent._activity.LoadLayout("current_pos",mostCurrent.activityBA);
 //BA.debugLineNum = 31;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 32;BA.debugLine="gpsClient.Initialize(\"gpsClient\")";
_gpsclient.Initialize("gpsClient");
 //BA.debugLineNum = 33;BA.debugLine="userLocation.Initialize";
_userlocation.Initialize();
 };
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 70;BA.debugLine="gpsClient.Stop";
_gpsclient.Stop();
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 66;BA.debugLine="cekGPS";
_cekgps();
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 73;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 80;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 81;BA.debugLine="StartActivity(pencarian)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pencarian.getObject()));
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public static String  _button4_click() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub Button4_Click";
 //BA.debugLineNum = 85;BA.debugLine="StartActivity(report)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._report.getObject()));
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _button5_click() throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub Button5_Click";
 //BA.debugLineNum = 89;BA.debugLine="StartActivity(radius)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._radius.getObject()));
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _cekgps() throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Sub cekGPS 'melakukan mengecekan GPS pada pengguna";
 //BA.debugLineNum = 39;BA.debugLine="If (latUser=0 And lngUser=0) Then";
if ((_latuser==0 && _lnguser==0)) { 
 //BA.debugLineNum = 40;BA.debugLine="If gpsClient.GPSEnabled=False Then";
if (_gpsclient.getGPSEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 41;BA.debugLine="ToastMessageShow(\"Enable GPS First\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enable GPS First"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 42;BA.debugLine="StartActivity(gpsClient.LocationSettingsIntent)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_gpsclient.getLocationSettingsIntent()));
 }else {
 //BA.debugLineNum = 44;BA.debugLine="gpsClient.Start(0,0)";
_gpsclient.Start(processBA,(long) (0),(float) (0));
 //BA.debugLineNum = 45;BA.debugLine="ProgressDialogShow(\"Wait For Location\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Wait For Location"));
 };
 }else {
 //BA.debugLineNum = 48;BA.debugLine="petaClient";
_petaclient();
 };
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private Button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Button3 As Button";
mostCurrent._button3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Button4 As Button";
mostCurrent._button4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private Button5 As Button";
mostCurrent._button5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _gpsclient_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _gpslocation) throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub gpsClient_LocationChanged (gpsLocation As Loca";
 //BA.debugLineNum = 53;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 54;BA.debugLine="userLocation=gpsLocation";
_userlocation = _gpslocation;
 //BA.debugLineNum = 55;BA.debugLine="gpsClient.Stop";
_gpsclient.Stop();
 //BA.debugLineNum = 56;BA.debugLine="latUser=userLocation.Latitude";
_latuser = _userlocation.getLatitude();
 //BA.debugLineNum = 57;BA.debugLine="lngUser=userLocation.Longitude";
_lnguser = _userlocation.getLongitude();
 //BA.debugLineNum = 58;BA.debugLine="petaClient";
_petaclient();
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _petaclient() throws Exception{
 //BA.debugLineNum = 61;BA.debugLine="Sub petaClient";
 //BA.debugLineNum = 62;BA.debugLine="WebView1.LoadUrl(\"\"&Main.Server&\"peta.php?lat=\"&l";
mostCurrent._webview1.LoadUrl(""+mostCurrent._main._server+"peta.php?lat="+BA.NumberToString(_latuser)+"&lng="+BA.NumberToString(_lnguser));
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim gpsClient As GPS";
_gpsclient = new anywheresoftware.b4a.gps.GPS();
 //BA.debugLineNum = 10;BA.debugLine="Dim userLocation As Location";
_userlocation = new anywheresoftware.b4a.gps.LocationWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Dim latUser, lngUser As Double";
_latuser = 0;
_lnguser = 0;
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
}
