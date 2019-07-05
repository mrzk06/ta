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
	Dim nameimages As String

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim camera1 As AdvancedCamera
	Dim Panel1 As Panel
	Dim p As Phone

	Private btnbrowse As Button
	Private btnsnap As Button
	Private btnvideo As Button

End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("cam")
	p.SetScreenOrientation(0)

End Sub

Sub Camera1_Ready (Success As Boolean)
	If Success Then
		camera1.StartPreview
		btnsnap.Enabled = True
	Else
		ToastMessageShow("Cannot open camera.", True)
	End If
End Sub

Sub Activity_Resume
	btnsnap.Enabled = False
	camera1.Initialize(Panel1, "Camera1")
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	camera1.Release
End Sub

Sub Camera1_PictureTaken (Data() As Byte)
camera1.StartPreview
	Dim out As OutputStream
	nameimages= report.iddisaster&"buktidisaster.jpg"
	out = File.OpenOutput(File.DirRootExternal, nameimages, False)
	out.WriteBytes(Data, 0, Data.Length)
	out.Close
	
	
	'Dim ftp As FTP_Auto
	'ftp.UploadFile(File.Dirinternal, nameimages, "http://gisdisaster.herokuapp.com/images/disaster/"&nameimages)
	
	'camera1.uploadFrame(nameimages,Data, "http://gisdisaster.herokuapp.com/images/disaster/")
	Log (report.iddisaster)
	ToastMessageShow("Image saved: " & File.Combine(File.DirRootExternal, nameimages), True)
	CargaImagen
	btnsnap.Enabled = True
	
End Sub

Sub CargaImagen
	Dim j As HttpJob
	Dim img As String = report.iddisaster&"buktidisaster.jpg"
	j.Initialize("", Me)
	Dim mp As MultipartFileData
	mp.Initialize
	mp.Dir = File.DirRootExternal
	mp.FileName = img
	mp.KeyName = "file"
	mp.ContentType = "image/jpg"
	j.PostMultipart("http://gisnaturaldisaster.herokuapp.com/takenphoto.php", Null, Array(mp))
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		Log(j.GetString)
	End If
	j.Release
End Sub

Sub btnbrowse_Click
	
End Sub

Sub btnsnap_Click
	btnsnap.Enabled = False
	camera1.TakePicture
	
End Sub

Sub btnvideo_Click
	StartActivity(takevid)
End Sub