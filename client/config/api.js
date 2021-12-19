// 以下是业务服务器API地址
// 本机开发时使用
 var apiRoot = 'https://pin.maxz.link/';
//  var apiRoot = 'http://localhost:8080/';
module.exports = {
  login: apiRoot + 'auth/login', //微信登录
  showFuzzyOrder: apiRoot + 'order/fuzzy', //按起点 终点显示行程 模糊匹配
  publish : apiRoot +'order/publish', //发布订单
  queryOrder : apiRoot+'order/query', //查询订单
  own: apiRoot + 'order/own', //查询我的订单
  join: apiRoot + 'order/join', //加入行程
  cancel: apiRoot + 'order/cancel', //取消行程
  save:apiRoot+'template/save', //保存formid
  advice:apiRoot+'order/advice' //推荐行程
};