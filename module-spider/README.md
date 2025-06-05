#

## 爬虫

爬取页面时，先得到html，再得到所有js/css/image/zip/... 再得到所有a标签。

顺序模式下，先保存html，再保存js/css/image/zip/...，再循环处理所有a标签。

多线程式下，先保存html，再将js/css/image/zip/...的链接保存到redis中(每保存一个文件，就删除一下)，
再将所有a标签保存到redis队列中（）

重试
