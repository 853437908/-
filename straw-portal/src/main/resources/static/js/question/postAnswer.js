let writeAnswerApp = new Vue({
    el: '#writeAnswerApp',
    data: {
    },
    methods: {
        postAnswer: function () {
            let questionId = location.search.substring(1);
            let content = $('#summernote').val();
            // 注意：以下data表示提交到服务器端的数据
            // 属性名称必须与AnswerDTO的属性名称保持一致
            let data = {
                questionId: questionId,
                content: content
            }
            $.ajax({
                url: '/api/v1/answers/post',
                data: data,
                type: 'post',
                success: function (json) {
                    if (json.state == 2000) {
                        alert('回复成功！');
                        // 应该将数据显示到列表
                        // 如果要上传图片，必须启动静态资源服务器
                        // $('#form-post-answer')[0].reset();
                        $('#summernote').summernote('reset');
                    } else {
                        alert(json.message);
                    }
                }
            });
        }
    }
});