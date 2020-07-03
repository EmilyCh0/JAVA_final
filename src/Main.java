/*
 * ���α׷���: ������ ���� �ý���
 * ���α׷� ����: 1. ��ǰ �����ϱ�  2. ������ ��� (��ǰ �߰�, ����, ���� ����, ���� �ҷ�����) 3. ���� ����
 * 				������ ��й�ȣ : 0000 / ��ǰ�� file.dat�� ����Ǿ� �ֽ��ϴ�.  
 * �ۼ���: ������
 */
//

import java.util.Scanner;

public class Main { 
	
	void printMenu(){         // ��ü �޴�
		System.out.println("*******************************");
		System.out.println("      ���Ͻô� �޴��� �����ϼ���                   ");
		System.out.println("*******************************");
		System.out.println("1. ���� ���� ");
		System.out.println("2. ������ ���");
		System.out.println("3. ���� ���� ");
		System.out.println("4. ���α׷� ����");
		System.out.print("���û��� : ");
	}
	
	void customer_printGoodsList(Goods[] goodsList, int count) {  // ��ǰ��, ���� ���
		if(count==0) {
			System.out.println("��ǰ ����� ����ֽ��ϴ�.");
		}
		else {
			System.out.println("**��ǰ ���**");
			for(int i=0; i<count;i++) {
				System.out.println(goodsList[i].getName()+" "+goodsList[i].getPrice()+"��");
			}
		}
	}
	
	void manager_printGoodsList(Goods[] goodsList, int count) {  // ��з�, ��ǰ��, �ڵ�, ����, ���  ���
		System.out.println("**��ǰ ���**");
		for(int i=0; i<count;i++) {
			System.out.println("ī�װ�: "+goodsList[i].getCategory()+" / ��ǰ��: "+goodsList[i].getName()+" / ��ǰ�ڵ�: "+goodsList[i].getCode()+" "
					+ "/ ����: "+goodsList[i].getPrice()+" / ���: "+goodsList[i].getStock());
		}
	}	
	
	void category_printGoodsList(Goods[] goods, int categoryCount) {  // Ư�� ī�װ��� ����ִ� ��ǰ�� ���
		int i=categoryCount;
		for(int t=0;t<i;t++) {
			System.out.println("��ǰ��: "+goods[t].getName());
		}

	}
	
	public static void main(String args[]) throws Exception{
		Main menu=new Main();
		int arraySize=100;
		Manager manager=new Manager(arraySize); 
		int totalPrice=0;  // ������ �� �ݾ��� �����ϴ� ����
		int choice=0;	// �޴� ���� ����
		
		while(choice!=4){ 	//���α׷� ���� ������ �ݺ��ǵ��� �Ѵ�. 
			Scanner scan = new Scanner(System.in);
			menu.printMenu();   //�޴� ���
			choice=scan.nextInt();  //�޴� �� ���� ���� ����
			scan.nextLine();
			switch(choice){  //1.���� ���� 2.��ǰ���� 3.���⺸�� 4.���α׷�����

				case 1: //���� ����
					String goodsName;  //������ ��ǰ�� �����ϴ� ����
					int amount;        //���ϴ� ��ǰ�� ���� �����ϴ� ����
					int sellEstimate=0;		//���� ���� �ݾ�
					
					try {
					System.out.println("ī�װ����� �Է��ϼ��� : ");
					goodsName=scan.next();
					if(manager.findGoodsByCategory(goodsName)==null) {  //ī�װ��� �ش�Ǵ� ��ǰ�� ���� ��.
						System.out.println("���Ͻô� ī�װ��� �������� �ʽ��ϴ�.");
						break;
					}
					//ī�װ��� ã��
					menu.category_printGoodsList(manager.findGoodsByCategory(goodsName),manager.getCategoryCount());
					System.out.println("������ ��ǰ�� �Է��ϼ��� : ");
					goodsName=scan.next();
					System.out.print("���ϴ� ������ �Է��ϼ��� : ");
					amount=scan.nextInt();
					scan.nextLine();
					int code=manager.nameToCode(goodsName);  
					// ������ ���� �ڵ带 ���� ��ǰ�� ��� Ȯ���Ѵ�.
					if(code==-1) { //��ǰ���� �߸� �Է��� ���. �� �Է��� ��ǰ�� ����Ʈ�� ���� ���
						System.out.println("��ǰ���� �߸� �Է��ϼ̽��ϴ�.");
						break;
					}
					else{    
						manager.findGoodsByCode(code).checkStock(amount);         //��� Ȯ��
						sellEstimate+=manager.findGoodsByCode(code).getPrice()*amount;  //��ǰ�� ����*������ sellEstimate�� ����.
						
						int payChoice=0;
						while(payChoice!=2) { // �����ϱ�
							try {
							System.out.println("���� ���� �ݾ��� "+sellEstimate+"�Դϴ�.");
							System.out.println("1. �����ϱ�  2. ���� ���");
							System.out.println("���û��� : ");
							payChoice=scan.nextInt();
							scan.nextLine();
							}catch(java.util.InputMismatchException e) {       
								System.out.println("�߸��� �Է��Դϴ�.");	
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
							switch(payChoice) {
							case 1:
								try {
								System.out.print("�����Ͻ� �ݾ��� �Է��ϼ��� : ");
								int pay=scan.nextInt();
								scan.nextLine();
								while(pay<sellEstimate) {   //�ݾ��� ������� Ȯ��
									System.out.println("�ݾ��� ������� �ʽ��ϴ�.");
									System.out.println("�߰��� �����Ͻ� �ݾ��� �Է��ϼ��� : ");  //�ݾ��� ����� �� ���� �߰� �ݾ��� ����.
									int p=scan.nextInt();
									pay+=p;
								}
								if(pay>=sellEstimate) {
									System.out.println("�Ž����� : "+(pay-sellEstimate));  //�Ž����� ����ؼ� ���
									System.out.println("������ �Ϸ�Ǿ����ϴ�.");
									totalPrice=sellEstimate;                          
									manager.sales+=totalPrice;                        //�Ѹ��⿡ ���� �߰�
									manager.findGoodsByCode(code).sell(amount);       // �ش� ��ǰ�� ã�� sell �Լ��� ��� ����.
									sellEstimate=0;    // �����ص� �ݾ� �ʱ�ȭ
									payChoice=2;       //�ݺ��� ����
								}
								}catch(NullPointerException e) {
									System.out.println("���� �߻�");
								}catch(java.util.InputMismatchException e) {       
									System.out.println("�߸��� �Է��Դϴ�.");	
								}catch(Exception e) {
									System.out.println("���ܰ� �߻��߽��ϴ�.");
								}
								break;
							case 2: 
								sellEstimate=0;  // ���� ���. �����ص� �ݾ� �ʱ�ȭ
								payChoice=2;     //�ݺ��� ����
								break;
							default:
								break;
							}
						}
						break;
					}
					}catch(NullPointerException e) {
						System.out.println("���� �߻�");
					}catch(java.util.InputMismatchException e) {       
						System.out.println("�߸��� �Է��Դϴ�.");	
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					
					
					break;
				case 2: //��ǰ����
					
					// �޴��� ��й�ȣ �Է°� Ȯ��
					int password=0000;
					System.out.println("������ ��й�ȣ�� �Է��ϼ��� : ");
					int input_p=scan.nextInt();
					scan.nextLine();
					if(password!=input_p) {
						System.out.println("��й�ȣ�� Ʋ�Ƚ��ϴ�!");
						System.out.println("������ ��带 �����մϴ�");
						break;
					}
					
					else {
						int mChoice=0;  //manager�� ���� ���� ����.
						
						while(mChoice!=5) { //�����ڰ� ������ ��带 �����Ҷ����� �ݺ���. 
							System.out.println("���Ͻô� �޴��� �����ϼ���.");  //�����ڰ� ��ǰ ������ ���� �����ϴ� �޴�
							System.out.println("1. �� ��ǰ �߰�  2. ��ǰ ��� ����  3. ���� �ҷ�����  4.�����ϱ�   5. ������ ��� ���� "); 
							try {
							System.out.println("���� ���� : ");
							mChoice=scan.nextInt();
							scan.nextLine();
							}catch(java.util.InputMismatchException e) {       
								System.out.println("�߸��� �Է��Դϴ�.");	
							}catch(Exception e) {
								System.out.println("���ܰ� �߻��߽��ϴ�.");
							}
							switch(mChoice) {  
							case 1:  // �� ��ǰ�� ����Ʈ�� �߰��Ѵ�.
								try {
								String category;
								String name;
								int price;
								int stock;
								System.out.println("���� ī�װ�: ");
								category=scan.next();
								System.out.println("��ǰ��: ");
								name=scan.next();
								System.out.println("����: ");
								price=scan.nextInt();
								scan.nextLine();
								System.out.println("����: ");
								stock=scan.nextInt();
								scan.nextLine();
								
								Goods new_goods=new Goods(category,name,stock,price);
								manager.addNewGoods(new_goods);  
								}catch(NullPointerException e) {
									System.out.println("���� �߻�");
								}catch(java.util.InputMismatchException e) {       
									System.out.println("�߸��� �Է��Դϴ�.");	
								}catch(Exception e) {
									System.out.println("���ܰ� �߻��߽��ϴ�.");
								}
								break;
							case 2:  //��ǰ ��� ����
								int choice2=0;
								try {
								System.out.println("1.��ǰ ����  2.��ǰ��� Ȯ��");
								choice2=scan.nextInt();
								scan.nextLine();
								switch(choice2) {
								case 1:
									System.out.println("��ǰ�� �Ǵ� ī�װ����� �Է��ϼ��� : ");
									String cg=scan.next();
									if(manager.findGoodsIndex(cg)==-1) {   //��ǰ�� �ش��ϴ� ��ǰ�� ���� ��.
										if(manager.findGoodsByCategory(cg)==null) {  //ī�װ��� �ش�Ǵ� ��ǰ�� ���� ��.
											System.out.println("���Ͻô� ��ǰ �Ǵ� ī�װ��� �������� �ʽ��ϴ�.");
											break;
										}
										//ī�װ��� ã��
										menu.category_printGoodsList(manager.findGoodsByCategory(cg),manager.getCategoryCount());
										System.out.println("�����ϰ��� �ϴ� ��ǰ���� �Է��ϼ��� : ");
										String deleteG=scan.next();
										int index=manager.findGoodsIndex(deleteG);
										manager.deleteGoods(index);
									}
									else { //��ǰ���� ����� �Է��� ���
										int index=manager.findGoodsIndex(cg);
										manager.deleteGoods(index);  // ��ǰ ����
									}
									break;
								case 2:
									menu.manager_printGoodsList(manager.getList(), manager.getNum());  //��ǰ ��� ���
									break;
								default:
									break;
								}
								}catch(NullPointerException e) {
									System.out.println("���� �߻�");
								}catch(java.util.InputMismatchException e) {       
									System.out.println("�߸��� �Է��Դϴ�.");	
								}catch(Exception e) {
									System.out.println("���ܰ� �߻��߽��ϴ�.");
								}
								break;
							case 3:  //���� �ҷ�����
								System.out.println("������ �̸��� �Է��ϼ��� : ");
								String gfileName=scan.nextLine();
								manager.openFile(gfileName);
								System.out.println("�ҷ����� �Ϸ�");
								break;
							case 4:		//���� �����ϱ�
								System.out.println("������ ������ �̸��� �Է��ϼ��� : ");
								String sfileName=scan.nextLine();
								manager.saveFile(sfileName);
								System.out.println("���� �Ϸ�");
								break;
							case 5:
								break;
							default:
								System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
								break;
							}
						}
					}
					break;
					
				case 3:  //���� ���� 
					System.out.println("�� ���� : "+manager.sales);
					break;
				case 4:  //���α׷� ����.
					System.out.println("���α׷��� �����մϴ�.");
					break;
				default:
					System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
					break;
			}
		}
	}
}
