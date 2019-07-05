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

public class gallery extends Activity implements B4AActivity{
	public static gallery mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.gallery");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (gallery).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.gallery");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.gallery", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (gallery) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (gallery) Resume **");
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
		return gallery.class;
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
        BA.LogInfo("** Activity (gallery) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (gallery) Resume **");
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
public static String _viewfoto1 = "";
public static String _id_gallery = "";
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button6 = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public b4a.example.dateutils _dateutils = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.current_pos _current_pos = null;
public b4a.example.pencarian _pencarian = null;
public b4a.example.report _report = null;
public b4a.example.radius _radius = null;
public b4a.example.informasi _informasi = null;
public b4a.example.location_route _location_route = null;
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
 //BA.debugLineNum = 24;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 26;BA.debugLine="Activity.LoadLayout(\"gallery\")";
mostCurrent._activity.LoadLayout("gallery",mostCurrent.activityBA);
 //BA.debugLineNum = 27;BA.debugLine="id_gallery = informasi.id";
_id_gallery = mostCurrent._informasi._id;
 //BA.debugLineNum = 28;BA.debugLine="fotoquery";
_fotoquery();
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _executeremotequery(String _query,String _jobname) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
 //BA.debugLineNum = 48;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 49;BA.debugLine="Dim Job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 50;BA.debugLine="Job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,gallery.getObject());
 //BA.debugLineNum = 51;BA.debugLine="Job.PostString(\"\"&Main.server&\"json.php\", Query)";
_job._poststring(""+mostCurrent._main._server+"json.php",_query);
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _fotoquery() throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Sub fotoquery";
 //BA.debugLineNum = 41;BA.debugLine="ProgressDialogShow(\"Loading\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading"));
 //BA.debugLineNum = 43;BA.debugLine="ExecuteRemoteQuery(\"SELECT photo_video  FROM disa";
_executeremotequery("SELECT photo_video  FROM disaster_event WHERE id='"+_id_gallery+"'","ViewFoto1");
 //BA.debugLineNum = 44;BA.debugLine="Log(id_gallery)";
anywheresoftware.b4a.keywords.Common.Log(_id_gallery);
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Private ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private Button6 As Button";
mostCurrent._button6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _res = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _agn = null;
String _serverimage = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _w = null;
String _image = "";
String _gambar = "";
 //BA.debugLineNum = 54;BA.debugLine="Sub Jobdone (Job As HttpJob)";
 //BA.debugLineNum = 56;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 57;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 58;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 59;BA.debugLine="res= Job.GetString";
_res = _job._getstring();
 //BA.debugLineNum = 60;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 61;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 62;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,_viewfoto1)) {
case 0: {
 //BA.debugLineNum = 65;BA.debugLine="Dim agn As List";
_agn = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 66;BA.debugLine="agn=parser.NextArray";
_agn = _parser.NextArray();
 //BA.debugLineNum = 67;BA.debugLine="If agn.Size - 1 < 0 Then";
if (_agn.getSize()-1<0) { 
 //BA.debugLineNum = 68;BA.debugLine="Log(agn.Size)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_agn.getSize()));
 //BA.debugLineNum = 69;BA.debugLine="Msgbox(\"Galery Foto tidak ditemukan\", \"Pering";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Galery Foto tidak ditemukan"),BA.ObjectToCharSequence("Peringatan"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 71;BA.debugLine="Dim serverimage = \"http://gisnaturaldisaster.";
_serverimage = "http://gisnaturaldisaster.herokuapp.com/images/disaster/";
 //BA.debugLineNum = 73;BA.debugLine="For i=0 To agn.Size-1";
{
final int step16 = 1;
final int limit16 = (int) (_agn.getSize()-1);
_i = (int) (0) ;
for (;(step16 > 0 && _i <= limit16) || (step16 < 0 && _i >= limit16) ;_i = ((int)(0 + _i + step16))  ) {
 //BA.debugLineNum = 74;BA.debugLine="Dim w As Map";
_w = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 75;BA.debugLine="w=agn.Get(i)";
_w.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_agn.Get(_i)));
 //BA.debugLineNum = 76;BA.debugLine="Log(\"haha\")";
anywheresoftware.b4a.keywords.Common.Log("haha");
 //BA.debugLineNum = 77;BA.debugLine="Dim image = w.Get(\"photo_video\") As String";
_image = BA.ObjectToString(_w.Get((Object)("photo_video")));
 //BA.debugLineNum = 80;BA.debugLine="If image==\"\" Then";
if ((_image).equals("")) { 
 //BA.debugLineNum = 81;BA.debugLine="Msgbox(\"No Data\",\"Info\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No Data"),BA.ObjectToCharSequence("Info"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 83;BA.debugLine="Dim gambar = \"\"&serverimage&\"\"&image As Str";
_gambar = ""+_serverimage+""+_image;
 //BA.debugLineNum = 84;BA.debugLine="WebView1.LoadUrl(gambar)";
mostCurrent._webview1.LoadUrl(_gambar);
 };
 }
};
 };
 break; }
}
;
 };
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim ViewFoto1=\"ViewFoto1\" As String";
_viewfoto1 = "ViewFoto1";
 //BA.debugLineNum = 10;BA.debugLine="Dim id_gallery As String";
_id_gallery = "";
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
}
