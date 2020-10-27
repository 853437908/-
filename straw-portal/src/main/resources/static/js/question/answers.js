let answersApp = new Vue({
    el: '#answersApp',
    data: {
        answers: [
            { userNickName: '成老师', createdTimeText: '37分钟前', content: '说了半天还是没答案啊', comments: [] },
            { userNickName: '小刘老师', createdTimeText: '3小时前', content: '你们能不能好好说', comments: [] },
            { userNickName: '范老师', createdTimeText: '6小时前', content: '你们说了几天也等于啥也没说', comments: [] },
            { userNickName: '王老师', createdTimeText: '2天前', content: '不好说也得好好说', comments: [] },
            { userNickName: '大刘老师', createdTimeText: '3天前', content: '这个问题不好说啊', comments: [] }
        ]
    },
    methods: {
        loadAnswers: function() {
            let id = location.search;
            if (!id) {
                alert("非法访问！参数不足！");
                location.href = '/index.html';
                return;
            }
            id = id.substring(1);
            if (!id || isNaN(id)) { // is not a number
                alert("非法访问！参数不足！");
                location.href = '/index.html';
                return;
            }
            $.ajax({
                url: '/api/v1/answers/question/' + id,
                success: function(json) {
                    let answers = json.data;
                    for (let i = 0; i < answers.length; i++) {
                        answers[i].createdTimeText = getCreatedTimeText(answers[i].createdTime);
                    }
                    answersApp.answers = answers;
                }
            });
        },
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
                        alert('发表答案成功！');
                        // 应该将数据显示到列表
                        // 如果要上传图片，必须启动静态资源服务器
                        // $('#form-post-answer')[0].reset();
                        $('#summernote').summernote('reset');
                        // 获取服务器端返回的新回答案的数据
                        let answer = json.data;
                        answer.createdTimeText = getCreatedTimeText(answer.createdTime);
                        answer.comments = [];
                        // unshift()：在数组顶部添加元素
                        answersApp.answers.unshift(answer);
                        // 切到当前页面的“回答列表”顶部
                        location.href = "#answerListTop";
                    } else {
                        alert(json.message);
                    }
                }
            });
        },
        postComment: function (answerId) {
            let content = $('#commentContent' + answerId).val();
            // alert("准备发表答案id=" + answerId + "的评论，评论内容是：" + content);
            let data = {
                answerId: answerId,
                content: content
            }
            $.ajax({
                url: '/api/v1/comments/post',
                data: data,
                type: 'post',
                success: function(json) {
                    if (json.state == 2000) {
                        alert("发表评论成功！");
                        // 从服务器端返回的数据中获取“评论”数据对象
                        let comment = json.data;
                        // 由于当前页面的数据answers包含多条“回答”
                        // 需要先找到本次评论对应的“回答”
                        // 则遍历整个answers（即所有“回答”），检查id与参数answerId是否相同
                        for (let i = 0; i < answersApp.answers.length; i++) {
                            if (answersApp.answers[i].id == answerId) {
                                answersApp.answers[i].comments.unshift(comment);
                                break;
                            }
                        }
                        location.href = "#answer" + answerId;
                    } else {
                        alert(json.message);
                    }
                }
            });
        },
        deleteComment: function (comments, index) {
            let commentId = comments[index].id;
            let confirmMessage = '即将删除id=' + commentId
                + "的评论，删除操作将不可恢复，您确定要删除这条评论吗？";
            if (!confirm(confirmMessage)) {
               // alert("您已经取消了删除操作！");
               return;
            }
            // alert("您确认了删除操作，即将为您执行删除！");
            $.ajax({
                url: '/api/v1/comments/' + commentId + "/delete",
                type: 'post',
                success: function (json) {
                    if (json.state == 2000) {
                        alert("删除成功！");
                        comments.splice(index, 1);
                    } else {
                        alert(json.message);
                    }
                }
            });
        },
        updateComment: function (comments, index) {
            let commentId = comments[index].id;
            let content = $('#CommentContent' + commentId).val();
            // alert("即将修改id=" + commentId + "的评论正文为：" + content);
            $.ajax({
                url: '/api/v1/comments/' + commentId + "/update",
                data: 'content=' + content,
                type: 'post',
                success: function (json) {
                    if (json.state == 2000) {
                        // alert("修改成功！！！");
                        // comments[index] = json.data; // 错误
                        Vue.set(comments, index, json.data);
                        $('#updateComment' + commentId).collapse('hide');
                    } else {
                        alert(json.message);
                    }
                }
            });
        }
    },
    created: function () {
        this.loadAnswers();
    }
});