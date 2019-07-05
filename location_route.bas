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
	Dim latAsal, lngAsal, latTujuan, lngTujuan, latHenti, lngHenti, idmes As String
	Dim id, lat, lng, id_angkot, destination, route_color As String
	'Type angkotrm_lines (isi1 As String,isi2 As String,isi3 As String)
	'Dim ANGKOTRM="ANGKOTRM" As String

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private WebView1 As WebView
	Private Button6 As Button
	
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("location_route")
	posisi1
	latAsal = current_pos.latUser
	lngAsal = current_pos.lngUser
	'idmes = informasi.id

End Sub

Sub Activity_Resume
	latAsal = current_pos.latUser
	lngAsal = current_pos.lngUser

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ExecuteRemoteQuery(Query As String, JobName As String)
	Dim Job As HttpJob
	Job.Initialize(JobName, Me)
	Job.PostString(""&Main.server&"json.php", Query)
End Sub

Sub posisi1
	WebView1.LoadUrl(""&Main.Server&"peta.php?lat="&latTujuan&"&lng="&lngTujuan)
	Log("lat posisi 1: "&latTujuan&" Long : "&lngTujuan)
End Sub

Sub lihatrute(a1 As Float,a2 As Float, b1 As Float, b2 As Float)
	WebView1.LoadUrl(""&Main.Server&"route.php?a1="&latAsal&"&a2="&lngAsal&"&b1="&latTujuan&"&b2="&lngTujuan)
	Log(""&Main.Server&"route.php?a1="&latAsal&"&a2="&lngAsal&"&b1="&latTujuan&"&b2="&lngTujuan)
End Sub


Sub Button6_Click
	If current_pos.latUser=0 And current_pos.lngUser=0 Then
		Log("posisi saat ini belum ada")
		ToastMessageShow("Click Button Current Position First !", True)
		StartActivity(current_pos)
	Else
		lihatrute(latAsal,lngAsal,latTujuan,lngTujuan)
		Log(latTujuan)
		Log(lngTujuan)
		Log(latAsal)
	End If
End Sub


