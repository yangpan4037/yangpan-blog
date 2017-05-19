package com.yangpan.background.query;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询后封装查询结果对象
 */
@SuppressWarnings("all")
public class PageList {
	
	// 每页显示条数pageSize
	private int pageSize;
	
	// 当前页面：currentPage
	private int currentPage;
	
	// 总的条数
	private int totalCount;
	
	// 总的页数：计算的
	private int totalPage;
	
	// 当前页的数据
	private List data = new ArrayList();

	//无参构造器
	public PageList() {}
	
	//有参构造器
	public PageList(int pageSize, int currentPage, int totalCount) {
		// 1.处理负数
		this.pageSize = pageSize < 0 ? 10 : pageSize;
		this.currentPage = currentPage < 0 ? 1 : currentPage;
		this.totalCount = totalCount;

		// 2.计算总的页数
		this.totalPage = (int) Math.ceil(this.totalCount * 1.0 / this.pageSize);

		// 3.判断当前页码不能大于总的页数
		this.currentPage = this.currentPage > this.totalPage ? this.totalPage : this.currentPage;
	}
	
	// 额外添加的方法

	public int getBegin() {
		return (currentPage - 1) * pageSize + 1;
	}

	public int getEnd() {
		int total = currentPage * pageSize;
		return total > totalCount ? totalCount : total;
	}
	
	/**
	 *	返回分页html代码的方法 
	 */
//	public String getPage() {
//		StringBuilder builder = new StringBuilder();
//		// 处理首页
//		if (this.currentPage == 1) {
//			builder.append("<li class='prev disabled'><a href='javascript:;'>首页</a></li>");
//			builder.append("<li class='prev disabled'><a href='javascript:;'>上一页</a></li>");
//		} else {
//			builder.append("<li class='prev'><a href='javascript:;' onclick='go(1);'>首页</a></li>");
//			builder.append("<li class='prev'><a href='javascript:;' onclick='go(" + (this.currentPage - 1) + ");'>上一页</a></li>");
//		}
//		/**
//		 * 处理中间页码
//		 * 中间页 类似123...15
//		 */
//		if(this.totalPage <= 5){
//			for (int i = 1; i <= this.totalPage; i++) {
//				if (this.currentPage == i) {
//					builder.append("<li class='active'><a href='javascript:;'>" + i + "</a></li>");
//				} else {
//					builder.append("<li class='prev'><a href='javascript:;' onclick='go(" + i + ");'>" + i + "</a></li>");
//				}
//			}
//		}else{
//			if(this.currentPage + 5 <= this.totalPage){
//				builder.append("<li class='prev active'><a href='javascript:;' onclick='go(" + this.currentPage + ");'>" + this.currentPage + "</a></li>");
//				builder.append("<li class='prev'><a href='javascript:;' onclick='go(" + (this.currentPage+1) + ");'>" + (this.currentPage+1) + "</a></li>");
//				builder.append("<li class='prev'><a href='javascript:;' onclick='go(" + (this.currentPage+2) + ");'>" + (this.currentPage+2) + "</a></li>");
//				builder.append("<li class='prev disabled'><a href='javascript:;'>...</a></li>");
//				builder.append("<li class='prev'><a href='javascript:;' onclick='go(" + this.totalPage + ");'>" + this.totalPage + "</a></li>");
//			}else{
//				for (int i = this.totalPage-4; i <= this.totalPage; i++) {
//					if(i == this.totalPage-3){
//						builder.append("<li class='prev disabled'><a href='javascript:;'>...</a></li>");
//					}else{
//						if (this.currentPage == i) {
//							builder.append("<li class='active'><a href='javascript:;'>" + i + "</a></li>");
//						} else {
//							builder.append("<li class='prev'><a href='javascript:;' onclick='go(" + i + ");'>" + i + "</a></li>");
//						}
//					}
//				}
//			}
//		}
//		
//
//		// 处理尾页
//		if (this.currentPage == this.totalPage) {
//			builder.append("<li class='next disabled'><a href='javascript:;'>下一页</a></li>");
//			builder.append("<li class='next disabled'><a href='javascript:;'>尾页</a></li>");
//		} else {
//			builder.append("<li class='next'><a href='javascript:;' onclick='go(" + (this.currentPage + 1) + ");'>下一页</a></li>");
//			builder.append("<li class='next'><a href='javascript:;' onclick='go(" + this.totalPage + ");'>尾页</a></li>");
//		}
//		return builder.toString();
//	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PageList [pageSize=" + pageSize + ", currentPage=" + currentPage + ", totalCount=" + totalCount
				+ ", totalPage=" + totalPage + ", data.size=" + data.size() + "]";
	}
	
}
