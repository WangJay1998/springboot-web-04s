package com.ktjiaoyu.demo.utils;

public class Page {
      //当前页
      private int PageDq=1;
      //总数量
      private int PageSum=0;

      //每页的数量
      private int PageSize=5;

      //页数
      private int PageCount=1;

    public int getPageDq() {
        return PageDq;
    }

    public void setPageDq(int pageDq) {
        PageDq = pageDq;
    }

    public int getPageSum() {
        return PageSum;
    }

    public void setPageSum(int pageSum) {
        PageSum = pageSum;
        this.setTotalPageCountByRs();
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public void setTotalPageCountByRs(){
        if(this.PageSum%this.PageSize==0){
            this.PageCount=this.PageSum/this.PageSize;
        } else if(this.PageSum%this.PageSize>0){
            this.PageCount=this.PageSum/this.PageSize+1;
        }else{
            this.PageCount=0;
        }
    }
}
