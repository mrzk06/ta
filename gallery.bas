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
	Dim ViewFoto1="ViewFoto1" As String
	Dim id_gallery As String

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private ListView1 As ListView
	Private Label3 As Label
	Private Button6 As Button
	Private WebView1 As WebView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("gallery")
	id_gallery = informasi.id
	fotoquery

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub fotoquery
	ProgressDialogShow("Loading")
	'eksekusi query mengambil data foto industri kerajinan berdasarkan id ik yang dipilih
	ExecuteRemoteQuery("SELECT photo_video  FROM disaster_event WHERE id='"&id_gallery&"'","ViewFoto1")
Log(id_gallery)
End Sub


Sub ExecuteRemoteQuery(Query As String, JobName As String)
	Dim Job As HttpJob
	Job.Initialize(JobName, Me)
	Job.PostString(""&Main.server&"json.php", Query)
End Sub

Sub Jobdone (Job As HttpJob)

	ProgressDialogHide
	If Job.Success Then
		Dim res As String
		res= Job.GetString
		Dim parser As JSONParser
		parser.Initialize(res)
		Select Job.JobName
			Case ViewFoto1
'			ResetImagesBackground
				Dim agn As List
				agn=parser.NextArray
				If agn.Size - 1 < 0 Then
					Log(agn.Size)
					Msgbox("Galery Foto tidak ditemukan", "Peringatan")
				Else
					Dim serverimage = "http://gisnaturaldisaster.herokuapp.com/images/disaster/" As String
					'Dim serverimage = "http://192.168.137.1/disaster/images/disaster/" As String
					For i=0 To agn.Size-1
						Dim w As Map
						w=agn.Get(i)
						Log("haha")
						Dim image = w.Get("photo_video") As String
						'Dim image1 = serverimage&"/"&image As String
						'Dim gambar = ""&serverimage&""&"prn1.jpg" As String
						If image=="" Then
							Msgbox("No Data","Info")
						Else
							Dim gambar = ""&serverimage&""&image As String
							WebView1.LoadUrl(gambar)
						End If
						
						
						'Dim link As Map
						'link.Initialize
						'meletakkan foto pada imageview
						'link.Put(ListView1, gambar)
						
						'CallSubDelayed2(Starter, "Download", link)
						'Dim w As Map
						'w=agn.Get(i)
						'Log("haha")
						'	Dim link As Map
						'	link.Initialize
						'meletakkan foto pada imageview
						'URL=Server2&w.Get("gallery_culinary")
						'link.Put(ImageView1, URL)
						'	Dim urlnya=Main.Server3&""&w.Get("gallery_culinary") As String
						'link.Put(ImageView1, urlnya)
						'CallSubDelayed2(imageDownloader, "Download", link)
					Next
				End If
		End Select
	End If


End Sub



