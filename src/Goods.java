import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Goods {
	private String category;  //물품의 상위 카테고리 
	private String goodsName; //물품명
	private int goodsCode;    //물품 코드
	private int stock;        //재고
	private int price;        //가격
	Goods(){
		goodsName=null;
		goodsCode=0;
		stock=0;
		price=0;
	}
	Goods(String category, String goodsName, int stock, int price){
		this.category=category;
		this.goodsName=goodsName;
		this.stock=stock;
		this.price=price;
	}
	
	String getCategory() {
		return category;
	}
	String getName() {
		return goodsName;
	}
	int getCode() {
		return goodsCode;
	}
	int getStock() {
		return stock;
	}
	void checkStock (int amount) throws Exception {  // 재고 확인
		if(stock<amount) {
			throw new Exception("재고가 부족합니다!");
		}
	}
	int getPrice() {
		return price;
	}
	void setCategory(String c) {
		this.category=c;
	}
	void setName(String name) {
		this.goodsName=name;
	}
	void setCode(int code) {
		this.goodsCode=code;
	}
	void setPrice(int p) {
		this.price=p;
	}
	void setStock(int s) throws Exception{
		if(stock<0) {
			throw new Exception("설정할 수 없는 값입니다.");
		}
		else {
			this.stock=s;
		}
	}
	void addStock(int amount) {
		this.stock+=amount;
	}
	void subStock(int amount) throws Exception{
		if(stock<amount) {
			throw new Exception("재고가 부족합니다.");
		}
		else {
			this.stock-=amount;
		}
	}
	void sell(int amount) throws Exception{
		if(stock<amount) {
			throw new Exception("재고가 부족합니다.");
		}
		else {
			this.stock-=amount;
		}
	}
	
	void save(DataOutputStream out) throws Exception{  //객체의 정보를 저장.
		try {
			out.writeUTF(goodsName);
			out.writeUTF(category);
			out.writeInt(price);
			out.writeInt(stock);
		} catch (FileNotFoundException e) {
			throw new Exception("파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			throw new Exception("파일을 출력할 수 없습니다.");
		}
		finally {
			try {
				//out.close();
			}
			catch(Exception e) {	
			}
		}
	}
	void read(DataInputStream in) {
		try {
			this.goodsName=in.readUTF();
			this.category=in.readUTF();
			this.price=in.readInt();
			this.stock=in.readInt();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

