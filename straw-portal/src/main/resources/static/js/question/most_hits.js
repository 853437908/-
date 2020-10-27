let mostHitQuestionsApp = new Vue({
    el: '#mostHitQuestionsApp',
    data: {
        questions: [
            { id: 1, title: '第1个问题', status: 0, hits: 20, statusText: '未回复', statusClass: "text-warning" },
            { id: 3, title: '第2个问题', status: 2, hits: 42, statusText: '已解决', statusClass: "text-success" },
            { id: 7, title: '第3个问题', status: 0, hits: 67, statusText: '未回复', statusClass: "text-warning" },
            { id: 10, title: '第4个问题', status: 1, hits: 35, statusText: '未解决', statusClass: "text-info" },
            { id: 17, title: '第5个问题', status: 1, hits: 16, statusText: '未解决', statusClass: "text-info" },
        ]
    },
    methods: {
        loadMostHitQuestions: function () {
            $.ajax({
                url: '/api/v1/questions/hits',
                success: function (json) {
                    let questions = [];
                    let statusTexts = ['未回复', '未解决', '已解决'];
                    let statusClasses = ['text-warning', 'text-info', 'text-success'];
                    for (let i = 0; i < json.data.length; i++) {
                        questions[i] = json.data[i];
                        questions[i].statusText = statusTexts[questions[i].status];
                        questions[i].statusClass = statusClasses[questions[i].status];
                    }
                    mostHitQuestionsApp.questions = questions;
                }
            });
        }
    },
    created: function () {
        this.loadMostHitQuestions();
    }
});