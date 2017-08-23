/**
 * 初始化uploadify上传
 * @param imgId 上传成功后 显示上传图片的 img标签的ID
 * @param filePathId 上传成功后 文件存储路径
 * @param webFilePath 文件服务主域名
 * @param fileTypeExts 文件类型 可选("file","image","doc") 多个可使用","分隔
 */
function fileUpload(param) {
	var timestamp = (new Date()).valueOf();
	var token = "";
	var language = "cn";
	var domain = "";
	this.defaults = {
		fileSizeLimit: "5MB"
	};
	parma = $.extend({},this.defaults,param)
	var uploadId, imgId, filePathId, webFilePath, fileTypeExts, callback, fileQueue = "fileQueue";
	var width = param.width;
	var height= param.height;
	if (param) {
		uploadId = param.uploadId;
		if (param.imgId) {
			imgId = param.imgId
		}
		if (param.filePathId) {
			filePathId = param.filePathId
		}
		if (param.webFilePath) {
			webFilePath = param.webFilePath
		}
		if (param.fileType) {
			fileType = param.fileType
		}
		if (param.fileQueue) {
			fileQueue = param.fileQueue
		}
		if (param.callback) {
			callback = param.callback
		}
	}
	var uploadBtn = $("#" + uploadId);
	var contextPath = param.contextpath;
	$.getJSON(contextPath + '/getUploadToken?t=' + timestamp,
			function (data){
				data = eval("(" + data + ")");
				if (data.uploadToken) {
					token = data.uploadToken;
					language = data.language;
					domain = data.domain;
					uploadBtn.uploadify({
						'swf':param.swf,
						'uploader':webFilePath + '/upload.do?action=upload',//后台请求的处理
						'queueID':'fileQueue',//与下面的id对应
						'buttionText':'上传',
						'queueSizeLimit':5,
						'fileTypeExts':fileTypeExts,//控制可上传文件的扩展名，启用本项时需同时声明fileDesc
						'fileDesc':'Any old file you want...',
						'auto':true,
						'multi':true,
						'width':param.width,
						'heigth':param.heigth,
						'uploadLimit':param.uploadLimit,
						'fileSizeLimit':param.fileSizeLimit,//文件上传大小限制
						'overrideEvents':['onUploadSuccess','onUploadError','onSelectError','onSelect','onDialogClose'],
						'onUploadError':uploadify_onUploadError,
						'onSelectError':uploadify_onSelectError,
						'onSelect':function(fileObj){
							
							var limit;
							var fileSizeLimit = param.fileSizeLimit;
							if (fileSizeLimit.indexOf('KB') > 0) {
								limit = fileSizeLimit.substring(0,fileSizeLimit.indexOf('KB'));
								limit = limit*1024;
							} else if (fileSizeLimit.indexOf('MB') > 0) {
								limit = fileSizeLimit.substring(0,fileSizeLimit.indexOf('MB'));
								limit = limit*1024*1024;
							}
							if (fileObj.size > limit) {
								layer.alert("上传文件不允许超过" + fileSizeLimit, {icon:2});
								$("#uploadify").uploadifyCancel(queueID);
								return false;
							}
						},
						'onUploadcomplete':function (fileObj, _path, data) {
							$.cookie('JSESSIONID',token);//跨域传输之后必须设置cookie，否则会丢失此次的session
						},
						'onUploadSuccess':functino (fileObj, _path, status) {
							var obj = eval('(' + _path + ')');
							//现在拿到的是临时文件，需要再取一次
							$.getJSON(contextPath + '/getFile?uuid=' + obj.fileUUIDs[0], function (data) {
								console.log(data);
								//执行回调函数 判断是否有回调函数，如果没有就默认执行
								if (callback) {
									callback(status, fileObj, data);
								} else {
									console.log('请先设置callback');
								}
							});
						}
					});
				}
	});
}

var uploadify_onUploadError = function (file, errorCode, errorMsg, errorString) {
	var msgText = "上传失败\n";
	switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			msgText += "HTTP 错误\n" + errorMsg;
			break;
		case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
			msgText += "上传文件丢失，请重新上传";
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERRROR:
			msgText += "安全性错误\n" + errorMsg;
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			msgText += "每次最多上传" + this.settings.uploadLimit + "张图片";
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			msgText += "IO错误";
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED;
			msgText += errorMsg;
			break;
		case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
			msgText += "找不到文件，请重新操作";
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			msgText += "参数错误";
			break;
		default:
			msgText += "文件:\n错误码:" + errorCode + "\n" + errorMsg + "\n" + errorString;
	}
	layer.alert(msgText);
	return false;
}

var uploadify_onSelectError = function(file, errorCode, errorMsg) {
	var msgText = "上传失败\n";
	switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			msgText += "每次最多上传" + this.settings.queueSizeLimit + "个文件";
			return;
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			msgText += "文件大小超多限制(" + this.setting.fileSizeLimit + ")";
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			msgText += "文件大小为0";
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			msgText += "文件格式不正确，仅限" + this.settings.fileTypeExts;
			break;
		default:
			msgText += "错误代码:" + errorCode + "\n" +errorMsg;
	}
	layer.alert(msgText);
	return false;
}