/*
 * 프로그램명: 편의점 관리 시스템
 * 프로그램 내용: 1. 물품 구입하기  2. 관리자 모드 (물품 추가, 삭제, 파일 저장, 파일 불러오기) 3. 매출 보고
 * 				관리자 비밀번호 : 0000 / 물품은 file.dat에 저장되어 있습니다.  
 * 작성자: 조서연
 */
//

import java.util.Scanner;

public class Main { 
	
	void printMenu(){         // 전체 메뉴
		System.out.println("*******************************");
		System.out.println("      원하시는 메뉴를 선택하세요                   ");
		System.out.println("*******************************");
		System.out.println("1. 물건 구입 ");
		System.out.println("2. 관리자 모드");
		System.out.println("3. 매출 보고 ");
		System.out.println("4. 프로그램 종료");
		System.out.print("선택사항 : ");
	}
	
	void customer_printGoodsList(Goods[] goodsList, int count) {  // 물품명, 가격 출력
		if(count==0) {
			System.out.println("물품 목록이 비어있습니다.");
		}
		else {
			System.out.println("**물품 목록**");
			for(int i=0; i<count;i++) {
				System.out.println(goodsList[i].getName()+" "+goodsList[i].getPrice()+"원");
			}
		}
	}
	
	void manager_printGoodsList(Goods[] goodsList, int count) {  // 대분류, 물품명, 코드, 가격, 재고  출력
		System.out.println("**물품 목록**");
		for(int i=0; i<count;i++) {
			System.out.println("카테고리: "+goodsList[i].getCategory()+" / 물품명: "+goodsList[i].getName()+" / 물품코드: "+goodsList[i].getCode()+" "
					+ "/ 가격: "+goodsList[i].getPrice()+" / 재고: "+goodsList[i].getStock());
		}
	}	
	
	void category_printGoodsList(Goods[] goods, int categoryCount) {  // 특정 카테고리에 들어있는 물품명 출력
		int i=categoryCount;
		for(int t=0;t<i;t++) {
			System.out.println("물품명: "+goods[t].getName());
		}

	}
	
	public static void main(String args[]) throws Exception{
		Main menu=new Main();
		int arraySize=100;
		Manager manager=new Manager(arraySize); 
		int totalPrice=0;  // 구매할 총 금액을 저장하는 변수
		int choice=0;	// 메뉴 선택 사항
		
		while(choice!=4){ 	//프로그램 종료 전까지 반복되도록 한다. 
			Scanner scan = new Scanner(System.in);
			menu.printMenu();   //메뉴 출력
			choice=scan.nextInt();  //메뉴 중 선택 사항 저장
			scan.nextLine();
			switch(choice){  //1.물건 구입 2.물품관리 3.매출보고 4.프로그램종료

				case 1: //물건 구입
					String goodsName;  //선택한 물품명 저장하는 변수
					int amount;        //원하는 물품의 수량 저장하는 변수
					int sellEstimate=0;		//지불 예상 금액
					
					try {
					System.out.println("카테고리명을 입력하세요 : ");
					goodsName=scan.next();
					if(manager.findGoodsByCategory(goodsName)==null) {  //카테고리에 해당되는 물품도 없을 때.
						System.out.println("원하시는 카테고리가 존재하지 않습니다.");
						break;
					}
					//카테고리로 찾기
					menu.category_printGoodsList(manager.findGoodsByCategory(goodsName),manager.getCategoryCount());
					System.out.println("구입할 물품을 입력하세요 : ");
					goodsName=scan.next();
					System.out.print("원하는 개수를 입력하세요 : ");
					amount=scan.nextInt();
					scan.nextLine();
					int code=manager.nameToCode(goodsName);  
					// 위에서 구한 코드를 통해 물품의 재고를 확인한다.
					if(code==-1) { //물품명을 잘못 입력한 경우. 즉 입력한 물품이 리스트에 없는 경우
						System.out.println("물품명을 잘못 입력하셨습니다.");
						break;
					}
					else{    
						manager.findGoodsByCode(code).checkStock(amount);         //재고 확인
						sellEstimate+=manager.findGoodsByCode(code).getPrice()*amount;  //물품의 가격*수량을 sellEstimate에 저장.
						
						int payChoice=0;
						while(payChoice!=2) { // 결제하기
							try {
							System.out.println("최종 결제 금액은 "+sellEstimate+"입니다.");
							System.out.println("1. 결제하기  2. 결제 취소");
							System.out.println("선택사항 : ");
							payChoice=scan.nextInt();
							scan.nextLine();
							}catch(java.util.InputMismatchException e) {       
								System.out.println("잘못된 입력입니다.");	
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
							switch(payChoice) {
							case 1:
								try {
								System.out.print("지불하실 금액을 입력하세요 : ");
								int pay=scan.nextInt();
								scan.nextLine();
								while(pay<sellEstimate) {   //금액이 충분한지 확인
									System.out.println("금액이 충분하지 않습니다.");
									System.out.println("추가로 지불하실 금액을 입력하세요 : ");  //금액이 충불할 때 까지 추가 금액을 받음.
									int p=scan.nextInt();
									pay+=p;
								}
								if(pay>=sellEstimate) {
									System.out.println("거스름돈 : "+(pay-sellEstimate));  //거스름돈 계산해서 출력
									System.out.println("결제가 완료되었습니다.");
									totalPrice=sellEstimate;                          
									manager.sales+=totalPrice;                        //총매출에 수익 추가
									manager.findGoodsByCode(code).sell(amount);       // 해당 물품을 찾아 sell 함수로 재고를 줄임.
									sellEstimate=0;    // 저장해둔 금액 초기화
									payChoice=2;       //반복문 종료
								}
								}catch(NullPointerException e) {
									System.out.println("오류 발생");
								}catch(java.util.InputMismatchException e) {       
									System.out.println("잘못된 입력입니다.");	
								}catch(Exception e) {
									System.out.println("예외가 발생했습니다.");
								}
								break;
							case 2: 
								sellEstimate=0;  // 결제 취소. 저장해둔 금액 초기화
								payChoice=2;     //반복문 종료
								break;
							default:
								break;
							}
						}
						break;
					}
					}catch(NullPointerException e) {
						System.out.println("오류 발생");
					}catch(java.util.InputMismatchException e) {       
						System.out.println("잘못된 입력입니다.");	
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					
					
					break;
				case 2: //물품관리
					
					// 메니저 비밀번호 입력과 확인
					int password=0000;
					System.out.println("관리자 비밀번호를 입력하세요 : ");
					int input_p=scan.nextInt();
					scan.nextLine();
					if(password!=input_p) {
						System.out.println("비밀번호가 틀렸습니다!");
						System.out.println("관리자 모드를 종료합니다");
						break;
					}
					
					else {
						int mChoice=0;  //manager의 선택 사항 저장.
						
						while(mChoice!=5) { //관리자가 관리자 모드를 종료할때까지 반복함. 
							System.out.println("원하시는 메뉴를 선택하세요.");  //관리자가 물품 관리를 위해 선택하는 메뉴
							System.out.println("1. 새 물품 추가  2. 물품 재고 관리  3. 파일 불러오기  4.저장하기   5. 관리자 모드 종료 "); 
							try {
							System.out.println("선택 사항 : ");
							mChoice=scan.nextInt();
							scan.nextLine();
							}catch(java.util.InputMismatchException e) {       
								System.out.println("잘못된 입력입니다.");	
							}catch(Exception e) {
								System.out.println("예외가 발생했습니다.");
							}
							switch(mChoice) {  
							case 1:  // 새 물품을 리스트에 추가한다.
								try {
								String category;
								String name;
								int price;
								int stock;
								System.out.println("상위 카테고리: ");
								category=scan.next();
								System.out.println("물품명: ");
								name=scan.next();
								System.out.println("가격: ");
								price=scan.nextInt();
								scan.nextLine();
								System.out.println("수량: ");
								stock=scan.nextInt();
								scan.nextLine();
								
								Goods new_goods=new Goods(category,name,stock,price);
								manager.addNewGoods(new_goods);  
								}catch(NullPointerException e) {
									System.out.println("오류 발생");
								}catch(java.util.InputMismatchException e) {       
									System.out.println("잘못된 입력입니다.");	
								}catch(Exception e) {
									System.out.println("예외가 발생했습니다.");
								}
								break;
							case 2:  //물품 재고 관리
								int choice2=0;
								try {
								System.out.println("1.물품 삭제  2.물품목록 확인");
								choice2=scan.nextInt();
								scan.nextLine();
								switch(choice2) {
								case 1:
									System.out.println("물품명 또는 카테고리명을 입력하세요 : ");
									String cg=scan.next();
									if(manager.findGoodsIndex(cg)==-1) {   //물품명에 해당하는 물품이 없을 때.
										if(manager.findGoodsByCategory(cg)==null) {  //카테고리에 해당되는 물품도 없을 때.
											System.out.println("원하시는 물품 또는 카테고리가 존재하지 않습니다.");
											break;
										}
										//카테고리로 찾기
										menu.category_printGoodsList(manager.findGoodsByCategory(cg),manager.getCategoryCount());
										System.out.println("삭제하고자 하는 물품명을 입력하세요 : ");
										String deleteG=scan.next();
										int index=manager.findGoodsIndex(deleteG);
										manager.deleteGoods(index);
									}
									else { //물품명을 제대로 입력한 경우
										int index=manager.findGoodsIndex(cg);
										manager.deleteGoods(index);  // 물품 삭제
									}
									break;
								case 2:
									menu.manager_printGoodsList(manager.getList(), manager.getNum());  //물품 목록 출력
									break;
								default:
									break;
								}
								}catch(NullPointerException e) {
									System.out.println("오류 발생");
								}catch(java.util.InputMismatchException e) {       
									System.out.println("잘못된 입력입니다.");	
								}catch(Exception e) {
									System.out.println("예외가 발생했습니다.");
								}
								break;
							case 3:  //파일 불러오기
								System.out.println("파일의 이름을 입력하세요 : ");
								String gfileName=scan.nextLine();
								manager.openFile(gfileName);
								System.out.println("불러오기 완료");
								break;
							case 4:		//파일 저장하기
								System.out.println("저장할 파일의 이름을 입력하세요 : ");
								String sfileName=scan.nextLine();
								manager.saveFile(sfileName);
								System.out.println("저장 완료");
								break;
							case 5:
								break;
							default:
								System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
								break;
							}
						}
					}
					break;
					
				case 3:  //매출 보고 
					System.out.println("총 매출 : "+manager.sales);
					break;
				case 4:  //프로그램 종료.
					System.out.println("프로그램을 종료합니다.");
					break;
				default:
					System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
					break;
			}
		}
	}
}
