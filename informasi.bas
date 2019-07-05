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
	Private DIS="DIS" As String
	Private DAM="DAM" As String
	Private DON="DON" As String
	Dim id, lat, lng, damage, donation, donators As String

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private Label2 As Label
	Private Label3 As Label
	Private Label4 As Label
	Private Label5 As Label
	Private Label6 As Label
	Private Label11 As Label
	Private Label18 As Label
	Private Label20 As Label
	Private Label12 As Label
	Private Label10 As Label
	Private Label9 As Label
	Private Label8 As Label
	Private Label7 As Label
	Private Label23 As Label
	Private Label26 As Label
	Private Button7 As Button
	Private Button6 As Button
	Dim iddisaster As String
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	If FirstTime Then
		If  pencarian.id<>"" Then
			iddisaster=pencarian.id
			Log("address")
			Log(iddisaster)
		Else If radius.id<>"" Then
			iddisaster=radius.id
		End If
	End If
	Activity.LoadLayout("informasi")

End Sub

Sub Activity_Resume
	If  pencarian.id<>"" Then
		iddisaster=pencarian.id
		Log("address")
		Log(iddisaster)
	Else If radius.id<>"" Then
		iddisaster=radius.id
	End If
	pencarian.id=""
	radius.id=""
	detailDisaster
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ExecuteRemoteQuery(Query As String, JobName As String)
	Dim Job As HttpJob
	Job.Initialize(JobName, Me)
	Job.PostString(""&Main.server&"json.php", Query)
End Sub

Sub detailDisaster
	ProgressDialogShow("Loading ....")
	'query untuk mengambil data industri kerajinan yang dipilih
	'ExecuteRemoteQuery("select industrikerajinan.id_ik, industrikerajinan.nama, industrikerajinan.alamat, industrikerajinan.telp, detailangkotik.pemberhentian, st_x(st_centroid(industrikerajinan.geom)) As lng, st_y(st_centroid(industrikerajinan.geom)) As lat from industrikerajinan, detailangkotik where detailangkotik.id_ik=industrikerajinan.id_ik And industrikerajinan.id_ik='"&id_ik2&"'","IK")
	'ExecuteRemoteQuery("select worship_place.id, worship_place.name as worship_place_name, worship_place.address, worship_place.land_size, worship_place.building_size, worship_place.park_area_size, worship_place.capacity, worship_place.est, worship_place.last_renovation, worship_place.imam, worship_place.jamaah, worship_place.teenager, category_worship_place.name as category_name, image, ST_X(ST_Centroid(geom)) AS lng, ST_Y(ST_CENTROID(geom)) As lat from worship_place join category_worship_place on category_worship_place.id=worship_place.id_category where worship_place.id='"&idmes&"'","MES")
	ExecuteRemoteQuery("select disaster_event.id, disaster_event.address, type_disaster.name as type_disaster_id, disaster_event.date, disaster_event.time, disaster_event.total_loss, cause_of_disaster.causes as cause_id, disaster_event.number_of_victims, ST_X(ST_Centroid(disaster_event.geom)) AS lng, ST_Y(ST_CENTROID(disaster_event.geom)) As lat from disaster_event join cause_of_disaster on cause_of_disaster.id=disaster_event.cause_id join type_disaster on type_disaster.id=disaster_event.type_disaster_id where disaster_event.id='"&iddisaster&"'","DIS")
	ExecuteRemoteQuery("select details_damage.total as total_damage, damage_material.name_material as damage from details_damage join damage_material on damage_material.id= details_damage.damage_id where details_damage.disaster_event_id='"&iddisaster&"'","DAM")
	ExecuteRemoteQuery("select details_donation.date_donation_given, donation.name as donation, instansi.name_instansi as donator from details_donation join donation on donation.id= details_donation.donation_id join instansi on instansi.id=details_donation.donators where details_donation.disaster_event_id='"&iddisaster&"'","DON")
End Sub

Sub JobDone(Job As HttpJob)
	ProgressDialogHide
	If Job.Success Then
		Dim res As String
		res = Job.GetString
		Log("Response from server :"&res)
		Dim parser As JSONParser
		parser.Initialize(res)
		Select Job.JobName
			Case DIS
				Dim dis_array As List
				dis_array = parser.NextArray
				If (dis_array.Size>0) Then
					Dim m As Map
					m= dis_array.Get(0)
					id=m.Get("id")
					Label2.Text=m.Get("type_disaster_id")
					Label7.Text=m.Get("address")
					Label8.Text=m.Get("date")
					Label9.Text=m.Get("time")
					Label10.Text=m.Get("cause_id")
					Label12.Text=m.Get("number_of_victims")
					Label20.Text=m.Get("total_loss")
					lng=m.Get("lng")
					lat=m.Get("lat")
					Log(Label10)
				Else
					Msgbox("No Data","Info")
				End If
			Case DAM
				Dim dam_array As List
				dam_array = parser.NextArray
				If (dam_array.Size>0) Then
					Dim m As Map
					m= dam_array.Get(0)
					'id=m.Get("id")
					Label23.Text=m.Get("damage")
				End If
			Case DON
				Dim don_array As List
				don_array = parser.NextArray
				If (don_array.Size>0) Then
					Dim m As Map
					m= don_array.Get(0)
					'id=m.Get("id")
					Label26.Text=m.Get("donation")
				End If
			
		End Select
	End If
End Sub


Sub Button6_Click
	StartActivity(gallery)
End Sub
Sub Button7_Click
	location_route.latTujuan=lat
	location_route.lngTujuan=lng
	StartActivity(location_route)
End Sub



