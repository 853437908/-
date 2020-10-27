let navTagsApp = new Vue({
    el: '#navTagsApp',
    data: {
        tags: [
            { id: 1, name: '第1阶段' },
            { id: 2, name: '第2阶段' },
            { id: 3, name: '第3阶段' },
            { id: 4, name: '第4阶段' }
        ]
    },
    methods: {
        loadTags: function() {
            $.ajax({
                url: '/api/v1/tags',
                type: 'get',
                dataType: 'json',
                success: function(json) {
                    navTagsApp.tags = json.data;
                }
            });
        }
    },
    created: function () {
        this.loadTags();
    }
});