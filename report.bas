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
	Dim iddisaster As String

	
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private Tampil_Jeniss = "Tampil_Jeniss" As String
	Private Save_Case = "Save_Case" As String
	Private id_report = "id_report" As String
	Private id_reporter = "id_reporter" As String
	Dim jeniss_terpilih As String
	
	Type jeniss_lines (id As String,name As String)
	Dim arrayJeniss(100) As String
	
	Type reporter_lines (id As String, no_hp As String)
	Dim arrayReporter(100) As String
	
	Type report_lines (id As String)
	Dim arrayReport(100) As String

	Type save_lines (response_code As Int,message As String)
	Dim idreport_terpilih As String
	Dim Idreporter_terpilih As String
	
	Private WebView1 As WebView
	Private ScrollView1 As ScrollView
	
	Private EditText1 As EditText
	Private Spinner1 As Spinner
	'Private EditText2 As EditText
	'Private EditText3 As EditText
	Private EditText4 As EditText
	Private EditText5 As EditText
	Private EditText6 As EditText
	Private Button1 As Button
	Private Button2 As Button
	Private Button3 As Button
	Private Button4 As Button
	Private Button5 As Button
	Private Save As Button
	
	Private text_Label1 As String
	Private text_Label2 As String
	Private text_Label3 As String
	Private text_Label4 As String
	Private text_Label5 As String
	Private text_Label6 As String
	Private text_Label7 As String
	Private text_Label8 As String
	Private text_Label9 As String
	Private btncamera As Button

	
	Private idreport As EditText
	Private idreporter As EditText
	Private ProgressBar1 As ProgressBar
	Private Label8 As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("report")
	If FirstTime Then
		gpsClient.Initialize("gpsClient")
		userLocation.Initialize
	End If
	WebView1.LoadUrl(""&Main.Server&"map.html")
	ScrollView1.Panel.LoadLayout("formreport")
	tampilkan_jeniss
	keep
	EditText1.Text=latUser&","&lngUser
	Spinner1.TextSize=11
	

End Sub

Sub Activity_Resume
	cekGPS
	EditText6.Text=takecam.nameimages
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	gpsClient.Stop
End Sub

Sub petaClient
	WebView1.LoadUrl(""&Main.Server&"peta.php?lat="&latUser&"&lng="&lngUser)
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
	EditText1.Text=latUser&","&lngUser
End Sub


Sub Button1_Click
	StartActivity(Main)
End Sub

Sub Button2_Click
	StartActivity(current_pos)
End Sub

Sub Button3_Click
	StartActivity(pencarian)
End Sub

Sub Button4_Click
	
End Sub

Sub Button5_Click
	StartActivity(radius)
End Sub

Sub ExecuteRemoteQuery(Query As String, JobName As String)
	Dim Job As HttpJob
	Job.Initialize(JobName, Me)
	Job.PostString(""&Main.Server&"json.php",Query)
End Sub
Sub ExecuteRemoteQueryInsert(Query As String, JobName As String)
	Dim Job As HttpJob
	Job.Initialize(JobName, Me)
	Job.PostString(""&Main.Server&"addreport.php",Query)
	'Log(""&Main.Server&"addreport.php?idreport="&idreport_terpilih&"&idreporter="&Idreporter_terpilih&"&latuser="&latUser&"&lnguser="&lngUser&"&date="&text_Label4&"&time="&text_Label5&"&jenis="&text_Label3&"&reporter="&text_Label6&"&nohp="&text_Label7&"&image="&text_Label8&"")
End Sub

Sub Spinner1_ItemClick (Position As Int, Value As Object)
	Spinner1.Color=Colors.Blue
	jeniss_terpilih = arrayJeniss(Position)
	Log(jeniss_terpilih)
End Sub

Sub tampilkan_jeniss
	
	ProgressDialogShow("Loading...")
	ExecuteRemoteQuery("SELECT * from type_disaster","Tampil_Jeniss")
End Sub

Sub keep
	ProgressDialogShow("Loading...")
	ExecuteRemoteQuery("SELECT MAX(id) as id FROM report","id_report")
	ExecuteRemoteQuery("SELECT MAX(id) as id FROM reporter","id_reporter")
End Sub


Sub simpan
	
	EditText1.Text=latUser&" "&lngUser
	EditText6.Text=takecam.nameimages
	'text_Label1 = EditText1.Text
	text_Label2 = EditText1.Text
	text_Label3 = jeniss_terpilih
	'text_Label4 = EditText2.Text
	'text_Label5 = EditText3.Text
	text_Label6 = EditText4.Text
	text_Label7 = EditText5.Text
	text_Label8 = EditText6.Text
	
	
	ExecuteRemoteQueryInsert("INSERT INTO reporter (id, name, no_hp) VALUES ("&idreporter.Text&",'"&EditText4.Text&"', "&EditText5.Text&")","Save_Case")
	ProgressDialogShow("Loading...")
	ExecuteRemoteQueryInsert("INSERT INTO report (id, type_disaster_id, date, time, foto_video, pelapor_id,lat,long) VALUES ('"&idreport.Text&"','"&text_Label3&"', CURRENT_DATE,CURRENT_TIME,'"&text_Label8&"',"&idreporter.Text&","&latUser&","&lngUser&")","Save_Case")
	Log("INSERT INTO reporter (id, name, no_hp) VALUES ("&idreporter.Text&","&EditText4.Text&", "&EditText5.Text&"")
	Log("INSERT INTO report (id, type_disaster_id, date, time, foto_video, reporter_id,lat,long) VALUES ("&idreport.Text&","&text_Label3&", "&text_Label4&","&text_Label5&","&text_Label8&","&idreporter.Text&","&latUser&","&lngUser&"")
	
	'Msgbox("select * from cases where cast(no_urut as text) like '"&periode_terpilih&""&jorong_terpilih&"%' order by no_urut desc limit 1;INSERT INTO cases (id_periode='"&periode_terpilih&"', id_jorong='"&jorong_terpilih&"', id_type='"&kasus_terpilih&"', id_disease='"&sakit_terpilih&"', id_age='"&age_terpilih&"', gender='"&gender_terpilih&"', jumlah='"&EditText1.Text&"' )","Save_Case")
	'Log("select * from cases where cast(no_urut as text) like '"&periode_terpilih&""&jorong_terpilih&"%' order by no_urut desc limit 1;'"&periode_terpilih&"','"&jorong_terpilih&"','"&kasus_terpilih&"','"&sakit_terpilih&"','"&age_terpilih&"','"&gender_terpilih&"','"&EditText1.Text&"'")
End Sub

Sub JobDone(Job As HttpJob)
	ProgressDialogHide
	If Job.Success Then
		Dim res As String
		res = Job.GetString
		Log("Response :"& res)
		Dim parser As JSONParser
		parser.Initialize(res)
		Select Job.JobName
			Case Tampil_Jeniss
				'Spinner1.Add("--Select--")
				Dim jeniss_array As List
				jeniss_array = parser.NextArray
				For i=0 To jeniss_array.Size -1
					Dim m As Map
					m = jeniss_array.Get(i)
					Dim b As jeniss_lines
					b.Initialize
					b.id= m.Get("id")
					b.name = m.Get("name")
					Spinner1.Add(b.name)
					arrayJeniss(i) = b.id
					Log(b.id)
				Next
			Case id_report
				Dim report_array As List
				report_array = parser.NextArray
				For i=0 To report_array.Size -1
					Dim m As Map
					m = report_array.Get(i)
					Dim r As report_lines
					r.Initialize
					r.id= m.Get("id")
					Dim hurufreport As String
					Dim angkareport As String
					angkareport= r.id.SubString2(1,5)
					hurufreport= r.id.SubString2(0,1)
					angkareport= angkareport + 1
					r.id=hurufreport&angkareport
					idreport.Text=r.id
					arrayReport(i) = r.id
					iddisaster=r.id
					Log(r.id)
				Next
			Case id_reporter
				Dim reporter_array As List
				reporter_array = parser.NextArray
				For i=0 To reporter_array.Size -1
					Dim m As Map
					m = reporter_array.Get(i)
					Dim t As reporter_lines
					t.Initialize
					t.id= m.Get("id")
					Dim hurufreporter As String
					Dim angkareporter As String
					angkareporter= t.id.SubString2(1,5)
					hurufreporter= t.id.SubString2(0,1)
					angkareporter= angkareporter + 1
					t.id=hurufreporter&angkareporter
					idreporter.Text=t.id
					arrayReporter(i) = t.id
					Log(t.id)
				Next

			Case Save_Case
				Try
					Msgbox("Data has been Sent! :)","Success")
					Activity.Finish
				Catch
					Msgbox("Data can not be Sent :(" , "error" )
				End Try
				
				
		End Select
	End If
End Sub


Sub Save_Click
	simpan
	Dim Up As UploadFilePhp
	Up.Initialize("Up")
	Up.doFileUpload(ProgressBar1,Label8,File.DirRootExternal&takecam.nameimages, "http://gisnaturaldisaster.herokuapp.com/mobile/upload.php")
End Sub

Sub btncamera_Click
	StartActivity(takecam)
End Sub

