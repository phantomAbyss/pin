// pages/room/room.js
var api = require('../../config/api.js');
var util = require('../../utils/util.js');
var user = require('../../utils/user.js');

var app = getApp();
var socketOpen = false;
var frameBuffer_Data, session, SocketTask;
var that = this;
var url = 'wss://pin.maxz.link/chatRoom/';
// var url ='ws://localhost:8080/chatRoom/'
Page({
  /**
   * 页面的初始数据
   */
  data: {
    height: 0, //屏幕高度
    chatHeight: 0, //聊天屏幕高度
    orderId: null,
    contentList: [],
    order: {},
    textMessage: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    //获取屏幕的高度
    wx.getSystemInfo({
      success(res) {
        that.setData({
          height: wx.getSystemInfoSync().windowHeight,
          chatHeight: wx.getSystemInfoSync().windowHeight - 90
        })
      }
    })
    that.setData({
      orderId: options.orderId
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

    if (app.globalData.roomList.indexOf(this.data.orderId) === -1) {
      this.webSocket()
    }
    var that = this;
    SocketTask.onOpen(res => {
      socketOpen = true;
    })
    SocketTask.onClose(onClose => {
      socketOpen = false;
      var idx = app.globalData.roomList.indexOf(this.data.orderId)
      app.globalData.roomList.splice(idx, 1)
      wx.navigateBack({
        delta: 1
      })

    })
    SocketTask.onError(onError => {
      socketOpen = false
    })
    SocketTask.onMessage(onMessage => {
      var that = this
      if (onMessage.data == "LOGIN") {
        wx.removeStorageSync('token')
        wx.navigateTo({
          url: "/pages/login/login"
        });
      } else {
        var list = that.data.contentList
        var json = JSON.parse(onMessage.data)
        if (json.length > 1) {
          for (let i = 0; i < json.length; i++) {
            list.push(json[i])
          }
        } else if (json instanceof Array) {
          list.push(json[0])
        } else {
          list.push(json)
        }
        that.setData({
          contentList: list,
          scrollTop: 99999999
        })
      }
    })

    util.request(api.queryOrder, {
      orderId: this.data.orderId,
      token: wx.getStorageSync('token')
    }, 'POST').then(res => {
      var that = this
      if (res.success) {
        that.setData({
          order: res.order
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    SocketTask.close(function (close) {})
    var idx = app.globalData.roomList.indexOf(this.data.orderId)
    if (idx != -1) {
      app.globalData.roomList.splice(idx, 1)
    }
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    SocketTask.close(function (close) {})
    var idx = app.globalData.roomList.indexOf(this.data.orderId)
    if (idx != -1) {
      app.globalData.roomList.splice(idx, 1)
    }
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

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
  webSocket: function () {
    // 创建Socket
    var app = getApp()
    var orderId = this.data.orderId
    var token = wx.getStorageSync('token')
    var data = {
      "token": token
    }
    SocketTask = wx.connectSocket({
      url: url + orderId + "/" + token,
      data: data,
      header: {
        'content-type': 'application/json'
      },
      method: 'post',
      success: function (res) {
        socketOpen = true;
        app.globalData.roomList.push(orderId)
      },
      fail: function (err) {
        wx.showToast({
          title: '网络异常！',
        })
      },
    })
  },
  chatInputSendTextMessage: function (e) {
    this.sendSocketMessage(e.detail.value);
    var that = this;
    var info = wx.getStorageSync('userInfo');
    var time = util.formatTime(new Date())
    var content = {
      "info": {
        "nickName": info.nickName,
        "avatarUrl": info.avatarUrl
      },
      "message": e.detail.value,
      "ownMsg": true,
      "time": time
    }
    var list = that.data.contentList
    list.push(content)
    that.setData({
      textMessage: '',
      contentList: list,
      scrollTop: 99999999
    })
  },
  sendSocketMessage: function (msg) {
    var that = this;
    SocketTask.send({
      data: msg
    }, function (res) {})
  }
})