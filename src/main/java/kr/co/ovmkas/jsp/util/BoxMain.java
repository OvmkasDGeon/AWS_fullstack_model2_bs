package kr.co.ovmkas.jsp.util;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

//제네릭
public class BoxMain {
	public static void main(String[] args) {
		Box<String> box = new Box<>();
		box.push("1");
		box.push("2");
		box.push("3");
//		System.out.println(box);
//		System.out.println(box.pop());
//		System.out.println(box.pop());
//		System.out.println(box.pop());
		box.unshift("123");
//		System.out.println(box.shift());
//		System.out.println(box);
		
//		box.setItem("abd");
//		System.out.println(box);
//		System.out.println(box.getItem());
//		
//		Box<Fruit> fruitBox = new Box</*Fruit*/>();
//		fruitBox.setItem(new Apple());
	
	}
}

@ToString
class Box<T>{
//	private T item;
//	
//	public void setItem(T item) {
//		this.item = item;
//	}
//	public T getItem() {
//		return item;
//	}
	private List<T> items = new ArrayList<>();
	public void push(T item) {
		items.add(item);
	}
	public T pop() {
		return items.remove(items.size()-1);
	}
	public void unshift(T item) {
		items.add(0, item);
	}
	public T shift() {
		return items.remove(0);
	}
}

abstract class Fruit {}

class Apple extends Fruit{}

class Grape extends Fruit{}