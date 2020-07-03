import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.DataOutputStream;
public class Manager {
	
	Goods[] goodsList;
	int goodsCode=100; //�ڵ�� 100������ ���������� �ο�
	int categoryCount=0;
	Manager(int size){ 
		goodsList=new Goods[size];
	}
	
	int sales=0;  //���� ����.
	int num=0; //��ǰ�� ���� ����. �� ��ǰ �߰��� �ε��� ����
	int salesList[];  //������ �迭�� �����Ͽ� ��迡 ����� ����.
	
	int getNum() {
		return num;
	}
	void addNewGoods( Goods goods) throws Exception{ // �� ��ǰ �߰�  //�迭�� ũ�Ⱑ �Ѵ� ��� throw exception 
		if(num>100) {
			throw new Exception("������ �����մϴ�.");
		}
		goodsList[num]=goods;
		goodsList[num].setCode(goodsCode);  //�ڵ�� �ڵ� �ο�.
		goodsCode++;
		num++;  //��ǰ ���� �߰�.
	}
	
	Goods findGoodsByCode (int key) throws Exception{ // �ڵ� �Է��Ͽ� ��ǰ ã��, ã�� ��ǰ ���� ��� ��»��� ���ο� �߰��� ����.
		for(int i=0;i<num;i++) {
			if(goodsList[i].getCode()==key) {
				return goodsList[i];
			}
			
		}
		throw new Exception("ã�� �ڵ尡 �����ϴ�.");
		//return null;
	}
	
	int nameToCode(String name) throws Exception{  // ��ǰ �̸��� �ڵ�� �ٲٴ� �Լ�. �մ��̳� �޴����� �̸��� �Է��ϸ� �̸� �ڵ�� �ٲپ� ã�� ���� ������ �̷������.
		for(int i=0; i<num;i++) {
			if(goodsList[i].getName().equals(name)) {
				return goodsList[i].getCode();
			}
			if(i==num) {
				throw new Exception("��ǰ���� �߸� �Է��ϼ̽��ϴ�.");
			}
		}	
		return (-1); //ã�� ��ǰ�� ���� ���.  exception ����� �߰��� ����
	}
	
	int findGoodsIndex(String goodsName) throws Exception{
		for(int i=0;i<num;i++) {
			if(goodsList[i].getName().equals(goodsName)) {
				return i;
			}
			if(i==num) {
				throw new Exception("��ǰ���� �߸� �Է��ϼ̽��ϴ�.");
			}
		}
		return -1;
	}

	Goods[] findGoodsByCategory(String category) {
		Goods[] temp = new Goods[num];
		int j=0;
		for(int i=0;i<num;i++) {
			if(goodsList[i].getCategory().equals(category)) {
				temp[j]=goodsList[i];
				j++;
			}
		}
		categoryCount=j;
		if(j==0) { //ī�װ��� �ش��ϴ� ��ǰ�� ���� ���
			return null;
		}
		else {
			return temp;
		}
	}
	
	int getCategoryCount() {
		return categoryCount;
	}
	
	void deleteGoods(int index) { //��ǰ ����
		while(index!=num) {
			goodsList[index]=goodsList[++index];  //�����ϴ� ��ǰ �ڿ� �ִ� ��ǰ ���� �� ĭ�� ������.
		}
		goodsList[index]=null;  //������ ��ǰ�� �迭 �� ĭ�� ���������Ƿ� ������ ��ǰ ����.
		num--;  //��ǰ �� ���� ����.
	}
	
	Goods[] getList() { //��ǰ ����Ʈ ��ȯ.
		return goodsList;
	}
	
	void saveFile(String fileName) {
		DataOutputStream out=null;
		try {
			out=new DataOutputStream(new FileOutputStream(fileName));
			out.writeInt(num);
			out.writeInt(sales);
			for(int i=0;i<num;i++) {
				goodsList[i].save(out);
			}
		} catch (Exception e) {
			
		}finally {
			try {
				out.close();
			}
			catch(Exception e) {	
			}
		}
	}
	
	void openFile(String fileName) {
		DataInputStream in=null;
		try {		
			in=new DataInputStream(new FileInputStream(fileName));
			int count=in.readInt();
			sales=in.readInt();
			for(int i=0;i<count;i++) {
				Goods new_goods=new Goods();
				new_goods.read(in);
				addNewGoods(new_goods);
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

