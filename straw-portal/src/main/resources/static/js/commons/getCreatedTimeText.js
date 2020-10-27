function getCreatedTimeText(createdTime) {
    let now = new Date().getTime();
    let pastTime = (now - new Date(createdTime).getTime()) / 1000;
    let createdTimeText = "未知时间";
    if (pastTime < 60) { // 不足1分钟
        createdTimeText = "刚刚";
    } else if (pastTime < 60 * 60) { // 不足1小时
        createdTimeText = parseInt(pastTime / 60) + "分钟前";
    } else if (pastTime < 60 * 60 * 24) {
        createdTimeText = parseInt(pastTime / 60 / 60) + "小时前";
    } else {
        createdTimeText = parseInt(pastTime / 60 / 60 / 24) + "天前";
    }
    return createdTimeText;
}