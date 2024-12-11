module.exports = {
  devServer: {
    port:8080,
    open:true,
    host:'localhost',
    proxy: { //匹配规则
        '/': {
            //要访问的跨域的域名
            target: 'http://localhost:2222',
            // target: 'http://localhost:18081',

            ws: true,
            secure:false, // 使用的是http协议则设置为false，https协议则设置为true
            changOrigin: true, //开启代理
            // pathRewrite: {
            //     '^/api': ''
            // }
        }
    }
  }
}
