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

public class report extends Activity implements B4AActivity{
	public static report mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.report");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (report).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.report");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.report", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (report) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (report) Resume **");
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
		return report.class;
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
        BA.LogInfo("** Activity (report) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (report) Resume **");
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
public static String _iddisaster = "";
public static String _tampil_jeniss = "";
public static String _save_case = "";
public static String _id_report = "";
public static String _id_reporter = "";
public static String _jeniss_terpilih = "";
public static String[] _arrayjeniss = null;
public static String[] _arrayreporter = null;
public static String[] _arrayreport = null;
public static String _idreport_terpilih = "";
public static String _idreporter_terpilih = "";
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext1 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext4 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext5 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _save = null;
public static String _text_label1 = "";
public static String _text_label2 = "";
public static String _text_label3 = "";
public static String _text_label4 = "";
public static String _text_label5 = "";
public static String _text_label6 = "";
public static String _text_label7 = "";
public static String _text_label8 = "";
public static String _text_label9 = "";
public anywheresoftware.b4a.objects.ButtonWrapper _btncamera = null;
public anywheresoftware.b4a.objects.EditTextWrapper _idreport = null;
public anywheresoftware.b4a.objects.EditTextWrapper _idreporter = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label8 = null;
public b4a.example.dateutils _dateutils = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.current_pos _current_pos = null;
public b4a.example.pencarian _pencarian = null;
public b4a.example.radius _radius = null;
public b4a.example.informasi _informasi = null;
public b4a.example.location_route _location_route = null;
public b4a.example.gallery _gallery = null;
public b4a.example.takecam _takecam = null;
public b4a.example.takevid _takevid = null;
public b4a.example.scrollview _scrollview = null;
public static class _jeniss_lines{
public boolean IsInitialized;
public String id;
public String name;
public void Initialize() {
IsInitialized = true;
id = "";
name = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _reporter_lines{
public boolean IsInitialized;
public String id;
public String no_hp;
public void Initialize() {
IsInitialized = true;
id = "";
no_hp = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _report_lines{
public boolean IsInitialized;
public String id;
public void Initialize() {
IsInitialized = true;
id = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _save_lines{
public boolean IsInitialized;
public int response_code;
public String message;
public void Initialize() {
IsInitialized = true;
response_code = 0;
message = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 77;BA.debugLine="Activity.LoadLayout(\"report\")";
mostCurrent._activity.LoadLayout("report",mostCurrent.activityBA);
 //BA.debugLineNum = 78;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 79;BA.debugLine="gpsClient.Initialize(\"gpsClient\")";
_gpsclient.Initialize("gpsClient");
 //BA.debugLineNum = 80;BA.debugLine="userLocation.Initialize";
_userlocation.Initialize();
 };
 //BA.debugLineNum = 82;BA.debugLine="WebView1.LoadUrl(\"\"&Main.Server&\"map.html\")";
mostCurrent._webview1.LoadUrl(""+mostCurrent._main._server+"map.html");
 //BA.debugLineNum = 83;BA.debugLine="ScrollView1.Panel.LoadLayout(\"formreport\")";
mostCurrent._scrollview1.getPanel().LoadLayout("formreport",mostCurrent.activityBA);
 //BA.debugLineNum = 84;BA.debugLine="tampilkan_jeniss";
_tampilkan_jeniss();
 //BA.debugLineNum = 85;BA.debugLine="keep";
_keep();
 //BA.debugLineNum = 86;BA.debugLine="EditText1.Text=latUser&\",\"&lngUser";
mostCurrent._edittext1.setText(BA.ObjectToCharSequence(BA.NumberToString(_latuser)+","+BA.NumberToString(_lnguser)));
 //BA.debugLineNum = 87;BA.debugLine="Spinner1.TextSize=11";
mostCurrent._spinner1.setTextSize((float) (11));
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 98;BA.debugLine="gpsClient.Stop";
_gpsclient.Stop();
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 93;BA.debugLine="cekGPS";
_cekgps();
 //BA.debugLineNum = 94;BA.debugLine="EditText6.Text=takecam.nameimages";
mostCurrent._edittext6.setText(BA.ObjectToCharSequence(mostCurrent._takecam._nameimages));
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static String  _btncamera_click() throws Exception{
 //BA.debugLineNum = 290;BA.debugLine="Sub btncamera_Click";
 //BA.debugLineNum = 291;BA.debugLine="StartActivity(takecam)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._takecam.getObject()));
 //BA.debugLineNum = 292;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 131;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 135;BA.debugLine="StartActivity(current_pos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._current_pos.getObject()));
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 138;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 139;BA.debugLine="StartActivity(pencarian)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._pencarian.getObject()));
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _button4_click() throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub Button4_Click";
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _button5_click() throws Exception{
 //BA.debugLineNum = 146;BA.debugLine="Sub Button5_Click";
 //BA.debugLineNum = 147;BA.debugLine="StartActivity(radius)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._radius.getObject()));
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _cekgps() throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub cekGPS 'melakukan mengecekan GPS pada pengguna";
 //BA.debugLineNum = 106;BA.debugLine="If (latUser=0 And lngUser=0) Then";
if ((_latuser==0 && _lnguser==0)) { 
 //BA.debugLineNum = 107;BA.debugLine="If gpsClient.GPSEnabled=False Then";
if (_gpsclient.getGPSEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 108;BA.debugLine="ToastMessageShow(\"Enable GPS First\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enable GPS First"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 109;BA.debugLine="StartActivity(gpsClient.LocationSettingsIntent)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_gpsclient.getLocationSettingsIntent()));
 }else {
 //BA.debugLineNum = 111;BA.debugLine="gpsClient.Start(0,0)";
_gpsclient.Start(processBA,(long) (0),(float) (0));
 //BA.debugLineNum = 112;BA.debugLine="ProgressDialogShow(\"Wait For Location\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Wait For Location"));
 };
 }else {
 //BA.debugLineNum = 115;BA.debugLine="petaClient";
_petaclient();
 };
 //BA.debugLineNum = 117;BA.debugLine="End Sub";
return "";
}
public static String  _executeremotequery(String _query,String _jobname) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
 //BA.debugLineNum = 150;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 151;BA.debugLine="Dim Job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 152;BA.debugLine="Job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,report.getObject());
 //BA.debugLineNum = 153;BA.debugLine="Job.PostString(\"\"&Main.Server&\"json.php\",Query)";
_job._poststring(""+mostCurrent._main._server+"json.php",_query);
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public static String  _executeremotequeryinsert(String _query,String _jobname) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
 //BA.debugLineNum = 155;BA.debugLine="Sub ExecuteRemoteQueryInsert(Query As String, JobN";
 //BA.debugLineNum = 156;BA.debugLine="Dim Job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 157;BA.debugLine="Job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,report.getObject());
 //BA.debugLineNum = 158;BA.debugLine="Job.PostString(\"\"&Main.Server&\"addreport.php\",Que";
_job._poststring(""+mostCurrent._main._server+"addreport.php",_query);
 //BA.debugLineNum = 159;BA.debugLine="Log(\"\"&Main.Server&\"addreport.php?idreport=\"&idre";
anywheresoftware.b4a.keywords.Common.Log(""+mostCurrent._main._server+"addreport.php?idreport="+mostCurrent._idreport_terpilih+"&idreporter="+mostCurrent._idreporter_terpilih+"&latuser="+BA.NumberToString(_latuser)+"&lnguser="+BA.NumberToString(_lnguser)+"&date="+mostCurrent._text_label4+"&time="+mostCurrent._text_label5+"&jenis="+mostCurrent._text_label3+"&reporter="+mostCurrent._text_label6+"&nohp="+mostCurrent._text_label7+"&image="+mostCurrent._text_label8+"");
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 20;BA.debugLine="Private Tampil_Jeniss = \"Tampil_Jeniss\" As String";
mostCurrent._tampil_jeniss = "Tampil_Jeniss";
 //BA.debugLineNum = 21;BA.debugLine="Private Save_Case = \"Save_Case\" As String";
mostCurrent._save_case = "Save_Case";
 //BA.debugLineNum = 22;BA.debugLine="Private id_report = \"id_report\" As String";
mostCurrent._id_report = "id_report";
 //BA.debugLineNum = 23;BA.debugLine="Private id_reporter = \"id_reporter\" As String";
mostCurrent._id_reporter = "id_reporter";
 //BA.debugLineNum = 24;BA.debugLine="Dim jeniss_terpilih As String";
mostCurrent._jeniss_terpilih = "";
 //BA.debugLineNum = 26;BA.debugLine="Type jeniss_lines (id As String,name As String)";
;
 //BA.debugLineNum = 27;BA.debugLine="Dim arrayJeniss(100) As String";
mostCurrent._arrayjeniss = new String[(int) (100)];
java.util.Arrays.fill(mostCurrent._arrayjeniss,"");
 //BA.debugLineNum = 29;BA.debugLine="Type reporter_lines (id As String, no_hp As Strin";
;
 //BA.debugLineNum = 30;BA.debugLine="Dim arrayReporter(100) As String";
mostCurrent._arrayreporter = new String[(int) (100)];
java.util.Arrays.fill(mostCurrent._arrayreporter,"");
 //BA.debugLineNum = 32;BA.debugLine="Type report_lines (id As String)";
;
 //BA.debugLineNum = 33;BA.debugLine="Dim arrayReport(100) As String";
mostCurrent._arrayreport = new String[(int) (100)];
java.util.Arrays.fill(mostCurrent._arrayreport,"");
 //BA.debugLineNum = 35;BA.debugLine="Type save_lines (response_code As Int,message As";
;
 //BA.debugLineNum = 36;BA.debugLine="Dim idreport_terpilih As String";
mostCurrent._idreport_terpilih = "";
 //BA.debugLineNum = 37;BA.debugLine="Dim Idreporter_terpilih As String";
mostCurrent._idreporter_terpilih = "";
 //BA.debugLineNum = 39;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private EditText1 As EditText";
mostCurrent._edittext1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private Spinner1 As Spinner";
mostCurrent._spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private EditText4 As EditText";
mostCurrent._edittext4 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private EditText5 As EditText";
mostCurrent._edittext5 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private EditText6 As EditText";
mostCurrent._edittext6 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private Button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private Button3 As Button";
mostCurrent._button3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private Button4 As Button";
mostCurrent._button4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private Button5 As Button";
mostCurrent._button5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private Save As Button";
mostCurrent._save = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private text_Label1 As String";
mostCurrent._text_label1 = "";
 //BA.debugLineNum = 57;BA.debugLine="Private text_Label2 As String";
mostCurrent._text_label2 = "";
 //BA.debugLineNum = 58;BA.debugLine="Private text_Label3 As String";
mostCurrent._text_label3 = "";
 //BA.debugLineNum = 59;BA.debugLine="Private text_Label4 As String";
mostCurrent._text_label4 = "";
 //BA.debugLineNum = 60;BA.debugLine="Private text_Label5 As String";
mostCurrent._text_label5 = "";
 //BA.debugLineNum = 61;BA.debugLine="Private text_Label6 As String";
mostCurrent._text_label6 = "";
 //BA.debugLineNum = 62;BA.debugLine="Private text_Label7 As String";
mostCurrent._text_label7 = "";
 //BA.debugLineNum = 63;BA.debugLine="Private text_Label8 As String";
mostCurrent._text_label8 = "";
 //BA.debugLineNum = 64;BA.debugLine="Private text_Label9 As String";
mostCurrent._text_label9 = "";
 //BA.debugLineNum = 65;BA.debugLine="Private btncamera As Button";
mostCurrent._btncamera = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private idreport As EditText";
mostCurrent._idreport = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private idreporter As EditText";
mostCurrent._idreporter = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private Label8 As Label";
mostCurrent._label8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _gpsclient_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _gpslocation) throws Exception{
 //BA.debugLineNum = 119;BA.debugLine="Sub gpsClient_LocationChanged (gpsLocation As Loca";
 //BA.debugLineNum = 120;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 121;BA.debugLine="userLocation=gpsLocation";
_userlocation = _gpslocation;
 //BA.debugLineNum = 122;BA.debugLine="gpsClient.Stop";
_gpsclient.Stop();
 //BA.debugLineNum = 123;BA.debugLine="latUser=userLocation.Latitude";
_latuser = _userlocation.getLatitude();
 //BA.debugLineNum = 124;BA.debugLine="lngUser=userLocation.Longitude";
_lnguser = _userlocation.getLongitude();
 //BA.debugLineNum = 125;BA.debugLine="petaClient";
_petaclient();
 //BA.debugLineNum = 126;BA.debugLine="EditText1.Text=latUser&\",\"&lngUser";
mostCurrent._edittext1.setText(BA.ObjectToCharSequence(BA.NumberToString(_latuser)+","+BA.NumberToString(_lnguser)));
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _res = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _jeniss_array = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
b4a.example.report._jeniss_lines _b = null;
anywheresoftware.b4a.objects.collections.List _report_array = null;
b4a.example.report._report_lines _r = null;
String _hurufreport = "";
String _angkareport = "";
anywheresoftware.b4a.objects.collections.List _reporter_array = null;
b4a.example.report._reporter_lines _t = null;
String _hurufreporter = "";
String _angkareporter = "";
 //BA.debugLineNum = 205;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 206;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 207;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 208;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 209;BA.debugLine="res = Job.GetString";
_res = _job._getstring();
 //BA.debugLineNum = 210;BA.debugLine="Log(\"Response :\"& res)";
anywheresoftware.b4a.keywords.Common.Log("Response :"+_res);
 //BA.debugLineNum = 211;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 212;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 213;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,mostCurrent._tampil_jeniss,mostCurrent._id_report,mostCurrent._id_reporter,mostCurrent._save_case)) {
case 0: {
 //BA.debugLineNum = 216;BA.debugLine="Dim jeniss_array As List";
_jeniss_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 217;BA.debugLine="jeniss_array = parser.NextArray";
_jeniss_array = _parser.NextArray();
 //BA.debugLineNum = 218;BA.debugLine="For i=0 To jeniss_array.Size -1";
{
final int step12 = 1;
final int limit12 = (int) (_jeniss_array.getSize()-1);
_i = (int) (0) ;
for (;(step12 > 0 && _i <= limit12) || (step12 < 0 && _i >= limit12) ;_i = ((int)(0 + _i + step12))  ) {
 //BA.debugLineNum = 219;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 220;BA.debugLine="m = jeniss_array.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_jeniss_array.Get(_i)));
 //BA.debugLineNum = 221;BA.debugLine="Dim b As jeniss_lines";
_b = new b4a.example.report._jeniss_lines();
 //BA.debugLineNum = 222;BA.debugLine="b.Initialize";
_b.Initialize();
 //BA.debugLineNum = 223;BA.debugLine="b.id= m.Get(\"id\")";
_b.id = BA.ObjectToString(_m.Get((Object)("id")));
 //BA.debugLineNum = 224;BA.debugLine="b.name = m.Get(\"name\")";
_b.name = BA.ObjectToString(_m.Get((Object)("name")));
 //BA.debugLineNum = 225;BA.debugLine="Spinner1.Add(b.name)";
mostCurrent._spinner1.Add(_b.name);
 //BA.debugLineNum = 226;BA.debugLine="arrayJeniss(i) = b.id";
mostCurrent._arrayjeniss[_i] = _b.id;
 //BA.debugLineNum = 227;BA.debugLine="Log(b.id)";
anywheresoftware.b4a.keywords.Common.Log(_b.id);
 }
};
 break; }
case 1: {
 //BA.debugLineNum = 230;BA.debugLine="Dim report_array As List";
_report_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 231;BA.debugLine="report_array = parser.NextArray";
_report_array = _parser.NextArray();
 //BA.debugLineNum = 232;BA.debugLine="For i=0 To report_array.Size -1";
{
final int step26 = 1;
final int limit26 = (int) (_report_array.getSize()-1);
_i = (int) (0) ;
for (;(step26 > 0 && _i <= limit26) || (step26 < 0 && _i >= limit26) ;_i = ((int)(0 + _i + step26))  ) {
 //BA.debugLineNum = 233;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 234;BA.debugLine="m = report_array.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_report_array.Get(_i)));
 //BA.debugLineNum = 235;BA.debugLine="Dim r As report_lines";
_r = new b4a.example.report._report_lines();
 //BA.debugLineNum = 236;BA.debugLine="r.Initialize";
_r.Initialize();
 //BA.debugLineNum = 237;BA.debugLine="r.id= m.Get(\"id\")";
_r.id = BA.ObjectToString(_m.Get((Object)("id")));
 //BA.debugLineNum = 238;BA.debugLine="Dim hurufreport As String";
_hurufreport = "";
 //BA.debugLineNum = 239;BA.debugLine="Dim angkareport As String";
_angkareport = "";
 //BA.debugLineNum = 240;BA.debugLine="angkareport= r.id.SubString2(1,5)";
_angkareport = _r.id.substring((int) (1),(int) (5));
 //BA.debugLineNum = 241;BA.debugLine="hurufreport= r.id.SubString2(0,1)";
_hurufreport = _r.id.substring((int) (0),(int) (1));
 //BA.debugLineNum = 242;BA.debugLine="angkareport= angkareport + 1";
_angkareport = BA.NumberToString((double)(Double.parseDouble(_angkareport))+1);
 //BA.debugLineNum = 243;BA.debugLine="r.id=hurufreport&angkareport";
_r.id = _hurufreport+_angkareport;
 //BA.debugLineNum = 244;BA.debugLine="idreport.Text=r.id";
mostCurrent._idreport.setText(BA.ObjectToCharSequence(_r.id));
 //BA.debugLineNum = 245;BA.debugLine="arrayReport(i) = r.id";
mostCurrent._arrayreport[_i] = _r.id;
 //BA.debugLineNum = 246;BA.debugLine="iddisaster=r.id";
_iddisaster = _r.id;
 //BA.debugLineNum = 247;BA.debugLine="Log(r.id)";
anywheresoftware.b4a.keywords.Common.Log(_r.id);
 }
};
 break; }
case 2: {
 //BA.debugLineNum = 250;BA.debugLine="Dim reporter_array As List";
_reporter_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 251;BA.debugLine="reporter_array = parser.NextArray";
_reporter_array = _parser.NextArray();
 //BA.debugLineNum = 252;BA.debugLine="For i=0 To reporter_array.Size -1";
{
final int step46 = 1;
final int limit46 = (int) (_reporter_array.getSize()-1);
_i = (int) (0) ;
for (;(step46 > 0 && _i <= limit46) || (step46 < 0 && _i >= limit46) ;_i = ((int)(0 + _i + step46))  ) {
 //BA.debugLineNum = 253;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 254;BA.debugLine="m = reporter_array.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_reporter_array.Get(_i)));
 //BA.debugLineNum = 255;BA.debugLine="Dim t As reporter_lines";
_t = new b4a.example.report._reporter_lines();
 //BA.debugLineNum = 256;BA.debugLine="t.Initialize";
_t.Initialize();
 //BA.debugLineNum = 257;BA.debugLine="t.id= m.Get(\"id\")";
_t.id = BA.ObjectToString(_m.Get((Object)("id")));
 //BA.debugLineNum = 258;BA.debugLine="Dim hurufreporter As String";
_hurufreporter = "";
 //BA.debugLineNum = 259;BA.debugLine="Dim angkareporter As String";
_angkareporter = "";
 //BA.debugLineNum = 260;BA.debugLine="angkareporter= t.id.SubString2(1,5)";
_angkareporter = _t.id.substring((int) (1),(int) (5));
 //BA.debugLineNum = 261;BA.debugLine="hurufreporter= t.id.SubString2(0,1)";
_hurufreporter = _t.id.substring((int) (0),(int) (1));
 //BA.debugLineNum = 262;BA.debugLine="angkareporter= angkareporter + 1";
_angkareporter = BA.NumberToString((double)(Double.parseDouble(_angkareporter))+1);
 //BA.debugLineNum = 263;BA.debugLine="t.id=hurufreporter&angkareporter";
_t.id = _hurufreporter+_angkareporter;
 //BA.debugLineNum = 264;BA.debugLine="idreporter.Text=t.id";
mostCurrent._idreporter.setText(BA.ObjectToCharSequence(_t.id));
 //BA.debugLineNum = 265;BA.debugLine="arrayReporter(i) = t.id";
mostCurrent._arrayreporter[_i] = _t.id;
 //BA.debugLineNum = 266;BA.debugLine="Log(t.id)";
anywheresoftware.b4a.keywords.Common.Log(_t.id);
 }
};
 break; }
case 3: {
 //BA.debugLineNum = 270;BA.debugLine="Try";
try { //BA.debugLineNum = 271;BA.debugLine="Msgbox(\"Data has been Sent! :)\",\"Success\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Data has been Sent! :)"),BA.ObjectToCharSequence("Success"),mostCurrent.activityBA);
 //BA.debugLineNum = 272;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 } 
       catch (Exception e67) {
			processBA.setLastException(e67); //BA.debugLineNum = 274;BA.debugLine="Msgbox(\"Data can not be Sent :(\" , \"error\" )";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Data can not be Sent :("),BA.ObjectToCharSequence("error"),mostCurrent.activityBA);
 };
 break; }
}
;
 };
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public static String  _keep() throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Sub keep";
 //BA.debugLineNum = 175;BA.debugLine="ProgressDialogShow(\"Loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading..."));
 //BA.debugLineNum = 176;BA.debugLine="ExecuteRemoteQuery(\"SELECT MAX(id) as id FROM rep";
_executeremotequery("SELECT MAX(id) as id FROM report","id_report");
 //BA.debugLineNum = 177;BA.debugLine="ExecuteRemoteQuery(\"SELECT MAX(id) as id FROM rep";
_executeremotequery("SELECT MAX(id) as id FROM reporter","id_reporter");
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public static String  _petaclient() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub petaClient";
 //BA.debugLineNum = 102;BA.debugLine="WebView1.LoadUrl(\"\"&Main.Server&\"peta.php?lat=\"&l";
mostCurrent._webview1.LoadUrl(""+mostCurrent._main._server+"peta.php?lat="+BA.NumberToString(_latuser)+"&lng="+BA.NumberToString(_lnguser));
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 12;BA.debugLine="Dim iddisaster As String";
_iddisaster = "";
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _save_click() throws Exception{
com.spinter.uploadfilephp.UploadFilePhp _up = null;
 //BA.debugLineNum = 283;BA.debugLine="Sub Save_Click";
 //BA.debugLineNum = 284;BA.debugLine="simpan";
_simpan();
 //BA.debugLineNum = 285;BA.debugLine="Dim Up As UploadFilePhp";
_up = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 286;BA.debugLine="Up.Initialize(\"Up\")";
_up.Initialize(processBA,"Up");
 //BA.debugLineNum = 287;BA.debugLine="Up.doFileUpload(ProgressBar1,Label8,File.DirRootE";
_up.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(mostCurrent._label8.getObject()),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+mostCurrent._takecam._nameimages,"http://gisnaturaldisaster.herokuapp.com/mobile/upload.php");
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _simpan() throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Sub simpan";
 //BA.debugLineNum = 183;BA.debugLine="EditText1.Text=latUser&\" \"&lngUser";
mostCurrent._edittext1.setText(BA.ObjectToCharSequence(BA.NumberToString(_latuser)+" "+BA.NumberToString(_lnguser)));
 //BA.debugLineNum = 184;BA.debugLine="EditText6.Text=takecam.nameimages";
mostCurrent._edittext6.setText(BA.ObjectToCharSequence(mostCurrent._takecam._nameimages));
 //BA.debugLineNum = 186;BA.debugLine="text_Label2 = EditText1.Text";
mostCurrent._text_label2 = mostCurrent._edittext1.getText();
 //BA.debugLineNum = 187;BA.debugLine="text_Label3 = jeniss_terpilih";
mostCurrent._text_label3 = mostCurrent._jeniss_terpilih;
 //BA.debugLineNum = 190;BA.debugLine="text_Label6 = EditText4.Text";
mostCurrent._text_label6 = mostCurrent._edittext4.getText();
 //BA.debugLineNum = 191;BA.debugLine="text_Label7 = EditText5.Text";
mostCurrent._text_label7 = mostCurrent._edittext5.getText();
 //BA.debugLineNum = 192;BA.debugLine="text_Label8 = EditText6.Text";
mostCurrent._text_label8 = mostCurrent._edittext6.getText();
 //BA.debugLineNum = 195;BA.debugLine="ExecuteRemoteQueryInsert(\"INSERT INTO reporter (i";
_executeremotequeryinsert("INSERT INTO reporter (id, name, no_hp) VALUES ("+mostCurrent._idreporter.getText()+",'"+mostCurrent._edittext4.getText()+"', "+mostCurrent._edittext5.getText()+")","Save_Case");
 //BA.debugLineNum = 196;BA.debugLine="ProgressDialogShow(\"Loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading..."));
 //BA.debugLineNum = 197;BA.debugLine="ExecuteRemoteQueryInsert(\"INSERT INTO report (id,";
_executeremotequeryinsert("INSERT INTO report (id, type_disaster_id, date, time, foto_video, pelapor_id,lat,long) VALUES ('"+mostCurrent._idreport.getText()+"','"+mostCurrent._text_label3+"', CURRENT_DATE,CURRENT_TIME,'"+mostCurrent._text_label8+"',"+mostCurrent._idreporter.getText()+","+BA.NumberToString(_latuser)+","+BA.NumberToString(_lnguser)+")","Save_Case");
 //BA.debugLineNum = 198;BA.debugLine="Log(\"INSERT INTO reporter (id, name, no_hp) VALUE";
anywheresoftware.b4a.keywords.Common.Log("INSERT INTO reporter (id, name, no_hp) VALUES ("+mostCurrent._idreporter.getText()+","+mostCurrent._edittext4.getText()+", "+mostCurrent._edittext5.getText()+"");
 //BA.debugLineNum = 199;BA.debugLine="Log(\"INSERT INTO report (id, type_disaster_id, da";
anywheresoftware.b4a.keywords.Common.Log("INSERT INTO report (id, type_disaster_id, date, time, foto_video, reporter_id,lat,long) VALUES ("+mostCurrent._idreport.getText()+","+mostCurrent._text_label3+", "+mostCurrent._text_label4+","+mostCurrent._text_label5+","+mostCurrent._text_label8+","+mostCurrent._idreporter.getText()+","+BA.NumberToString(_latuser)+","+BA.NumberToString(_lnguser)+"");
 //BA.debugLineNum = 203;BA.debugLine="End Sub";
return "";
}
public static String  _spinner1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Sub Spinner1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 163;BA.debugLine="Spinner1.Color=Colors.Blue";
mostCurrent._spinner1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 164;BA.debugLine="jeniss_terpilih = arrayJeniss(Position)";
mostCurrent._jeniss_terpilih = mostCurrent._arrayjeniss[_position];
 //BA.debugLineNum = 165;BA.debugLine="Log(jeniss_terpilih)";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._jeniss_terpilih);
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
public static String  _tampilkan_jeniss() throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Sub tampilkan_jeniss";
 //BA.debugLineNum = 170;BA.debugLine="ProgressDialogShow(\"Loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading..."));
 //BA.debugLineNum = 171;BA.debugLine="ExecuteRemoteQuery(\"SELECT * from type_disaster\",";
_executeremotequery("SELECT * from type_disaster","Tampil_Jeniss");
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
}
