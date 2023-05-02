package kr.co.ovmkas.jsp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
	private int pageNum = 1;
	private int amount = 10;
	private int category = 1;
	private String[] type;
	private String keyword ="";
	
	public Criteria(int pageNum, int amount) {
		super();
		this.pageNum = pageNum;
		this.amount = amount;
	}
	public String getQueryString() {
		String str = "";
		str += "amount=" + amount + "&category="+category;
		str += getTypeStr();
		return str;
	}
	public String getFullQueryString() {
		String str ="pageNum="+ pageNum + "&";
		str += getQueryString();
		return str;
	}
	
	public String getTypeStr() {
		String str ="";
		if(type != null) {
			for(String s: type) {
				str += "&type=" +s;
			}
			str += "&keyword=" + keyword;
		}
		return str;
	}
}
