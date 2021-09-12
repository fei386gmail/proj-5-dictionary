import Mock from 'mockjs'

Mock.mock('http://localhost:8081/api/getData?ID=bookstore', 'get', {
     'data': [
      "bookable  adj.<主英>可预订的",
      "bookcase  n.书架,书柜",
      "bookie  n.<俚> 赛马的赌注者",
      "booklore  n.书本知识",
      "bookmobile  n.流动图书馆",
      "bookplate  n.藏书标签",
      "bookstore  n.书店"
    ]
})