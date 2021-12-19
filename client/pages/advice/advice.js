// pages/advice/advice.js
var api = require('../../config/api.js');
var util = require('../../utils/util.js');
var user = require('../../utils/user.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderList: [],
    msgShow: false

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  getAdviceOrders: function (res) {
    var dot = {
      latitude: res.latitude,
      longitude: res.longitude
    }
    util.request(api.advice, {
      dot: dot,
      token: wx.getStorageSync('token')
    }, 'POST').then(res => {
      wx.hideLoading();
      if (res.success) {
        if (res.adviceList.length === 0) {
          this.setData({
            msgShow: true,
            orderList: []
          })
        } else {
          this.setData({
            msgShow: false,
            orderList: res.adviceList,
          })
        }
      }
    }).catch(res => {
      wx.hideLoading()
      util.showErrorToast("服务器繁忙,稍后再来吧")
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this
    wx.getLocation({
      success: function (res) {
        that.getAdviceOrders(res)
      }
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var that = this
    wx.getLocation({
      success: function (res) {
        that.getAdviceOrders(res);
        wx.stopPullDownRefresh();
      }
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  join: function (e) {
    util.request(api.join, {
      orderId: e.target.dataset.id,
      token: wx.getStorageSync('token')
    }, 'POST').then(res => {
      if (!res.success) {
        util.showErrorToast('已加入该行程')
      }
    }).catch(res => {})
    wx.navigateTo({
      url: '/pages/room/room?orderId=' + e.target.dataset.id,
    })
    util.request(api.save, {
      templateId: e.detail.formId,
      token: wx.getStorageSync('token')
    }, 'POST')
  }
})