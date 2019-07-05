package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_vid{

public static void LS_320x480_1(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("panel1").vw.setLeft((int)((0d / 100 * width)));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("panel1").vw.setTop((int)((0d / 100 * height)));
views.get("panel1").vw.setHeight((int)((90d / 100 * height) - ((0d / 100 * height))));
views.get("panel2").vw.setLeft((int)((0d / 100 * width)));
views.get("panel2").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("panel2").vw.setTop((int)((90d / 100 * height)));
views.get("panel2").vw.setHeight((int)((100d / 100 * height) - ((90d / 100 * height))));
views.get("btnbrowse").vw.setLeft((int)((5d / 100 * width)));
views.get("btnbrowse").vw.setWidth((int)((25d / 100 * width) - ((5d / 100 * width))));
views.get("btnbrowse").vw.setTop((int)((90d / 100 * height)));
views.get("btnbrowse").vw.setHeight((int)((100d / 100 * height) - ((90d / 100 * height))));
views.get("btnsnap").vw.setLeft((int)((40d / 100 * width)));
views.get("btnsnap").vw.setWidth((int)((60d / 100 * width) - ((40d / 100 * width))));
views.get("btnsnap").vw.setTop((int)((90d / 100 * height)));
views.get("btnsnap").vw.setHeight((int)((100d / 100 * height) - ((90d / 100 * height))));
views.get("btncam").vw.setLeft((int)((75d / 100 * width)));
views.get("btncam").vw.setWidth((int)((95d / 100 * width) - ((75d / 100 * width))));
views.get("btncam").vw.setTop((int)((90d / 100 * height)));
views.get("btncam").vw.setHeight((int)((100d / 100 * height) - ((90d / 100 * height))));

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}