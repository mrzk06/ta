package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_home{

public static void LS_320x480_1(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("panel1").vw.setTop((int)(0d));
views.get("panel1").vw.setHeight((int)((100d / 100 * height) - (0d)));
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
views.get("about").vw.setLeft((int)((0d / 100 * width)));
views.get("about").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("about").vw.setTop((int)((12d / 100 * height)));
views.get("about").vw.setHeight((int)((89d / 100 * height) - ((12d / 100 * height))));
views.get("panel3").vw.setLeft((int)(0d));
views.get("panel3").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("panel3").vw.setTop((int)((90d / 100 * height)));
views.get("panel3").vw.setHeight((int)((100d / 100 * height) - ((90d / 100 * height))));
views.get("button1").vw.setLeft((int)((0d / 100 * width)));
views.get("button1").vw.setWidth((int)((20d / 100 * width) - ((0d / 100 * width))));
views.get("button1").vw.setTop((int)((0d / 100 * height)));
views.get("button1").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));
views.get("button2").vw.setLeft((int)((20d / 100 * width)));
views.get("button2").vw.setWidth((int)((40d / 100 * width) - ((20d / 100 * width))));
views.get("button2").vw.setTop((int)((0d / 100 * height)));
views.get("button2").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));
views.get("button3").vw.setLeft((int)((40d / 100 * width)));
views.get("button3").vw.setWidth((int)((60d / 100 * width) - ((40d / 100 * width))));
views.get("button3").vw.setTop((int)((0d / 100 * height)));
views.get("button3").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));
views.get("button4").vw.setLeft((int)((60d / 100 * width)));
views.get("button4").vw.setWidth((int)((80d / 100 * width) - ((60d / 100 * width))));
views.get("button4").vw.setTop((int)((0d / 100 * height)));
views.get("button4").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));
views.get("button5").vw.setLeft((int)((80d / 100 * width)));
views.get("button5").vw.setWidth((int)((100d / 100 * width) - ((80d / 100 * width))));
//BA.debugLineNum = 28;BA.debugLine="Button5.SetTopAndBottom(0%y,10%y)"[home/320x480,scale=1]
views.get("button5").vw.setTop((int)((0d / 100 * height)));
views.get("button5").vw.setHeight((int)((10d / 100 * height) - ((0d / 100 * height))));

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}