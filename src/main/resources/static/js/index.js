/**
 *
 */
var submitData;
var dropz;
var d;
var submiting;
var dialogHtml = '<h4 class="mdl-dialog__title">确认上传</h4><div class="mdl-spinner mdl-js-spinner is-active" style="margin-left: 50%;margin-top: 30px"></div><div class="mdl-dialog__content"><div style="float: left; margin: 10px;"><img id="img" style="width: 100px; height: 100px;" src=""></div><div style="float: left;"><p id="app_version">1.0(Build 1)</p><div><input id="app_name" class="mdl-textfield__input" type="text"></div></div><div style="clear: both"><hr><div><div style="float: left;"><label>短链接：</label></div><div style="float: left;"><label>http://fir.im/</label></div><div><input id="short_url" type="text" class="mdl-textfield__input" style="width: 200px;"></div><br><br><span>更新日志：</span><textarea class="mdl-textfield__input" type="text" rows="4" id="ulogs"></textarea></div></div></div>';
function initDrop() {
    dropz = new Dropzone("#dropz", {
        url: "/uploadpk",
        parallelUploads: 1,
        dictDefaultMessage: "拖拽或点击上传文件",
        paramName: "file",
        maxFiles: 1,
        maxFilesize: 512,
        acceptedFiles: ".apk",
        addedfile: function (file) {
            d.showModal();
            $('.mdl-dialog__content').hide();
            $('.mdl-dialog__actions').hide();
            $('.mdl-spinner').show();
        },
        success: function (file, data) {
            $('.mdl-dialog__content').show();
            $('.mdl-dialog__actions').show();
            $('.mdl-spinner').hide();
            submitData = data.data;
            submitData.fileSize = file.size;
            $("#img").attr('src', data.data.appIcon);
            $("#app_version").html(data.data.versionName + "(Build " + data.data.versionCode + ")");
            $("#app_name").attr("value", data.data.appName);
            $("#short_url").attr("value", data.data.shortUrl);
            if (!data.data.new) {
                $("#short_url").attr('disabled', true);
                $("#app_name").attr('disabled', true);
            }
        },
        uploadprogress: function (file, progress, bs) {
            // console.log("File " + file.name + ", progress= " + progress + ", bs=" + bs);
        }
    });
}

function initDialog() {
    d = dialog({
        width: 400,
        content: dialogHtml,
        okValue: '提交',
        ok: saveProject,
        cancelValue: '取消',
        cancel: function () {
            dropz.removeAllFiles();
            initDialog();
        }
    });
}
function saveProject() {
    submitData.ulogs = $('#ulogs').val();
    if (submitData.new) {
        submitData.shortUrl = $("#short_url").val();
        submitData.appName = $("#app_name").val();
    }
    submiting.showModal();
    $.ajax({
        url: '/project/add',
        type: 'POST',
        data: JSON.stringify(submitData),
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function (data, status, xhr) {
            submiting.close().remove();
            if (data.status == 0)
                window.location.href = "/project/" + data.data.id;
        },
        error: function (xhr, error, exception) {
            submiting.close().remove();
            alert(exception.toString());
        }
    });
}
function init() {
    initDrop();
    initDialog();
    submiting = dialog({
        width: 100,
        height: 100,
        content: '<div align="center" style="height: 100%;margin-top: 40%">正在提交......</div>',
        cancelDisplay: false,
        cancel: function () {
            return false;
        }
    });
}
$(document).ready(init());