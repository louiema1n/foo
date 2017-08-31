/**
 * Created by Louie on 2017-08-16.
 */
// timestamp转换成datetime
function timeStamp2String (time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1;
    var date = datetime.getDate();
    var hour = datetime.getHours();
    var minute = datetime.getMinutes();
    var second = datetime.getSeconds();
    var mseconds = datetime.getMilliseconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
};

/**
 * 判断是否为闰年
 * @param year
 */
function isLeapYear(year) {
    return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
}

/**
 * 获取当月天数（一三五七八十腊 三十一天永不差）
 * @param year
 * @param month
 */
function getMonthDays(year, month) {
    return [31, null, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month] || (isLeapYear(year) ? 29 : 28);
}

/**
 * 根据年月日获取其在当年的第几周
 * @param year  年份
 * @param month 月份-从0开始
 * @param date  号数
 */
function getWeekNum(year, month, date) {
    var days = date;
    // 计算当天是当年的第几天
    for (var i = 0; i < month; i++) {
        days += getMonthDays(year, i);
    }
    // 计算当年的第一天是周几
    var yearFirstDay = new Date(year, 0, 1).getDay() || 7;
    // 计算周数
    var weekNum;
    if (yearFirstDay == 1) {
        weekNum = Math.ceil(days / 7);
    } else {
        days -= (7 - yearFirstDay + 1);
        weekNum = Math.ceil(days / 7) + 1;
    }
    return weekNum;
}
