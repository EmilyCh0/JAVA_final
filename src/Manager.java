import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.DataOutputStream;
public class Manager {
	
	Goods[] goodsList;
	int goodsCode=100; //코드는 100번부터 순차적으로 부여
	int categoryCount=0;
	Manager(int size){ 
		goodsList=new Goods[size];
	}
	
	int sales=0;  //매출 저장.
	int num=0; //물품의 개수 저장. 새 물품 추가시 인덱스 역할
	int salesList[];  //매출을 배열로 저장하여 통계에 사용할 예정.
	
	int getNum() {
		return num;
	}
	void addNewGoods( Goods goods) throws Exception{ // 새 물품 추가  //배열의 크기가 넘는 경우 throw exception 
		if(num>100) {
			throw new Exception("공간이 부족합니다.");
		}
		goodsList[num]=goods;
		goodsList[num].setCode(goodsCode);  //코드는 자동 부여.
		goodsCode++;
		num++;  //물품 개수 추가.
	}
	
	Goods findGoodsByCode (int key) throws Exception{ // 코드 입력하여 물품 찾기, 찾는 물품 없을 경우 출력사항 메인에 추가할 예정.
		for(int i=0;i<num;i++) {
			if(goodsList[i].getCode()==key) {
				return goodsList[i];
			}
			
		}
		throw new Exception("찾는 코드가 없습니다.");
		//return null;
	}
	
	int nameToCode(String name) throws Exception{  // 제품 이름을 코드로 바꾸는 함수. 손님이나 메니저가 이름을 입력하면 이를 코드로 바꾸어 찾는 등의 관리가 이루어진다.
		for(int i=0; i<num;i++) {
			if(goodsList[i].getName().equals(name)) {
				return goodsList[i].getCode();
			}
			if(i==num) {
				throw new Exception("제품명을 잘못 입력하셨습니다.");
			}
		}	
		return (-1); //찾는 물품이 없는 경우.  exception 출력을 추가할 예정
	}
	
	int findGoodsIndex(String goodsName) throws Exception{
		for(int i=0;i<num;i++) {
			if(goodsList[i].getName().equals(goodsName)) {
				return i;
			}
			if(i==num) {
				throw new Exception("제품명을 잘못 입력하셨습니다.");
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
		if(j==0) { //카테고리에 해당하는 물품이 없는 경우
			return null;
		}
		else {
			return temp;
		}
	}
	
	int getCategoryCount() {
		return categoryCount;
	}
	
	void deleteGoods(int index) { //물품 삭제
		while(index!=num) {
			goodsList[index]=goodsList[++index];  //삭제하는 물품 뒤에 있는 물품 전부 한 칸씩 앞으로.
		}
		goodsList[index]=null;  //마지막 물품을 배열 앞 칸에 저장했으므로 마지막 물품 삭제.
		num--;  //물품 총 개수 저장.
	}
	
	Goods[] getList() { //물품 리스트 반환.
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

