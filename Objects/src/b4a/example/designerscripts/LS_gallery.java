package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_gallery{

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
views.get("label3").vw.setLeft((int)((30d / 100 * width)));
views.get("label3").vw.setWidth((int)((70d / 100 * width) - ((30d / 100 * width))));
views.get("label3").vw.setTop((int)((15d / 100 * height)));
views.get("label3").vw.setHeight((int)((20d / 100 * height) - ((15d / 100 * height))));
views.get("listview1").vw.setLeft((int)((5d / 100 * width)));
views.get("listview1").vw.setWidth((int)((95d / 100 * width) - ((5d / 100 * width))));
views.get("listview1").vw.setTop((int)((23d / 100 * height)));
views.get("listview1").vw.setHeight((int)((95d / 100 * height) - ((23d / 100 * height))));
views.get("webview1").vw.setLeft((int)((5d / 100 * width)));
views.get("webview1").vw.setWidth((int)((95d / 100 * width) - ((5d / 100 * width))));
views.get("webview1").vw.setTop((int)((23d / 100 * height)));
views.get("webview1").vw.setHeight((int)((95d / 100 * height) - ((23d / 100 * height))));

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}