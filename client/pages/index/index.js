var api = require('../../config/api.js');
var util = require('../../utils/util.js');
var user = require('../../utils/user.js');
//index.js
//获取应用实例

var app = getApp()
const date = new Date()
const months = []
const days = []
const hours = []
const minutes = []
const partnerNums = []

let m = date.getMonth() + 1;
for (let i = 1; i <= 3; i++) {
  months.push(m);
  if (++m > 12) {
    m = 1;
  }
}

for (let i = 1; i <= 31; i++) {
  days.push(i)
}

for (let i = 5; i <= 23; i++) {
  hours.push(i)
}
minutes.push(0)
minutes.push(30)
for (let i = 2; i <= 6; i++) {
  partnerNums.push(i);
}
let curDay, curHour;
if (curHour < 5) {
  curDay = date.getDate();
  curHour = 5;
} else if (curHour > 23) {
  curDay = date.getDate() + 1;
  curHour = 5;
} else {
  curDay = date.getDate();
  curHour = date.getHours() + 1;
}
Page({
  data: {
    activeIndex: 0,
    sliderOffset: 0,
    sliderLeft: 0,
    year: date.getFullYear(),
    months,
    month: date.getMonth() + 1,
    days,
    day: curDay,
    hours,
    hour: curHour,
    minutes,
    minute: 0,
    partnerNums,
    partnerNum: 2,
    publishShow: false,
    endName: null,
    startName: null,
    startAddress: null,
    endAddress: null,
    publishView: true,
    orderList: [],
    orderShow: false,
    value: [0, date.getDate() - 1, date.getHours() < 5 ? date.getHours() : date.getHours() - 4, 0, 0]
  },
  onPullDownRefresh: function () {
    this.checkCondition();
    wx.stopPullDownRefresh();
  },
  onReady: function () {
    //   if (!app.globalData.hasLogin){
    //     wx.navigateTo({
    //       url: "/pages/login/login"
    //     });
    //  }
    user.checkLogin().catch(() => {
      wx.navigateTo({
        url: "/pages/login/login"
      });
    });

  },
  chooseStart: function () {
    var that = this
    wx.chooseLocation({
      success: function (res) {
        that.setData({
          startAddress: res,
          startName: res.name
        });
        that.checkCondition();
      }
    })
  },
  chooseEnd: function () {
    var that = this
    wx.chooseLocation({
      success: function (res) {
        that.setData({
          endAddress: res,
          endName: res.name
        });
        that.checkCondition();
      }
    })
  },
  checkCondition: function () {
    if (this.data.endAddress != null && this.data.startAddress != null) {
      wx.showLoading({
        title: '加载中...',
      });
      var startAddress = {
        latitude: this.data.startAddress.latitude,
        longitude: this.data.startAddress.longitude
      }
      var endAddress = {
        latitude: this.data.endAddress.latitude,
        longitude: this.data.endAddress.longitude
      }
      util.request(api.showFuzzyOrder, {
        startDot: startAddress,
        endDot: endAddress,
        token: wx.getStorageSync('token')
      }, 'POST').then(res => {
        wx.hideLoading();
        if (res.success) {
          if (res.orderList.length === 0) {
            this.setData({
              publishView: true,
              orderShow: true,
            })
          } else {
            this.setData({
              publishView: true,
              orderShow: true,
            })
          }
          this.setData({
            publishShow: true,
            orderList: res.orderList
          })
        }

      }).catch(res => {
        wx.hideLoading()
        util.showErrorToast("服务器繁忙,稍后再来吧")
      })
    }
  },
  publish: function (e) {
    this.setData({
      publishView: false
    })
    util.request(api.save, {
      "templateId": e.detail.formId,
      "token": wx.getStorageSync('token')
    }, 'POST')
  },
  cancel: function () {
    this.setData({
      publishView: true
    })
  },

  tabClick: function (e) {
    this.setData({
      sliderOffset: e.currentTarget.offsetLeft,
      activeIndex: e.currentTarget.id
    });
  },

  bindChange(e) {
    const val = e.detail.value
    this.setData({
      month: this.data.months[val[0]],
      day: this.data.days[val[1]],
      hour: this.data.hours[val[2]],
      minute: this.data.minutes[val[3]],
      partnerNum: this.data.partnerNums[val[4]]
    })
    if (this.data.month < date.getMonth() + 1 && this.data.year === date.getFullYear()) {
      this.setData({
        year: this.data.year + 1
      })
    }
    if (this.data.month >= date.getMonth() + 1 && this.data.year > date.getFullYear()) {
      this.setData({
        year: date.getFullYear()
      })
    }

  },
  confirm: function (e) {
    var selectDate = new Date();
    selectDate.setFullYear(this.data.year, this.data.month - 1, this.data.day);
    selectDate.setHours(this.data.hour);
    selectDate.setMinutes(this.data.minute);
    var today = new Date();
    if (selectDate < today) {
      util.showErrorToast("日期选择错误")
      return;
    } else {
      var data = this.data;
      var that = this;
      util.request(api.publish, {
        startAddress: {
          name: data.startAddress.name,
          address: data.startAddress.address,
          dot: {
            latitude: data.startAddress.latitude,
            longitude: data.startAddress.longitude
          }
        },
        endAddress: {
          name: data.endAddress.name,
          address: data.endAddress.address,
          dot: {
            latitude: data.endAddress.latitude,
            longitude: data.endAddress.longitude
          }
        },
        timeDTO: {
          year: data.year,
          month: data.month,
          day: data.day,
          hour: data.hour,
          minute: data.minute
        },
        targetNum: data.partnerNum,
        token: wx.getStorageSync('token')
      }, 'POST').then(res => {
        if (res.success) {
          util.showSuccessToast();
          this.setData({
            publishView: true
          })
          this.checkCondition()
        } else {
          util.showErrorToast("请求有误~")
        }
      }).catch(res => {
        util.showErrorToast("请求超时~")
      })
    }
  },


  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;
    if (this.data.endAddress != null && this.data.startAddress != null) {
      wx.showLoading({
        title: '加载中...',
      });
      var startAddress = {
        latitude: this.data.startAddress.latitude,
        longitude: this.data.startAddress.longitude
      }
      var endAddress = {
        latitude: this.data.endAddress.latitude,
        longitude: this.data.endAddress.longitude
      }
      util.request(api.showFuzzyOrder, {
        startDot: startAddress,
        endDot: endAddress,
        token: wx.getStorageSync('token')
      }, 'POST').then(res => {
        wx.hideLoading();
        if (res.success) {
          if (res.orderList.length === 0) {
            this.setData({
              publishView: true,
              orderShow: true,
            })
          } else {
            this.setData({
              publishView: true,
              orderShow: true,
            })
          }
          this.setData({
            publishShow: true,
            orderList: res.orderList
          })
        }

      }).catch(res => {
        wx.hideLoading()
        util.showErrorToast("服务器繁忙,稍后再来吧")
      })
    }
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