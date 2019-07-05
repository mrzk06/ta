package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_cam{

public static void LS_480x320_1(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("panel3").vw.setLeft((int)(0d));
views.get("panel3").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 3;BA.debugLine="Panel3.SetTopAndBottom(0,100%y)"[cam/480x320,scale=1]
views.get("panel3").vw.setTop((int)(0d));
views.get("panel3").vw.setHeight((int)((100d / 100 * height) - (0d)));
//BA.debugLineNum = 4;BA.debugLine="Panel1.SetLeftAndRight(5%x,95%x)"[cam/480x320,scale=1]
views.get("panel1").vw.setLeft((int)((5d / 100 * width)));
views.get("panel1").vw.setWidth((int)((95d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 5;BA.debugLine="Panel1.SetTopAndBottom(0,80%y)"[cam/480x320,scale=1]
views.get("panel1").vw.setTop((int)(0d));
views.get("panel1").vw.setHeight((int)((80d / 100 * height) - (0d)));
//BA.debugLineNum = 6;BA.debugLine="Panel2.SetLeftAndRight(0,100%x)"[cam/480x320,scale=1]
views.get("panel2").vw.setLeft((int)(0d));
views.get("panel2").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 7;BA.debugLine="Panel2.SetTopAndBottom(80%y,100%y)"[cam/480x320,scale=1]
views.get("panel2").vw.setTop((int)((80d / 100 * height)));
views.get("panel2").vw.setHeight((int)((100d / 100 * height) - ((80d / 100 * height))));
//BA.debugLineNum = 8;BA.debugLine="btnbrowse.SetLeftAndRight(20%x,35%x)"[cam/480x320,scale=1]
views.get("btnbrowse").vw.setLeft((int)((20d / 100 * width)));
views.get("btnbrowse").vw.setWidth((int)((35d / 100 * width) - ((20d / 100 * width))));
//BA.debugLineNum = 9;BA.debugLine="btnbrowse.SetTopAndBottom(80%y,100%y)"[cam/480x320,scale=1]
views.get("btnbrowse").vw.setTop((int)((80d / 100 * height)));
views.get("btnbrowse").vw.setHeight((int)((100d / 100 * height) - ((80d / 100 * height))));
//BA.debugLineNum = 11;BA.debugLine="btnsnap.SetLeftAndRight(40%x,60%x)"[cam/480x320,scale=1]
views.get("btnsnap").vw.setLeft((int)((40d / 100 * width)));
views.get("btnsnap").vw.setWidth((int)((60d / 100 * width) - ((40d / 100 * width))));
//BA.debugLineNum = 12;BA.debugLine="btnsnap.SetTopAndBottom(80%y,100%y)"[cam/480x320,scale=1]
views.get("btnsnap").vw.setTop((int)((80d / 100 * height)));
views.get("btnsnap").vw.setHeight((int)((100d / 100 * height) - ((80d / 100 * height))));
//BA.debugLineNum = 14;BA.debugLine="btnvideo.SetLeftAndRight(65%x,80%x)"[cam/480x320,scale=1]
views.get("btnvideo").vw.setLeft((int)((65d / 100 * width)));
views.get("btnvideo").vw.setWidth((int)((80d / 100 * width) - ((65d / 100 * width))));
//BA.debugLineNum = 15;BA.debugLine="btnvideo.SetTopAndBottom(80%y,100%y)"[cam/480x320,scale=1]
views.get("btnvideo").vw.setTop((int)((80d / 100 * height)));
views.get("btnvideo").vw.setHeight((int)((100d / 100 * height) - ((80d / 100 * height))));

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}