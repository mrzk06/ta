package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_current_pos{

public static void LS_320x480_1(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("webview1").vw.setLeft((int)((2d / 100 * width)));
views.get("webview1").vw.setWidth((int)((98d / 100 * width) - ((2d / 100 * width))));
views.get("webview1").vw.setTop((int)((13d / 100 * height)));
views.get("webview1").vw.setHeight((int)((89d / 100 * height) - ((13d / 100 * height))));
views.get("imageview1").vw.setLeft((int)((5d / 100 * width)));
views.get("imageview1").vw.setWidth((int)((20d / 100 * width) - ((5d / 100 * width))));
views.get("imageview1").vw.setTop((int)((1d / 100 * height)));
views.get("imageview1").vw.setHeight((int)((11d / 100 * height) - ((1d / 100 * height))));
views.get("label1").vw.setTop((int)((1d / 100 * height)));
views.get("label1").vw.setHeight((int)((11d / 100 * height) - ((1d / 100 * height))));
views.get("label1").vw.setLeft((int)((24d / 100 * width)));
views.get("label1").vw.setWidth((int)((90d / 100 * width) - ((24d / 100 * width))));
views.get("panel2").vw.setLeft((int)(0d));
views.get("panel2").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("panel2").vw.setTop((int)(0d));
views.get("panel2").vw.setHeight((int)((12d / 100 * height) - (0d)));
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("panel1").vw.setTop((int)(0d));
views.get("panel1").vw.setHeight((int)((100d / 100 * height) - (0d)));
views.get("panel3").vw.setLeft((int)(0d));
views.get("panel3").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 16;BA.debugLine="Panel3.SetTopAndBottom(90%y,100%y)"[current_pos/320x480,scale=1]
views.get("panel3").vw.setTop((int)((90d / 100 * height)));
views.get("panel3").vw.setHeight((int)((100d / 100 * height) - ((90d / 100 * height))));
//BA.debugLineNum = 18;BA.debugLine="Button1.SetLeftAndRight(0%x,20%x)"[current_pos/320x480,scale=1]
views.get("button1").vw.setLeft((int)((0d / 100 * width)));
views.get("button1").vw.setWidth((int)((20d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 19;BA.debugLine="Button1.SetTopAndBottom(0%y,10%y)"[current_pos/320x480,scale=1]
views.get("button1").vw.setTop((int)((0d / 100 * height)));
views.get("button1").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 20;BA.debugLine="Button2.SetLeftAndRight(20%x,40%x)"[current_pos/320x480,scale=1]
views.get("button2").vw.setLeft((int)((20d / 100 * width)));
views.get("button2").vw.setWidth((int)((40d / 100 * width) - ((20d / 100 * width))));
//BA.debugLineNum = 21;BA.debugLine="Button2.SetTopAndBottom(0%y,10%y)"[current_pos/320x480,scale=1]
views.get("button2").vw.setTop((int)((0d / 100 * height)));
views.get("button2").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 22;BA.debugLine="Button3.SetLeftAndRight(40%x,60%x)"[current_pos/320x480,scale=1]
views.get("button3").vw.setLeft((int)((40d / 100 * width)));
views.get("button3").vw.setWidth((int)((60d / 100 * width) - ((40d / 100 * width))));
//BA.debugLineNum = 23;BA.debugLine="Button3.SetTopAndBottom(0%y,10%y)"[current_pos/320x480,scale=1]
views.get("button3").vw.setTop((int)((0d / 100 * height)));
views.get("button3").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 24;BA.debugLine="Button4.SetLeftAndRight(60%x,80%x)"[current_pos/320x480,scale=1]
views.get("button4").vw.setLeft((int)((60d / 100 * width)));
views.get("button4").vw.setWidth((int)((80d / 100 * width) - ((60d / 100 * width))));
//BA.debugLineNum = 25;BA.debugLine="Button4.SetTopAndBottom(0%y,10%y)"[current_pos/320x480,scale=1]
views.get("button4").vw.setTop((int)((0d / 100 * height)));
views.get("button4").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 26;BA.debugLine="Button5.SetLeftAndRight(80%x,100%x)"[current_pos/320x480,scale=1]
views.get("button5").vw.setLeft((int)((80d / 100 * width)));
views.get("button5").vw.setWidth((int)((100d / 100 * width) - ((80d / 100 * width))));
//BA.debugLineNum = 27;BA.debugLine="Button5.SetTopAndBottom(0%y,10%y)"[current_pos/320x480,scale=1]
views.get("button5").vw.setTop((int)((0d / 100 * height)));
views.get("button5").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}