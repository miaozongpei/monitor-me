
function VideoImg(videoId,canvasId) {
    var video =  document.getElementById(videoId);
    var canvas = document.getElementById(canvasId)
    var context = canvas.getContext("2d");
    var drawImageIndex;
    this.start=function(intervalCallback,successCallback){
        try {
            navigator.getUserMedia({
                video: true
            }, function(stream) {
                video.srcObject = stream;
                drawImageIndex=setInterval(function () {
                    context.drawImage(video, 0, 0, 180, 200);
                    intervalCallback();
                },2000);
            }, function(error) {
                alert("Failed to get access to local media. Error code was " + error.code + ".");
                return;
            });
        } catch (e) {
            alert("getUserMedia error:", e);
            return;
        }
        successCallback();
    };
    this.getImgData=function(){
        var imgData = canvas.toDataURL();
       return imgData.substr(22); //在前端截取22位之后的字符串作为图像数据
    }
}

