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

public class informasi extends Activity implements B4AActivity{
	public static informasi mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.informasi");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (informasi).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.informasi");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.informasi", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (informasi) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (informasi) Resume **");
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
		return informasi.class;
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
        BA.LogInfo("** Activity (informasi) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (informasi) Resume **");
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
public static String _dis = "";
public static String _dam = "";
public static String _don = "";
public static String _id = "";
public static String _lat = "";
public static String _lng = "";
public static String _damage = "";
public static String _donation = "";
public static String _donators = "";
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label11 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label18 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label20 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label12 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label9 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label23 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label26 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button6 = null;
public static String _iddisaster = "";
public b4a.example.dateutils _dateutils = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.current_pos _current_pos = null;
public b4a.example.pencarian _pencarian = null;
public b4a.example.report _report = null;
public b4a.example.radius _radius = null;
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
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 43;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 44;BA.debugLine="If  pencarian.id<>\"\" Then";
if ((mostCurrent._pencarian._id).equals("") == false) { 
 //BA.debugLineNum = 45;BA.debugLine="iddisaster=pencarian.id";
mostCurrent._iddisaster = mostCurrent._pencarian._id;
 //BA.debugLineNum = 46;BA.debugLine="Log(\"address\")";
anywheresoftware.b4a.keywords.Common.Log("address");
 //BA.debugLineNum = 47;BA.debugLine="Log(iddisaster)";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._iddisaster);
 }else if((mostCurrent._radius._id).equals("") == false) { 
 //BA.debugLineNum = 49;BA.debugLine="iddisaster=radius.id";
mostCurrent._iddisaster = mostCurrent._radius._id;
 };
 };
 //BA.debugLineNum = 52;BA.debugLine="Activity.LoadLayout(\"informasi\")";
mostCurrent._activity.LoadLayout("informasi",mostCurrent.activityBA);
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 57;BA.debugLine="If  pencarian.id<>\"\" Then";
if ((mostCurrent._pencarian._id).equals("") == false) { 
 //BA.debugLineNum = 58;BA.debugLine="iddisaster=pencarian.id";
mostCurrent._iddisaster = mostCurrent._pencarian._id;
 //BA.debugLineNum = 59;BA.debugLine="Log(\"address\")";
anywheresoftware.b4a.keywords.Common.Log("address");
 //BA.debugLineNum = 60;BA.debugLine="Log(iddisaster)";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._iddisaster);
 }else if((mostCurrent._radius._id).equals("") == false) { 
 //BA.debugLineNum = 62;BA.debugLine="iddisaster=radius.id";
mostCurrent._iddisaster = mostCurrent._radius._id;
 };
 //BA.debugLineNum = 64;BA.debugLine="pencarian.id=\"\"";
mostCurrent._pencarian._id = "";
 //BA.debugLineNum = 65;BA.debugLine="radius.id=\"\"";
mostCurrent._radius._id = "";
 //BA.debugLineNum = 66;BA.debugLine="detailDisaster";
_detaildisaster();
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _button6_click() throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub Button6_Click";
 //BA.debugLineNum = 143;BA.debugLine="StartActivity(gallery)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._gallery.getObject()));
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _button7_click() throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Sub Button7_Click";
 //BA.debugLineNum = 146;BA.debugLine="location_route.latTujuan=lat";
mostCurrent._location_route._lattujuan = _lat;
 //BA.debugLineNum = 147;BA.debugLine="location_route.lngTujuan=lng";
mostCurrent._location_route._lngtujuan = _lng;
 //BA.debugLineNum = 148;BA.debugLine="StartActivity(location_route)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._location_route.getObject()));
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _detaildisaster() throws Exception{
 //BA.debugLineNum = 79;BA.debugLine="Sub detailDisaster";
 //BA.debugLineNum = 80;BA.debugLine="ProgressDialogShow(\"Loading ....\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading ...."));
 //BA.debugLineNum = 84;BA.debugLine="ExecuteRemoteQuery(\"select disaster_event.id, dis";
_executeremotequery("select disaster_event.id, disaster_event.address, type_disaster.name as type_disaster_id, disaster_event.date, disaster_event.time, disaster_event.total_loss, cause_of_disaster.causes as cause_id, disaster_event.number_of_victims, ST_X(ST_Centroid(disaster_event.geom)) AS lng, ST_Y(ST_CENTROID(disaster_event.geom)) As lat from disaster_event join cause_of_disaster on cause_of_disaster.id=disaster_event.cause_id join type_disaster on type_disaster.id=disaster_event.type_disaster_id where disaster_event.id='"+mostCurrent._iddisaster+"'","DIS");
 //BA.debugLineNum = 85;BA.debugLine="ExecuteRemoteQuery(\"select details_damage.total a";
_executeremotequery("select details_damage.total as total_damage, damage_material.name_material as damage from details_damage join damage_material on damage_material.id= details_damage.damage_id where details_damage.disaster_event_id='"+mostCurrent._iddisaster+"'","DAM");
 //BA.debugLineNum = 86;BA.debugLine="ExecuteRemoteQuery(\"select details_donation.date_";
_executeremotequery("select details_donation.date_donation_given, donation.name as donation, instansi.name_instansi as donator from details_donation join donation on donation.id= details_donation.donation_id join instansi on instansi.id=details_donation.donators where details_donation.disaster_event_id='"+mostCurrent._iddisaster+"'","DON");
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _executeremotequery(String _query,String _jobname) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
 //BA.debugLineNum = 73;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 74;BA.debugLine="Dim Job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 75;BA.debugLine="Job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,informasi.getObject());
 //BA.debugLineNum = 76;BA.debugLine="Job.PostString(\"\"&Main.server&\"json.php\", Query)";
_job._poststring(""+mostCurrent._main._server+"json.php",_query);
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 20;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private Label11 As Label";
mostCurrent._label11 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Label18 As Label";
mostCurrent._label18 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Label20 As Label";
mostCurrent._label20 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private Label12 As Label";
mostCurrent._label12 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Label10 As Label";
mostCurrent._label10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Label9 As Label";
mostCurrent._label9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Label8 As Label";
mostCurrent._label8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private Label7 As Label";
mostCurrent._label7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private Label23 As Label";
mostCurrent._label23 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Label26 As Label";
mostCurrent._label26 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private Button7 As Button";
mostCurrent._button7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private Button6 As Button";
mostCurrent._button6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim iddisaster As String";
mostCurrent._iddisaster = "";
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _res = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _dis_array = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.List _dam_array = null;
anywheresoftware.b4a.objects.collections.List _don_array = null;
 //BA.debugLineNum = 89;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 90;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 91;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 92;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 93;BA.debugLine="res = Job.GetString";
_res = _job._getstring();
 //BA.debugLineNum = 94;BA.debugLine="Log(\"Response from server :\"&res)";
anywheresoftware.b4a.keywords.Common.Log("Response from server :"+_res);
 //BA.debugLineNum = 95;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 96;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 97;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,_dis,_dam,_don)) {
case 0: {
 //BA.debugLineNum = 99;BA.debugLine="Dim dis_array As List";
_dis_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 100;BA.debugLine="dis_array = parser.NextArray";
_dis_array = _parser.NextArray();
 //BA.debugLineNum = 101;BA.debugLine="If (dis_array.Size>0) Then";
if ((_dis_array.getSize()>0)) { 
 //BA.debugLineNum = 102;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 103;BA.debugLine="m= dis_array.Get(0)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_dis_array.Get((int) (0))));
 //BA.debugLineNum = 104;BA.debugLine="id=m.Get(\"id\")";
_id = BA.ObjectToString(_m.Get((Object)("id")));
 //BA.debugLineNum = 105;BA.debugLine="Label2.Text=m.Get(\"type_disaster_id\")";
mostCurrent._label2.setText(BA.ObjectToCharSequence(_m.Get((Object)("type_disaster_id"))));
 //BA.debugLineNum = 106;BA.debugLine="Label7.Text=m.Get(\"address\")";
mostCurrent._label7.setText(BA.ObjectToCharSequence(_m.Get((Object)("address"))));
 //BA.debugLineNum = 107;BA.debugLine="Label8.Text=m.Get(\"date\")";
mostCurrent._label8.setText(BA.ObjectToCharSequence(_m.Get((Object)("date"))));
 //BA.debugLineNum = 108;BA.debugLine="Label9.Text=m.Get(\"time\")";
mostCurrent._label9.setText(BA.ObjectToCharSequence(_m.Get((Object)("time"))));
 //BA.debugLineNum = 109;BA.debugLine="Label10.Text=m.Get(\"cause_id\")";
mostCurrent._label10.setText(BA.ObjectToCharSequence(_m.Get((Object)("cause_id"))));
 //BA.debugLineNum = 110;BA.debugLine="Label12.Text=m.Get(\"number_of_victims\")";
mostCurrent._label12.setText(BA.ObjectToCharSequence(_m.Get((Object)("number_of_victims"))));
 //BA.debugLineNum = 111;BA.debugLine="Label20.Text=m.Get(\"total_loss\")";
mostCurrent._label20.setText(BA.ObjectToCharSequence(_m.Get((Object)("total_loss"))));
 //BA.debugLineNum = 112;BA.debugLine="lng=m.Get(\"lng\")";
_lng = BA.ObjectToString(_m.Get((Object)("lng")));
 //BA.debugLineNum = 113;BA.debugLine="lat=m.Get(\"lat\")";
_lat = BA.ObjectToString(_m.Get((Object)("lat")));
 //BA.debugLineNum = 114;BA.debugLine="Log(Label10)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._label10));
 }else {
 //BA.debugLineNum = 116;BA.debugLine="Msgbox(\"No Data\",\"Info\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No Data"),BA.ObjectToCharSequence("Info"),mostCurrent.activityBA);
 };
 break; }
case 1: {
 //BA.debugLineNum = 119;BA.debugLine="Dim dam_array As List";
_dam_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 120;BA.debugLine="dam_array = parser.NextArray";
_dam_array = _parser.NextArray();
 //BA.debugLineNum = 121;BA.debugLine="If (dam_array.Size>0) Then";
if ((_dam_array.getSize()>0)) { 
 //BA.debugLineNum = 122;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 123;BA.debugLine="m= dam_array.Get(0)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_dam_array.Get((int) (0))));
 //BA.debugLineNum = 125;BA.debugLine="Label23.Text=m.Get(\"damage\")";
mostCurrent._label23.setText(BA.ObjectToCharSequence(_m.Get((Object)("damage"))));
 };
 break; }
case 2: {
 //BA.debugLineNum = 128;BA.debugLine="Dim don_array As List";
_don_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 129;BA.debugLine="don_array = parser.NextArray";
_don_array = _parser.NextArray();
 //BA.debugLineNum = 130;BA.debugLine="If (don_array.Size>0) Then";
if ((_don_array.getSize()>0)) { 
 //BA.debugLineNum = 131;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 132;BA.debugLine="m= don_array.Get(0)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_don_array.Get((int) (0))));
 //BA.debugLineNum = 134;BA.debugLine="Label26.Text=m.Get(\"donation\")";
mostCurrent._label26.setText(BA.ObjectToCharSequence(_m.Get((Object)("donation"))));
 };
 break; }
}
;
 };
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private DIS=\"DIS\" As String";
_dis = "DIS";
 //BA.debugLineNum = 10;BA.debugLine="Private DAM=\"DAM\" As String";
_dam = "DAM";
 //BA.debugLineNum = 11;BA.debugLine="Private DON=\"DON\" As String";
_don = "DON";
 //BA.debugLineNum = 12;BA.debugLine="Dim id, lat, lng, damage, donation, donators As S";
_id = "";
_lat = "";
_lng = "";
_damage = "";
_donation = "";
_donators = "";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
}
