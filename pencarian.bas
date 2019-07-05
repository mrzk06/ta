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
	Private cari_filter = "cari_filter" As String
	Dim id, address, name, latitude, longitude As String
	Type daftardisaster_lines (isi1 As String, isi2 As String, isi3 As String, isi4 As String, isi5 As String)
	Private Tampil_Tahun = "Tampil_Tahun" As String
	Private Tampil_Bulan= "Tampil_Bulan" As String
	Private Tampil_Jenis= "Tampil_Jenis" As String
	Private Tampil_Kecamatan = "Tampil_Kecamatan" As String
	
	Dim tahun_terpilih As String
	Type tahuns_lines (id As String, name As String)
	Dim arrayTahun(100) As String
	
	Dim bulan_terpilih As String
	Type bulans_lines (id As String, name As String)
	Dim arrayBulan(100) As String
	
	Dim district_terpilih As String
	Type districts_lines (id As String,name As String)
	Dim arrayDistrict(100) As String
	
	Dim jenis_terpilih As String
	Type jenis_lines (id As String,name As String)
	Dim arrayJenis(100) As String
	

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Private WebView1 As WebView
	Private ListView1 As ListView
	
	Private Button1 As Button
	Private Button2 As Button
	Private Button3 As Button
	Private Button4 As Button
	Private Button5 As Button

	Private Spinner1 As Spinner
	Private Spinner2 As Spinner
	Private Spinner3 As Spinner
	Private Spinner4 As Spinner
	Private Button6 As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("search")
	posisi1
	tampilkan_tahun
	tampilkan_jenis
	tampilkan_bulan
	tampilkan_kecamatan
	
	ListView1.TwoLinesLayout.Label.TextSize=13
	ListView1.TwoLinesLayout.SecondLabel.TextSize=12
	ListView1.Color=Colors.white
	ListView1.TwoLinesLayout.Label.TextColor=Colors.Black
	ListView1.TwoLinesLayout.SecondLabel.TextColor=Colors.Black
	ListView1.TwoLinesLayout.Label.Color=Colors.White
	ListView1.TwoLinesLayout.SecondLabel.Color=Colors.White
	
	
End Sub

Sub posisi1
    WebView1.LoadUrl(""&Main.Server&"map.html")
End Sub

Sub carifilter
	ProgressDialogShow("Loading...")
	If bulan_terpilih=="" And district_terpilih=="" And jenis_terpilih=="" Then
		ExecuteRemoteQuery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and date_part('year', a.date)="&tahun_terpilih&"","cari_filter")
	Else If bulan_terpilih=="" And district_terpilih=="" Then
		Log(jenis_terpilih)
		ExecuteRemoteQuery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and b.id like '%"&jenis_terpilih.ToUpperCase&"%' and date_part('year', a.date)="&tahun_terpilih&"","cari_filter")
	Else If district_terpilih=="" And jenis_terpilih=="" Then
		ExecuteRemoteQuery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and date_part('year', a.date)="&tahun_terpilih&" and date_part('month', a.date)="&bulan_terpilih&"","cari_filter")
	Else If bulan_terpilih=="" And jenis_terpilih=="" Then
		ExecuteRemoteQuery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and district.id like '%"&district_terpilih.ToUpperCase&"%' and date_part('year', a.date)="&tahun_terpilih&"","cari_filter")
	Else If bulan_terpilih=="" Then
		ExecuteRemoteQuery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and district.id like '%"&district_terpilih.ToUpperCase&"%' and b.id="&jenis_terpilih.ToUpperCase&" and date_part('year', a.date)="&tahun_terpilih&"","cari_filter")
	Else If district_terpilih=="" Then
		ExecuteRemoteQuery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom)  and b.id like '%"&jenis_terpilih.ToUpperCase&"%' and date_part('year', a.date)="&tahun_terpilih&" and date_part('month', a.date)="&bulan_terpilih&"","cari_filter")
	Else If jenis_terpilih=="" Then
		ExecuteRemoteQuery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and district.id like '%"&district_terpilih.ToUpperCase&"%' and date_part('year', a.date)="&tahun_terpilih&" and date_part('month', a.date)='"&bulan_terpilih&"'","cari_filter")
	Else If bulan_terpilih=="" And district_terpilih=="" And jenis_terpilih=="" And tahun_terpilih  Then
	  Msgbox("Pilih Filter!","Pemberitahun")
	Else 
		ExecuteRemoteQuery("SELECT distinct a.date,a.id,a.address,a.time,b.name as name, st_x(st_centroid(a.geom)) as lon,st_y(st_centroid(a.geom)) as lat from disaster_event as a left join type_disaster as b on a.type_disaster_id=b.id, district where st_contains(district.geom, a.geom) and district.id like '%"&district_terpilih.ToUpperCase&"%' and b.id like '%"&jenis_terpilih.ToUpperCase&"%' and date_part('year', a.date)="&tahun_terpilih&" and date_part('month', a.date)="&bulan_terpilih&"","cari_filter")
	End If
		
End Sub

Sub Button6_Click
	WebView1.LoadUrl(""&Main.Server&"peta2.php?district="&district_terpilih.ToUpperCase&"&jenis="&jenis_terpilih.ToUpperCase&"&year="&tahun_terpilih&"&bulan="&bulan_terpilih)
	Log(""&Main.Server&"peta2.php?district="&district_terpilih.ToUpperCase&"&jenis="&jenis_terpilih.ToUpperCase&"&year="&tahun_terpilih&"&bulan="&bulan_terpilih)
	carifilter
End Sub

Sub Activity_Resume
	ListView1.Clear
	posisi1
End Sub

Sub tampilkan_tahun
	
	ProgressDialogShow("Loading...")
	
	ExecuteRemoteQuery("SELECT * from year","Tampil_Tahun")
End Sub

Sub tampilkan_bulan
	
	ProgressDialogShow("Loading...")
	
	ExecuteRemoteQuery("SELECT * from month ORDER BY id","Tampil_Bulan")
End Sub
Sub tampilkan_jenis
	
	ProgressDialogShow("Loading...")
	
	ExecuteRemoteQuery("SELECT * from type_disaster","Tampil_Jenis")
End Sub
Sub tampilkan_kecamatan
	
	ProgressDialogShow("Loading...")
	
	ExecuteRemoteQuery("SELECT * from district","Tampil_Kecamatan")
End Sub

Sub ExecuteRemoteQuery(Query As String, JobName As String)
	Dim Job As HttpJob
	Job.Initialize(JobName, Me)
	Job.PostString(""&Main.Server&"json.php",Query)
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
			Case Tampil_Kecamatan
				Dim district_array As List
				district_array = parser.NextArray
				'Spinner4.Add("---All District ---")
				For i=0 To district_array.Size -1
					Dim m As Map
					m = district_array.Get(i)
					Dim f As districts_lines
					f.Initialize
					f.id= m.Get("id")
					f.name= m.Get("name")
					Spinner4.Add(f.name)
					Log(f.name)
					arrayDistrict(i) = f.id
				Next
			Case Tampil_Jenis
				Dim jenis_array As List
				jenis_array = parser.NextArray
				'Spinner3.Add("---All Type---")
				For i=0 To jenis_array.Size -1
					Dim m As Map
					m = jenis_array.Get(i)
					Dim l As jenis_lines
					l.Initialize
					l.id= m.Get("id")
					l.name = m.Get("name")
					Spinner3.Add(l.name)
					Log(l.name)
					arrayJenis(i) = l.id
				Next
			Case Tampil_Tahun
				Dim tahun_array As List
				tahun_array = parser.NextArray
				'Spinner1.Add("---All Year---")
				For i=0 To tahun_array.Size -1
					Dim m As Map
					m = tahun_array.Get(i)
					Dim b As tahuns_lines
					b.Initialize
					b.id= m.Get("id")
					b.name= m.Get("name")
					Spinner1.Add(b.name)
					arrayTahun(i) = b.name
				Next
			Case Tampil_Bulan
				Dim bulan_array As List
				bulan_array = parser.NextArray
				'Spinner2.Add("---All Month---")
				For i=0 To bulan_array.Size -1
					Dim m As Map
					m = bulan_array.Get(i)
					Dim t  As bulans_lines
					t.Initialize
					t.id= m.Get("index")
					t.name= m.Get("name")
					Spinner2.Add(t.name)
					Log(t.name)
					arrayBulan(i) = t.id
				Next
			Case cari_filter
				ListView1.Clear
				Dim cari_array As List
				cari_array = parser.NextArray
				If cari_array.Size -1 < 0 Then
					Msgbox("Disaster Area Not Found", "Pemberitahuan")
				Else
					For i=0 To cari_array.Size -1
						Dim m As Map
						m = cari_array.Get(i)
						Dim c  As daftardisaster_lines
						c.Initialize
						c.isi1= m.Get("id")
						c.isi2= m.Get("name")
						c.isi3= m.Get("address")
						c.isi4=m.Get("lat")
						c.isi5=m.Get("lon")
						ListView1.AddTwoLines2(c.isi2, c.isi3,c.isi1)
						'arrayCari(i) = c.address
					Next
				End If
		End Select
	End If
End Sub



Sub Spinner1_ItemClick (Position As Int, Value As Object)
	Spinner1.Prompt="Select Year"
	Spinner1.Color=Colors.Blue
	tahun_terpilih = arrayTahun(Position)
	Log(tahun_terpilih)
	
End Sub

Sub Spinner2_ItemClick (Position As Int, Value As Object)
	Spinner2.Prompt="Select Month"	
	Spinner2.Color=Colors.Blue
	bulan_terpilih = arrayBulan(Position)
	Log(bulan_terpilih)
End Sub

Sub Spinner3_ItemClick (Position As Int, Value As Object)
	Spinner3.Prompt="Select Type"
	Spinner3.Color=Colors.Blue
	jenis_terpilih = arrayJenis(Position)
	Log(jenis_terpilih)
End Sub

Sub Spinner4_ItemClick (Position As Int, Value As Object)
	Spinner4.Prompt="Select District"
	Spinner4.Color=Colors.Blue
	district_terpilih = arrayDistrict(Position)
	Log(district_terpilih)
End Sub


Sub Button1_Click
	StartActivity(Main)
End Sub

Sub Button2_Click
	StartActivity(current_pos)
End Sub

Sub Button3_Click
	
End Sub

Sub Button4_Click
	StartActivity(report)
End Sub

Sub Button5_Click
	StartActivity(radius)
End Sub


Sub ListView1_ItemClick (Position As Int, Value As Object)
	id=Value
	Log("id"&id)
	StartActivity("informasi")
End Sub