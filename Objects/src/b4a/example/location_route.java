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

public class location_route extends Activity implements B4AActivity{
	public static location_route mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.location_route");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (location_route).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.location_route");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.location_route", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (location_route) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (location_route) Resume **");
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
		return location_route.class;
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
        BA.LogInfo("** Activity (location_route) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (location_route) Resume **");
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
public static String _latasal = "";
public static String _lngasal = "";
public static String _lattujuan = "";
public static String _lngtujuan = "";
public static String _lathenti = "";
public static String _lnghenti = "";
public static String _idmes = "";
public static String _id = "";
public static String _lat = "";
public static String _lng = "";
public static String _id_angkot = "";
public static String _destination = "";
public static String _route_color = "";
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button6 = null;
public b4a.example.dateutils _dateutils = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.current_pos _current_pos = null;
public b4a.example.pencarian _pencarian = null;
public b4a.example.report _report = null;
public b4a.example.radius _radius = null;
public b4a.example.informasi _informasi = null;
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
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 28;BA.debugLine="Activity.LoadLayout(\"location_route\")";
mostCurrent._activity.LoadLayout("location_route",mostCurrent.activityBA);
 //BA.debugLineNum = 29;BA.debugLine="posisi1";
_posisi1();
 //BA.debugLineNum = 30;BA.debugLine="latAsal = current_pos.latUser";
_latasal = BA.NumberToString(mostCurrent._current_pos._latuser);
 //BA.debugLineNum = 31;BA.debugLine="lngAsal = current_pos.lngUser";
_lngasal = BA.NumberToString(mostCurrent._current_pos._lnguser);
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 37;BA.debugLine="latAsal = current_pos.latUser";
_latasal = BA.NumberToString(mostCurrent._current_pos._latuser);
 //BA.debugLineNum = 38;BA.debugLine="lngAsal = current_pos.lngUser";
_lngasal = BA.NumberToString(mostCurrent._current_pos._lnguser);
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _button6_click() throws Exception{
 //BA.debugLineNum = 63;BA.debugLine="Sub Button6_Click";
 //BA.debugLineNum = 64;BA.debugLine="If current_pos.latUser=0 And current_pos.lngUser=";
if (mostCurrent._current_pos._latuser==0 && mostCurrent._current_pos._lnguser==0) { 
 //BA.debugLineNum = 65;BA.debugLine="Log(\"posisi saat ini belum ada\")";
anywheresoftware.b4a.keywords.Common.Log("posisi saat ini belum ada");
 //BA.debugLineNum = 66;BA.debugLine="ToastMessageShow(\"Click Button Current Position";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Click Button Current Position First !"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 67;BA.debugLine="StartActivity(current_pos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._current_pos.getObject()));
 }else {
 //BA.debugLineNum = 69;BA.debugLine="lihatrute(latAsal,lngAsal,latTujuan,lngTujuan)";
_lihatrute((float)(Double.parseDouble(_latasal)),(float)(Double.parseDouble(_lngasal)),(float)(Double.parseDouble(_lattujuan)),(float)(Double.parseDouble(_lngtujuan)));
 //BA.debugLineNum = 70;BA.debugLine="Log(latTujuan)";
anywheresoftware.b4a.keywords.Common.Log(_lattujuan);
 //BA.debugLineNum = 71;BA.debugLine="Log(lngTujuan)";
anywheresoftware.b4a.keywords.Common.Log(_lngtujuan);
 //BA.debugLineNum = 72;BA.debugLine="Log(latAsal)";
anywheresoftware.b4a.keywords.Common.Log(_latasal);
 };
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _executeremotequery(String _query,String _jobname) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
 //BA.debugLineNum = 46;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 47;BA.debugLine="Dim Job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 48;BA.debugLine="Job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,location_route.getObject());
 //BA.debugLineNum = 49;BA.debugLine="Job.PostString(\"\"&Main.server&\"json.php\", Query)";
_job._poststring(""+mostCurrent._main._server+"json.php",_query);
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 20;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Button6 As Button";
mostCurrent._button6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _lihatrute(float _a1,float _a2,float _b1,float _b2) throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Sub lihatrute(a1 As Float,a2 As Float, b1 As Float";
 //BA.debugLineNum = 58;BA.debugLine="WebView1.LoadUrl(\"\"&Main.Server&\"route.php?a1=\"&l";
mostCurrent._webview1.LoadUrl(""+mostCurrent._main._server+"route.php?a1="+_latasal+"&a2="+_lngasal+"&b1="+_lattujuan+"&b2="+_lngtujuan);
 //BA.debugLineNum = 59;BA.debugLine="Log(\"\"&Main.Server&\"route.php?a1=\"&latAsal&\"&a2=\"";
anywheresoftware.b4a.keywords.Common.Log(""+mostCurrent._main._server+"route.php?a1="+_latasal+"&a2="+_lngasal+"&b1="+_lattujuan+"&b2="+_lngtujuan);
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static String  _posisi1() throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub posisi1";
 //BA.debugLineNum = 53;BA.debugLine="WebView1.LoadUrl(\"\"&Main.Server&\"peta.php?lat=\"&l";
mostCurrent._webview1.LoadUrl(""+mostCurrent._main._server+"peta.php?lat="+_lattujuan+"&lng="+_lngtujuan);
 //BA.debugLineNum = 54;BA.debugLine="Log(\"lat posisi 1: \"&latTujuan&\" Long : \"&lngTuju";
anywheresoftware.b4a.keywords.Common.Log("lat posisi 1: "+_lattujuan+" Long : "+_lngtujuan);
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim latAsal, lngAsal, latTujuan, lngTujuan, latHe";
_latasal = "";
_lngasal = "";
_lattujuan = "";
_lngtujuan = "";
_lathenti = "";
_lnghenti = "";
_idmes = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim id, lat, lng, id_angkot, destination, route_c";
_id = "";
_lat = "";
_lng = "";
_id_angkot = "";
_destination = "";
_route_color = "";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
}
