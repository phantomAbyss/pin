var api = require('../../config/api.js');
var util = require('../../utils/util.js');
var user = require('../../utils/user.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    height: 0,  //屏幕高度
    chatHeight: 0,//聊天屏幕高度
    orderList:[] ,
    warn : false 
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
    this.show();
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
    var that = this;
    that.show();
    wx.stopPullDownRefresh();
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
    wx.navigateTo({
      url: '/pages/room/room?orderId=' + e.target.dataset.orderid,
    })
  },

  show: function () {
    var that = this;
    util.request(api.own, {
      "token": wx.getStorageSync('token')
    }, 'POST').then(res => {
      if (res.success) {
        that.setData({
          warn :false,
          orderList: res.orderList
        })
      }
      if(res.orderList.length === 0){
        that.setData({
          warn: true
        })
      }

    }).catch(res =>{

    })

  },
  exit:function(e){
    var that = this;
    wx.showModal({
      content: '是否决定退出队伍?',
      confirmText: "退出",
      cancelText: "不退出",
      success: function (res) {
        
        if (res.confirm) {
          
          util.request(api.cancel, {
            formId: e.detail.formId,
            orderId: e.target.dataset.orderid,
            token: wx.getStorageSync('token')
          }, 'POST').then(res => {
            if (res.success) {
              var list = that.data.orderList;

                for (let i = 0; i < list.length; i++) {
                  if(list[i].id === e.target.dataset.orderid){
                    list.splice(i, 1);
                    break
                  }
                }
                if(list.length>0){
                that.setData({
                  warn : false,
                  orderList: list,
                })
                }else{
                  that.setData({
                    listen: true,
                    warn:true,
                    orderList: list,
                  })
                }
              util.showSuccessToast()
            }
          })


        }
      }
    });
  }

})