$(document).ready(function () {
    $('#summernote').summernote({
        height: 300,
        tabsize: 2,
        lang: 'zh-CN',
        placeholder: '请输入问题的详细描述...',
        callbacks: {
            onImageUpload: function (files) {
                // ---------------------------------------
                // 当前函数的参数名称是自定义，它表示用户选择的若干个文件
                // Summernote在调用该函数时，会把用户选择的文件作为函数的参数
                // ---------------------------------------
                if (!files || files.length < 1) {
                    alert("请选择您要上传的文件！");
                    return;
                }
                if (files.length > 1) {
                    alert("一次只允许上传1个文件！");
                    return;
                }

                let formData = new FormData();
                let file = files[0];
                formData.append("imageFile", file);
                console.log("form data >>> " + formData);

                $.ajax({
                    url: '/api/v1/questions/upload-image',
                    type: 'post',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(json) {
                        if (json.state == 2000) {
                            // alert(json.data);
                            let img = new Image(); // <img>
                            img.src = json.data; // <img src="xxx">
                            $('#summernote').summernote('insertNode', img);
                        } else {
                            alert(json.message);
                        }
                    }
                });
            }
        }
    });
});