/**
 * 图片上传2
 */

$(document).ready(function () {
    new PageInitrectangle().init();
});


function PageInitrectangle() {
    var api = null;
    var _this = this;
    this.init = function () {
    	$("[name='uprectangle']").on('click', this.portraitrectangle);
    };

    this.portraitrectangle = function () {
    	$btn=$(this);
        var model = $.scojs_modal({
                title: '上传图片',
                content: template('portraitrectangle')
            }
        );
        model.show();
        var fileUp = new Filerectangle();
        var portrait = $('#filerectangle');
        var alert = $('#alert');
        fileUp.portrait(portrait, '/file/upload_Resize_file', _this.getExtraData);
        portrait.on('change', _this.readURL);
        portrait.on('fileuploaderror', function (event, data, msg) {
            alert.removeClass('hidden').html(msg);
            fileUp.fileinput('disable');
        });
        portrait.on('fileclear', function (event) {
            alert.addClass('hidden').html();
        });
        portrait.on('fileloaded', function (event, file, previewId, index, reader) {
            alert.addClass('hidden').html();
        });
        portrait.on('fileuploaded', function (event, data) {
            if (!data.response.status) {
               alert.html(data.response.message).removeClass('hidden');
            }
        })
    };

    this.readURL = function () {
    	var _imghtml='<img src="" id="cut-img" class="img-responsive img-thumbnail"/>';
        var input = $('#filerectangle');
        if (document.all && document.addEventListener && !window.atob) {
        	$(".uploadtips .fileinput-upload").css("display","none");
        	var _html='<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，上传图片暂不支持。 请 <a href="http://browsehappy.osfipin.com/" target="_blank">升级浏览器</a>以获得更好的体验！</p>';
        	layer.open({
                title: '升级浏览器',
                area: ['360px', 'auto'],
                content: _html,
                btn: ["关闭"],
                yes: function(index, layero) {
                    //按钮【按钮一】的回调
                    layer.close(index);
                }

            })
        	return
        }
        /*if (input[0].files && input[0].files[0]) {*/
            var reader = new FileReader();
            reader.readAsDataURL(input[0].files[0]);
            reader.onload = function (e) {
            	$(".selectimg").append(_imghtml);
            	var img = $('#cut-img');
                img.removeAttr('src');
                
                /*裁剪图片按比例缩放*/
                var width = img.attr('width');//区域宽度
                var height = img.attr('height');//区域高度
                var showWidth = width;//最终显示宽度
                var showHeight = height;//最终显示高度
                var ratio = width / height;//宽高比
                var imgWidth, imgHeight, imgratio;
                img.attr('src', e.target.result);
            	var imgObj = new Image();
            	imgObj.src = e.target.result;
            	imgRatio = imgObj.width / imgObj.height;//实际宽高比
                if (ratio > imgRatio) {
                    showWidth = height * imgRatio + 4;//调整宽度太小
                    img.attr('width', showWidth).css('margin-right', (width - showWidth));
                    img.Jcrop({
                    	setSelect: [0, 0, 400, 300],
                        handleSize: 10,
                        aspectRatio: 4/3,
                        onSelect: updateCords
                    }, function () {
                        api = this;
                        $(".jcrop-holder").css('margin', '0 auto');
                    });
                } else {
                    showHeight = width / imgRatio + 4;//调高度太小
                    img.attr('height', showHeight).css('margin-bottom', (height - showHeight));
                    img.Jcrop({
                    	setSelect: [0, 0, 400, 300],
                        handleSize: 10,
                        aspectRatio: 4/3,
                        onSelect: updateCords
                    }, function () {
                        api = this;
                        showHeight = width / imgRatio + 4;//调高度太小
                        $(".jcrop-holder").css('margin', '0 auto');
                        $(".jcrop-holder").attr('height', showHeight).css('margin-bottom', (height - showHeight));
                    });
                }
                
                /*img.attr('src', e.target.result);
                img.Jcrop({
                    setSelect: [0, 0, 400, 300],
                    handleSize: 10,
                    aspectRatio: 4/3,
                    onSelect: updateCords
                }, function () {
                    api = this;
                });*/
            };
            if (api != undefined) {
                api.destroy();
            }
        /*}*/
        //ie9预览图片用滤镜，jcrop裁剪不行
        /*else{  
        	alert("请升级浏览器ie10及以上，或使用chrome浏览器！");*/
        	/*$(".selectimg").append(_imghtml);
        	var img = $('#cut-img');
        	$('#fileUpload')[0].select();  
        	$('#fileUpload')[0].blur();  
        	var nfile = document.selection.createRange().text; 
        	document.selection.empty();     
            $('#cut-img')[0].style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src='"+nfile+"')"; 
        	$('#cut-img').Jcrop({
                setSelect: [20, 20, 200, 200],
                handleSize: 10,
                aspectRatio: 1,
                onSelect: updateCords
            }, function () {
                api = this;
            });*/
        	
        /*}*/
        function updateCords(obj) {
            $("#x").val(obj.x);
            $("#y").val(obj.y);
            $("#w").val(obj.w);
            $("#h").val(obj.h);
        }
    };

    this.getExtraData = function () {
        return {
            sw: $('.jcrop-holder').css('width'),//图片压缩宽度
            sh: $('.jcrop-holder').css('height'),//图片压缩高度
            x: $('#x').val(),//选框的左上角原点坐标
            y: $('#y').val(),//选框的左上角原点坐标
            w: $('#w').val(),//截取选框的宽度
            h: $('#h').val()//截取选框的高度
        }
    }
}

function refresh() {
    window.location.reload();
}

/*function FileUpload() {
    this.portrait = function (target, uploadUrl, data) {
        target.fileinput({
            language: 'zh', //设置语言
            uploadUrl: '/file/upload_Resize_file', //上传的地址
            dropZoneEnabled: false,//是否显示拖拽区域
            uploadExtraData: data,//上传时除了文件以外的其他额外数据
            allowedPreviewTypes: ['image'],
	        allowedFileTypes: ['image'],
	        allowedFileExtensions:  ['gif', 'jpg'],
	        showRemove: false,
	        showCancel: false,
            showUpload: true, //是否显示上传按钮
            showCaption: false,//是否显示标题
            maxFileSize : 400,
            maxFileCount: 1,
            browseClass: "btn btn-primary"
        }).on("fileuploaded", function(event, data) {
        	if(data && data.response && data.response.code == 0){
	        	//添加图片
	        	var _html='<img class="upimg" src="http://192.168.0.132/'+data.response.rows+'">';
	        	var $imgdiv=$btn.parent().find(".environ");
	        	$imgdiv.append(_html);
	        	if($imgdiv.html()==null || $imgdiv.html().length == 0){
	        		$imgdiv.append(_html);
	        	}else{
	        		$imgdiv.find("img").remove();
	        		$imgdiv.append(_html);
	        	}
        	}else{
        		alert(data.response.desc);
        	}
        	var modal = $.scojs_modal({
    		    keyboard: true
    		});
        	modal.close();
        	
       });
    }
}*/