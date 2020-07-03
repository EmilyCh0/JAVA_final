import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Goods {
	private String category;  //��ǰ�� ���� ī�װ� 
	private String goodsName; //��ǰ��
	private int goodsCode;    //��ǰ �ڵ�
	private int stock;        //���
	private int price;        //����
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
	void checkStock (int amount) throws Exception {  // ��� Ȯ��
		if(stock<amount) {
			throw new Exception("��� �����մϴ�!");
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
			throw new Exception("������ �� ���� ���Դϴ�.");
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
			throw new Exception("��� �����մϴ�.");
		}
		else {
			this.stock-=amount;
		}
	}
	void sell(int amount) throws Exception{
		if(stock<amount) {
			throw new Exception("��� �����մϴ�.");
		}
		else {
			this.stock-=amount;
		}
	}
	
	void save(DataOutputStream out) throws Exception{  //��ü�� ������ ����.
		try {
			out.writeUTF(goodsName);
			out.writeUTF(category);
			out.writeInt(price);
			out.writeInt(stock);
		} catch (FileNotFoundException e) {
			throw new Exception("������ ã�� �� �����ϴ�.");
		} catch (IOException e) {
			throw new Exception("������ ����� �� �����ϴ�.");
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

