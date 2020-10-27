Vue.component('v-select', VueSelect.VueSelect);
let createQuestionApp = new Vue({
    el: '#createQuestionApp',
    data: {
        title:null,
        tags: [],
        selectedTagIds: [],
        teachers:[
            {label:'王克金',value:1}
        ],
        selectedTeacherIds:[]
    },
    methods:{
        loadTags:function () {
            $.ajax({
                url:"/api/v1/tags",
                type:'get',
                dataType:'json',
                success:function (json) {
                    let tags=[];
                    for (let i = 0; i < json.data.length; i++) {
                        let op = {
                            label: json.data[i].name,
                            value: json.data[i].id
                        };
                        tags[i] = op;
                    }
                    createQuestionApp.tags=tags
                }
            })
        },
        loadTeacher: function () {
            $.ajax({
                url: '/api/v1/user/teacher/list',
                type: 'get',
                success: function (json) {
                    let teachers = [];
                    for (let i = 0; i < json.data.length; i++) {
                        let teacher = {
                            label: json.data[i].nickname,
                            value: json.data[i].id
                        }
                        teachers[i] = teacher;
                    }
                    createQuestionApp.teachers = teachers;
                }
            });
        },
        createQuestion: function () {
            let content = $('#summernote').val();
            console.log("标题：" + this.title);
            console.log("选中的标签：" + this.selectedTagIds);
            console.log("选中的老师：" + this.selectedTeacherIds);
            console.log("正文：" + content);
            $.ajax({
                url: '/api/v1/questions/create',
                type: 'post',
                traditional: true,
                data: {
                    title: createQuestionApp.title,
                    tagIds: createQuestionApp.selectedTagIds,
                    teacherIds: createQuestionApp.selectedTeacherIds,
                    content: content
                },
                success: function (json) {
                    if (json.state == 2000) {
                        alert("发表问题成功！！！")
                    } else {
                        alert(json.message);
                    }
                }
            });
        }

    },
    /*created 表示页面刚刚加载完成自动执行的*/
    created:function () {
        this.loadTags();
        this.loadTeacher();
    }


});











