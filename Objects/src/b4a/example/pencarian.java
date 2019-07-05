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

public class pencarian extends Activity implements B4AActivity{
	public static pencarian mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.pencarian");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (pencarian).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.pencarian");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.pencarian", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (pencarian) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (pencarian) Resume **");
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
		return pencarian.class;
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
        BA.LogInfo("** Activity (pencarian) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (pencarian) Resume **");
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
public static String _cari_filter = "";
public static String _id = "";
public static String _address = "";
public static String _name = "";
public static String _latitude = "";
public static String _longitude = "";
public static String _tampil_tahun = "";
public static String _tampil_bulan = "";
public static String _tampil_jenis = "";
public static String _tampil_kecamatan = "";
public static String _tahun_terpilih = "";
public static String[] _arraytahun = null;
public static String _bulan_terpilih = "";
public static String[] _arraybulan = null;
public static String _district_terpilih = "";
public static String[] _arraydistrict = null;
public static String _jenis_terpilih = "";
public static String[] _arrayjenis = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button5 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner2 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner3 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button6 = null;
public b4a.example.dateutils _dateutils = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.current_pos _current_pos = null;
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
public static class _daftardisaster_lines{
public boolean IsInitialized;
public String isi1;
public String isi2;
public String isi3;
public String isi4;
public String isi5;
public void Initialize() {
IsInitialized = true;
isi1 = "";
isi2 = "";
isi3 = "";
isi4 = "";
isi5 = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _tahuns_lines{
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
public static class _bulans_lines{
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
public static class _districts_lines{
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
public static class _jenis_lines{
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
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 59;BA.debugLine="Activity.LoadLayout(\"search\")";
mostCurrent._activity.LoadLayout("search",mostCurrent.activityBA);
 //BA.debugLineNum = 60;BA.debugLine="posisi1";
_posisi1();
 //BA.debugLineNum = 61;BA.debugLine="tampilkan_tahun";
_tampilkan_tahun();
 //BA.debugLineNum = 62;BA.debugLine="tampilkan_jenis";
_tampilkan_jenis();
 //BA.debugLineNum = 63;BA.debugLine="tampilkan_bulan";
_tampilkan_bulan();
 //BA.debugLineNum = 64;BA.debugLine="tampilkan_kecamatan";
_tampilkan_kecamatan();
 //BA.debugLineNum = 66;BA.debugLine="ListView1.TwoLinesLayout.Label.TextSize=13";
mostCurrent._listview1.getTwoLinesLayout().Label.setTextSize((float) (13));
 //BA.debugLineNum = 67;BA.debugLine="ListView1.TwoLinesLayout.SecondLabel.TextSize=12";
mostCurrent._listview1.getTwoLinesLayout().SecondLabel.setTextSize((float) (12));
 //BA.debugLineNum = 68;BA.debugLine="ListView1.Color=Colors.white";
mostCurrent._listview1.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 69;BA.debugLine="ListView1.TwoLinesLayout.Label.TextColor=Colors.B";
mostCurrent._listview1.getTwoLinesLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 70;BA.debugLine="ListView1.TwoLinesLayout.SecondLabel.TextColor=Co";
mostCurrent._listview1.getTwoLinesLayout().SecondLabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 71;BA.debugLine="ListView1.TwoLinesLayout.Label.Color=Colors.White";
mostCurrent._listview1.getTwoLinesLayout().Label.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 72;BA.debugLine="ListView1.TwoLinesLayout.SecondLabel.Color=Colors";
mostCurrent._listview1.getTwoLinesLayout().SecondLabel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 112;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 113;BA.debugLine="ListView1.Clear";
mostCurrent._listview1.Clear();
 //BA.debugLineNum = 114;BA.debugLine="posisi1";
_posisi1();
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 274;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 275;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 276;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 278;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 279;BA.debugLine="StartActivity(current_pos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._current_pos.getObject()));
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 282;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 284;BA.debugLine="End Sub";
return "";
}
public static String  _button4_click() throws Exception{
 //BA.debugLineNum = 286;BA.debugLine="Sub Button4_Click";
 //BA.debugLineNum = 287;BA.debugLine="StartActivity(report)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._report.getObject()));
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _button5_click() throws Exception{
 //BA.debugLineNum = 290;BA.debugLine="Sub Button5_Click";
 //BA.debugLineNum = 291;BA.debugLine="StartActivity(radius)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._radius.getObject()));
 //BA.debugLineNum = 292;BA.debugLine="End Sub";
return "";
}
public static String  _button6_click() throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub Button6_Click";
 //BA.debugLineNum = 107;BA.debugLine="WebView1.LoadUrl(\"\"&Main.Server&\"peta2.php?distri";
mostCurrent._webview1.LoadUrl(""+mostCurrent._main._server+"peta2.php?district="+_district_terpilih.toUpperCase()+"&jenis="+_jenis_terpilih.toUpperCase()+"&year="+_tahun_terpilih+"&bulan="+_bulan_terpilih);
 //BA.debugLineNum = 108;BA.debugLine="Log(\"\"&Main.Server&\"peta2.php?district=\"&district";
anywheresoftware.b4a.keywords.Common.Log(""+mostCurrent._main._server+"peta2.php?district="+_district_terpilih.toUpperCase()+"&jenis="+_jenis_terpilih.toUpperCase()+"&year="+_tahun_terpilih+"&bulan="+_bulan_terpilih);
 //BA.debugLineNum = 109;BA.debugLine="carifilter";
_carifilter();
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _carifilter() throws Exception{
 //BA.debugLineNum = 81;BA.debugLine="Sub carifilter";
 //BA.debugLineNum = 82;BA.debugLine="ProgressDialogShow(\"Loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading..."));
 //BA.debugLineNum = 83;BA.debugLine="If bulan_terpilih==\"\" And district_terpilih==\"\" A";
if ((_bulan_terpilih).equals("") && (_district_terpilih).equals("") && (_jenis_terpilih).equals("")) { 
 //BA.debugLineNum = 84;BA.debugLine="ExecuteRemoteQuery(\"SELECT distinct a.date,a.id,";
_executeremotequery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and date_part('year', a.date)="+_tahun_terpilih+"","cari_filter");
 }else if((_bulan_terpilih).equals("") && (_district_terpilih).equals("")) { 
 //BA.debugLineNum = 86;BA.debugLine="Log(jenis_terpilih)";
anywheresoftware.b4a.keywords.Common.Log(_jenis_terpilih);
 //BA.debugLineNum = 87;BA.debugLine="ExecuteRemoteQuery(\"SELECT distinct a.date,a.id,";
_executeremotequery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and b.id like '%"+_jenis_terpilih.toUpperCase()+"%' and date_part('year', a.date)="+_tahun_terpilih+"","cari_filter");
 }else if((_district_terpilih).equals("") && (_jenis_terpilih).equals("")) { 
 //BA.debugLineNum = 89;BA.debugLine="ExecuteRemoteQuery(\"SELECT distinct a.date,a.id,";
_executeremotequery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and date_part('year', a.date)="+_tahun_terpilih+" and date_part('month', a.date)="+_bulan_terpilih+"","cari_filter");
 }else if((_bulan_terpilih).equals("") && (_jenis_terpilih).equals("")) { 
 //BA.debugLineNum = 91;BA.debugLine="ExecuteRemoteQuery(\"SELECT distinct a.date,a.id,";
_executeremotequery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and district.id like '%"+_district_terpilih.toUpperCase()+"%' and date_part('year', a.date)="+_tahun_terpilih+"","cari_filter");
 }else if((_bulan_terpilih).equals("")) { 
 //BA.debugLineNum = 93;BA.debugLine="ExecuteRemoteQuery(\"SELECT distinct a.date,a.id,";
_executeremotequery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and district.id like '%"+_district_terpilih.toUpperCase()+"%' and b.id="+_jenis_terpilih.toUpperCase()+" and date_part('year', a.date)="+_tahun_terpilih+"","cari_filter");
 }else if((_district_terpilih).equals("")) { 
 //BA.debugLineNum = 95;BA.debugLine="ExecuteRemoteQuery(\"SELECT distinct a.date,a.id,";
_executeremotequery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom)  and b.id like '%"+_jenis_terpilih.toUpperCase()+"%' and date_part('year', a.date)="+_tahun_terpilih+" and date_part('month', a.date)="+_bulan_terpilih+"","cari_filter");
 }else if((_jenis_terpilih).equals("")) { 
 //BA.debugLineNum = 97;BA.debugLine="ExecuteRemoteQuery(\"SELECT distinct a.date,a.id,";
_executeremotequery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and district.id like '%"+_district_terpilih.toUpperCase()+"%' and date_part('year', a.date)="+_tahun_terpilih+" and date_part('month', a.date)='"+_bulan_terpilih+"'","cari_filter");
 }else if((_bulan_terpilih).equals("") && (_district_terpilih).equals("") && (_jenis_terpilih).equals("") && BA.ObjectToBoolean(_tahun_terpilih)) { 
 //BA.debugLineNum = 99;BA.debugLine="Msgbox(\"Pilih Filter!\",\"Pemberitahun\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Pilih Filter!"),BA.ObjectToCharSequence("Pemberitahun"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 101;BA.debugLine="ExecuteRemoteQuery(\"SELECT distinct a.date,a.id,";
_executeremotequery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and district.id like '%"+_district_terpilih.toUpperCase()+"%' and b.id like '%"+_jenis_terpilih.toUpperCase()+"%' and date_part('year', a.date)="+_tahun_terpilih+" and date_part('month', a.date)="+_bulan_terpilih+"","cari_filter");
 };
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _executeremotequery(String _query,String _jobname) throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
 //BA.debugLineNum = 143;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 144;BA.debugLine="Dim Job As HttpJob";
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 145;BA.debugLine="Job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,pencarian.getObject());
 //BA.debugLineNum = 146;BA.debugLine="Job.PostString(\"\"&Main.Server&\"json.php\",Query)";
_job._poststring(""+mostCurrent._main._server+"json.php",_query);
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 40;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private Button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private Button3 As Button";
mostCurrent._button3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private Button4 As Button";
mostCurrent._button4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private Button5 As Button";
mostCurrent._button5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private Spinner1 As Spinner";
mostCurrent._spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private Spinner2 As Spinner";
mostCurrent._spinner2 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private Spinner3 As Spinner";
mostCurrent._spinner3 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private Spinner4 As Spinner";
mostCurrent._spinner4 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private Button6 As Button";
mostCurrent._button6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _res = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _district_array = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
b4a.example.pencarian._districts_lines _f = null;
anywheresoftware.b4a.objects.collections.List _jenis_array = null;
b4a.example.pencarian._jenis_lines _l = null;
anywheresoftware.b4a.objects.collections.List _tahun_array = null;
b4a.example.pencarian._tahuns_lines _b = null;
anywheresoftware.b4a.objects.collections.List _bulan_array = null;
b4a.example.pencarian._bulans_lines _t = null;
anywheresoftware.b4a.objects.collections.List _cari_array = null;
b4a.example.pencarian._daftardisaster_lines _c = null;
 //BA.debugLineNum = 149;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 150;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 151;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 152;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 153;BA.debugLine="res = Job.GetString";
_res = _job._getstring();
 //BA.debugLineNum = 154;BA.debugLine="Log(\"Response :\"& res)";
anywheresoftware.b4a.keywords.Common.Log("Response :"+_res);
 //BA.debugLineNum = 155;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 156;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 157;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,_tampil_kecamatan,_tampil_jenis,_tampil_tahun,_tampil_bulan,_cari_filter)) {
case 0: {
 //BA.debugLineNum = 159;BA.debugLine="Dim district_array As List";
_district_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 160;BA.debugLine="district_array = parser.NextArray";
_district_array = _parser.NextArray();
 //BA.debugLineNum = 162;BA.debugLine="For i=0 To district_array.Size -1";
{
final int step12 = 1;
final int limit12 = (int) (_district_array.getSize()-1);
_i = (int) (0) ;
for (;(step12 > 0 && _i <= limit12) || (step12 < 0 && _i >= limit12) ;_i = ((int)(0 + _i + step12))  ) {
 //BA.debugLineNum = 163;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 164;BA.debugLine="m = district_array.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_district_array.Get(_i)));
 //BA.debugLineNum = 165;BA.debugLine="Dim f As districts_lines";
_f = new b4a.example.pencarian._districts_lines();
 //BA.debugLineNum = 166;BA.debugLine="f.Initialize";
_f.Initialize();
 //BA.debugLineNum = 167;BA.debugLine="f.id= m.Get(\"id\")";
_f.id = BA.ObjectToString(_m.Get((Object)("id")));
 //BA.debugLineNum = 168;BA.debugLine="f.name= m.Get(\"name\")";
_f.name = BA.ObjectToString(_m.Get((Object)("name")));
 //BA.debugLineNum = 169;BA.debugLine="Spinner4.Add(f.name)";
mostCurrent._spinner4.Add(_f.name);
 //BA.debugLineNum = 170;BA.debugLine="Log(f.name)";
anywheresoftware.b4a.keywords.Common.Log(_f.name);
 //BA.debugLineNum = 171;BA.debugLine="arrayDistrict(i) = f.id";
_arraydistrict[_i] = _f.id;
 }
};
 break; }
case 1: {
 //BA.debugLineNum = 174;BA.debugLine="Dim jenis_array As List";
_jenis_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 175;BA.debugLine="jenis_array = parser.NextArray";
_jenis_array = _parser.NextArray();
 //BA.debugLineNum = 177;BA.debugLine="For i=0 To jenis_array.Size -1";
{
final int step26 = 1;
final int limit26 = (int) (_jenis_array.getSize()-1);
_i = (int) (0) ;
for (;(step26 > 0 && _i <= limit26) || (step26 < 0 && _i >= limit26) ;_i = ((int)(0 + _i + step26))  ) {
 //BA.debugLineNum = 178;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 179;BA.debugLine="m = jenis_array.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_jenis_array.Get(_i)));
 //BA.debugLineNum = 180;BA.debugLine="Dim l As jenis_lines";
_l = new b4a.example.pencarian._jenis_lines();
 //BA.debugLineNum = 181;BA.debugLine="l.Initialize";
_l.Initialize();
 //BA.debugLineNum = 182;BA.debugLine="l.id= m.Get(\"id\")";
_l.id = BA.ObjectToString(_m.Get((Object)("id")));
 //BA.debugLineNum = 183;BA.debugLine="l.name = m.Get(\"name\")";
_l.name = BA.ObjectToString(_m.Get((Object)("name")));
 //BA.debugLineNum = 184;BA.debugLine="Spinner3.Add(l.name)";
mostCurrent._spinner3.Add(_l.name);
 //BA.debugLineNum = 185;BA.debugLine="Log(l.name)";
anywheresoftware.b4a.keywords.Common.Log(_l.name);
 //BA.debugLineNum = 186;BA.debugLine="arrayJenis(i) = l.id";
_arrayjenis[_i] = _l.id;
 }
};
 break; }
case 2: {
 //BA.debugLineNum = 189;BA.debugLine="Dim tahun_array As List";
_tahun_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 190;BA.debugLine="tahun_array = parser.NextArray";
_tahun_array = _parser.NextArray();
 //BA.debugLineNum = 192;BA.debugLine="For i=0 To tahun_array.Size -1";
{
final int step40 = 1;
final int limit40 = (int) (_tahun_array.getSize()-1);
_i = (int) (0) ;
for (;(step40 > 0 && _i <= limit40) || (step40 < 0 && _i >= limit40) ;_i = ((int)(0 + _i + step40))  ) {
 //BA.debugLineNum = 193;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 194;BA.debugLine="m = tahun_array.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_tahun_array.Get(_i)));
 //BA.debugLineNum = 195;BA.debugLine="Dim b As tahuns_lines";
_b = new b4a.example.pencarian._tahuns_lines();
 //BA.debugLineNum = 196;BA.debugLine="b.Initialize";
_b.Initialize();
 //BA.debugLineNum = 197;BA.debugLine="b.id= m.Get(\"id\")";
_b.id = BA.ObjectToString(_m.Get((Object)("id")));
 //BA.debugLineNum = 198;BA.debugLine="b.name= m.Get(\"name\")";
_b.name = BA.ObjectToString(_m.Get((Object)("name")));
 //BA.debugLineNum = 199;BA.debugLine="Spinner1.Add(b.name)";
mostCurrent._spinner1.Add(_b.name);
 //BA.debugLineNum = 200;BA.debugLine="arrayTahun(i) = b.name";
_arraytahun[_i] = _b.name;
 }
};
 break; }
case 3: {
 //BA.debugLineNum = 203;BA.debugLine="Dim bulan_array As List";
_bulan_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 204;BA.debugLine="bulan_array = parser.NextArray";
_bulan_array = _parser.NextArray();
 //BA.debugLineNum = 206;BA.debugLine="For i=0 To bulan_array.Size -1";
{
final int step53 = 1;
final int limit53 = (int) (_bulan_array.getSize()-1);
_i = (int) (0) ;
for (;(step53 > 0 && _i <= limit53) || (step53 < 0 && _i >= limit53) ;_i = ((int)(0 + _i + step53))  ) {
 //BA.debugLineNum = 207;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 208;BA.debugLine="m = bulan_array.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_bulan_array.Get(_i)));
 //BA.debugLineNum = 209;BA.debugLine="Dim t  As bulans_lines";
_t = new b4a.example.pencarian._bulans_lines();
 //BA.debugLineNum = 210;BA.debugLine="t.Initialize";
_t.Initialize();
 //BA.debugLineNum = 211;BA.debugLine="t.id= m.Get(\"index\")";
_t.id = BA.ObjectToString(_m.Get((Object)("index")));
 //BA.debugLineNum = 212;BA.debugLine="t.name= m.Get(\"name\")";
_t.name = BA.ObjectToString(_m.Get((Object)("name")));
 //BA.debugLineNum = 213;BA.debugLine="Spinner2.Add(t.name)";
mostCurrent._spinner2.Add(_t.name);
 //BA.debugLineNum = 214;BA.debugLine="Log(t.name)";
anywheresoftware.b4a.keywords.Common.Log(_t.name);
 //BA.debugLineNum = 215;BA.debugLine="arrayBulan(i) = t.id";
_arraybulan[_i] = _t.id;
 }
};
 break; }
case 4: {
 //BA.debugLineNum = 218;BA.debugLine="ListView1.Clear";
mostCurrent._listview1.Clear();
 //BA.debugLineNum = 219;BA.debugLine="Dim cari_array As List";
_cari_array = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 220;BA.debugLine="cari_array = parser.NextArray";
_cari_array = _parser.NextArray();
 //BA.debugLineNum = 221;BA.debugLine="If cari_array.Size -1 < 0 Then";
if (_cari_array.getSize()-1<0) { 
 //BA.debugLineNum = 222;BA.debugLine="Msgbox(\"Disaster Area Not Found\", \"Pemberitah";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Disaster Area Not Found"),BA.ObjectToCharSequence("Pemberitahuan"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 224;BA.debugLine="For i=0 To cari_array.Size -1";
{
final int step71 = 1;
final int limit71 = (int) (_cari_array.getSize()-1);
_i = (int) (0) ;
for (;(step71 > 0 && _i <= limit71) || (step71 < 0 && _i >= limit71) ;_i = ((int)(0 + _i + step71))  ) {
 //BA.debugLineNum = 225;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 226;BA.debugLine="m = cari_array.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_cari_array.Get(_i)));
 //BA.debugLineNum = 227;BA.debugLine="Dim c  As daftardisaster_lines";
_c = new b4a.example.pencarian._daftardisaster_lines();
 //BA.debugLineNum = 228;BA.debugLine="c.Initialize";
_c.Initialize();
 //BA.debugLineNum = 229;BA.debugLine="c.isi1= m.Get(\"id\")";
_c.isi1 = BA.ObjectToString(_m.Get((Object)("id")));
 //BA.debugLineNum = 230;BA.debugLine="c.isi2= m.Get(\"name\")";
_c.isi2 = BA.ObjectToString(_m.Get((Object)("name")));
 //BA.debugLineNum = 231;BA.debugLine="c.isi3= m.Get(\"address\")";
_c.isi3 = BA.ObjectToString(_m.Get((Object)("address")));
 //BA.debugLineNum = 232;BA.debugLine="c.isi4=m.Get(\"lat\")";
_c.isi4 = BA.ObjectToString(_m.Get((Object)("lat")));
 //BA.debugLineNum = 233;BA.debugLine="c.isi5=m.Get(\"lon\")";
_c.isi5 = BA.ObjectToString(_m.Get((Object)("lon")));
 //BA.debugLineNum = 234;BA.debugLine="ListView1.AddTwoLines2(c.isi2, c.isi3,c.isi1";
mostCurrent._listview1.AddTwoLines2(BA.ObjectToCharSequence(_c.isi2),BA.ObjectToCharSequence(_c.isi3),(Object)(_c.isi1));
 }
};
 };
 break; }
}
;
 };
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 295;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 296;BA.debugLine="id=Value";
_id = BA.ObjectToString(_value);
 //BA.debugLineNum = 297;BA.debugLine="Log(\"id\"&id)";
anywheresoftware.b4a.keywords.Common.Log("id"+_id);
 //BA.debugLineNum = 298;BA.debugLine="StartActivity(\"informasi\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("informasi"));
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
return "";
}
public static String  _posisi1() throws Exception{
 //BA.debugLineNum = 77;BA.debugLine="Sub posisi1";
 //BA.debugLineNum = 78;BA.debugLine="WebView1.LoadUrl(\"\"&Main.Server&\"map.html\")";
mostCurrent._webview1.LoadUrl(""+mostCurrent._main._server+"map.html");
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private cari_filter = \"cari_filter\" As String";
_cari_filter = "cari_filter";
 //BA.debugLineNum = 10;BA.debugLine="Dim id, address, name, latitude, longitude As Str";
_id = "";
_address = "";
_name = "";
_latitude = "";
_longitude = "";
 //BA.debugLineNum = 11;BA.debugLine="Type daftardisaster_lines (isi1 As String, isi2 A";
;
 //BA.debugLineNum = 12;BA.debugLine="Private Tampil_Tahun = \"Tampil_Tahun\" As String";
_tampil_tahun = "Tampil_Tahun";
 //BA.debugLineNum = 13;BA.debugLine="Private Tampil_Bulan= \"Tampil_Bulan\" As String";
_tampil_bulan = "Tampil_Bulan";
 //BA.debugLineNum = 14;BA.debugLine="Private Tampil_Jenis= \"Tampil_Jenis\" As String";
_tampil_jenis = "Tampil_Jenis";
 //BA.debugLineNum = 15;BA.debugLine="Private Tampil_Kecamatan = \"Tampil_Kecamatan\" As";
_tampil_kecamatan = "Tampil_Kecamatan";
 //BA.debugLineNum = 17;BA.debugLine="Dim tahun_terpilih As String";
_tahun_terpilih = "";
 //BA.debugLineNum = 18;BA.debugLine="Type tahuns_lines (id As String, name As String)";
;
 //BA.debugLineNum = 19;BA.debugLine="Dim arrayTahun(100) As String";
_arraytahun = new String[(int) (100)];
java.util.Arrays.fill(_arraytahun,"");
 //BA.debugLineNum = 21;BA.debugLine="Dim bulan_terpilih As String";
_bulan_terpilih = "";
 //BA.debugLineNum = 22;BA.debugLine="Type bulans_lines (id As String, name As String)";
;
 //BA.debugLineNum = 23;BA.debugLine="Dim arrayBulan(100) As String";
_arraybulan = new String[(int) (100)];
java.util.Arrays.fill(_arraybulan,"");
 //BA.debugLineNum = 25;BA.debugLine="Dim district_terpilih As String";
_district_terpilih = "";
 //BA.debugLineNum = 26;BA.debugLine="Type districts_lines (id As String,name As String";
;
 //BA.debugLineNum = 27;BA.debugLine="Dim arrayDistrict(100) As String";
_arraydistrict = new String[(int) (100)];
java.util.Arrays.fill(_arraydistrict,"");
 //BA.debugLineNum = 29;BA.debugLine="Dim jenis_terpilih As String";
_jenis_terpilih = "";
 //BA.debugLineNum = 30;BA.debugLine="Type jenis_lines (id As String,name As String)";
;
 //BA.debugLineNum = 31;BA.debugLine="Dim arrayJenis(100) As String";
_arrayjenis = new String[(int) (100)];
java.util.Arrays.fill(_arrayjenis,"");
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _spinner1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 244;BA.debugLine="Sub Spinner1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 245;BA.debugLine="Spinner1.Prompt=\"Select Year\"";
mostCurrent._spinner1.setPrompt(BA.ObjectToCharSequence("Select Year"));
 //BA.debugLineNum = 246;BA.debugLine="Spinner1.Color=Colors.Blue";
mostCurrent._spinner1.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 247;BA.debugLine="tahun_terpilih = arrayTahun(Position)";
_tahun_terpilih = _arraytahun[_position];
 //BA.debugLineNum = 248;BA.debugLine="Log(tahun_terpilih)";
anywheresoftware.b4a.keywords.Common.Log(_tahun_terpilih);
 //BA.debugLineNum = 250;BA.debugLine="End Sub";
return "";
}
public static String  _spinner2_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 252;BA.debugLine="Sub Spinner2_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 253;BA.debugLine="Spinner2.Prompt=\"Select Month\"";
mostCurrent._spinner2.setPrompt(BA.ObjectToCharSequence("Select Month"));
 //BA.debugLineNum = 254;BA.debugLine="Spinner2.Color=Colors.Blue";
mostCurrent._spinner2.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 255;BA.debugLine="bulan_terpilih = arrayBulan(Position)";
_bulan_terpilih = _arraybulan[_position];
 //BA.debugLineNum = 256;BA.debugLine="Log(bulan_terpilih)";
anywheresoftware.b4a.keywords.Common.Log(_bulan_terpilih);
 //BA.debugLineNum = 257;BA.debugLine="End Sub";
return "";
}
public static String  _spinner3_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 259;BA.debugLine="Sub Spinner3_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 260;BA.debugLine="Spinner3.Prompt=\"Select Type\"";
mostCurrent._spinner3.setPrompt(BA.ObjectToCharSequence("Select Type"));
 //BA.debugLineNum = 261;BA.debugLine="Spinner3.Color=Colors.Blue";
mostCurrent._spinner3.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 262;BA.debugLine="jenis_terpilih = arrayJenis(Position)";
_jenis_terpilih = _arrayjenis[_position];
 //BA.debugLineNum = 263;BA.debugLine="Log(jenis_terpilih)";
anywheresoftware.b4a.keywords.Common.Log(_jenis_terpilih);
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
return "";
}
public static String  _spinner4_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 266;BA.debugLine="Sub Spinner4_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 267;BA.debugLine="Spinner4.Prompt=\"Select District\"";
mostCurrent._spinner4.setPrompt(BA.ObjectToCharSequence("Select District"));
 //BA.debugLineNum = 268;BA.debugLine="Spinner4.Color=Colors.Blue";
mostCurrent._spinner4.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 269;BA.debugLine="district_terpilih = arrayDistrict(Position)";
_district_terpilih = _arraydistrict[_position];
 //BA.debugLineNum = 270;BA.debugLine="Log(district_terpilih)";
anywheresoftware.b4a.keywords.Common.Log(_district_terpilih);
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
return "";
}
public static String  _tampilkan_bulan() throws Exception{
 //BA.debugLineNum = 124;BA.debugLine="Sub tampilkan_bulan";
 //BA.debugLineNum = 126;BA.debugLine="ProgressDialogShow(\"Loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading..."));
 //BA.debugLineNum = 128;BA.debugLine="ExecuteRemoteQuery(\"SELECT * from month ORDER BY";
_executeremotequery("SELECT * from month ORDER BY id","Tampil_Bulan");
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static String  _tampilkan_jenis() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub tampilkan_jenis";
 //BA.debugLineNum = 132;BA.debugLine="ProgressDialogShow(\"Loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading..."));
 //BA.debugLineNum = 134;BA.debugLine="ExecuteRemoteQuery(\"SELECT * from type_disaster\",";
_executeremotequery("SELECT * from type_disaster","Tampil_Jenis");
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return "";
}
public static String  _tampilkan_kecamatan() throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Sub tampilkan_kecamatan";
 //BA.debugLineNum = 138;BA.debugLine="ProgressDialogShow(\"Loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading..."));
 //BA.debugLineNum = 140;BA.debugLine="ExecuteRemoteQuery(\"SELECT * from district\",\"Tamp";
_executeremotequery("SELECT * from district","Tampil_Kecamatan");
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}
public static String  _tampilkan_tahun() throws Exception{
 //BA.debugLineNum = 117;BA.debugLine="Sub tampilkan_tahun";
 //BA.debugLineNum = 119;BA.debugLine="ProgressDialogShow(\"Loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Loading..."));
 //BA.debugLineNum = 121;BA.debugLine="ExecuteRemoteQuery(\"SELECT * from year\",\"Tampil_T";
_executeremotequery("SELECT * from year","Tampil_Tahun");
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
}
