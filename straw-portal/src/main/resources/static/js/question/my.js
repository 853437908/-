let questionsApp = new Vue({
    el: '#questionsApp',
    data: {
        questions: [
            {
                statusText: '已解决',
                statusClass: 'badge-success',
                title: '这是第1个问题',
                content: '很<b>严肃</b>的提出了第1个问题',
                tags: [
                    { id: 8, name: 'Java基础' },
                    { id: 12, name: 'Spring' },
                    { id: 15, name: 'SpringBoot' }
                ],
                userNickName: '天下第一',
                hits: '826',
                createdTimeText: '8小时之前',
                tagImage: '/img/tags/8.jpg'
            },
            {
                statusText: '未回复',
                statusClass: 'badge-warning',
                title: '这是第2个问题',
                content: '我也不告诉你是什么问题……',
                tags: [
                    { id: 10, name: 'MySQL' },
                    { id: 17, name: 'SpringSecurity' }
                ],
                userNickName: '天下第一',
                hits: '537',
                createdTimeText: '15小时之前',
                tagImage: '/img/tags/10.jpg'
            }
        ],
        pageInfo: null
    },
    methods: {
        loadMyQuestions: function (page) {
            if (!page || page < 1) {
                page = 1;
            }
            $.ajax({
                url: '/api/v1/questions/my',
                data: 'page=' + page,
                success: function (json) {
                    // json.data.list
                    let data = json.data;
                    let questions = data.list;
                    let statusTexts = ['未回复', '未解决', '已解决'];
                    let statusClasses = ['badge-warning', 'badge-info', 'badge-success'];
                    for (let i = 0; i < questions.length; i++) {
                        questions[i].statusText = statusTexts[questions[i].status];
                        questions[i].statusClass = statusClasses[questions[i].status];
                        questions[i].tagImage = '/img/tags/' + questions[i].tags[0].id + '.jpg';
                        questions[i].createdTimeText = getCreatedTimeText(questions[i].createdTime);
                    }
                    questionsApp.questions = questions;
                    questionsApp.pageInfo = data;
                }
            });
        }
    },
    created: function () {
        this.loadMyQuestions();
    }
});