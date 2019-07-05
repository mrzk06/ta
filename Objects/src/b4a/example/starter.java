package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class starter extends  android.app.Service{
	public static class starter_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (starter) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, starter.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, true, BA.class);
		}

	}
    static starter mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return starter.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "b4a.example", "b4a.example.starter");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.starter", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!true && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (starter) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (true) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (starter) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (true)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (starter) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        BA.LogInfo("** Service (starter) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.collections.Map _cache = null;
public static anywheresoftware.b4a.objects.collections.Map _tasks = null;
public static anywheresoftware.b4a.objects.collections.Map _ongoingtasks = null;
public b4a.example.dateutils _dateutils = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.current_pos _current_pos = null;
public b4a.example.pencarian _pencarian = null;
public b4a.example.report _report = null;
public b4a.example.radius _radius = null;
public b4a.example.informasi _informasi = null;
public b4a.example.location_route _location_route = null;
public b4a.example.gallery _gallery = null;
public b4a.example.takecam _takecam = null;
public b4a.example.takevid _takevid = null;
public b4a.example.scrollview _scrollview = null;
public static String  _download(anywheresoftware.b4a.objects.collections.Map _imageviewsmap) throws Exception{
int _i = 0;
String _link = "";
anywheresoftware.b4a.objects.ListViewWrapper _iv = null;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
 //BA.debugLineNum = 25;BA.debugLine="Sub Download (ImageViewsMap As Map)";
 //BA.debugLineNum = 26;BA.debugLine="For i = 0 To ImageViewsMap.Size - 1";
{
final int step1 = 1;
final int limit1 = (int) (_imageviewsmap.getSize()-1);
_i = (int) (0) ;
for (;(step1 > 0 && _i <= limit1) || (step1 < 0 && _i >= limit1) ;_i = ((int)(0 + _i + step1))  ) {
 //BA.debugLineNum = 27;BA.debugLine="tasks.Put(ImageViewsMap.GetKeyAt(i), ImageViewsM";
_tasks.Put(_imageviewsmap.GetKeyAt(_i),_imageviewsmap.GetValueAt(_i));
 //BA.debugLineNum = 28;BA.debugLine="Dim link As String = ImageViewsMap.GetValueAt(i)";
_link = BA.ObjectToString(_imageviewsmap.GetValueAt(_i));
 //BA.debugLineNum = 29;BA.debugLine="If cache.ContainsKey(link) Then";
if (_cache.ContainsKey((Object)(_link))) { 
 //BA.debugLineNum = 30;BA.debugLine="Dim iv As ListView = ImageViewsMap.GetKeyAt(i)";
_iv = new anywheresoftware.b4a.objects.ListViewWrapper();
_iv.setObject((anywheresoftware.b4a.objects.ListViewWrapper.SimpleListView)(_imageviewsmap.GetKeyAt(_i)));
 //BA.debugLineNum = 31;BA.debugLine="iv.SetBackgroundImage(cache.Get(link))";
_iv.SetBackgroundImageNew((android.graphics.Bitmap)(_cache.Get((Object)(_link))));
 }else if(_ongoingtasks.ContainsKey((Object)(_link))==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 33;BA.debugLine="ongoingTasks.Put(link, \"\")";
_ongoingtasks.Put((Object)(_link),(Object)(""));
 //BA.debugLineNum = 34;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 35;BA.debugLine="j.Initialize(link, Me)";
_j._initialize(processBA,_link,starter.getObject());
 //BA.debugLineNum = 36;BA.debugLine="j.Download(link)";
_j._download(_link);
 };
 }
};
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
int _i = 0;
String _link = "";
anywheresoftware.b4a.objects.ListViewWrapper _iv = null;
 //BA.debugLineNum = 41;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 42;BA.debugLine="ongoingTasks.Remove(Job.JobName)";
_ongoingtasks.Remove((Object)(_job._jobname));
 //BA.debugLineNum = 43;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 44;BA.debugLine="Dim bmp As Bitmap = Job.GetBitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_bmp = _job._getbitmap();
 //BA.debugLineNum = 45;BA.debugLine="cache.Put(Job.JobName, bmp)";
_cache.Put((Object)(_job._jobname),(Object)(_bmp.getObject()));
 //BA.debugLineNum = 46;BA.debugLine="If tasks.IsInitialized Then";
if (_tasks.IsInitialized()) { 
 //BA.debugLineNum = 47;BA.debugLine="For i = 0 To tasks.Size - 1";
{
final int step6 = 1;
final int limit6 = (int) (_tasks.getSize()-1);
_i = (int) (0) ;
for (;(step6 > 0 && _i <= limit6) || (step6 < 0 && _i >= limit6) ;_i = ((int)(0 + _i + step6))  ) {
 //BA.debugLineNum = 48;BA.debugLine="Dim link As String = tasks.GetValueAt(i)";
_link = BA.ObjectToString(_tasks.GetValueAt(_i));
 //BA.debugLineNum = 49;BA.debugLine="If link = Job.JobName Then";
if ((_link).equals(_job._jobname)) { 
 //BA.debugLineNum = 50;BA.debugLine="Dim iv As ListView = tasks.GetKeyAt(i)";
_iv = new anywheresoftware.b4a.objects.ListViewWrapper();
_iv.setObject((anywheresoftware.b4a.objects.ListViewWrapper.SimpleListView)(_tasks.GetKeyAt(_i)));
 //BA.debugLineNum = 51;BA.debugLine="iv.SetBackgroundImage(bmp)";
_iv.SetBackgroundImageNew((android.graphics.Bitmap)(_bmp.getObject()));
 };
 }
};
 };
 }else {
 //BA.debugLineNum = 56;BA.debugLine="Log(\"Error downloading image: \" & Job.JobName &";
anywheresoftware.b4a.keywords.Common.Log("Error downloading image: "+_job._jobname+anywheresoftware.b4a.keywords.Common.CRLF+_job._errormessage);
 };
 //BA.debugLineNum = 58;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Private cache As Map";
_cache = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 7;BA.debugLine="Private tasks As Map";
_tasks = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 8;BA.debugLine="Private ongoingTasks As Map";
_ongoingtasks = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 12;BA.debugLine="tasks.Initialize";
_tasks.Initialize();
 //BA.debugLineNum = 13;BA.debugLine="cache.Initialize";
_cache.Initialize();
 //BA.debugLineNum = 14;BA.debugLine="ongoingTasks.Initialize";
_ongoingtasks.Initialize();
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
}
