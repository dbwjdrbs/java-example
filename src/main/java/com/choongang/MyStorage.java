package com.choongang;

import java.util.Scanner;

public class MyStorage {
    public final static String EMPTY = "등록 가능";
    static String[] products = new String[]{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};
    static int[] counts = new int[]{0, 0, 0, 0, 0};

    /**
     * 1. 물건 정보 등록
     **/
    static void prod_input(Scanner s) {
        int cnt = 0;
        for (String str : products) {
            if (str.equals(EMPTY)) {
                cnt++;
            }
        }
        if (cnt == 0) System.out.println("[Warning] 목록이 가득 찼습니다.");

        cnt = 0;
        System.out.print("[System] 제품 등록을 원하는 제품명을 입력하세요 : ");
        for (int i = 0; i < products.length; i++) {
            if (products[i].equals(EMPTY)) { // 빈곳이 있을 경우 추가 가능
                String prod_name = s.nextLine();
                for (int j = 0; j < products.length; j++) { // 중복 검사
                    if (products[j].equals(prod_name)) {
                        System.out.println("[Warning] 중복된 품목 입니다.");
                        products[i] = EMPTY;
                        break;
                    } else if (prod_name.isEmpty()) {
                        System.out.println("[Warning] 잘못된 입력입니다.");
                        break;
                    } else {
                        products[i] = prod_name;
                        System.out.println("[System] 등록이 완료됬습니다.");
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * 2. 물건 정보 제거
     **/
    static void prod_remove(Scanner s) {
        int cnt = 0;
        for (String str : products) {
            if (str.equals(EMPTY)) {
                cnt++;
            }
        }
        if (cnt == 5) {
            System.out.println("[Warning] 물품이 없습니다.");
        } else {
            System.out.print("[System] 제품 등록 취소를 원하는 제품명을 입력하세요 : ");
            String prod_name = s.nextLine();
            for (int i = 0; i < products.length; i++) {
                if (products[i].equals(prod_name)) {
                    products[i] = EMPTY;
                    counts[i] = 0;
                    System.out.println("[System] 제품 취소가 완료됬습니다.");
                    break;
                } else {
                    System.out.println("[Warning] 해당 이름의 물품은 존재하지 않습니다.");
                    break;
                }
            }
        }
    }

    /**
     * 3. 물건 넣기
     **/
    public static void prod_amount_add(Scanner s) {
        System.out.println("[System] 물건의 수량을 추가합니다.(입고)");
        System.out.print("[System] 수량을 추가할 제품명을 입력하세요 : ");
        String input = s.nextLine();  // 제품명 입력 받기
        int foundIdx = -1;  // 찾은 인덱스 초기화
        for (int i = 0; i < products.length; ++i) {
            if (input.equals(products[i])) {  // 입력한 제품명과 일치하는 제품을 찾으면
                foundIdx = i;  // 인덱스 저장
                break;  // 반복 중단
            }
        }

        if (foundIdx < 0) {  // 제품을 찾지 못한 경우
            System.out.println("[Warning] 입력한 제품명이 없습니다. 다시 확인하여 주세요.");
            return;
        }

        System.out.print("[System] 추가할 수량을 입력해주세요 : ");

        // 숫자가 아닌 경우, 에러 메시지를 출력해야 합니다.
        // TODO:

        String cnt = s.nextLine();  // 수량 입력 받기
        if (isValid(cnt)) {
            counts[foundIdx] += Integer.parseInt(cnt);  // 제품의 수량을 증가
            System.out.println("[Clear] 정상적으로 제품의 수량 추가가 완료되었습니다.");
        }
        System.out.println("[Warning] 잘못된 입력입니다.");
    }

    /**
     * 4. 출고
     **/
    public static void prod_amount_decrease(Scanner s) {
        System.out.println("[System] 제품의 출고를 진행합니다.");
        System.out.println("[System] 현재 등록된 물건 수량 ▼");
        prod_search();
        System.out.print("[System] 출고를 진행할 제품명을 입력하세요 : ");
        String prod_name = s.nextLine();
        boolean isValid = true;
        for (int i = 0; i < products.length; i++) {
            if (products[i].equals(prod_name)) {
                System.out.print("[System] 원하는 출고량을 입력하세요 : ");
                String num = s.nextLine();
                if (isValid(num)) {
                    if (counts[i] < Integer.parseInt(num)) { // 유효성 검증
                        System.out.println("[Error] 재고가 부족합니다.");
                    } else {
                        counts[i] -= Integer.parseInt(num);
                        System.out.println("[Clear] 출고가 완료되었습니다.");
                    }
                }

            }
        }
        System.out.println("[Warning] 입력한 제품명이 없습니다. 다시 확인하여 주세요.");
    }

    /**
     * 유효성 검증
     */
    private static boolean isValid(String num) {
        String digits = "1234567890";

        for (char ch : num.toCharArray()) {
            if (digits.indexOf(ch) == -1) {
                System.out.println("[Warning] 잘못된 입력 입니다.");
                return false;
            } else return true;
        }
        return false;
    }

    /**
     * 5. 재고 조회
     **/
    static void prod_search() {
        for (int i = 0; i < products.length; ++i) {
            System.out.println("> " + products[i] + " : " + counts[i] + "개");
        }
    }

    /**
     * 메인 메뉴
     **/
    public static void showMenu() {
        System.out.println("1. 물건 정보(제품명) 등록하기");
        System.out.println("2. 물건 정보(제품명) 등록 취소하기");
        System.out.println("3. 물건 넣기 (제품 입고)");
        System.out.println("4. 물건 빼기 (제품 출고)");
        System.out.println("5. 재고 조회");
        System.out.println("6. 프로그램 종료");
        System.out.println("-".repeat(30));  // '-' 문자를 30번 반복하여 줄을 그어 메뉴 구분을 명확하게 합니다.
    }

    /**
     * 입력
     **/
    public static int selectMenu(Scanner s) {
        System.out.print("[System] 원하는 기능의 번호를 입력하세요 : ");
        String select = s.nextLine();  // 사용자로부터 정수 입력을 받아 변수 select에 저장합니다.
        if (isValid(select)) {
            System.out.println("[Warning] 잘못된 입력입니다.");
            return Integer.parseInt(select);  // 입력 받은 정수를 반환합니다.
        }
        return -1;
    }

    /**
     * 엔트리 포인트
     **/
    public static void main(String[] args) {
        System.out.println("[Item_Storage]");
        System.out.printf("[System] 점장님 어서오세요.\n[System] 해당 프로그램의 기능입니다.\n");


        Scanner s = new Scanner(System.in);
        while (true) {
            showMenu();
            int menu = selectMenu(s);  // 메뉴 선택을 받습니다.
            if (menu == 6) {  // '프로그램 종료' 선택 시
                System.out.println("[System] 프로그램을 종료합니다.");
                break;  // 반복문을 종료하고 프로그램을 종료합니다.
            }
            // 선택된 메뉴에 따라 해당 기능을 실행합니다.
            switch (menu) {
                case 1:
                    prod_input(s);
                    break;
                case 2:
                    prod_remove(s);
                    break;
                case 3:
                    prod_amount_add(s);
                    break;
                case 4:
                    prod_amount_decrease(s);
                    break;
                case 5:
                    System.out.println("[System] 현재 등록된 물건 목록 ▼");
                    prod_search();
                    break;
                case -1:
                    break;
                default:
                    System.out.println("[System] 시스템 번호를 다시 확인하여 주세요.");
            }
        }
        s.close();  // Scanner 객체를 닫습니다.
    }
}
