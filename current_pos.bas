B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim gpsClient As GPS
	Dim userLocation As Location
	Dim latUser, lngUser As Double

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private WebView1 As WebView
	Private Button1 As Button
	Private Button2 As Button
	Private Button3 As Button
	Private Button4 As Button
	Private Button5 As Button

End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("current_pos")
	If FirstTime Then
		gpsClient.Initialize("gpsClient")
		userLocation.Initialize
	End If

End Sub

Sub cekGPS 'melakukan mengecekan GPS pada pengguna
	If (latUser=0 And lngUser=0) Then
		If gpsClient.GPSEnabled=False Then
			ToastMessageShow("Enable GPS First", True)
			StartActivity(gpsClient.LocationSettingsIntent)
		Else
			gpsClient.Start(0,0)
			ProgressDialogShow("Wait For Location")
		End If
	Else
		petaClient
	End If
End Sub

Sub gpsClient_LocationChanged (gpsLocation As Location) 'mengambil koordinat pengguna
	ProgressDialogHide
	userLocation=gpsLocation
	gpsClient.Stop
	latUser=userLocation.Latitude
	lngUser=userLocation.Longitude
	petaClient
End Sub

Sub petaClient
	WebView1.LoadUrl(""&Main.Server&"peta.php?lat="&latUser&"&lng="&lngUser)
End Sub

Sub Activity_Resume
    cekGPS
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	gpsClient.Stop
End Sub
Sub Button1_Click
	StartActivity(Main)
End Sub

Sub Button2_Click
	
End Sub

Sub Button3_Click
	StartActivity(pencarian)
End Sub

Sub Button4_Click
	StartActivity(report)
End Sub

Sub Button5_Click
	StartActivity(radius)
End Sub
