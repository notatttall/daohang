//////////////////////////////////////
///////////////地图初始化/////////////////
//////////////////////////////////////
function  MapInit(div,path,map_swf){
    // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. 
    var swfVersionStr = "11.1.0";
	// To use express install, set to playerProductInstall.swf, otherwise the empty string. 
	var xiSwfUrlStr = path+"playerProductInstall.swf";
	var flashvars = {};
	flashvars.path=path;
	var params = {};
	params.quality = "high";
	params.bgcolor = "#ffffff";
	params.wmode="opaque";
	params.allowscriptaccess = "sameDomain";
	params.allowfullscreen = "true";
	var attributes = {};
	attributes.id = "f_map";
	attributes.name = "f_map";
	attributes.align = "middle";
	swfobject.embedSWF(
		path+map_swf, div, 
    	"100%", "100%", 
    	swfVersionStr, xiSwfUrlStr, 
    	flashvars, params, attributes);
	// JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
	swfobject.createCSS("#"+div, "display:block;text-align:left;");
}

//////////////////////////////////////
///////////////车辆追踪/////////////////
//////////////////////////////////////
//车辆追踪初始化是否成功状态
var isVehicelTrackInitalSucess=false;
//车辆追踪初始化完成，回调函数，Flex调用
function VehicelTrackInitalSucess()
{
    isVehicelTrackInitalSucess=true;
}
function InitialMapTrack()
{
    f_map.InitialMapTrack();
}
//运行车辆跟踪
function RunVehicleTrack(json)
{
	f_map.RunVehicleTrack(json);
}
//清除车辆监控图形数据
function ClearVehicleTrack()
{
	f_map.ClearVehicleTrack();
}