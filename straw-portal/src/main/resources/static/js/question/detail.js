let questionInfoApp = new Vue({
    el: '#questionInfoApp',
    data: {
        question: {
            title: 'Vue中的v-text和v-html有什么区别？',
            content: '感觉都是用来设置标签内部显示的内容的，区别在哪里呢？',
            userNickName: '天下无敌',
            createdTimeText: '58分钟前',
            hits: 998,
            tags: [
                { id: 5, name: 'Java SE' },
                { id: 7, name: 'Spring' },
                { id: 16, name: 'Mybatis' }
            ]
        }
    },
    methods: {
        loadQuestion: function () {
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
                url: '/api/v1/questions/' + id,
                success: function(json) {
                    if (json.state == 2000) {
                        let question = json.data;
                        question.createdTimeText = getCreatedTimeText(question.createdTime);
                        questionInfoApp.question = question;
                    } else {
                        alert(json.message);
                        location.href = "/index.html";
                    }
                }
            });
        }
    },
    created: function () {
        this.loadQuestion();
    }
});